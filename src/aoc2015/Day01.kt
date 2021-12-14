package aoc2015

import readInput

fun main() {
    fun part1(input: List<String>): Int {
       var counter = 0
        for(c in input.first()){
            if(c == '(') counter++ else counter--
        }
        return counter
    }

    fun part2(input: List<String>): Int {
        var counter = 0
        for(i in input.first().indices){
            if(input.first()[i] == '(') counter++ else{
                counter--
                if (counter < 0) return i + 1
            }
        }
        return counter
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 7)

    val input = readInput("2015-Day01")
    println(part1(input))
    println(part2(input))
}