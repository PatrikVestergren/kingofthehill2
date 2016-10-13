package com.kingofthehill.repository.model;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LapEntity {

    private final Integer id;
    private final long transponder;
    private final int lapNr;
    private final long lapTime;
    private final LocalDateTime time;

    public LapEntity(Integer id, long transponder, int lapNr, long lapTime, Timestamp timestamp) {
        this.id = id;
        this.transponder = transponder;
        this.lapNr = lapNr;
        this.lapTime = lapTime;
        this.time = toLocalDateTime(timestamp);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) return null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(timestamp);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateStr, dtf);
    }

    public Integer getId() {
        return id;
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

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "LapEntity{" +
                "id=" + id +
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

        LapEntity lapEntity = (LapEntity) o;

        if (transponder != lapEntity.transponder) return false;
        if (lapNr != lapEntity.lapNr) return false;
        if (lapTime != lapEntity.lapTime) return false;
        if (id != null ? !id.equals(lapEntity.id) : lapEntity.id != null) return false;
        return time != null ? time.equals(lapEntity.time) : lapEntity.time == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (transponder ^ (transponder >>> 32));
        result = 31 * result + lapNr;
        result = 31 * result + (int) (lapTime ^ (lapTime >>> 32));
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
