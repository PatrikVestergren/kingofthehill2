package com.kingofthehill.repository.model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by patrikv on 06/03/16.
 */
public class Lap {

    private Integer id;
    private final String driver;
    private final long transponder;
    private final long lapNr;
    private final long lapTime;
    private LocalDateTime time;


    public Lap() {
        this(null, "", 0l, 0l, 0l, null);
    }

    public Lap(Integer id, String driver, long transponder, long lapNr, long lapTime, Timestamp timestamp) {
        this.id = id;
        this.driver = driver;
        this.transponder = transponder;
        this.lapNr = lapNr;
        this.lapTime = lapTime;
        this.time = toLocalDateTime(timestamp);
    }

    public Lap(String name, long transponder, long lapNr, long lapTime) {
        this(null, name, transponder, lapNr, lapTime, null);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) return null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(timestamp);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateStr, dtf);
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

    public Integer getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Lap{" +
                "id=" + id +
                ", driver='" + driver + '\'' +
                ", transponder=" + transponder +
                ", lapNr=" + lapNr +
                ", lapTime=" + lapTime +
                ", time=" + time +
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
        if (id != null ? !id.equals(lap.id) : lap.id != null) return false;
        if (driver != null ? !driver.equals(lap.driver) : lap.driver != null) return false;
        return time != null ? time.equals(lap.time) : lap.time == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (driver != null ? driver.hashCode() : 0);
        result = 31 * result + (int) (transponder ^ (transponder >>> 32));
        result = 31 * result + (int) (lapNr ^ (lapNr >>> 32));
        result = 31 * result + (int) (lapTime ^ (lapTime >>> 32));
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
