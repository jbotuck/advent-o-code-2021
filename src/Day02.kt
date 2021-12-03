fun main() {
    fun part1(input: List<String>): Int {
        val commands = input.map {
                s -> s.split(" ").let { it[0] to it[1].toInt() }
        }
        var depth = 0
        var horizontal = 0
        for(command in commands){
            when(command.first){
                "forward" -> horizontal += command.second
                "up" -> depth -= command.second
                "down" -> depth += command.second
            }
        }
        return depth * horizontal
    }

    fun part2(input: List<String>): Int {
        val commands = input.map {
                s -> s.split(" ").let { it[0] to it[1].toInt() }
        }
        var depth = 0
        var horizontal = 0
        var aim = 0
        for(command in commands){
            when(command.first){
                "forward" -> {
                    horizontal += command.second
                    depth += command.second * aim
                }
                "up" -> aim -= command.second
                "down" -> aim += command.second
            }
        }
        return depth * horizontal
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 7)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}