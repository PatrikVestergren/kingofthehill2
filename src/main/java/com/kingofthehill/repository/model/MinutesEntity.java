package com.kingofthehill.repository.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MinutesEntity implements Comparable<MinutesEntity> {

    private final int id;
    private final long transponder;
    private final int nrOfLaps;
    private final long totalTime;
    private final SimpleDateFormat lapFormat = new SimpleDateFormat("mm:ss.SSS");
    private final List<Integer> laps;
    private final Timestamp time;

  /*  public MinutesEntity(int id, long transponder, int nrOfLaps, long totalTime, List<Integer> laps, Timestamp timestamp) {
        this.id = id;
        this.transponder = transponder;
        this.nrOfLaps = nrOfLaps;
        this.totalTime = totalTime;
        this.laps = laps;
        this.time = toLocalDate(timestamp);
    }

    public MinutesEntity(int bestminutesid, long transponder, int nroflaps, long totaltime, Array lapids, Timestamp modtime) throws SQLException {
        this(bestminutesid, transponder, nroflaps, totaltime, Arrays.asList((Integer[]) lapids.getArray()), modtime);
    }*/

    public MinutesEntity(Builder builder) {
        this.id = builder.id;
        this.transponder = builder.transponder;
        this.nrOfLaps = builder.nrOfLaps;
        this.totalTime = builder.totalTime;
        this.laps = builder.laps;
        this.time = builder.time;
    }

    public static Builder getCloneBuilder(MinutesEntity entity) {
        return new Builder()
                .setId(entity.getId())
                .setTransponder(entity.getTransponder())
                .setNrOfLaps(entity.getNrOfLaps())
                .setTotalTime(entity.getTotalTime())
                .setLaps(entity.getLaps())
                .setTime(entity.getTime());
    }


    public int getId() {
        return id;
    }

    public long getTransponder() {
        return transponder;
    }

    public int getNrOfLaps() {
        return nrOfLaps;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public List<Integer> getLaps() {
        return laps;
    }

    public Timestamp getTime() {
        return time;
    }

    public LocalDate getLocalDate() {
        return toLocalDate(getTime());
    }

    private LocalDate toLocalDate(Timestamp timestamp) {
        if (timestamp == null) return null;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(timestamp);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, dtf);
    }

    @Override
    public int compareTo(MinutesEntity o) {
        if (nrOfLaps == o.getNrOfLaps())
            return Long.compare(totalTime, o.getTotalTime());
        return Long.compare(o.getNrOfLaps(), nrOfLaps);
    }

    @Override
    public String toString() {
        return nrOfLaps + "/" + lapFormat.format(totalTime);
    }

    public boolean isBetterThan(MinutesEntity o) {
        if (nrOfLaps == o.getNrOfLaps())
            return totalTime < o.getTotalTime();
        return nrOfLaps > o.getNrOfLaps();
    }

    public static class Builder {
        private int id;
        private long transponder;
        private int nrOfLaps;
        private long totalTime;
        private List<Integer> laps;
        private Timestamp time;

        public MinutesEntity build() {
            return new MinutesEntity(this);
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTransponder(long transponder) {
            this.transponder = transponder;
            return this;
        }

        public Builder setNrOfLaps(int nrOfLaps) {
            this.nrOfLaps = nrOfLaps;
            return this;
        }

        public Builder setTotalTime(long totalTime) {
            this.totalTime = totalTime;
            return this;
        }

        public Builder setLaps(List<Integer> laps) {
            this.laps = laps;
            return this;
        }

        public Builder setTime(Timestamp time) {
            this.time = time;
            return this;
        }
    }
}
