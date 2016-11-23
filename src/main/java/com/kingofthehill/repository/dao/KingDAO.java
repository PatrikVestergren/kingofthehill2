package com.kingofthehill.repository.dao;

import com.kingofthehill.api.CurrentRacer;
import com.kingofthehill.repository.LapBinder;
import com.kingofthehill.repository.mapper.CurrentRacerMapper;
import com.kingofthehill.repository.model.*;
import com.kingofthehill.repository.BestBinder;
import com.kingofthehill.repository.mapper.LapEntityMapper;
import com.kingofthehill.repository.mapper.BestMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;
//@UseStringTemplate3StatementLocator
public interface KingDAO {

    @SqlQuery("select * from laps")
    @RegisterMapper(LapEntityMapper.class)
    List<LapEntity> allLaps();

    @SqlQuery("SELECT * FROM laps WHERE modTime > current_date - interval '1' day")
    @RegisterMapper(LapEntityMapper.class)
    List<LapEntity> getTodaysLaps();

    @SqlUpdate("insert into laps (transponder, lapnr, laptime) values(:transponder, :lapnr, :laptime)")
    Integer insertLap(@Bind("transponder") long transponder, @Bind("lapnr") int lapnr, @Bind("laptime") long laptime);

    @SqlQuery("SELECT * FROM laps WHERE modTime > current_date - interval '1' day AND transponder = :transponder")
    @RegisterMapper(LapEntityMapper.class)
    List<LapEntity> getTodaysLapsFor(@Bind("transponder") long transponder);

    @SqlQuery("select name from transponder where transponder = :transponder")
    String getNameFromTransponder(@Bind("transponder") long transponder);

    @SqlUpdate("insert into transponder (transponder, name) values(:transponder, :name)")
    Integer insertTransponder(@Bind("transponder") long transponder, @Bind("name") String name);

    @SqlUpdate("update transponder set name = :name where transponder = :transponder")
    void updateTransponderName(@Bind("transponder") long transponder, @Bind("name") String name);

    @SqlQuery("SELECT * FROM bestminutes")
    @RegisterMapper(BestMapper.class)
    List<BestEntity> getBestMinutes();

    @SqlQuery("SELECT * FROM bestminutes WHERE modTime > current_date - interval '1' day AND transponder = :transponder")
    @RegisterMapper(BestMapper.class)
    BestEntity getTodaysBestMinutesFor(@Bind("transponder") long transponder);

    @SqlUpdate("insert into bestMinutes (transponder, nroflaps, totaltime, lapids, modtime) values(:transponder, :nroflaps, :totaltime, :lapids, :modtime)")
    Integer insertBestMinutes(@BestBinder BestEntity minutesHolder);

    @SqlUpdate("update bestMinutes set nroflaps=:nroflaps, totaltime=:totaltime, lapids=:lapids, modtime=:modtime where id = :id")
    void updateBestMinutes(@BestBinder BestEntity clone);

    @SqlQuery("SELECT * FROM bestminutes WHERE transponder = :transponder")
    @RegisterMapper(BestMapper.class)
    List<BestEntity> getBestMinutesFor(@Bind("transponder") long transponder);

    @SqlQuery("SELECT * FROM bestlaps")
    @RegisterMapper(BestMapper.class)
    List<BestEntity> getBestLaps();

    @SqlQuery("SELECT * FROM bestlaps WHERE modTime > current_date - interval '1' day AND transponder = :transponder")
    @RegisterMapper(BestMapper.class)
    BestEntity getTodaysBestLapsFor(@Bind("transponder") long transponder);

    @SqlUpdate("insert into bestlaps (transponder, nroflaps, totaltime, lapids, modtime) values(:transponder, :nroflaps, :totaltime, :lapids, :modtime)")
    Integer insertBestLaps(@BestBinder BestEntity lapsHolder);

    @SqlUpdate("update bestlaps set nroflaps=:nroflaps, totaltime=:totaltime, lapids=:lapids, modtime=:modtime where id = :id")
    void updateBestLaps(@BestBinder BestEntity clone);

    @SqlQuery("SELECT * FROM bestlaps WHERE transponder = :transponder")
    @RegisterMapper(BestMapper.class)
    List<BestEntity> getBestLapsFor(@Bind("transponder") long transponder);

    @SqlQuery("SELECT l.*, t.name FROM laps l JOIN transponder t ON a.transponder = t.transponder WHERE l.modTime > current_date - interval '1' day AND l.transponder = :transponder")
    @RegisterMapper(CurrentRacerMapper.class)
    List<CurrentRacer> getCurrentRacer(@Bind("transponder") long transponder);
}

