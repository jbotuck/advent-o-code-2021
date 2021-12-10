import java.util.*

fun main() {
    fun part1(input: List<String>): Long {
        val map = input.map { s -> s.map { it.toString().toInt() } }
        var sum = 0L
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map.isLowPoint(y, x)) {
                    sum += map[y][x] + 1
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val map = input.map { s -> s.map { it.toString().toInt() } }
        val top3 = PriorityQueue<Long>()
        top3.addAll(listOf(0, 0, 0))
        for (y in map.indices) {
            for (x in map[y].indices) {
                map.getBasinSize(y, x).takeIf { it > top3.peek() }?.let {
                    top3.add(it.toLong())
                    top3.remove()
                }
            }
        }
        return top3.reduce(Long::times)
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 5)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private fun List<List<Int>>.getBasinSize(y: Int, x: Int): Int {
    if (!isLowPoint(y, x)) return 0
    val basin = mutableSetOf<Pair<Int, Int>>()
    val nodesToVisit = mutableListOf(y to x)
    while (nodesToVisit.isNotEmpty()) {
        val currentNode = nodesToVisit.removeAt(0)
        nodesToVisit.addAll(neighborsOf(currentNode.first, currentNode.second).filter { it !in basin })
        basin.add(currentNode)
    }
    return basin.size
}

private fun List<List<Int>>.neighborsOf(y: Int, x: Int): List<Pair<Int, Int>> {
    val ret = mutableListOf<Pair<Int, Int>>()
    if (x != 0) addToIfNot9(ret, y, x - 1)
    if (x != this[y].indices.last) addToIfNot9(ret, y, x + 1)
    if (y != 0)addToIfNot9(ret, y - 1, x)
    if (y != indices.last) addToIfNot9(ret, y + 1, x)
    return ret
}

private fun List<List<Int>>.addToIfNot9(list: MutableList<Pair<Int, Int>>, y: Int, x: Int) {
    this[y][x].takeIf { it != 9 }?.let { list.add(y to x) }
}

private fun List<List<Int>>.isLowPoint(y: Int, x: Int): Boolean {
    var ret = true
    if (x != 0) ret = this[y][x] < this[y][x - 1]
    if (x != this[y].indices.last) ret = ret && this[y][x] < this[y][x + 1]
    if (y != 0) ret = ret && this[y][x] < this[y - 1][x]
    if (y != indices.last) ret = ret && this[y][x] < this[y + 1][x]
    return ret
}
