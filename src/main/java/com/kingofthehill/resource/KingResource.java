package com.kingofthehill.resource;

import com.codahale.metrics.annotation.Timed;
import com.kingofthehill.repository.Repository;
import com.kingofthehill.repository.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/**
 * Created by patrik on 2016-06-18.
 */
@Path("/laps")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/json")
public class KingResource {

    private final Repository repository;

    public KingResource(Repository repository) {
        this.repository = repository;
    }

    @POST
    @Timed
    public Response addLap(Lap lap) {
        repository.insert(lap);
        return Response.status(200).build();

    }

    @GET
    @Timed
    public List<LapEntity> getLaps() {
        return repository.allLaps();
    }

    @GET
    @Timed
    @Path("todays")
    public List<LapEntity> getTodaysLaps() {
        return repository.getTodaysLaps();
    }

    @GET
    @Path("todaysBestMinutes")
    public List<BestMinute> getTodaysBestMinutes() {
        return repository.getTodaysBestMinutes();
    }

    @GET
    @Path("current")
    public List<CurrentRow> getCurrents() {
        return repository.getCurrents();
    }

    @GET
    @Timed
    @Path("bestMinutes")
    public List<MinutesEntity> getBestMinutes() {
        return repository.getBestMinutes();
    }

    @GET
    @Timed
    @Path("recalc")
    public Integer reCalculate() {
        repository.reCalculate();
        return 1;
    }
}