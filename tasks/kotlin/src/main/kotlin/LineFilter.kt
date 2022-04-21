import java.util.*
import java.util.function.Predicate

class LineFilter(private val ignoreCase: Boolean, regexString: String) : Predicate<String> {
    private val regex: Regex = if (ignoreCase) {
        Regex(regexString.lowercase(Locale.getDefault()))
    } else {
        Regex(regexString)
    }

    override fun test(line: String): Boolean {
        return if (ignoreCase) {
            regex.matches(line.lowercase(Locale.getDefault()))
        } else {
            regex.matches(line)
        }
    }
}