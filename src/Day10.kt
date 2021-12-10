fun main() {
    val closeToOpen = mapOf('>' to '<', ')' to '(', ']' to '[', '}' to '{')
    val closeToScore1 = mapOf(')' to 3L, ']' to 57, '}' to 1197, '>' to 25137)
    fun part1(input: List<String>): Long {
        return input.sumOf {
            val stack = mutableListOf<Char>()
            for (c in it) {
                if (c !in closeToOpen.keys) stack.add(c)
                else {
                    if (stack.isEmpty() || closeToOpen[c] != stack.removeLast()) return@sumOf closeToScore1[c]!!
                }
            }
            0
        }
    }
//    ): 1 point.
//    ]: 2 points.
//}: 3 points.
//>: 4 points.
    val openToScore = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
    fun part2score(line: String): Long? {
        val stack = mutableListOf<Char>()
        for (c in line) {
            if (c !in closeToOpen.keys) stack.add(c)
            else {
                if (stack.isEmpty() || closeToOpen[c] != stack.removeLast()) return null
            }
        }
        var score = 0L
        while (stack.isNotEmpty()) {
            score = 5 * score + openToScore[stack.removeLast()]!!
        }
        return score
    }

    fun part2(input: List<String>): Long {
        val sorted = input.mapNotNull { part2score(it) }.sorted()
        return sorted[sorted.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}


