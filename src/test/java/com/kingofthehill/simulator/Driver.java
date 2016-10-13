package com.kingofthehill.simulator;

import com.fasterxml.jackson.core.JsonFactory;
import com.kingofthehill.repository.model.Lap;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by patrikv on 06/03/16.
 */
public class Driver implements Runnable {

    private final RestClient client;
    private final long FACTOR;
    private final String name;
    private final long transponder;
    private final long avgLapTime;
    private boolean stopped;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final JsonFactory factory = new JsonFactory();

    public Driver(final RestClient client, final String name, final long avgLapTime, final long transponder) {
        System.out.println("Driver.Driver");
        this.client = client;
        this.name = name;
        this.avgLapTime = avgLapTime;
        FACTOR = avgLapTime / 10;
        this.transponder = transponder;
    }

    public void run() {
        System.out.println(name + " -> go()");
        int i = 1;
        while (!stopped) {
            Long lapTime = generateLapTime();
            ScheduledFuture<Lap> f = executor.schedule(work(i, lapTime), lapTime, TimeUnit.MILLISECONDS);
            try {
                Lap l = f.get();
                System.out.println(l);
                client.send(l);
                i++;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private Callable<Lap> work(final int lapNr, final long lapTime) {
        return () -> {
            Lap lap = new Lap(name, transponder, lapNr, lapTime);
            return lap;
        };
    }

    private long generateLapTime() {
        Random r = new Random();
        return (avgLapTime - FACTOR)+((long)(r.nextDouble()*((avgLapTime + FACTOR)-(avgLapTime - FACTOR))));
    }

    public void stop() {
        stopped = true;
        executor.shutdownNow();
    }
}
