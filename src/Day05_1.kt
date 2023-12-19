import kotlin.system.measureTimeMillis

fun main() {

    data class Block(val src: Long, val dst: Long, val length: Long)
    data class AlmanacMap(val from: String, val to: String, val blocks: List<Block>)

    fun part1(input: List<String>): Long {

        val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }

        val almanac = input.drop(2)
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

        val r = seeds.map { seed ->
            val ll = almanac.fold(mutableListOf(seed)) { ret, page ->
                val size = ret.size
                val seed = ret.last()
                run findSeed@{
                    page.blocks.forEach { block ->
                        if (seed in block.src..block.src + block.length) {
                            val offset = seed - block.src
                            ret.add(block.dst + offset)
                            return@findSeed
                        }
                    }
                }
                if (size == ret.size) {
                    ret.add(ret.last())
                }
                ret
            }
                .also { println(it) }
            ll
        }
        return r.map { it.last() }.min()
    }

    fun part2(input: List<String>): Long {
        val seedRanges = input.get(0).split(": ").drop(1)
            .flatMap { it.split(" ") }
            .map { it.toLong() }
            .windowed(2, 2, false)
            .map { LongRange(it.first(), it.first() + it.last() - 1) }

        // Build the almanac (each page is a map)
        val almanac = input.drop(2)
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

//        val r = seedRanges.map { seedRange ->
//            seedRange.map { seed ->
        var result = mutableSetOf(Long.MAX_VALUE)
        for (i in 0..<seedRanges.size) {

            println("going through the first range ${seedRanges[i]}")
            for (i in seedRanges[i]){
                val seed = i
//            }
//            val locations = seedRanges[i].map { seed ->
//                println("seed: $seed")
                val location = almanac.fold(seed) { ret, page ->
                    val seed = ret
                    var nextSeed = ret
                    for (j in 0..<page.blocks.size) {
                        val block = page.blocks[j]
                        if (seed in block.src..block.src + block.length) {
                            val offset = seed - block.src
                            nextSeed = block.dst + offset
                            break
                        }
                    }
//                    println("page: ${page.from}->${page.to} : ${nextSeed}")
                    nextSeed
                }
//                    .also { println("final number is ${it}") }

                // Check if the solutions is smaller than any other one
                if (result.min() > location)
                {
                    result.clear()
                    result.add(location)
                }
            }

            println("done with range: ${seedRanges[i]}")
        }
        println(result)
        return result.min()
//return 0L
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 35L)
    val testInput2 = readInput("Day05_test")
    check(part2(testInput2) == 46L)

//    measureTimeMillis {
//        (961540761..1451537511).toSet()
//            .forEach { it+1 }
//    }.also { println("ms: $it") }

    val input = readInput("Day05")
//    measureTimeMillis { part1(input).println() }.also { println("ms: $it") }
    measureTimeMillis { part2(input).println() }.also { println("ms: $it") }
}
