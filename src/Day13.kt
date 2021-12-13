fun main() {
    fun part1(input: List<String>): Int {
        val blankLine = input.indexOfFirst { it.isEmpty() }
        val coordinates =
            input.subList(0, blankLine).map { it.split(',') }.map { it.first().toInt() to it.last().toInt() }
        val foldInstructions = input.subList(blankLine + 1, input.size)
            .map { it.substringAfter("fold along ").split("=") }
            .map { Instruction(it.first() == "x", it.last().toInt()) }
        return coordinates.map { foldInstructions.first().transform(it) }.toSet().size
    }

    fun dotify(coordinates: Set<Pair<Int, Int>>): String {
        val maxX = coordinates.maxOf { it.first }
        val maxY = coordinates.maxOf { it.second }
        val matrix = Array(maxY + 1) { CharArray(maxX + 1){'.'} }
        for (pair in coordinates) matrix[pair.second][pair.first] = '#'
        println()
        for(y in matrix.indices){
            for(x in matrix[y].indices) print(matrix[y][x])
            println()
        }
        return "done"
    }

    fun part2(input: List<String>): String {
        val blankLine = input.indexOfFirst { it.isEmpty() }
        var coordinates =
            input.subList(0, blankLine).map { it.split(',') }.map { it.first().toInt() to it.last().toInt() }.toSet()
        val foldInstructions = input.subList(blankLine + 1, input.size)
            .map { it.substringAfter("fold along ").split("=") }
            .map { Instruction(it.first() == "x", it.last().toInt()) }
        for (instruction in foldInstructions) {
            coordinates = coordinates.map { instruction.transform(it) }.toSet()
        }
        return dotify(coordinates)
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day10_test")
//    check(part1(testInput) == 26397L)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

data class Instruction(val isX: Boolean, val foldPoint: Int) {
    fun transform(coordinates: Pair<Int, Int>): Pair<Int, Int> {
        return if (isX) reflect(coordinates.first, foldPoint) to coordinates.second else coordinates.first to reflect(
            coordinates.second,
            foldPoint
        )
    }

    private fun reflect(origin: Int, foldPoint: Int): Int {
        if (origin <= foldPoint) return origin
        return foldPoint - (origin - foldPoint)
    }

}