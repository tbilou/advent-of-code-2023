import kotlin.system.measureTimeMillis

fun main() {
    // | is a vertical pipe connecting north and south.
    // - is a horizontal pipe connecting east and west.
    // L is a 90-degree bend connecting north and east.
    // J is a 90-degree bend connecting north and west.
    // 7 is a 90-degree bend connecting south and west.
    // F is a 90-degree bend connecting south and east.
    // S is the start of the pipe.
    fun navigate(matrix: Matrix, piece: String, vector: String): String {
        when (piece) {
            "F" -> if (vector == "LEFT") {
                matrix.down()
                return "DOWN"
            } else if (vector == "UP") {
                matrix.right()
                return "RIGHT"
            }

            "|" -> if (vector == "DOWN") {
                matrix.down()
                return "DOWN"
            } else if (vector == "UP") {
                matrix.up()
                return "UP"
            }

            "L" -> if (vector == "DOWN") {
                matrix.right()
                return "RIGHT"
            } else if (vector == "LEFT") {
                matrix.up()
                return "UP"
            }

            "-" -> if (vector == "RIGHT") {
                matrix.right()
                return "RIGHT"
            } else if (vector == "LEFT") {
                matrix.left()
                return "LEFT"
            }

            "J" -> if (vector == "RIGHT") {
                matrix.up()
                return "UP"
            } else if (vector == "DOWN") {
                matrix.left()
                return "LEFT"
            }

            "7" -> if (vector == "UP") {
                matrix.left()
                return "LEFT"
            } else if (vector == "RIGHT") {
                matrix.down()
                return "DOWN"
            }
        }
        return ""
    }

    fun calculatePath(startPiece: String, startVector: String, matrix: Matrix): List<Coord> {
        var piece = startPiece
        var vector = startVector
        var steps = mutableListOf<Coord>()
        while (piece != "S") {
            vector = navigate(matrix, piece, vector)
            piece = matrix.elem()
            steps.add(matrix.pos())
        }
        return steps.toList()
    }

    fun initMatrix(input: List<String>): Matrix {
        val height = input.size
        val width = input.first().length
        val matrix = Matrix(width, height)
        matrix.fill(input)
        return matrix
    }

    fun shoelaceFormula(coords: List<Coord>): Int {
        var sum1 = 0
        var sum2 = 0
        for (i in 0 until coords.size - 1) {
            sum1 += coords[i].row * coords[i + 1].col
            sum2 += coords[i].col * coords[i + 1].row
        }
        sum1 += coords.last().row * coords.first().col
        sum2 += coords.last().col * coords.first().row
        return (0.5 * Math.abs(sum1 - sum2)).toInt()
    }

    fun part1(input: List<String>): Int {
        val matrix = initMatrix(input)
        val start = matrix.find("S")
        matrix.goto(start!!.row, start!!.col)

        // Hardcoded starting piece and vector (only works because the staring piece is an F)
        var piece = "F"
        var vector = "LEFT"

        // Navigate
        val path = calculatePath(piece, vector, matrix)

        return path.size / 2
    }

    fun part2(input: List<String>): Int {
        // Picks theorem (gives you the number of points inside a polygon)
        // A = i + b/2 - 1
        // A -> Area of the polygon
        // B -> Number of points on the boundary
        // I -> Number of points inside the polygon


        // Shoelace formula (gives you the area of a polygon)
        // A = 1/2 * |(x1y2 + x2y3 + ... + xn-1yn + xny1) - (y1x2 + y2x3 + ... + yn-1x1 + ynx1)|

        // Coordinates of the vertices of the polygon
        val matrix = initMatrix(input)
        val start = matrix.find("S")
        matrix.goto(start!!.row, start!!.col)
        val path = calculatePath("F", "LEFT", matrix)
        val area = shoelaceFormula(path.filter { matrix.elemAt(it) !in listOf("-", "|") })
        val I = area - path.size / 2 + 1


        return I
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test1")
    check(part1(testInput) == 4)
    val testInput2 = readInput("Day10_test")
    check(part2(testInput2) == 4)

    val input = readInput("Day10")
    measureTimeMillis { part1(input).println() }.also { println("ms: $it") }
    measureTimeMillis { part2(input).println() }.also { println("ms: $it") }
}


// This matrix can be used in 2 ways
// 1. Given a starting position, you can move the "cursor" around the matrix (up, down, left, right)
// 2. It can also give you the neighbours of a given position
class Matrix(width: Int, height: Int) {

    // Initialize the matrix with dots .
    private val grid: Array<Array<String>> = Array(width) {
        Array(height) { "." }
    }

    private var currentPosition = Coord(0, 0)

    fun fill(data: List<String>) {
        data.forEachIndexed { row, line ->
            line.forEachIndexed { col, elem ->
                grid[row][col] = elem.toString()
            }
        }
    }

    fun elem(): String {
        return grid[currentPosition.row][currentPosition.col]
    }

    fun elemAt(Coord: Coord): String {
        return grid[Coord.row][Coord.col]
    }

    // For part2 only
    fun pos(): Coord {
        return currentPosition
    }

    fun setElement(element: String) {
        grid[currentPosition.row][currentPosition.col] = element
    }

    fun goto(row: Int, col: Int) {
        currentPosition = Coord(row, col)
    }

    fun find(needle: String): Coord? {
        grid.forEachIndexed { row, line ->
            if (line.indexOf(needle) != -1) {
                return Coord(row, line.indexOf(needle))
            }
        }
        return null
    }

    fun up() {
        if (currentPosition.row > 0) {
            currentPosition = Coord(currentPosition.row - 1, currentPosition.col)
        }
    }

    fun down() {
        if (currentPosition.row < grid.size - 1) {
            currentPosition = Coord(currentPosition.row + 1, currentPosition.col)
        }
    }

    fun left() {
        if (currentPosition.col > 0) {
            currentPosition = Coord(currentPosition.row, currentPosition.col - 1)
        }
    }

    fun right() {
        if (currentPosition.col < grid[0].size - 1) {
            currentPosition = Coord(currentPosition.row, currentPosition.col + 1)
        }
    }

    fun around(): List<Neighbour> {
        return getNeighbours(currentPosition)
    }

    fun getNeighbours(coord: Coord): List<Neighbour> {
        return listOfNotNull(
            getUp(coord),
            getDown(coord),
            getLeft(coord),
            getRight(coord)
        )
    }

    private fun getUp(coord: Coord): Neighbour? {
        val row = coord.row
        if (row > 0) {
            val neighbour = Coord(row - 1, coord.col)
            return Neighbour("UP", neighbour, grid[neighbour.row][neighbour.col])
        } else {
            return null
        }
    }

    private fun getDown(coord: Coord): Neighbour? {
        val row = coord.row
        if (row < grid.size - 1) {
            val neighbour = Coord(row + 1, coord.col)
            return Neighbour("DOWN", neighbour, grid[neighbour.row][neighbour.col])
        } else {
            return null
        }
    }

    private fun getLeft(coord: Coord): Neighbour? {
        val col = coord.col
        if (col > 0) {
            val neighbour = Coord(coord.row, col - 1)
            return Neighbour("LEFT", neighbour, grid[neighbour.row][neighbour.col])
        } else {
            return null
        }
    }

    private fun getRight(coord: Coord): Neighbour? {
        val col = coord.col
        if (col < grid[0].size - 1) {
            val neighbour = Coord(coord.row, col + 1)
            return Neighbour("RIGHT", neighbour, grid[neighbour.row][neighbour.col])
        } else {
            return null
        }
    }

    fun print() {
        grid.forEach { line ->
            line.forEach { print(" $it ") }
            println("")
        }
    }
}

data class Coord(val row: Int, val col: Int)
data class Neighbour(val location: String, val coord: Coord, val element: String)