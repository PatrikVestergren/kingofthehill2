package com.kingofthehill.repository.model;

/**
 * Created by patrik.vestergren on 2016-10-07.
 */
public class CurrentRow {

    private final String driver;
    private final long transponder;
    private final int nrOfLaps;
    private final String nLaps;
    private final String nMinutes;

    private CurrentRow(CurrentRowBuilder builder) {
        this.driver = builder.driver;
        this.transponder = builder.transponder;
        this.nrOfLaps = builder.nrOfLaps;
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

        public CurrentRowBuilder setnLaps(String nLaps) {
            this.nLaps = nLaps;
            return this;
        }

        public CurrentRowBuilder setnMinutes(String nMinutes) {
            this.nMinutes = nMinutes;
            return this;
        }

        public CurrentRow build() {
            return new CurrentRow(this);
        }
    }

}
