package ru.nastya.random.interfaces;

import java.util.List;

public interface RandomInterface {
    /**
     * Возвращает список рандомных чисел задаваемой длины распределения Пуасонна.
     *
     * @param lambda Среднее количество успешных испытаний в заданной области возможных исходов.
     * @param num Количество создоваемых чисел
     * @return List<Integer> Список рандомных чисел длиной num
     */
    List<Integer> getPoissonList(double lambda,int num);
    /**
     * Возвращает список рандомных чисел задаваемой длины нормального распределения.
     *
     * @param num Количество создоваемых чисел
     * @return List<Integer> Список рандомных чисел длиной num
     */
    List<Integer> getNormalList(int num);

    /**
     * Возвращает список рандомных чисел задаваемой длины равномерного распределения.
     *
     * @param num Количество создоваемых чисел
     * @return List<Integer> Список рандомных чисел длиной num
     */
    List<Integer> getUniformList(int num);
}
