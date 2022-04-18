package org.example.distribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.example.log.Log;

public abstract class Distribution {

    private final Log log = new Log(this.getClass());

    private final Random random = new Random();

    protected Random getRandom() {
        return random;
    }

    public abstract double getPoint();

    public List<Double> getPointList(int n) {
        List<Double> result = new ArrayList<>();

        log.info("Start generating data...");
        for (int i = 0; i < n; i++) {
            result.add(getPoint());
        }

        log.info("End generating data");
        return result;
    }
}
