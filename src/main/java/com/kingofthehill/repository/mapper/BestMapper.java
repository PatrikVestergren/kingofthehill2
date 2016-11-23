package com.kingofthehill.repository.mapper;

import com.kingofthehill.repository.model.BestEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BestMapper implements ResultSetMapper<BestEntity> {
    @Override
    public BestEntity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new BestEntity.Builder()
                .setId(r.getInt("id"))
                .setTransponder(r.getLong("transponder"))
                .setNrOfLaps(r.getInt("nroflaps"))
                .setTotalTime(r.getLong("totaltime"))
                .setLaps(Arrays.asList((Integer[]) r.getArray("lapids").getArray()))
                .setTime(r.getTimestamp("modtime"))
                .build();
    }
}
