package com.kingofthehill.repository.mapper;

import com.kingofthehill.repository.model.LapEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LapEntityMapper implements ResultSetMapper<LapEntity> {

    @Override
    public LapEntity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new LapEntity(r.getInt("lapid"), r.getInt("transponder"), r.getInt("lapnr"), r.getLong("laptime"), r.getTimestamp("modtime"));
    }
}