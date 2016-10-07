package com.kingofthehill.repository.mapper;

/**
 * Created by patrik.vestergren on 2016-10-07.
 */

import com.kingofthehill.repository.model.Lap;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LapMapper implements ResultSetMapper<Lap> {

    @Override
    public Lap map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Lap lap = new Lap(r.getInt("did"), r.getString("driver"), r.getInt("transponder"), r.getInt("lapnr"), r.getLong("laptime"), r.getTimestamp("modtime"));
        return lap;
    }
}