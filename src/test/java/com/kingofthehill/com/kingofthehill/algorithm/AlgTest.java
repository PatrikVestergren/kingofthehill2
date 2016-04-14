package com.kingofthehill.com.kingofthehill.algorithm;

/**
 * Created by patrikv on 08/04/16.
 */

import com.kingofthehill.Lap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlgTest {


    @Test
    public void verifyNotReachedFiveMinutesSubLapsShouldBeEmpty() {
        List<Lap> laps = new ArrayList<>();
        assertTrue(new Alg(5).subLaps(laps).isEmpty());

        laps.add(new Lap("", 0l, 1l, 299998l));
        assertTrue(new Alg(5).subLaps(laps).isEmpty());

        laps.add(new Lap("", 0l, 2l, 1l));
        assertTrue(new Alg(5).subLaps(laps).isEmpty());
    }

    @Test
    public void verifySubLapsExactFiveMinutes() {
        List<Lap> laps = new ArrayList<>();
        Lap l1 = new Lap("", 0l, 1l, 300000l);
        laps.add(l1);
        List<MinutesHolder> actual = new Alg(5).subLaps(laps);
        assertTrue(actual.size() == 1);
        MinutesHolder head = actual.get(0);
        assertTrue(head.getNrOfLaps() == 1);
    }

    @Test
    public void verifySubLaps() {
        List<Lap> laps = new ArrayList<>();
        Lap l1 = new Lap("", 0l, 1l, 150000l);
        Lap l2 = new Lap("", 0l, 2l, 100000l);
        Lap l3 = new Lap("", 0l, 3l, 150000l);
        laps.add(l1);
        laps.add(l2);
        laps.add(l3);
        List<MinutesHolder> expected = new ArrayList<>();
        expected.add(new MinutesHolder(laps));
        List<MinutesHolder> actual = new Alg(5).subLaps(laps);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void verifyBestMinutes() {
        List<Lap> laps = new ArrayList<>();

        laps.add(new Lap("", 0l, 1l, 170000l));
        laps.add(new Lap("", 0l, 2l, 120000l));
        laps.add(new Lap("", 0l, 3l, 150000l));
        laps.add(new Lap("", 0l, 4l, 10000l));
        laps.add(new Lap("", 0l, 5l, 10000l));
        laps.add(new Lap("", 0l, 6l, 250000l));

        Optional<MinutesHolder> res = new Alg(5).getBestMinutes(laps);
        MinutesHolder best = res.get();
        assertTrue(best.getNrOfLaps() == 5);
        assertTrue(best.getTotalTime() == 540000);
        System.out.println(best);
    }

    @Test
    public void verifyGetTopTen() {
        List<Lap> driver1 = new ArrayList<>();
        driver1.add(new Lap("driver1", 12345l, 1l, 120000l));
        driver1.add(new Lap("driver1", 12345l, 2l, 120000l));
        driver1.add(new Lap("driver1", 12345l, 3l, 320000l));

        List<Lap> driver2 = new ArrayList<>();
        driver2.add(new Lap("driver2", 1212l, 1l, 90000l));
        driver2.add(new Lap("driver2", 1212l, 2l, 90000l));
        driver2.add(new Lap("driver2", 1212l, 3l, 90000l));
        driver2.add(new Lap("driver2", 1212l, 4l, 90000l));

        List<Lap> driver3 = new ArrayList<>();
        driver3.add(new Lap("driver3", 12343l, 1l, 150000l));
        driver3.add(new Lap("driver3", 12343l, 2l, 100000l));
        driver3.add(new Lap("driver3", 12343l, 3l, 10000l));
        driver3.add(new Lap("driver3", 12343l, 4l, 50000l));
        driver3.add(new Lap("driver3", 12343l, 5l, 50000l));

        List<Lap> driver4 = new ArrayList<>();
        driver4.add(new Lap("driver4", 12399l, 1l, 250000l));
        driver4.add(new Lap("driver4", 12399l, 2l, 215000l));

        Optional<MinutesHolder> dr1 = new Alg(5).getBestMinutes(driver1);
        Optional<MinutesHolder> dr2 = new Alg(5).getBestMinutes(driver2);
        Optional<MinutesHolder> dr3 = new Alg(5).getBestMinutes(driver3);
        Optional<MinutesHolder> dr4 = new Alg(5).getBestMinutes(driver4);

        List<MinutesHolder> minutes = new ArrayList<>();
        minutes.add(dr1.get());
        minutes.add(dr2.get());
        minutes.add(dr3.get());
        minutes.add(dr4.get());

        List<MinutesHolder> topTen = Alg.getTopN(10, minutes);
        topTen.forEach(h -> System.out.println(h));


    }

    private List<Lap> generateLaps(String driver, Long transponder, Long lapTime, int numberOfLaps) {
        List<Lap> laps = new ArrayList<>();
        LongStream.range(0, numberOfLaps)
                .forEach(nbr -> laps.add(new Lap(driver, transponder, nbr, lapTime)));
        return laps;
    }
}
