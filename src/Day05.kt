fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.map { it.toLine() }
        val points = mutableMapOf<Point, Int>()
        for (line in lines) {
            for (point in line.part1points()) {
                points[point] = (points[point] ?: 0) + 1
            }
        }
        return points.count { it.value > 1 }
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { it.toLine() }
        val points = mutableMapOf<Point, Int>()
        for (line in lines) {
            for (point in line.part2points()) {
                points[point] = (points[point] ?: 0) + 1
            }
        }
        return points.count { it.value > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun String.toLine(): Line {
    return split(" -> ").map { it.toPoint() }.let { Line(it.first(), it.last()) }
}

private fun String.toPoint(): Point {
    return split(",").map { it.toInt() }.let { Point(it.first(), it.last()) }
}

data class Line(val a: Point, val b: Point) {
    fun part1points(): List<Point> {
        if (a.x == b.x) return (minOf(a.y, b.y)..maxOf(a.y, b.y)).map { Point(a.x, it) }
        if (a.y == b.y) return (minOf(a.x, b.x)..maxOf(a.x, b.x)).map { Point(it, a.y) }
        return emptyList()
    }

    fun part2points(): List<Point> {
        part1points().takeIf { it.isNotEmpty() }?.let { return it }
        return if (a.x < b.x) {
            if (a.y < b.y) a.downRightTo(b) else a.upRightTo(b)
        } else if (b.y < a.y) b.downRightTo(a) else b.upRightTo(a)

    }
}

data class Point(val x: Int, val y: Int) {
    fun downRightTo(b: Point): List<Point> {
        val ret = mutableListOf<Point>()
        var step = 0
        while(x + step <= b.x){
            ret.add(Point(x + step, y + step))
            step++
        }
        return ret
    }

    fun upRightTo(b: Point): List<Point> {
        val ret = mutableListOf<Point>()
        var step = 0
        while(x + step <= b.x){
            ret.add(Point(x + step, y - step))
            step++
        }
        return ret
    }
}
