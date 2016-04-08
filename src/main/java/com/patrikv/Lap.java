package com.patrikv;

import java.time.LocalDateTime;

/**
 * Created by patrikv on 06/03/16.
 */
public class Lap {

    private final String driver;
    private final long transponder;
    private final long lapNr;
    private final long lapTime;
    private final LocalDateTime ts;

    public Lap() {
        this("", 0l, 0l, 0l, LocalDateTime.now());
    }

    public Lap(String driver, long transponder, long lapNr, long lapTime, LocalDateTime ts) {
        this.driver = driver;
        this.transponder = transponder;
        this.lapNr = lapNr;
        this.lapTime = lapTime;
        this.ts = ts;
    }

    public String getDriver() {
        return driver;
    }

    public long getTransponder() {
        return transponder;
    }

    public long getLapNr() {
        return lapNr;
    }

    public long getLapTime() {
        return lapTime;
    }

    public LocalDateTime getTs() {
        return ts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lap lap = (Lap) o;

        if (transponder != lap.transponder) return false;
        if (lapNr != lap.lapNr) return false;
        if (lapTime != lap.lapTime) return false;
        if (driver != null ? !driver.equals(lap.driver) : lap.driver != null) return false;
        return ts != null ? ts.equals(lap.ts) : lap.ts == null;

    }

    @Override
    public int hashCode() {
        int result = driver != null ? driver.hashCode() : 0;
        result = 31 * result + (int) (transponder ^ (transponder >>> 32));
        result = 31 * result + (int) (lapNr ^ (lapNr >>> 32));
        result = 31 * result + (int) (lapTime ^ (lapTime >>> 32));
        result = 31 * result + (ts != null ? ts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Lap{" +
                "driver='" + driver + '\'' +
                ", transponder=" + transponder +
                ", lapNr=" + lapNr +
                ", lapTime=" + lapTime +
                ", ts=" + ts +
                '}';
    }
}
