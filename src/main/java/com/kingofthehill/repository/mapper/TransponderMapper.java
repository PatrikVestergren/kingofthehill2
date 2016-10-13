package com.kingofthehill.repository.mapper;

import com.kingofthehill.repository.model.Transponder;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransponderMapper implements ResultSetMapper<Transponder> {
    @Override
    public Transponder map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Transponder(r.getLong("transponder"), r.getString("name"));
    }
}
