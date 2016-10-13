package com.kingofthehill.repository;

import com.kingofthehill.WebsocketKing;
import com.kingofthehill.com.kingofthehill.algorithm.LapsAlgorithm;
import com.kingofthehill.com.kingofthehill.algorithm.LapsHolder;
import com.kingofthehill.com.kingofthehill.algorithm.MinutesAlgorithm;
import com.kingofthehill.repository.model.MinutesEntity;
import com.kingofthehill.repository.dao.KingDAO;
import com.kingofthehill.repository.model.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by patrikv on 06/03/16.
 */

public class Repository {

    private final KingDAO dao;
    private final MinutesAlgorithm minutesAlgorithm;
    private final LapsAlgorithm lapsAlgorithm;

    public Repository(KingDAO dao) {
        this.dao = dao;
        this.minutesAlgorithm = new MinutesAlgorithm(5);
        this.lapsAlgorithm = new LapsAlgorithm();
    }

    public List<LapEntity> getTodaysLaps() {
        return dao.getTodaysLaps();
    }

    public List<LapEntity> allLaps() {
        return dao.allLaps();
    }

    public void insert(Lap lap) {
        long transponder = lap.getTransponder();
        dao.insertLap(transponder, lap.getLapNr(), lap.getLapTime());

        updateName(lap);

        List<LapEntity> laps = dao.getTodaysLapsFor(transponder);
        Optional<MinutesEntity> best = minutesAlgorithm.getBestMinutes(laps);
        System.out.println("best is: " + best);
        if (best.isPresent()) {
            System.out.println("best is present");
            MinutesEntity todays = dao.getTodaysBestMinutesFor(transponder);
            MinutesEntity current = best.get();
            createOrUpdate(todays, current);
            Optional<MinutesEntity> old = dao.getBestMinutesFor(transponder).stream().sorted().findFirst();
            if (old.isPresent()) {
                createOrUpdate(old.get(), current);
            }
        }

        WebsocketKing.sendMessage("Got lap " + lap.getLapNr());
    }

    private void createOrUpdate(MinutesEntity todays, MinutesEntity current) {
        if (todays == null) {
            System.out.println("GONNA INSERT");
            dao.insertBestMinutes(current);
        } else if (current.isBetterThan(todays)) {
            MinutesEntity clone = MinutesEntity.getCloneBuilder(todays)
                    .setLaps(current.getLaps())
                    .setNrOfLaps(current.getNrOfLaps())
                    .setTotalTime(current.getTotalTime())
                    .setTime(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            System.out.println("GONNA UPDATE");
            dao.updateBestMinutes(clone);
        }
    }

    private void updateName(Lap lap) {
        Transponder t = dao.getTransponder(lap.getTransponder());
        if (t == null) {
            dao.insertTransponder(lap.getTransponder(), lap.getDriver());
        } else if (!t.getDriver().equals(lap.getDriver())) {
            dao.updateTransponderName(lap.getTransponder(), lap.getDriver());
        }
    }

    public List<BestMinute> getTodaysBestMinutes() {
        List<LapEntity> laps = dao.getTodaysLaps();
        Map<Long, List<LapEntity>> grouped = laps.stream()
                .collect(groupingBy(LapEntity::getTransponder, toList()));

        List<BestMinute> res = new ArrayList<>();

        grouped.forEach((k, v) -> {
            Optional<MinutesEntity> m = minutesAlgorithm.getBestMinutes(v);
            if (m.isPresent()) {
                res.add(new BestMinute(v.get(0).getTransponder(), v));
            }
        });

        return res;
    }

    public List<CurrentRow> getCurrents() {
        List<LapEntity> laps = dao.getTodaysLaps();
        Map<Long, List<LapEntity>> grouped = laps.stream()
                .collect(groupingBy(LapEntity::getTransponder, toList()));

        List<CurrentRow> res = new ArrayList<>();

        grouped.forEach((k, v) -> {
            if (!v.isEmpty()) {
                LapEntity head = v.get(0);
                CurrentRow.CurrentRowBuilder c = CurrentRow.getBuilder()
                        //.setDriver(head.getDriver())
                        .setTransponder(head.getTransponder())
                        .setNrOfLaps(v.size());
                Optional<LapsHolder> l = lapsAlgorithm.getBestLaps(v);
                if (l.isPresent()) {
                    c.setnLaps(l.get().toString());
                } else {
                    c.setnLaps("-");
                }
                Optional<MinutesEntity> m = minutesAlgorithm.getBestMinutes(v);
                if (m.isPresent()) {
                    c.setnMinutes(m.get().toString());
                } else {
                    c.setnMinutes("-");
                }
                res.add(c.build());
            }
        });

        return res;
    }

    public List<MinutesEntity> getBestMinutes() {
        return dao.getBestMinutes();
    }

    public void reCalculate() {
        Map<Long, List<LapEntity>> transponderLaps = dao.allLaps().stream()
                .collect(groupingBy(LapEntity::getTransponder, toList()));

        transponderLaps.forEach((transponder, laps) -> {
            Optional<MinutesEntity> bestM = minutesAlgorithm.getBestMinutes(laps);
            if (bestM.isPresent()) {
                Integer id = dao.insertBestMinutes(bestM.get());
                System.out.println("Id: " + id);
            } // Do the same for best three laps
        });
    }
}
