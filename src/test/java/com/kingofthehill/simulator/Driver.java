package com.kingofthehill.simulator;

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

    public Driver(final RestClient client, final String name, final long avgLapTime, final long transponder) {
        this.client = client;
        this.name = name;
        this.avgLapTime = avgLapTime;
        FACTOR = avgLapTime / 10;
        this.transponder = transponder;
    }

    public void run() {
        int lapNr = 0;
        while (!stopped) {
            Long lapTime = generateLapTime();
            ScheduledFuture<Lap> f = executor.schedule(work(lapNr++, lapTime), lapTime, TimeUnit.MILLISECONDS);
            try {
                client.send(f.get());
            } catch (Throwable e) {
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
