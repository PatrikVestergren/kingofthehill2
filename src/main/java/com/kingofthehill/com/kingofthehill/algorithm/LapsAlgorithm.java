package com.kingofthehill.com.kingofthehill.algorithm;

import com.kingofthehill.repository.model.LapEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by patrik.vestergren on 2016-10-07.
 */
public class LapsAlgorithm {

    public Optional<LapsHolder> getBestLaps(List<LapEntity> laps) {

        return subLaps(laps).stream()
                .sorted()
                .findFirst();
    }

    private List<LapsHolder> subLaps(List<LapEntity> input) {
        return rec(new ArrayList<>(input), new ArrayList<>(), new ArrayList<>());
    }

    private List<LapsHolder> rec(List<LapEntity> input, List<LapsHolder> res, List<LapEntity> acc) {
        if (input.isEmpty()) return res;

        LapEntity head = input.remove(0);
        acc.add(head);

        if (acc.size() == 3) {
            res.add(new LapsHolder(acc));
            return rec(input, res, new ArrayList<>());
        }
        
        return rec(input, res, acc);
    }
}
