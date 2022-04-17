package ru.nastya.random.tools;

import com.opencsv.CSVWriter;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WriteData {
    /**
     * Вписывает данные из списка в CSV файл.
     *
     * @param list Список значений.
     * @param directory Папка для csv файла
     * @throws IOException
     */
    public static void WriteCSV(List<Integer> list, String directory) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(directory));

        for(int i = 0; i < list.size(); i++) {
            writer.writeNext(new String[]{list.get(i).toString()});
        }
        writer.close();

    }

    /**
     * Вписывает данные из списка в CSV файл.
     *
     * @param list Список значений.
     * @param table Таблица для записи
     * @param jdbc Строка подключения
     * @param user Строка пользователя
     * @param password Строка пароля
     *
     * @throws IOException
     */
    public static void WriteBD(List<Integer> list, String table, String jdbc, String user, String password) throws SQLException {
        try (Connection c = DriverManager.getConnection(jdbc, user, password)) {
            String query = "insert into " + table + " values (?)";
            for (int n : list) {
                PreparedStatement statement = c.prepareStatement(query);
                statement.setInt(1, n);
                statement.executeUpdate();
            }
        }
    }
}
