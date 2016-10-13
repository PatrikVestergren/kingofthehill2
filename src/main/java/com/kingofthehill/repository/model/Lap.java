package com.kingofthehill.repository.model;

/**
 * Incomming Lap
 */
public class Lap {

    private final String driver;
    private final long transponder;
    private final int lapNr;
    private final long lapTime;


    public Lap() {
        this("", 0l, 0, 0l);
    }

    public Lap(String driver, long transponder, int lapNr, long lapTime) {
        this.driver = driver;
        this.transponder = transponder;
        this.lapNr = lapNr;
        this.lapTime = lapTime;
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

    public long getLapTime() {
        return lapTime;
    }

    @Override
    public String toString() {
        return "Lap{" +
                "driver='" + driver + '\'' +
                ", transponder=" + transponder +
                ", lapNr=" + lapNr +
                ", lapTime=" + lapTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lap lap = (Lap) o;

        if (transponder != lap.transponder) return false;
        if (lapNr != lap.lapNr) return false;
        if (lapTime != lap.lapTime) return false;
        return driver != null ? driver.equals(lap.driver) : lap.driver == null;

    }

    @Override
    public int hashCode() {
        int result = driver != null ? driver.hashCode() : 0;
        result = 31 * result + (int) (transponder ^ (transponder >>> 32));
        result = 31 * result + lapNr;
        result = 31 * result + (int) (lapTime ^ (lapTime >>> 32));
        return result;
    }
}
