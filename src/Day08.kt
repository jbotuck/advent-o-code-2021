fun main() {
    fun part1(input: List<String>): Long {
        val lines = input.map { line ->
            line.split(" | ").map { it.split(" ") }
        }
        return lines.sumOf { line -> line[1].count { it.length in listOf(2, 3, 4, 7) } }.toLong()

    }

    fun part2(input: List<String>): Long {
        val lines = input.map { line ->
            line.split(" | ").map { it.split(" ") }
        }
        return lines.sumOf { it.toValue() }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 5)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private fun List<List<String>>.toValue(): Long {
    val map = this[0].toMap()
    return this[1].joinToString(separator = "") { map[it.toSet()]!! }.toLong()
}

//0:      1:      2:      3:      4:
//aaaa    ....    aaaa    aaaa    ....
//b    c  .    c  .    c  .    c  b    c
//b    c  .    c  .    c  .    c  b    c
//....    ....    dddd    dddd    dddd
//e    f  .    f  e    .  .    f  .    f
//e    f  .    f  e    .  .    f  .    f
//gggg    ....    gggg    gggg    ....
//
//5:      6:      7:      8:      9:
//aaaa    aaaa    aaaa    aaaa    aaaa
//b    .  b    .  .    c  b    c  b    c
//b    .  b    .  .    c  b    c  b    c
//dddd    dddd    ....    dddd    dddd
//.    f  e    f  .    f  e    f  .    f
//.    f  e    f  .    f  e    f  .    f
//gggg    gggg    ....    gggg    gggg

private fun List<String>.toMap(): Map<Set<Char>, String> {
    val unInterpretedNotes = this.map { it.toSet() }.toMutableSet()
    val stringsByKnownValue = mutableMapOf<String, Set<Char>>()
    stringsByKnownValue["1"] = unInterpretedNotes.take{it.size == 2}
    stringsByKnownValue["7"] = unInterpretedNotes.take { it.size == 3 }
    stringsByKnownValue["4"] = unInterpretedNotes.take { it.size == 4 }
    stringsByKnownValue["8"] = unInterpretedNotes.take { it.size == 7}
    stringsByKnownValue["3"] = unInterpretedNotes.take { it.size == 5 && it.containsAll(stringsByKnownValue["1"]!!) }
    stringsByKnownValue["6"] = unInterpretedNotes.take { it.size == 6 && !it.containsAll(stringsByKnownValue["1"]!!)}
    stringsByKnownValue["9"] = unInterpretedNotes.take { it.size == 6 && it.containsAll(stringsByKnownValue["3"]!!)}
    stringsByKnownValue["0"] = unInterpretedNotes.take { it.size == 6 }
    stringsByKnownValue["5"] = unInterpretedNotes.take{stringsByKnownValue["9"]!!.containsAll(it)}
    stringsByKnownValue["2"] = unInterpretedNotes.first()
    return stringsByKnownValue.map { it.value to it.key }.toMap()
}
private fun MutableSet<Set<Char>>.take(predicate: (Set<Char>) -> Boolean) : Set<Char>{
    return this.first(predicate).also { remove(it) }
}
