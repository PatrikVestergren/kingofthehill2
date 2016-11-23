package com.kingofthehill.resource;

import com.codahale.metrics.annotation.Timed;
import com.kingofthehill.api.CurrentLap;
import com.kingofthehill.api.CurrentRacer;
import com.kingofthehill.repository.Repository;
import com.kingofthehill.repository.model.*;

import javax.ws.rs.*;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.container.AsyncResponse;


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
    public void addLap(Lap lap, @Suspended AsyncResponse response) {
        new Thread() {
            public void run() {
                try {
                    repository.insert(lap);
                } catch (Exception ex) {
                    response.resume(ex);
                    return;
                }
                Response rsp = Response.ok(lap,
                        MediaType.APPLICATION_JSON)
                        .build();
                response.resume(rsp);
            }
        }.start();
    }

    @GET
    @Timed
    public List<LapEntity> getLaps() {
        return repository.allLaps();
    }

    @GET
    @Timed
    @Path("todays")
    public List<CurrentLap> getTodaysLaps(@QueryParam("transponder") long transponder) {
        System.out.println("KingResource.getTodaysLaps " + transponder);
        return repository.getTodaysFor(transponder);
    }

//    @GET
//    @Path("todaysBestMinutes")
//    public List<BestMinute> getTodaysBestMinutes() {
//        return repository.getTodaysBestMinutes();
//    }

    @GET
    @Path("current")
    public List<CurrentRacer> getCurrents() {
        return repository.getCurrents();
    }

//    @GET
//    @Timed
//    @Path("bestMinutes")
//    public List<BestEntity> getBestMinutes() {
//        return repository.getBestMinutes();
//    }

    @GET
    @Timed
    @Path("recalc")
    public Integer reCalculate() {
        repository.reCalculate();
        return 1;
    }
}