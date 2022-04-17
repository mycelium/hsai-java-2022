package ru.nastya.random.generators;

import ru.nastya.random.interfaces.RandomInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerators implements RandomInterface {

    @Override
    public List<Integer> getPoissonList(double lambda, int num) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < num; i++) {
            list.add(PoissonGenerator.getPoisson(lambda));
        }
        return list;
    }

    @Override
    public List<Integer> getNormalList(int num) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < num; i++) {
            list.add((int) random.nextGaussian());
        }
        return list;
    }

    @Override
    public List<Integer> getUniformList(int num) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < num; i++) {
            list.add(random.nextInt());
        }
        return list;
    }
}
