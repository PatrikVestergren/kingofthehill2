package com.kingofthehill.repository.mapper;

import com.kingofthehill.repository.model.CurrentRacer;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrentRacerMapper implements ResultSetMapper<CurrentRacer> {

    @Override
    public CurrentRacer map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return CurrentRacer.getBuilder()
                .setDriver(r.getString("name"))
                .setTransponder(r.getLong("transponder"))
                .setLapTime(r.getLong("laptime"))
                .setLastPass(toLocalDate(r.getTimestamp("modtime")))
                .setNrOfLaps(0)
                .build();
    }

    private LocalDate toLocalDate(Timestamp timestamp) {
        if (timestamp == null) return null;
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(timestamp);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, dtf);
    }
}