package com.kingofthehill.repository.model;

import java.util.List;
import java.util.stream.Collectors;

public class BestMinute implements Comparable<BestMinute> {

    private final long transponder;
    private final int nrOfLaps;
    private final long totalTime;
    private final List<Integer> laps;

    public BestMinute(long transponder, List<LapEntity> laps) {
        this.transponder = transponder;
        this.nrOfLaps = laps.size();
        this.totalTime = laps.stream()
                .mapToLong(LapEntity::getLapTime)
                .sum();
        this.laps = laps.stream().map(LapEntity::getId).collect(Collectors.toList());
    }

    public long getTransponder() {
        return transponder;
    }

    public int getNrOfLaps() {
        return nrOfLaps;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public List<Integer> getLaps() {
        return laps;
    }

    @Override
    public int compareTo(BestMinute o) {
        if (nrOfLaps == o.getNrOfLaps())
            return Long.compare(totalTime, o.getTotalTime());
        return Long.compare(o.getNrOfLaps(), nrOfLaps);
    }
}
