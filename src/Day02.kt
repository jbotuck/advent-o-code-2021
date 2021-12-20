fun main() {
    fun commands(input: List<String>) = input.map { s ->
        s.split(" ").let { it[0].toOperation() to it[1].toInt() }
    }
    fun part1(input: List<String>): Int {
        val commands = commands(input)
        return commands.fold(Sub()) { sub, command -> sub.executePart1(command.first, command.second) }.xTimesY()
    }
    fun part2(input: List<String>): Int {
        val commands = commands(input)
        return commands.fold(Sub()) { sub, command -> sub.executePart2(command.first, command.second) }.xTimesY()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 7)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private fun String.toOperation() = Operation.valueOf(uppercase())

enum class Operation {
    FORWARD, UP, DOWN;
}

data class Sub(val x: Int = 0, val y: Int = 0, val aim: Int = 0) {
    fun executePart1(operation: Operation, value: Int) = when (operation) {
        Operation.FORWARD -> copy(x = x + value)
        Operation.UP -> copy(y = y - value)
        Operation.DOWN -> copy(y = y + value)
    }
    fun executePart2(operation: Operation, value: Int) = when(operation){
        Operation.FORWARD -> copy(x = x + value, y = y + aim * value )
        Operation.UP -> copy(aim = aim - value)
        Operation.DOWN -> copy(aim = aim + value)
    }

    fun xTimesY() = x * y
}