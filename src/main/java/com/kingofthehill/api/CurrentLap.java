package com.kingofthehill.api;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * Created by patrik on 2016-11-23.
 */
public class CurrentLap {

    private final SimpleDateFormat lapFormat = new SimpleDateFormat("mm:ss.SSS");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final String driver;
    private final long transponder;
    private final int lapNr;
    private final String cssClass;
    private final String lapTime;
    private final LocalDateTime time;

    private CurrentLap(CurrentLapBuilder builder) {
        this.driver = builder.driver;
        this.transponder = builder.transponder;
        this.lapNr = builder.lapNr;
        this.cssClass = builder.cssClass;
        this.lapTime = lapFormat.format(builder.lapTime);
        this.time = builder.time;
    }

    public String getDriver() {
        return driver;
    }

    public long getTransponder() {
        return transponder;
    }

    public int getLapNr() {
        return lapNr;
    }

    public String getCssClass() {
        return cssClass;
    }

    public String getLapTime() {
        return lapTime;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public static CurrentLapBuilder getBuilder() {
        return new CurrentLapBuilder();
    }

    public static class CurrentLapBuilder {
        private String driver;
        private long transponder;
        private int lapNr;
        private long lapTime;
        private String cssClass;
        private LocalDateTime time;

        public CurrentLapBuilder setTransponder(long transponder) {
            this.transponder = transponder;
            return this;
        }

        public CurrentLapBuilder setLapNr(int lapNr) {
            this.lapNr = lapNr;
            return this;
        }

        public CurrentLapBuilder setLapTime(long lapTime) {
            this.lapTime = lapTime;
            return this;
        }

        public CurrentLapBuilder setCssClass(String cssClass) {
            this.cssClass = cssClass;
            return this;
        }

        public CurrentLapBuilder setDriver(String driver) {
            this.driver = driver;
            return this;
        }

        public CurrentLapBuilder setTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public CurrentLap build() {
            return new CurrentLap(this);
        }
    }
}
