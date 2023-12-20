import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.math.min
import kotlin.system.measureTimeMillis

//    With my input I was running out of memory iterating over a list
//    measureTimeMillis {
//        (961540761..1451537511).toSet()
//            .forEach { it+1 }
//    }.also { println("ms: $it") }

fun main() {

    data class Block(val src: Long, val dst: Long, val length: Long)
    data class AlmanacMap(val from: String, val to: String, val blocks: List<Block>)

    fun parseAlmanac(input: List<String>): List<AlmanacMap> {
        return input.drop(2)
            .fold(mutableListOf(mutableListOf<String>())) { acc, line ->
                if (line.isBlank())
                    acc.add(mutableListOf())
                else
                    acc.last().add(line)
                acc
            }
            .map {
                val (from, _, to) = it.first().split('-', ' ')
                val blocks = it.drop(1).map {
                    val (dst, src, length) = it.split(" ").map { it.toLong() }
                    Block(src, dst, length)
                }
                AlmanacMap(from, to, blocks)
            }
    }

    fun getLocation(blocks: List<Block>, seed: Long): Long {
        // If there is no mapping on the block forward the same seed
        var loc = seed
        run findSeed@{
            blocks.forEach { block ->
                if (seed in block.src..block.src + block.length - 1) {
                    val offset = seed - block.src
                    loc = block.dst + offset
                    return@findSeed
                }
            }
        }
        return loc
    }

    fun part1(input: List<String>): Long {

        val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }
        val almanac = parseAlmanac(input)

        val locations = seeds.map { seed ->
            var loc = seed
            for (page in almanac) {
                loc = getLocation(page.blocks, loc)
            }
            loc
        }
        return locations.min()
    }


    fun part2(input: List<String>): Long {
        val seedRanges = input.get(0).split(": ").drop(1)
            .flatMap { it.split(" ") }
            .map { it.toLong() }
            .windowed(2, 2, false)
            .map { (start, length) -> LongRange(start, start + (length - 1)) }
        val almanac = parseAlmanac(input)

        var answer = 0L
        runBlocking(Dispatchers.Default) {
            val results = seedRanges.map { seedRange ->
                async {
                    var small = Long.MAX_VALUE
                    println("going through the first range $seedRange")
                    for (seed in seedRange) {
                        // Go through the pages of the almanac and find the location for each seed
                        var loc = seed
                        for (page in almanac) {
                            loc = getLocation(page.blocks, loc)
                        }
                        small = min(small, loc)
                    }
                    println("done with range: ${seedRange}")
                    small
                }
            }.awaitAll()
            answer = results.min()
        }
        return answer
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    val testInput2 = readInput("Day05_test")
    check(part2(testInput2) == 46L)

    val input = readInput("Day05")
    measureTimeMillis { part1(input).println() }.also { println("ms: $it") }
    measureTimeMillis { part2(input).println() }.also { println("ms: $it") }
}
