package com.kingofthehill.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kingofthehill.WebsocketKing;
import com.kingofthehill.com.kingofthehill.algorithm.LapsAlgorithm;
import com.kingofthehill.com.kingofthehill.algorithm.LapsHolder;
import com.kingofthehill.com.kingofthehill.algorithm.MinutesAlgorithm;
import com.kingofthehill.repository.model.MinutesEntity;
import com.kingofthehill.repository.dao.KingDAO;
import com.kingofthehill.repository.model.*;

import java.sql.Timestamp;
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

    public void insert(Lap lap) throws JsonProcessingException {
        long transponder = lap.getTransponder();
        dao.insertLap(transponder, lap.getLapNr(), lap.getLapTime());

        updateName(lap);

        List<LapEntity> laps = dao.getTodaysLapsFor(transponder);
        Optional<MinutesEntity> best = minutesAlgorithm.getBestMinutes(laps);
        Optional<LapsHolder> bestLaps = lapsAlgorithm.getBestLaps(laps);

        if (best.isPresent()) {
            MinutesEntity todays = dao.getTodaysBestMinutesFor(transponder);
            MinutesEntity current = best.get();
            createOrUpdate(todays, current);
            Optional<MinutesEntity> old = dao.getBestMinutesFor(transponder).stream().sorted().findFirst();
            if (old.isPresent()) {
                createOrUpdate(old.get(), current);
            }
        }

        CurrentRacer current = CurrentRacer.getBuilder()
                .setDriver(lap.getDriver())
                .setTransponder(lap.getTransponder())
                .setLapTime(lap.getLapTime())
                .setFastLap(laps.stream().mapToLong(x -> x.getLapTime()).min().getAsLong())
                .setNrOfLaps(lap.getLapNr() > laps.size() ? lap.getLapNr() : laps.size())
                .setnLaps(bestLaps.isPresent() ? bestLaps.get().toString() : "-")
                .setnMinutes(best.isPresent() ? best.get().toString() : "-")
                .build();

        WebsocketKing.sendMessage(current);
    }

    private void createOrUpdate(MinutesEntity todays, MinutesEntity current) {
        if (todays == null) {
            dao.insertBestMinutes(current);
        } else if (current.isBetterThan(todays)) {
            MinutesEntity clone = MinutesEntity.getCloneBuilder(todays)
                    .setLaps(current.getLaps())
                    .setNrOfLaps(current.getNrOfLaps())
                    .setTotalTime(current.getTotalTime())
                    .setTime(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            dao.updateBestMinutes(clone);
        }
    }

    private void updateName(Lap lap) {
        String name = dao.getNameFromTransponder(lap.getTransponder());
        if (name == null) {
            dao.insertTransponder(lap.getTransponder(), lap.getDriver());
        } else if (!name.equals(lap.getDriver())) {
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

    public List<CurrentRacer> getCurrents() {

        List<LapEntity> laps = dao.getTodaysLaps();
        Map<Long, List<LapEntity>> grouped = laps.stream()
                .collect(groupingBy(LapEntity::getTransponder, toList()));

        List<CurrentRacer> res = new ArrayList<>();

        grouped.forEach((k, v) -> {
            if (!v.isEmpty()) {
                LapEntity head = v.get(0);
                CurrentRacer.CurrentRowBuilder c = CurrentRacer.getBuilder()
                        .setDriver(dao.getNameFromTransponder(head.getTransponder()))
                        .setTransponder(head.getTransponder())
                        .setLapTime(head.getLapTime())
                        .setFastLap(v.stream().mapToLong(x -> x.getLapTime()).min().getAsLong())
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
            } // Do the same for best three laps
        });
    }
}
