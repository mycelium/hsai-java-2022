import java.nio.file.Paths
import java.nio.file.Files

//Пример запроса -n " [а-я]{12} " data/test.txt
//Пример запроса -nr " [а-я]{12} " data
fun main(args: Array<String>){
    if (args.size!=3&&args.size!=1) {
        println("Requires 1 or 3 params to work")
        return
    }
    if (args.size==1){
        if (args[0]=="--help") println("Grek utility\n-n <regEx> <pathToSingleFile>: Find in file\n-nr <regEx> <pathToFolder> : Find in files in folder")
        else println("Unknown key")
        return
    }
    val key = args[0]
    val regex = args[1].toRegex()
    val path = Paths.get(args[2])
    if (!Files.exists(path)) {
        println("File or directory not exist")
        return
    }
    //Не поняла значений ключей -В и -А
    when (key) {
        "-n" -> {
            println("Found in file: $path on request: $regex")
            var result = listOf<String>()
            for (line in Files.readAllLines(path)) {
                result = result + (regex.findAll(line).map{it.value}.toList())
            }
            if (result.isNotEmpty()) println(result)
        }
        "-nr" -> {
            println("Found in location: $path on request: $regex")
            for (p in Files.walk(path).filter(Files::isRegularFile)){
                var result = listOf<String>()
                for (line in Files.readAllLines(p)) {
                    result = result + (regex.findAll(line).map{it.value}.toList())
                }
                if (result.isNotEmpty()) println("Found in file: $p \n $result")
            }
        }
        else -> println("Unknown key")
    }
}