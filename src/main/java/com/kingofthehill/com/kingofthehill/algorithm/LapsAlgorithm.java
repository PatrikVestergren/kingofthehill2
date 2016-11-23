package com.kingofthehill.com.kingofthehill.algorithm;

import com.kingofthehill.repository.model.BestEntity;
import com.kingofthehill.repository.model.LapEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by patrik.vestergren on 2016-10-07.
 */
public class LapsAlgorithm {

    public Optional<BestEntity> getBestLaps(List<LapEntity> laps) {

        return subLaps(laps).stream()
                .sorted()
                .findFirst();
    }

    private List<BestEntity> subLaps(List<LapEntity> input) {
        return rec(new ArrayList<>(input), new ArrayList<>(), new ArrayList<>());
    }

    private List<BestEntity> rec(List<LapEntity> input, List<BestEntity> res, List<LapEntity> acc) {
        if (input.isEmpty()) return res;

        LapEntity head = input.remove(0);
        acc.add(head);

        if (acc.size() == 3) {
            res.add(create(acc));
            return rec(input, res, new ArrayList<>());
        }

        return rec(input, res, acc);
    }

    private BestEntity create(final List<LapEntity> sub) {
        return new BestEntity.Builder()
                .setTransponder(sub.get(0).getTransponder())
                .setLaps(sub.stream().map(LapEntity::getId).collect(Collectors.toList()))
                .setNrOfLaps(sub.size())
                .setTotalTime(sub.stream().mapToLong(LapEntity::getLapTime).sum())
                .build();
    }
}
