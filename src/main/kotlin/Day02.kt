fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { Game(it).play() }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { Game(it).fewestCubes() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    val testInput2 = readInput("Day02_test")
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private class Game(input:String){
    /// Game 1: => 1
    val id = input.substringAfter("Game ").substringBefore(":").toInt()
    ///: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green => ({amount:3, color:"blue}, {amount:1, color:"red"}...)
    val rounds = input.substringAfter(":")
        .split(";")
        .flatMap { it.split(",")}
        .map { GameMove(it) }
    fun play():Int{
        // If a game is valid, return its id
        return if (isValid()) id else 0
    }
    fun isValid():Boolean{
        /// Game rules : 12 red cubes, 13 green cubes, and 14 blue cubes
        val limits = hashMapOf("red" to 12, "green" to 13, "blue" to 14)
        return rounds.all { r -> r.amount <= limits[r.color]!! }
    }

    fun fewestCubes():Int{
        /// Find the max value by color
        val red = rounds.filter { it.color.equals("red") }.maxBy { it.amount }
        val green = rounds.filter { it.color.equals("green") }.maxBy { it.amount }
        val blue = rounds.filter { it.color.equals("blue") }.maxBy { it.amount }
        /// The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together
        val power = red.amount * green.amount * blue.amount
        return power
    }
}

private class GameMove(input:String){
    private val tokens = input.split(" ")
    val amount = tokens[1].toInt()
    val color = tokens[2]
}
