package com.kingofthehill.repository.dao;

import com.kingofthehill.repository.mapper.LapMapper;
import com.kingofthehill.repository.model.Lap;
import com.kingofthehill.repository.LapBinder;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by patrik on 2016-06-18.
 */
@RegisterMapper(LapMapper.class)
public interface KingDAO {

    @SqlQuery("select * from laps")
    List<Lap> allLaps();

    @SqlQuery("SELECT * FROM laps WHERE modTime > current_date - interval '1' day")
    List<Lap> getTodaysLaps();

    @SqlUpdate("insert into laps (driver, transponder, lapnr, laptime) values(:driver, :transponder, :lapnr, :laptime)")
    Integer insertLap(@BindBean @LapBinder Lap lap);

    @SqlQuery("SELECT * FROM laps WHERE modTime > current_date - interval '1' day AND transponder = :transponder")
    List<Lap> getTodaysLapsFor(@Bind("transponder") long transponder);
}

