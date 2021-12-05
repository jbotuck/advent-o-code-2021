fun main() {
    fun part1(input: List<String>): Int {
        val (numbersCalled, bingoBoards) = parseInput(input)
        val (bingoBoard, numberCalled) = findWinningBoard(numbersCalled, bingoBoards)
        return bingoBoard.calculateScore(numberCalled)
    }

    fun findLastWinningBoard(numbersCalled: MutableList<Int>, bingoBoards: List<BingoBoard>): Pair<BingoBoard, Int> {
        val boardsByIndex = bingoBoards.mapIndexed{i, board -> i to board}.toMap().toMutableMap()
        while (boardsByIndex.size > 1){
            val number = numbersCalled.removeFirst()
            val winningBoards = boardsByIndex.entries.mapNotNull { if(it.value.accept(number))it.key else null }
            for (index in winningBoards){
                boardsByIndex.remove(index)
            }
        }
        return findWinningBoard(numbersCalled, boardsByIndex.values)
    }

    fun part2(input: List<String>): Int {
        val (numbersCalled, bingoBoards) = parseInput(input)
        val (bingoBoard, numberCalled) = findLastWinningBoard(numbersCalled.toMutableList(), bingoBoards)
        return bingoBoard.calculateScore(numberCalled)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun findWinningBoard(numbers: List<Int>, boards: Collection<BingoBoard>): Pair<BingoBoard, Int> {
    for (i in numbers) {
        for (board in boards) {
            if (board.accept(i)) return board to i
        }
    }
    throw IllegalArgumentException("no one won")
}

fun emptyBoard() = (1..5).map { (1..5).map { false }.toMutableList() }.toMutableList()

data class BingoBoard(
    val spaces: Collection<Space>
) {
    init {
        require(!spaces.any { it.wasCalled }) { "not supported" }
    }

    private val spacesByBingoNumber = spaces.associateBy { it.bingoNumber }
    private val board = emptyBoard()

    //returns true if the board now wins
    fun accept(bingoNumber: Int): Boolean {
        spacesByBingoNumber[bingoNumber]?.let {
            it.wasCalled = true
            board[it.y][it.x] = true
            return hasWon(it.y, it.x)
        } ?: return false
    }

    private fun hasWon(y: Int, x: Int): Boolean {
        return board[y].all { it } || board.all { it[x] }
    }

    fun calculateScore(numberCalled: Int): Int {
        return spaces.filter { !it.wasCalled }.sumOf { it.bingoNumber } * numberCalled
    }
}

data class Space(val bingoNumber: Int, val y: Int, val x: Int, var wasCalled: Boolean = false)

fun parseInput(input: List<String>): Pair<List<Int>, List<BingoBoard>> {
    val numbers = input.first().split(",").map(String::toInt)
    val boards = mutableListOf<BingoBoard>()
    val boardsInput = input.subList(2, input.size)
    var currentBoard = mutableListOf<List<Int>>()
    for (line in boardsInput) {
        if (line.isEmpty()) {
            boards.add(buildBoard(currentBoard))
            currentBoard = mutableListOf()
            continue
        }
        currentBoard.add(line.split(" ").filter { it.isNotBlank() }.map(String::toInt))
    }
    boards.add(buildBoard(currentBoard))
    return numbers to boards
}

fun buildBoard(twoDList: List<List<Int>>): BingoBoard {
    return BingoBoard(twoDList.flatMapIndexed { y, list -> list.mapIndexed { x, elem -> Space(bingoNumber = elem, y = y, x = x) } })
}