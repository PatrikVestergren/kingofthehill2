package com.kingofthehill.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kingofthehill.WebsocketKing;
import com.kingofthehill.api.CurrentLap;
import com.kingofthehill.api.CurrentRacer;
import com.kingofthehill.com.kingofthehill.algorithm.LapsAlgorithm;
import com.kingofthehill.com.kingofthehill.algorithm.LapsHolder;
import com.kingofthehill.com.kingofthehill.algorithm.MinutesAlgorithm;
import com.kingofthehill.repository.model.BestEntity;
import com.kingofthehill.repository.dao.KingDAO;
import com.kingofthehill.repository.model.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.kingofthehill.api.CurrentLap.getBuilder;
import static java.util.Collections.emptyList;
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
        Optional<BestEntity> best = minutesAlgorithm.getBestMinutes(laps);
        Optional<BestEntity> bestLaps = lapsAlgorithm.getBestLaps(laps);

        handleBestMinutes(transponder, best);
        handleBestLaps(transponder, bestLaps);

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

    private void handleBestLaps(long transponder, Optional<BestEntity> best) {
        if (best.isPresent()) {
            BestEntity todays = dao.getTodaysBestLapsFor(transponder);
            BestEntity current = best.get();
            createOrUpdateBestLaps(todays, current);
            Optional<BestEntity> old = dao.getBestLapsFor(transponder).stream().sorted().findFirst();
            if (old.isPresent()) {
                createOrUpdateBestLaps(old.get(), current);
            }
        }
    }

    private void createOrUpdateBestLaps(BestEntity todays, BestEntity current) {
        if (todays == null) {
            dao.insertBestLaps(current);
        } else if (current.isBetterThan(todays)) {
            BestEntity clone = BestEntity.getCloneBuilder(todays)
                    .setLaps(current.getLaps())
                    .setNrOfLaps(current.getNrOfLaps())
                    .setTotalTime(current.getTotalTime())
                    .setTime(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            dao.updateBestLaps(clone);
        }
    }

    private void handleBestMinutes(long transponder, Optional<BestEntity> best) {
        if (best.isPresent()) {
            BestEntity todays = dao.getTodaysBestMinutesFor(transponder);
            BestEntity current = best.get();
            createOrUpdateBestMinutes(todays, current);
            Optional<BestEntity> old = dao.getBestMinutesFor(transponder).stream().sorted().findFirst();
            if (old.isPresent()) {
                createOrUpdateBestMinutes(old.get(), current);
            }
        }
    }

    private void createOrUpdateBestMinutes(BestEntity todays, BestEntity current) {
        if (todays == null) {
            dao.insertBestMinutes(current);
        } else if (current.isBetterThan(todays)) {
            BestEntity clone = BestEntity.getCloneBuilder(todays)
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
            Optional<BestEntity> m = minutesAlgorithm.getBestMinutes(v);
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
                Optional<BestEntity> l = lapsAlgorithm.getBestLaps(v);
                if (l.isPresent()) {
                    c.setnLaps(l.get().toString());
                } else {
                    c.setnLaps("-");
                }
                Optional<BestEntity> m = minutesAlgorithm.getBestMinutes(v);
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

    public List<CurrentLap> getTodaysFor(long transponder) {
        List<LapEntity> laps = dao.getTodaysLapsFor(transponder);

        if (laps.isEmpty()) {
            return emptyList();
        }

        String driver = dao.getNameFromTransponder(transponder);
        BestEntity daoBestMinutes = dao.getTodaysBestMinutesFor(transponder);
        List<Integer> bestMinutes = daoBestMinutes == null ? emptyList() : daoBestMinutes.getLaps();
        BestEntity daoBestLaps = dao.getTodaysBestLapsFor(transponder);
        List<Integer> bestLaps = daoBestLaps == null ? emptyList() : daoBestLaps.getLaps();
        long fastLap = laps.stream().mapToLong(LapEntity::getLapTime).min().getAsLong();
        Integer fastLapId = laps.stream().filter(l -> l.getLapTime() == fastLap).findFirst().get().getId();

        List<CurrentLap> result = new ArrayList<>();

        laps.forEach(lap -> {
            String cssClass = getCssClass(lap.getId(), bestMinutes, bestLaps, fastLapId);
            CurrentLap current = CurrentLap.getBuilder()
                    .setDriver(driver)
                    .setTransponder(lap.getTransponder())
                    .setLapNr(lap.getLapNr())
                    .setLapTime(lap.getLapTime())
                    .setTime(lap.getTime())
                    .setCssClass(cssClass)
                    .build();
            result.add(current);
        });

        return result;
    }

    private String getCssClass(Integer lapId, List<Integer> bestMinutes, List<Integer> bestLaps, Integer fastLapId) {
        System.out.println("Repository.getCssClass " + bestLaps + " lapId=" + lapId);
        if (lapId.equals(fastLapId))
            return "fastlap";
        if (bestLaps.stream().filter(id -> id.equals(lapId)).findFirst().isPresent())
            return "bestlaps";
        if (bestMinutes.stream().filter(id -> id.equals(lapId)).findFirst().isPresent())
            return "bestminutes";
        return "regular";
    }

    public List<BestEntity> getBestMinutes() {
        return dao.getBestMinutes();
    }

    public void reCalculate() {
        Map<Long, List<LapEntity>> transponderLaps = dao.allLaps().stream()
                .collect(groupingBy(LapEntity::getTransponder, toList()));

        transponderLaps.forEach((transponder, laps) -> {
            Optional<BestEntity> bestM = minutesAlgorithm.getBestMinutes(laps);
            if (bestM.isPresent()) {
                Integer id = dao.insertBestMinutes(bestM.get());
            } // Do the same for best three laps
        });
    }
}
