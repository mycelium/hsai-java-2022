package arguments

data class Args (
    var line_number: Boolean = false,
    var recursive: Boolean = false,
    var after_context: Int = 0,
    var before_context: Int = 0,
    var ignore_case: Boolean = false,
    var help: Boolean = false,
    var exclude: String = "",
    var regex: String = "",
    var path: String = "",
)
