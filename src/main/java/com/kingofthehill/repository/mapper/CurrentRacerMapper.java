package com.kingofthehill.repository.mapper;

import com.kingofthehill.Utils;
import com.kingofthehill.api.CurrentRacer;
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
                .setLastPass(Utils.toLocalDate(r.getTimestamp("modtime")))
                .setNrOfLaps(0)
                .build();
    }
}