data class Info (
        var afterRows: Int = 0,
        var beforeRows: Int = 0,
        var filePath: String = "",
        var pattern: String = "",
        var listExclude: MutableList<String> = mutableListOf()
)