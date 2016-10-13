package com.kingofthehill.repository.mapper;

import com.kingofthehill.repository.model.MinutesEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class MinutesMapper implements ResultSetMapper<MinutesEntity> {
    @Override
    public MinutesEntity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new MinutesEntity.Builder()
                .setId(r.getInt("bestminutesid"))
                .setTransponder(r.getLong("transponder"))
                .setNrOfLaps(r.getInt("nroflaps"))
                .setTotalTime(r.getLong("totaltime"))
                .setLaps(Arrays.asList((Integer[]) r.getArray("lapids").getArray()))
                .setTime(r.getTimestamp("modtime"))
                .build();
    }
}
