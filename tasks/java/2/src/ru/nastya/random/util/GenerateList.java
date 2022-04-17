package ru.nastya.random.util;

import ru.nastya.random.generators.RandomGenerators;
import ru.nastya.random.tools.WriteData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GenerateList {
    public static void ProcessingGenerate(int lambda, int num, String table, String jdbc, String user, String password, String directory, String Type, String  TypeSave) {
        List<Integer> list;
        RandomGenerators randomGenerators = new RandomGenerators();
        switch (Type) {
            case "1" -> {
                list = randomGenerators.getUniformList(num);
            }
            case "2" -> {
                list = randomGenerators.getNormalList(num);
            }
            case "3" -> {
                list = randomGenerators.getPoissonList(lambda, num);
            }
            default -> {
                System.out.println("Такой тип распределения не предусмотрен.");
                System.out.println("Предусмотренные типы:\n 1. Равномерное\n2. Нормальное\n3. Пуассона");
                return;
            }
        }
        switch (TypeSave) {
            case "1" -> {
                System.out.println("Данные сгенерированы");
                try {
                    WriteData.WriteCSV(list, directory);
                    System.out.println("Данные в файл " + directory + " записаны");
                } catch (IOException e) {
                    System.out.println("Произошла ошибка записи данных в файл: " + e.getMessage());
                }
            }
            case "2" -> {
                System.out.println("Данные сгенерированы");
                try {
                    WriteData.WriteBD(list, jdbc, user, password, directory);
                    System.out.println("Данные в файл " + directory + " записаны");
                } catch (SQLException e) {
                    System.out.println("Произошла ошибка записи данных в базу: " + e.getMessage());

                }
            }
            default -> {
                System.out.println("Такой тип записи данных не предусмотрен.");
                System.out.println("Предусмотренные типы:\n 1. В CSV файл\n2. В базу данных");
            }
        }

    }
}
