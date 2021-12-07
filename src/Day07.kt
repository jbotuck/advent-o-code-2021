import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Long {
        val positions = input.first().split(",").map(String::toLong)
        val min = positions.minOrNull()!!
        val max = positions.maxOrNull()!!
        return (min..max).minOf { target -> positions.sumOf { abs(target - it) } }
    }

    fun sumUpTo(l: Long): Long {
        return l * (l + 1) / 2
    }

    fun part2(input: List<String>): Long {
        val positions = input.first().split(",").map(String::toLong)
        val min = positions.minOrNull()!!
        val max = positions.maxOrNull()!!
        return (min..max).minOf { target -> positions.sumOf { sumUpTo(abs(target - it)) } }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 5)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}