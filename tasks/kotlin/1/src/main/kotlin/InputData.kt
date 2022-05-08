class InputData {
    var numerate = false //true - нумерация строк, false - нумерация отсутствует
    var recursive = false //true: input - директория, false: input - файл
    var before = -1 //сколько напечатать строк до найденной строки
    var after = -1 //сколько строк напечатать после найденной строки
    var ignore = false //true - игнорировать регистр, false - точное совпадение
    var help = false //true - вывести подсказку и завершить работу
    var exclude = "" //регулярное выражение для исключения файлов из поиска
    var regExp = "" //регулярное выражение для поиска в файлах
    var input = "" //входной файл или директория
    var errorMessage = "" //сообщение об ошибке ввода команды

    fun setParameters(args: Array<String>) {
        if (args.contains("--help")) {
            help = true
        } else if(args.size < 3) {
            errorMessage = "Not enough parameters"
        } else if (args[0] != "grek") {
            errorMessage = "'grek' is missing"
        } else {
            var idx = 1
            while (idx < args.size - 2) {
                if (args[idx][0] == '-') {
                    if (args[idx] == "--exclude") {
                        exclude = args[++idx]
                    } else if (args[idx] == "-A") {
                        try {
                            after = Integer.parseInt(args[++idx])
                            if(after < 0) {
                                errorMessage = "After parameter must be non-negative"
                                return
                            }
                        } catch (e: NumberFormatException) {
                            errorMessage = "After parameter must be non-negative integer"
                            return
                        }
                    } else if (args[idx] == "-B") {
                        try {
                            before = Integer.parseInt(args[++idx])
                            if(before < 0) {
                                errorMessage = "Before parameter must be non-negative"
                                return
                            }
                        } catch (e: NumberFormatException) {
                            errorMessage = "Before parameter must be integer"
                            return
                        }
                    } else if (args[idx].matches(Regex("-[nir]+"))) {
                        if (args[idx].contains("n")) {
                            numerate = true
                        }
                        if (args[idx].contains("r")) {
                            recursive = true
                        }
                        if (args[idx].contains("i")) {
                            ignore = true
                        }
                    } else {
                        val option = args[idx]
                        errorMessage = "Unknown option '$option'"
                        return
                    }
                } else {
                    errorMessage = "Flags expected"
                }
                idx++
            }
            regExp = args[args.size - 2]
            input = args[args.size - 1]
        }
    }
}