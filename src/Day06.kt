fun main() {
    fun part1(input: List<String>): Long {
        val startingTotals = LongArray(9)
        input.first().split(",").map { it.toInt() }.forEach {
            startingTotals[it]++
        }
        for( i in 1..80){
            startingTotals.advanceOneDay()
        }
        return startingTotals.sum()
    }

    fun part2(input: List<String>): Long {
        val startingTotals = LongArray(9)
        input.first().split(",").map { it.toInt() }.forEach {
            startingTotals[it]++
        }
        for( i in 1..256){
            startingTotals.advanceOneDay()
        }
        return startingTotals.sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 5)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private fun LongArray.advanceOneDay() {
    val newMoms = this[0]
    for(i in this.indices){
        this[i] = when(i){
            6-> newMoms + this[7]
            8 -> newMoms
            else -> this[i + 1]
        }
    }
}
