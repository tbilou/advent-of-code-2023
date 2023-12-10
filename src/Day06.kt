import kotlin.system.measureTimeMillis

fun main() {

    fun part1(input: List<String>): Int {
        val times = input.get(0)
            .substringAfter(": ")
            .split(" ")
            .filter { it.isNotBlank() }
        val distances = input.get(1)
            .substringAfter(": ")
            .split(" ")
            .filter { it.isNotBlank() }

        val races = times.zip(distances) { t, d -> Race(t.toInt(), d.toInt()) }.toList()

        val solutions: MutableList<Int> = mutableListOf()

        for (race in races) {
            solutions.add((0..race.time).map {
                println("time:$it distance:${it * (race.time - it)}")
                it * (race.time - it)
            }.toList().filter { it > race.distance }.count())
        }
        return solutions.reduce { acc, i -> acc * i }
    }


    fun part2(input: List<String>): Long {
        val time = input.get(0)
            .substringAfter(": ")
            .replace(" ","")
            .toLong()
        val distance = input.get(1)
            .substringAfter(": ")
            .replace(" ","")
            .toLong()

        val count = (0..time).map {
            println("time:$it distance:${it * (time - it)}")
            it * (time - it)
        }.toList().filter { it > distance }.count().toLong()

        return count
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    val testInput2 = readInput("Day06_test")
    check(part2(testInput2) == 71503L)

    val input = readInput("Day06")
    measureTimeMillis { part1(input).println() }.also { println("ms: $it") }
    measureTimeMillis { part2(input).println() }.also { println("ms: $it") }
}

data class Race(val time: Int, val distance: Int)