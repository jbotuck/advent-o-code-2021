import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        val grid: List<MutableList<Octopus>> =
            input.map { line -> line.map { NotFlashed(it.toString().toInt()) }.toMutableList() }
        var flashCount = 0L
        repeat(100) {
            flashCount += grid.executeStep()
        }
        return flashCount
    }

    fun part2(input: List<String>): Long {
        val grid: List<MutableList<Octopus>> =
            input.map { line -> line.map { NotFlashed(it.toString().toInt()) }.toMutableList() }
        repeat(Int.MAX_VALUE) {
            if(grid.executeStep() == 100) return it.toLong() + 1
        }
       throw IllegalArgumentException()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day10_test")
//    check(part1(testInput) == 26397L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

sealed class Octopus {
    abstract fun increment()
    abstract fun needsToFlash(): Boolean
}

class NotFlashed(energy: Int) : Octopus() {
    var energy = energy
        private set

    override fun increment() {
        energy++
    }

    override fun needsToFlash(): Boolean {
        return energy > 9
    }

    override fun toString(): String {
        return energy.toString()
    }
}

object Flashed : Octopus() {
    override fun increment() {
        //noop
    }

    override fun needsToFlash(): Boolean {
        return false
    }

    override fun toString(): String {
        return "F"
    }
}

private fun List<MutableList<Octopus>>.executeStep(): Int {
    var flashCount = 0
    for (l in this) {
        for (i in l.indices) {
            l[i].increment()
        }
    }
    var wasIncremented = true
    while (wasIncremented) {
        wasIncremented = false
        forEachIndexed { y, l ->
            for (x in l.indices) {
                if (l[x].needsToFlash()) {
                    wasIncremented = true
                    l[x] = Flashed
                    incrementSurrounding(y, x)
                    flashCount++
                }
            }
        }
    }
    forEachIndexed { y, l ->
        for (x in l.indices) {
            if (l[x] == Flashed) l[x] = NotFlashed(0)
        }
    }
    return flashCount
}

fun List<MutableList<Octopus>>.incrementSurrounding(y: Int, x: Int) {
    val minY = max(y - 1, 0)
    val maxY = min(y + 1, 9)
    val minX = max(x - 1, 0)
    val maxX = min(x + 1, 9)
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            this[y][x].increment()
        }
    }
}


