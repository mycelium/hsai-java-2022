package grek

fun <T> List<T>.getWindowAt(ix: Int, windowOpts: WindowOpts): Pair<Int, List<T>> {
    val safeFrom = 0.coerceAtLeast(ix - windowOpts.before)
    val safeTo = (ix + windowOpts.after).coerceAtMost(this.size - 1)
    return Pair(ix, this.subList(safeFrom, safeTo + 1))
}