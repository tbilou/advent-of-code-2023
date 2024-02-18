import kotlin.system.measureTimeMillis

fun main() {

    // 0   3   6   9  12  15
    // subtract the numbers (3-0) (6-3) (9-6) (12-9) (15-12) and return a new list with the results
    fun nextSequence(list: List<Int>): List<Int> {
        return list.windowed(2, 1, false).map { (a, b) -> b - a }

    }

    // 0   3   6   9  12  15
    //  3   3   3   3   3
    //    0   0   0   0

    fun part1(input: List<String>): Int {
        // Keep track of the last number in the sequence. From the example above, it would be 15,3 and 0
        var partials = mutableListOf<Int>()
        return input.sumOf { line ->
            val history = line.split(" ").map { it.toInt() }

            partials.add(history.last())
            var sequence = history
            while (true){
                sequence = nextSequence(sequence)
                if (sequence.all { it == 0 }){
                    break
                }
                else{
                    partials.add(sequence.last())
                }
            }
            partials.fold(0) { acc, value -> value + acc }
                .also { partials.clear() }
        }

    }

    fun part2(input: List<String>): Int {
        var partials = mutableListOf<Int>()
        return input.sumOf { line ->
            val history = line.split(" ").map { it.toInt() }

            partials.add(history.first())
            var sequence = history
            while (true){
                val response = nextSequence(sequence)
                sequence = response
                if (response.all { it == 0 }){
                    break
                }
                else{
                    partials.add(response.first())
                }
            }
            partials.reversed().fold(0) { acc, value -> value - acc }
                .also { partials.clear() }
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    val testInput2 = readInput("Day09_test")
    check(part2(testInput2) == 2)

    val input = readInput("Day09")
    measureTimeMillis { part1(input).println() }.also { println("ms: $it") }
    measureTimeMillis { part2(input).println() }.also { println("ms: $it") }
}