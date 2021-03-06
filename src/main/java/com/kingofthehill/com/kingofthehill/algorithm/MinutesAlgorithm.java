package com.kingofthehill.com.kingofthehill.algorithm;

import com.kingofthehill.repository.model.BestEntity;
import com.kingofthehill.repository.model.LapEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MinutesAlgorithm {

    private final int minutes;

    public MinutesAlgorithm(int nrOfMinutes) {
        this.minutes = nrOfMinutes * 60 * 1000;
    }

    public static List<BestEntity> getTopN(final int n, final List<BestEntity> laps) {
        return laps.stream()
                .sorted()
                .limit(n)
                .collect(Collectors.toList());
    }

    public Optional<BestEntity> getBestMinutes(final List<LapEntity> input) {
        return subLaps(input).stream()
                .sorted()
                .findFirst();
    }

    protected List<BestEntity> subLaps(final List<LapEntity> input) {
        List<LapEntity> laps = new ArrayList<>(input);
        List<BestEntity> result = new ArrayList<>();

        while (!laps.isEmpty()) {
            List<LapEntity> sub = rec(new ArrayList<>(laps), 0, new ArrayList<>());
            if (!sub.isEmpty()) result.add(create(sub));
            laps.remove(0);
        }
        return result;
    }

    private BestEntity create(final List<LapEntity> sub) {
        return new BestEntity.Builder()
                .setTransponder(sub.get(0).getTransponder())
                .setLaps(sub.stream().map(LapEntity::getId).collect(Collectors.toList()))
                .setNrOfLaps(sub.size())
                .setTotalTime(sub.stream().mapToLong(LapEntity::getLapTime).sum())
                .build();
    }

    private List<LapEntity> rec(final List<LapEntity> laps, final long acc, final List<LapEntity> result) {
        if (laps.isEmpty()) {
            if (acc < minutes) return new ArrayList<>();
            return result;
        }
        LapEntity head = laps.remove(0);
        result.add(head);
        if (acc + head.getLapTime() >= minutes) return result;
        return rec(laps, acc + head.getLapTime(), result);
    }
}
