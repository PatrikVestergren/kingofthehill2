package com.kingofthehill.com.kingofthehill.algorithm;

import com.kingofthehill.repository.model.LapEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static org.junit.Assert.*;

/**
 * Created by patrik.vestergren on 2016-10-08.
 */
public class LapsAlgorithmTest {

    private LapsAlgorithm algorithm;

    @Before
    public void setup() {
        algorithm = new LapsAlgorithm();
    }

    @Test
    public void verifyToFewLaps() {
        assertSame(empty(), algorithm.getBestLaps(new ArrayList<>()));
        assertSame(empty(), algorithm.getBestLaps(asList(createLap(1, 170000l))));
        assertSame(empty(), algorithm.getBestLaps(asList(createLap(1, 1l), createLap(2, 1l))));
    }

    private LapEntity createLap(int lapNr, long lapTime) {
        return new LapEntity(0, 0l, lapNr, lapTime, null);
    }

    @Test
    public void verifyBestLapsSimple() {
        List<LapEntity> laps = new ArrayList<>();
        LapEntity l1 = new LapEntity(0, 0l, 1, 100000l, null);
        LapEntity l2 = new LapEntity(0, 0l, 2, 100000l, null);
        LapEntity l3 = new LapEntity(0, 0l, 3, 100000l, null);
        laps.add(l1);
        laps.add(l2);
        laps.add(l3);

        LapsHolder actual = algorithm.getBestLaps(laps).get();
        assertTrue(actual.getTotalTime() == 300000);
        assertEquals(laps, actual.getLaps());
    }

    @Test
    public void verifyBestLaps() {
        List<LapEntity> laps = new ArrayList<>();
        LapEntity l1 = new LapEntity(0, 0l, 1, 60000, null);
        LapEntity l2 = new LapEntity(0, 0l, 2, 100000l, null);
        LapEntity l3 = new LapEntity(0, 0l, 3, 200000l, null);
        LapEntity l4 = new LapEntity(0, 0l, 4, 35000, null);
        LapEntity l5 = new LapEntity(0, 0l, 5, 37000, null);
        LapEntity l6 = new LapEntity(0, 0l, 6, 100000l, null);
        LapEntity l7 = new LapEntity(0, 0l, 6, 600000l, null);
        laps.add(l1);
        laps.add(l2);
        laps.add(l3);
        laps.add(l4);
        laps.add(l5);
        laps.add(l6);
        laps.add(l7);

        LapsHolder actual = algorithm.getBestLaps(laps).get();
        assertTrue(actual.getTotalTime() == 172000);
        assertEquals(asList(l4, l5, l6), actual.getLaps());
    }

    @Test
    public void verifyBestLapsAllSame() {
        List<LapEntity> laps = new ArrayList<>();
        LapEntity l1 = new LapEntity(0, 0l, 1, 100000l, null);
        LapEntity l2 = new LapEntity(0, 0l, 2, 100000l, null);
        LapEntity l3 = new LapEntity(0, 0l, 3, 100000l, null);
        LapEntity l4 = new LapEntity(0, 0l, 4, 100000l, null);
        LapEntity l5 = new LapEntity(0, 0l, 5, 100000l, null);
        LapEntity l6 = new LapEntity(0, 0l, 6, 100000l, null);
        LapEntity l7 = new LapEntity(0, 0l, 7, 100000l, null);
        laps.add(l1);
        laps.add(l2);
        laps.add(l3);
        laps.add(l4);
        laps.add(l5);
        laps.add(l6);
        laps.add(l7);

        LapsHolder actual = algorithm.getBestLaps(laps).get();
        assertTrue(actual.getTotalTime() == 300000);
        assertEquals(asList(l1, l2, l3), actual.getLaps());
    }
}