package com.kingofthehill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrikv on 06/03/16.
 */

public class LapRepository {
    private final List<Lap> laps = new ArrayList<>();

    public void save(final Lap lap) {
        laps.add(lap);
    }

    public List<Lap> findAll() {
        return laps;
    }
}
