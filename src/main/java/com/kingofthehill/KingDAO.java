package com.kingofthehill;

import org.skife.jdbi.v2.sqlobject.SqlQuery;

/**
 * Created by patrik on 2016-06-18.
 */
public interface KingDAO {
    @SqlQuery("select * from LAP")
    String findAllLaps();
}

