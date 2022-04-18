data class Arguments(
    var path: String = "",
    var pattern: String = "",
    var fileCheck: Boolean = false,
    var directoryCheck: Boolean = false,
    var registerIgnore: Boolean = false,
    var afterRows: Int = 0,
    var beforeRows: Int = 0,
    var excludeList: MutableList<String> = mutableListOf()
)