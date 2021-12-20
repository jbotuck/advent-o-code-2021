fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        return numbers.indices.count { it > 0 && numbers[it] > numbers[it - 1] }
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        return numbers.indices.count{ it > 2 &&  numbers[it] > numbers[it - 3] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)


    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
