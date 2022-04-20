package ru.mironova.grek

object Console {
    fun fileNotExist(name: String) = "File not exitst: $name\n"


    fun fileIsDir(name: String) = "File is directory: $name\n"


    fun notFileOrDir(name: String) = "ru.mironova.grek: $name: No such file or directory\n"


    fun fileWithoutAccess(name: String?) =
         if (name == null) "ru.mironova.grek: : Permission denied" else String.format("ru.mironova.grek: %s: Permission denied", name)

    fun uncorrectArg(arg: String?) = if (arg == null) "Uncorrect arg: " else "Uncorrect arg: $arg"

    fun keyWithoutArg(keyName: String?) = if (keyName == null) "Uncorrect arg: \nYou can use --help" else String.format("Uncorrect arg: %s\nYou can use --help", keyName)


    fun helpMessage() =
         """ru.mironova.grek [OPTION]... PATTRON [File]...
                Pattern selection and interpretation:
                    -r	recurse
                    -n	print line number with output lines
                    -i	ignore case
                    --exclude=[PATTRON]	exclude files by name"""
    
}