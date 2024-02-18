import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    var seed2soil: List<Banana> = listOf()
    var soil2fertilizer: List<Banana> = listOf()
    var fertilizer2water: List<Banana> = listOf()
    var water2light: List<Banana> = listOf()
    var light2temperature: List<Banana> = listOf()
    var temperature2humidity: List<Banana> = listOf()
    var humidity2location: List<Banana> = listOf()


    fun getDestination(l: List<Banana>, seed: Long): Long {
        var dest = seed
        // narrow the search to a single list
        measureTimeMillis {
            l.filter { (it.sourceRangeStart <= seed && it.sourceRangeEnd >= seed) }
                .forEach {
                    dest = it.destinationRangeStart + (seed - it.sourceRangeStart)
                }
        }
        return dest
    }

    fun getLocationForSeed(seed: Long): Long {

        var soil = getDestination(seed2soil, seed)
        var fertilizer = getDestination(soil2fertilizer, soil)
        val water = getDestination(fertilizer2water, fertilizer)
        val light = getDestination(water2light, water)
        val temp = getDestination(light2temperature, light)
        val humidity = getDestination(temperature2humidity, temp)
        val location = getDestination(humidity2location, humidity)
//        println("$seed->$fertilizer->$water->$light->$temp->$humidity->$location")
        return location
    }

    fun calculateMaps(input: List<String>) {
        // seed-to-soil
        seed2soil = input.get(1).split("\n").drop(1)
            .map { it.split(" ") }
            .map { (d, s, l) -> Banana(d.toLong(), s.toLong(), l.toLong()) }

        // soil-to-fertilizer
        soil2fertilizer = input.get(2).split("\n").drop(1)
            .map { it.split(" ") }
            .map { (d, s, l) -> Banana(d.toLong(), s.toLong(), l.toLong()) }

        // fertilizer-to-water
        fertilizer2water = input.get(3).split("\n").drop(1)
            .map { it.split(" ") }
            .map { (d, s, l) -> Banana(d.toLong(), s.toLong(), l.toLong()) }

        //water-to-light
        water2light = input.get(4).split("\n").drop(1)
            .map { it.split(" ") }
            .map { (d, s, l) -> Banana(d.toLong(), s.toLong(), l.toLong()) }

        //light-to-temperature
        light2temperature = input.get(5).split("\n").drop(1)
            .map { it.split(" ") }
            .map { (d, s, l) -> Banana(d.toLong(), s.toLong(), l.toLong()) }

        //temperature-to-humidity
        temperature2humidity = input.get(6).split("\n").drop(1)
            .map { it.split(" ") }
            .map { (d, s, l) -> Banana(d.toLong(), s.toLong(), l.toLong()) }

        //humidity-to-location
        humidity2location = input.get(7).split("\n").drop(1)
            .map { it.split(" ") }
            .map { (d, s, l) -> Banana(d.toLong(), s.toLong(), l.toLong()) }
    }


    fun part1(input: List<String>): Long {

        measureTimeMillis { calculateMaps(input) }
//            .also { println("time to calculate maps: $it") }


        val seeds: List<Long> = input.get(0).split(": ").drop(1)
            .flatMap { it.split(" ") }
            .map { it.toLong() }

        val seed2location = seeds.map { seed ->
            var location = 0L
            measureTimeMillis {
                location = getLocationForSeed(seed)
            }
//                .also { println("time to iterate over the maps $it") }
            location
        }
        val min = seed2location.min()

        return min
    }


    fun part2(input: List<String>): Long {
        calculateMaps(input)

        val seedRanges = input.get(0).split(": ").drop(1)
            .flatMap { it.split(" ") }
            .map { it.toLong() }
            .windowed(2, 2, false)
        var results: MutableList<Long> = mutableListOf()

        for (seedRange in seedRanges) {
            runBlocking {
//            results.add()
                val seeds = (seedRange.first()..seedRange.first() + seedRange.last() - 1).toSet()
                val r1 = seeds.asFlow()
                    .also { println("processing ${seeds.size}") }
                    .map { seed -> getLocationForSeed(seed) }
                    .toSet()
                results.add(r1.min())
            }

//            val seeds = (seedRange.first()..seedRange.first() + seedRange.last() - 1).toSet()
//
//            val r = seeds.parallelStream()
//                .also { println("processing ${seeds.size}") }
//                .map { seed -> getLocationForSeed(seed) }
//                .toList()
//
//            results.add(r.min())
        }

        return results.min()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInputSplitByNewline("Day05_test")
    check(part1(testInput) == 35L)
    val testInput2 = readInputSplitByNewline("Day05_test")
    check(part2(testInput2) == 46L)

    val input = readInputSplitByNewline("Day05")
    measureTimeMillis { part1(input).println() }.also { it.println() }
    measureTimeMillis { part2(input).println() }.also { it.println() }
}


data class Banana(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long
) {
    private val range: LongRange = sourceRangeStart..sourceRangeStart + rangeLength - 1
    val sourceRangeEnd: Long
        get() = sourceRangeStart + rangeLength - 1

    fun destination(source: Long): Long? {
        val index = range.indexOf(source)
        if (index >= 0) {
            val destination = destinationRangeStart + index
            return destination
        }
        return null
    }

    fun destination2(source: Long): Boolean {
        return range.contains(source)
    }
}