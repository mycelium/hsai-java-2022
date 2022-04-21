import java.nio.file.Path

class ArgumentsParser(private val arguments: Array<String>) {
    fun getGrepOptions(): GrepOptions {
        val before = if (arguments.contains("-B")) {
            arguments[arguments.indexOf("-B") + 1].toInt()
        } else {
            0
        }
        val after = if (arguments.contains("-A")) {
            arguments[arguments.indexOf("-A") + 1].toInt()
        } else {
            0
        }
        return GrepOptions(before, after, (before != 0) or (after != 0))
    }

    fun needsHelp(): Boolean {
        return arguments.contains("--help")
    }

    fun getPath(): Path {
        return Path.of(arguments.last())
    }

    fun getExcludedFiles(): List<Path> {
        val excludeArguments = arguments.withIndex().filter { s -> "--exclude".equals(s) }.map { i -> i.index }
        return excludeArguments.map { i -> arguments[i + 1] }.map { s -> Path.of(s) }.toList()
    }

    fun isIgnoreCase(): Boolean {
        return arguments.contains("-i")
    }

    fun isRecursive(): Boolean {
        return arguments.contains("-nr")
    }

    fun getRegexString(): String {
        val index = if (arguments.contains("-nr")) {
            arguments.indexOf("-nr")
        } else if (arguments.contains("-n")) {
            arguments.indexOf("-n")
        } else {
            throw IllegalArgumentException("No regex in parameters")
        }

        return arguments[index + 1]
    }
}