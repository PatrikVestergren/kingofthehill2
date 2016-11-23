package com.kingofthehill.com.kingofthehill.algorithm;

/**
 * Created by patrikv on 08/04/16.
 */

import com.kingofthehill.repository.model.BestEntity;
import com.kingofthehill.repository.model.BestMinute;
import com.kingofthehill.repository.model.LapEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinutesAlgorithmTest {


    @Test
    public void verifyNotReachedFiveMinutesSubLapsShouldBeEmpty() {
        List<LapEntity> laps = new ArrayList<>();
        assertTrue(new MinutesAlgorithm(5).subLaps(laps).isEmpty());

        laps.add(new LapEntity(0, 0l, 0, 299998l, null));
        assertTrue(new MinutesAlgorithm(5).subLaps(laps).isEmpty());

        laps.add(new LapEntity(0, 0l, 2, 1l, null));
        assertTrue(new MinutesAlgorithm(5).subLaps(laps).isEmpty());
    }

    @Test
    public void verifySubLapsExactFiveMinutes() {
        List<LapEntity> laps = new ArrayList<>();
        LapEntity l1 = new LapEntity(0, 0l, 1, 300000l, null);
        laps.add(l1);
        List<BestEntity> actual = new MinutesAlgorithm(5).subLaps(laps);
        assertTrue(actual.size() == 1);
        BestEntity head = actual.get(0);
        assertTrue(head.getNrOfLaps() == 1);
    }

    @Test
    public void verifySubLaps() {
        List<LapEntity> laps = new ArrayList<>();
        LapEntity l1 = new LapEntity(0, 0l, 1, 150000l, null);
        LapEntity l2 = new LapEntity(0, 0l, 2, 100000l, null);
        LapEntity l3 = new LapEntity(0, 0l, 3, 150000l, null);
        laps.add(l1);
        laps.add(l2);
        laps.add(l3);
        List<BestMinute> expected = new ArrayList<>();
        expected.add(new BestMinute(0l, laps));
        List<BestEntity> actual = new MinutesAlgorithm(5).subLaps(laps);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void verifyBestMinutes() {
        List<LapEntity> laps = new ArrayList<>();

        laps.add(new LapEntity(0, 0l, 1, 170000l, null));
        laps.add(new LapEntity(0, 0l, 2, 120000l, null));
        laps.add(new LapEntity(0, 0l, 3, 150000l, null));
        laps.add(new LapEntity(0, 0l, 4, 10000l, null));
        laps.add(new LapEntity(0, 0l, 5, 10000l, null));
        laps.add(new LapEntity(0, 0l, 6, 250000l, null));

        Optional<BestEntity> res = new MinutesAlgorithm(5).getBestMinutes(laps);
        BestEntity best = res.get();
        assertTrue(best.getNrOfLaps() == 5);
        assertTrue(best.getTotalTime() == 540000);
    }

    @Test
    public void verifyGetTopTen() {
        List<LapEntity> driver1 = new ArrayList<>();
        driver1.add(new LapEntity(0, 12345l, 1, 120000l, null));
        driver1.add(new LapEntity(0, 12345l, 2, 120000l, null));
        driver1.add(new LapEntity(0, 12345l, 3, 320000l, null));

        List<LapEntity> driver2 = new ArrayList<>();
        driver2.add(new LapEntity(0, 1212l, 1, 90000l, null));
        driver2.add(new LapEntity(0, 1212l, 2, 90000l, null));
        driver2.add(new LapEntity(0, 1212l, 3, 90000l, null));
        driver2.add(new LapEntity(0, 1212l, 4, 90000l, null));

        List<LapEntity> driver3 = new ArrayList<>();
        driver3.add(new LapEntity(0, 12343l, 1, 150000l, null));
        driver3.add(new LapEntity(0, 12343l, 2, 100000l, null));
        driver3.add(new LapEntity(0, 12343l, 3, 10000l, null));
        driver3.add(new LapEntity(0, 12343l, 4, 50000l, null));
        driver3.add(new LapEntity(0, 12343l, 5, 50000l, null));

        List<LapEntity> driver4 = new ArrayList<>();
        driver4.add(new LapEntity(0, 12399l, 1, 250000l, null));
        driver4.add(new LapEntity(0, 12399l, 2, 215000l, null));

        Optional<BestEntity> dr1 = new MinutesAlgorithm(5).getBestMinutes(driver1);
        Optional<BestEntity> dr2 = new MinutesAlgorithm(5).getBestMinutes(driver2);
        Optional<BestEntity> dr3 = new MinutesAlgorithm(5).getBestMinutes(driver3);
        Optional<BestEntity> dr4 = new MinutesAlgorithm(5).getBestMinutes(driver4);

        List<BestEntity> minutes = new ArrayList<>();
        minutes.add(dr1.get());
        minutes.add(dr2.get());
        minutes.add(dr3.get());
        minutes.add(dr4.get());

        List<BestEntity> topTen = MinutesAlgorithm.getTopN(10, minutes);
        topTen.forEach(h -> System.out.println(h));


    }

}
