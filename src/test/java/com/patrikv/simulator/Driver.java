package com.patrikv.simulator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.patrikv.Lap;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by patrikv on 06/03/16.
 */
public class Driver implements Runnable {

    private final Client client;
    private final long FACTOR;
    private final String name;
    private final long transponder;
    private final long avgLapTime;
    private boolean stopped;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final JsonFactory factory = new JsonFactory();

    public Driver(final Client client, final String name, final long avgLapTime, final long transponder) {
        System.out.println("Driver.Driver");
        this.client = client;
        this.name = name;
        this.avgLapTime = avgLapTime;
        FACTOR = avgLapTime / 10;
        this.transponder = transponder;
    }

    public void run() {
        System.out.println(name + " -> go()");
        long i = 1l;
        while (!stopped) {
            Long lapTime = generateLapTime();
            System.out.println("lapTime="+lapTime);
            ScheduledFuture<String> f = executor.schedule(work(i, lapTime), lapTime, TimeUnit.MILLISECONDS);
            try {
                String json = f.get();
                System.out.println(json);
                client.send(json);
                i++;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private Callable<String> work(final long lapNr, final long lapTime) {
        return () -> {
            Lap lap = new Lap(name, transponder, lapNr, lapTime, LocalDateTime.now());
            System.out.println("Driver.work " + lap);
            return createJSon(lap);
        };
    }

    private long generateLapTime() {
        Random r = new Random();
        return (avgLapTime - FACTOR)+((long)(r.nextDouble()*((avgLapTime + FACTOR)-(avgLapTime - FACTOR))));
    }

    private String createJSon(final Lap lap) {
        try {
            Writer writer = new StringWriter();
            JsonGenerator generator = factory.createGenerator(writer);
            generator.writeStartObject();
            generator.writeStringField("driver", lap.getDriver());
            generator.writeNumberField("transponder", lap.getTransponder());
            generator.writeNumberField("lapNr", lap.getLapNr());
            generator.writeNumberField("lapTime", lap.getLapTime());
            generator.writeEndObject();
            generator.close();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String();
    }

    public void stop() {
        stopped = true;
        executor.shutdownNow();
    }
}
