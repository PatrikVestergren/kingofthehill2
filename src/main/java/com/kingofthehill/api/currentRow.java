package com.kingofthehill.api;


import java.time.LocalDateTime;

public class currentRow {

    private final int lapNr;
    private final long transponder;
    private final String driver;
    private final long fastLap;
    private final int minutesNrOfLaps;
    private final long minutesTotalTime;
    private final LocalDateTime lastPass;

    public currentRow(int lapNr, long transponder, String driver, long fastLap, int minutesNrOfLaps, long minutesTotalTime, LocalDateTime lastPass) {
        this.lapNr = lapNr;
        this.transponder = transponder;
        this.driver = driver;
        this.fastLap = fastLap;
        this.minutesNrOfLaps = minutesNrOfLaps;
        this.minutesTotalTime = minutesTotalTime;
        this.lastPass = lastPass;
    }

    public int getLapNr() {
        return lapNr;
    }

    public long getTransponder() {
        return transponder;
    }

    public String getDriver() {
        return driver;
    }

    public long getFastLap() {
        return fastLap;
    }

    public int getMinutesNrOfLaps() {
        return minutesNrOfLaps;
    }

    public long getMinutesTotalTime() {
        return minutesTotalTime;
    }

    public LocalDateTime getLastPass() {
        return lastPass;
    }
}
