package ru.nastya.random;

import ru.nastya.random.util.GenerateList;

public class Main {

    public static void main(String[] args) {


        if (args.length == 10) {
            GenerateList.ProcessingGenerate(Integer.parseInt(args[4]),Integer.parseInt(args[1]),args[5],args[6],args[7],args[8],args[2],args[0],args[3]);
        } else if (args.length == 4){
            GenerateList.ProcessingGenerate(0,Integer.parseInt(args[1]),"","","","",args[2],args[0],args[3]);
        } else if (args.length == 5) {
            GenerateList.ProcessingGenerate(Integer.parseInt(args[4]),Integer.parseInt(args[1]),"","","","",args[2],args[0],args[3]);
        }
        else {
            System.out.println("Команда введена неправильно.\nИспользуйте параметры:");
            System.out.println("Тип_распределения Количество_элементов Файл_для_сохранения Тип_Сохранения Ламбда(Если требуется) Таблица_БД(Если требуется) Строка_подключения_бд(Если требуется) Пользователь(Если требуется) Пароль(Если требуется)");
        }
    }
}
