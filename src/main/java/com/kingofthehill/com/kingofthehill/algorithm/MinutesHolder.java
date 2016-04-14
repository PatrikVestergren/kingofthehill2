package com.kingofthehill.com.kingofthehill.algorithm;

/**
 * Created by patrikv on 08/04/16.
 */
import com.kingofthehill.Lap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MinutesHolder implements Comparable<MinutesHolder> {

    private final int nrOfLaps;
    private final long totalTime;
    private final SimpleDateFormat lapFormat;
    private final List<Lap> laps;

    public MinutesHolder(List<Lap> laps) {
        this.laps = new ArrayList<>(laps);
        lapFormat = new SimpleDateFormat("mm:ss.SSS");
        nrOfLaps = laps.size();
        totalTime = laps.stream()
                .mapToLong(Lap::getLapTime)
                .sum();
    }

    public int getNrOfLaps() {
        return nrOfLaps;
    }

    public long getTotalTime() {
        return totalTime;
    }

    @Override
    public int compareTo(MinutesHolder o) {
        if (nrOfLaps == o.getNrOfLaps())
            return Long.compare(totalTime, o.getTotalTime());
        return Long.compare(o.getNrOfLaps(), nrOfLaps);
    }

    @Override
    public String toString() {
        return nrOfLaps + "/" + lapFormat.format(totalTime);
    }
}
