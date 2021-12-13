fun main() {
    fun part1(input: List<String>): Long {
        val edges = input.map { line ->
            line.split("-").map { Node(it) }.let { Edge(it.first(), it.last()) }
        }
        val start = Node("start")
        val end = Node("end")
        val pathsToVisit =
            edges.mapNotNull { edge -> edge.nextNodeFrom(start)?.let { listOf(start, it) } }.toMutableList()
        var count = 0L
        while (pathsToVisit.isNotEmpty()) {
            val path = pathsToVisit.removeLast()
            for (node in edges.mapNotNull { it.nextNodeFrom(path.last()) }) {
                if (node == end) {
                    count++
                    continue
                }
                if (!node.isBig && path.contains(node)) continue
                pathsToVisit.add(path + node)
            }
        }
        return count
    }

    fun part2(input: List<String>): Long {
        val edges = input.map { line ->
            line.split("-").map { Node(it) }.let { Edge(it.first(), it.last()) }
        }
        val start = Node("start")
        val end = Node("end")
        val pathsToVisit =
            edges.mapNotNull { edge -> edge.nextNodeFrom(start)?.let { listOf(start, it) } }.toMutableList()
        var count = 0L
        while (pathsToVisit.isNotEmpty()) {
            val path = pathsToVisit.removeLast()
            for (node in edges.mapNotNull { it.nextNodeFrom(path.last()) }) {
                if (node == end) {
                    count++
                    continue
                }
                if(node == start) continue
                if (!node.isBig && path.contains(node) && path.filter { !it.isBig }.groupBy{it}.any { it.value.size ==2 } ) continue
                pathsToVisit.add(path + node)
            }
        }
        return count    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day10_test")
//    check(part1(testInput) == 26397L)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

data class Node(val name: String) {
    val isBig = name.first().isUpperCase()
}

data class Edge(val a: Node, val b: Node) {
    fun nextNodeFrom(s: String) = nextNodeFrom(Node(s))

    fun nextNodeFrom(from: Node): Node? {
        if (a == from) return b
        if (b == from) return a
        return null
    }
}
