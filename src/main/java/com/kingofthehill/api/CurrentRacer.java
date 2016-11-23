package com.kingofthehill.api;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class CurrentRacer {

    private final String driver;
    private final long transponder;
    private final int nrOfLaps;
    private final String lapTime;
    private final String fastLap;
    private final LocalDate lastPass;
    private final String nLaps;
    private final String nMinutes;
    private final SimpleDateFormat lapFormat = new SimpleDateFormat("mm:ss.SSS");

    private CurrentRacer(CurrentRowBuilder builder) {
        this.driver = builder.driver;
        this.transponder = builder.transponder;
        this.nrOfLaps = builder.nrOfLaps;
        this.lapTime = lapFormat.format(builder.lapTime);
        this.fastLap = lapFormat.format(builder.fastLap);
        this.lastPass = builder.lastPass;
        this.nLaps = builder.nLaps;
        this.nMinutes = builder.nMinutes;
    }

    public String getDriver() {
        return driver;
    }

    public long getTransponder() {
        return transponder;
    }

    public int getNrOfLaps() {
        return nrOfLaps;
    }

    public String getLapTime() {
        return lapTime;
    }

    public String getFastLap() {
        return fastLap;
    }

    public LocalDate getLastPass() {
        return lastPass;
    }

    public String getnLaps() {
        return nLaps;
    }

    public String getnMinutes() {
        return nMinutes;
    }

    public static CurrentRowBuilder getBuilder() {
        return new CurrentRowBuilder();
    }

    public static class CurrentRowBuilder {
        private String driver;
        private long transponder;
        private int nrOfLaps;
        private long lapTime;
        private long fastLap;
        private LocalDate lastPass;
        private String nLaps;
        private String nMinutes;

        public CurrentRowBuilder setDriver(String driver) {
            this.driver = driver;
            return this;
        }

        public CurrentRowBuilder setTransponder(long transponder) {
            this.transponder = transponder;
            return this;
        }

        public CurrentRowBuilder setNrOfLaps(int nrOfLaps) {
            this.nrOfLaps = nrOfLaps;
            return this;
        }

        public CurrentRowBuilder setLapTime(long lapTime) {
            this.lapTime = lapTime;
            return this;
        }

        public CurrentRowBuilder setFastLap(long fastLap) {
            this.fastLap = fastLap;
            return this;
        }

        public CurrentRowBuilder setLastPass(LocalDate lastPass) {
            this.lastPass = lastPass;
            return this;
        }

        public CurrentRowBuilder setnLaps(String nLaps) {
            this.nLaps = nLaps;
            return this;
        }

        public CurrentRowBuilder setnMinutes(String nMinutes) {
            this.nMinutes = nMinutes;
            return this;
        }

        public CurrentRacer build() {
            return new CurrentRacer(this);
        }
    }

}
