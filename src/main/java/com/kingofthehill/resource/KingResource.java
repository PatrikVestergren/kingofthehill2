package com.kingofthehill.resource;

import com.codahale.metrics.annotation.Timed;
import com.kingofthehill.repository.Repository;
import com.kingofthehill.repository.model.BestMinute;
import com.kingofthehill.repository.model.Lap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        System.out.println("KingResource.addLap");
        repository.insert(lap);
        return Response.status(200).build();

    }

    @GET
    @Timed
    public List<Lap> getLaps() {
        List<Lap> laps = repository.allLaps();
        return laps;
    }

    @GET
    @Timed
    @Path("todays")
    public List<Lap> getTodaysLaps() {
        return repository.getTodaysLaps();
    }

    @GET
    @Path("todaysBestMinutes")
    public List<BestMinute> getTodaysBestMinutes() {
        return repository.getTodaysBestMinutes();
    }
}