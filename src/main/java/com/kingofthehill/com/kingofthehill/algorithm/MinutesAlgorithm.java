package com.kingofthehill.com.kingofthehill.algorithm;

/**
 * Created by patrikv on 08/04/16.
 */
import com.kingofthehill.repository.model.Lap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MinutesAlgorithm {

    private final int minutes;

    public MinutesAlgorithm(int nrOfMinutes) {
        this.minutes = nrOfMinutes * 60 * 1000;
    }

    public static List<MinutesHolder> getTopN(final int n, final List<MinutesHolder> laps) {
        return laps.stream()
                .sorted()
                .limit(n)
                .collect(Collectors.toList());
    }

    public Optional<MinutesHolder> getBestMinutes(final List<Lap> input) {
        return subLaps(input).stream()
                .sorted()
                .findFirst();
    }

    protected List<MinutesHolder> subLaps(final List<Lap> input) {
        List<Lap> laps = new ArrayList<>(input);
        List<MinutesHolder> result = new ArrayList<>();

        while (!laps.isEmpty()) {
            List<Lap> sub = rec(new ArrayList<>(laps), 0, new ArrayList<>());
            if (!sub.isEmpty()) result.add(new MinutesHolder(sub));
            laps.remove(0);
        }
        return result;
    }

    private List<Lap> rec(List<Lap> laps, long acc, List<Lap> result) {
        if (laps.isEmpty()) {
            if (acc < minutes) return new ArrayList<>();
            return result;
        }
        Lap head = laps.remove(0);
        result.add(head);
        if (acc + head.getLapTime() >= minutes) return result;
        return rec(laps, acc + head.getLapTime(), result);
    }
}
