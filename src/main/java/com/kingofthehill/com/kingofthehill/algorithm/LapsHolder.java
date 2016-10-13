package com.kingofthehill.com.kingofthehill.algorithm;

import com.kingofthehill.repository.model.LapEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LapsHolder implements Comparable<LapsHolder> {

    private final long totalTime;
    private final SimpleDateFormat lapFormat;
    private final List<LapEntity> laps;

    public LapsHolder(List<LapEntity> laps) {
        this.totalTime = laps.stream().mapToLong(LapEntity::getLapTime).sum();
        lapFormat = new SimpleDateFormat("mm:ss.SSS");
        this.laps = new ArrayList<>(laps);
    }

    public long getTotalTime() {
        return totalTime;
    }

    public List<LapEntity> getLaps() {
        return laps;
    }

    @Override
    public int compareTo(LapsHolder o) {
        return Long.compare(totalTime, o.getTotalTime());
    }

    @Override
    public String toString() {
        return lapFormat.format(totalTime);
    }
}
