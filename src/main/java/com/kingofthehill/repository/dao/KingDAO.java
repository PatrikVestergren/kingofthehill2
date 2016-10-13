package com.kingofthehill.repository.dao;

import com.kingofthehill.repository.model.MinutesEntity;
import com.kingofthehill.repository.MinuteBinder;
import com.kingofthehill.repository.mapper.LapEntityMapper;
import com.kingofthehill.repository.mapper.MinutesMapper;
import com.kingofthehill.repository.mapper.TransponderMapper;
import com.kingofthehill.repository.model.BestMinute;
import com.kingofthehill.repository.model.LapEntity;
import com.kingofthehill.repository.model.Transponder;
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

    @SqlQuery("select * from transponder where transponder = :transponder")
    @RegisterMapper(TransponderMapper.class)
    Transponder getTransponder(@Bind("transponder") long transponder);

    @SqlUpdate("insert into transponder (transponder, name) values(:transponder, :name)")
    Integer insertTransponder(@Bind("transponder") long transponder, @Bind("name") String name);

    @SqlUpdate("update transponder set name = :name where transponder = :transponder")
    void updateTransponderName(@Bind("transponder") long transponder, @Bind("name") String name);

    @SqlQuery("SELECT * FROM bestminutes")
    @RegisterMapper(MinutesMapper.class)
    List<MinutesEntity> getBestMinutes();

    @SqlQuery("SELECT * FROM bestminutes WHERE modTime > current_date - interval '1' day AND transponder = :transponder")
    @RegisterMapper(MinutesMapper.class)
    MinutesEntity getTodaysBestMinutesFor(@Bind("transponder") long transponder);

    @SqlUpdate("insert into bestMinutes (transponder, nroflaps, totaltime, lapids, modtime) values(:transponder, :nroflaps, :totaltime, :lapids, :modtime)")
    Integer insertBestMinutes(@MinuteBinder MinutesEntity minutesHolder);

    @SqlUpdate("update bestMinutes set nroflaps=:nroflaps, totaltime=:totaltime, lapids=:lapids, modtime=:modtime where bestminutesid = :bestminutesid")
    void updateBestMinutes(@MinuteBinder MinutesEntity clone);

    @SqlQuery("SELECT * FROM bestminutes WHERE transponder = :transponder")
    @RegisterMapper(MinutesMapper.class)
    List<MinutesEntity> getBestMinutesFor(@Bind("transponder") long transponder);
}

