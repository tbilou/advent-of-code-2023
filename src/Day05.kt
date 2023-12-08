import kotlin.system.measureTimeMillis

fun main() {

    var seed2soil: List<Banana> = listOf()
    var soil2fertilizer: List<Banana> = listOf()
    var fertilizer2water: List<Banana> = listOf()
    var water2light: List<Banana> = listOf()
    var light2temperature: List<Banana> = listOf()
    var temperature2humidity: List<Banana> = listOf()
    var humidity2location: List<Banana> = listOf()


    fun getLocationForSeed(seed: Long): Long {
//        println("Seed number: $seed")
        var soil = seed2soil.map { b -> b.destination(seed) }
            .filter { (it != null) }
            .elementAtOrElse(0) { seed }
        val fertilizer = soil2fertilizer.map { b -> b.destination(soil!!) }
            .filter { (it != null) }
            .elementAtOrElse(0) { soil }
        val water = fertilizer2water.map { b -> b.destination(fertilizer!!) }
            .filter { (it != null) }
            .elementAtOrElse(0) { fertilizer }
        val light = water2light.map { b -> b.destination(water!!) }
            .filter { (it != null) }
            .elementAtOrElse(0) { water }
        val temp = light2temperature.map { b -> b.destination(light!!) }
            .filter { (it != null) }
            .elementAtOrElse(0) { light }
        val humidity = temperature2humidity.map { b -> b.destination(temp!!) }
            .filter { (it != null) }
            .elementAtOrElse(0) { temp }
        val location = humidity2location.map { b -> b.destination(humidity!!) }
            .filter { (it != null) }
            .elementAtOrElse(0) { humidity }
        return location!!
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
            .also { println("time to calculate maps: $it") }


        val seeds: List<Long> = input.get(0).split(": ").drop(1)
            .flatMap { it.split(" ") }
            .map { it.toLong() }

        val seed2location = seeds.map { seed ->
            var location = 0L
            measureTimeMillis {
                location = getLocationForSeed(seed)
            }.also { println("time to iterave over the maps $it") }
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
        var results:MutableList<Long> = mutableListOf()

        for (seedRange in seedRanges)
        {
            results.add( (seedRange.first()..seedRange.first()+seedRange.last()-1).toSet()
//                .also { println(it) }
                .map { seed -> getLocationForSeed(seed) }
                .min()
            )
        }

        return results.min()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput2("Day05_test")
    check(part1(testInput) == 35L)
    val testInput2 = readInput2("Day05_test")
    check(part2(testInput2) == 46L)

    val input = readInput2("Day05")
//    part1(input).println()
//    part2(input).println()
}

data class Banana(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long
) {
//    private val range: LongRange = sourceRangeStart..<sourceRangeStart + rangeLength -1
    fun destination(source: Long): Long? {
        val sourceRange = sourceRangeStart..sourceRangeStart + rangeLength - 1
        val contains = sourceRange.contains(source)
        if (contains) {
            val index = sourceRange.indexOf(source)
            val destination = destinationRangeStart + index
            return destination
        }
        return null
    }
}