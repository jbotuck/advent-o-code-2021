import kotlin.math.min

fun main() {
    fun solve(map: Array<IntArray>): Long {
        val results = Array(map.size) { LongArray(map.size) { Long.MAX_VALUE } }//assuming both x and y size are =
        val unVisited = mutableSetOf<Pair<Int, Int>>()
        val visited = mutableSetOf<Pair<Int, Int>>()
        unVisited.add(0 to 0)
        results[0][0] = 0
        while (unVisited.isNotEmpty()) {
            val current = unVisited.minByOrNull { (y, x) -> results[y][x] }!!
            if (current == (map.size - 1 to map.size - 1)) {
                break
            }
            unVisited.remove(current)
            val neighbors = current.getNeighbors(map.size)
            unVisited.addAll(neighbors - visited)
            for ((y, x) in neighbors) {
                results[y][x] = min(results[current.first][current.second] + map[y][x], results[y][x])
            }
            visited.add(current)
        }
        return results.last().last()
    }

    fun part1(input: List<String>): Long {
        val map = input.map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray()
        return solve(map)
    }

    fun part2(input: List<String>): Long {
        val easyMap = input.map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray()
        val realMap = Array(easyMap.size * 5) { IntArray(easyMap.size * 5) }
        for (yTileIndex in 0..4) {
            for (xTileIndex in 0..4) {
                for (y in easyMap.indices) {
                    for (x in easyMap.indices) {
                        realMap[yTileIndex * easyMap.size + y][xTileIndex * easyMap.size + x] =
                            (easyMap[y][x] + yTileIndex + xTileIndex).let { if (it >= 10) it - 9 else it }
                    }
                }
            }
        }
        println()
        return solve(realMap)
    }
    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day15_test")
//    check(part2(testInput) == 315L)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}

private fun Pair<Int, Int>.getNeighbors(size: Int): Set<Pair<Int, Int>> {
    val above = first.takeIf { it != 0 }?.let { first - 1 to second }
    val left = second.takeIf { it != 0 }?.let { first to second - 1 }
    val right = second.takeIf { it != size - 1 }?.let { first to second + 1 }
    val below = first.takeIf { it != size - 1 }?.let { first + 1 to second }
    return setOfNotNull(above, left, right, below)
}