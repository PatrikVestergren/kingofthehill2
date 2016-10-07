package com.kingofthehill.repository;

import com.kingofthehill.com.kingofthehill.algorithm.MinutesAlgorithm;
import com.kingofthehill.com.kingofthehill.algorithm.MinutesHolder;
import com.kingofthehill.repository.dao.KingDAO;
import com.kingofthehill.repository.model.BestMinute;
import com.kingofthehill.repository.model.Lap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by patrikv on 06/03/16.
 */

public class Repository {

    private final KingDAO dao;
    private final MinutesAlgorithm alg;

    public Repository(KingDAO dao) {
        this.dao = dao;
        this.alg = new MinutesAlgorithm(5);
    }

    public List<Lap> getTodaysLaps() {
        return dao.getTodaysLaps();
    }

    public List<Lap> allLaps() {
        return dao.allLaps();
    }

    public void insert(Lap lap) {
        dao.insertLap(lap);
    }

    public List<BestMinute> getTodaysBestMinutes() {
        List<Lap> laps = dao.getTodaysLaps();
        Map<Long, List<Lap>> grouped = laps.stream()
                .collect(Collectors.groupingBy(Lap::getTransponder, Collectors.toList()));

        List<BestMinute> res = new ArrayList<>();

        grouped.forEach((k, v) -> {
            Optional<MinutesHolder> m = alg.getBestMinutes(v);
            if (m.isPresent()) {
                res.add(new BestMinute(v.get(0).getDriver(), k, m.get().toString()));
            }
        });

        return res;
    }
}
