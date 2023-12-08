fun main() {

    val seed2soil:MutableMap<Long, Long> = mutableMapOf()
    val soil2fertilizer:MutableMap<Long, Long> = mutableMapOf()
    val fertilizer2water:MutableMap<Long, Long> = mutableMapOf()
    val water2light:MutableMap<Long, Long> = mutableMapOf()
    val light2temperature:MutableMap<Long, Long> = mutableMapOf()
    val temperature2humidity:MutableMap<Long, Long> = mutableMapOf()
    val humidity2location:MutableMap<Long, Long> = mutableMapOf()


    fun getLocationForSeed(seed: Long): Long {
        val soil = seed2soil.getOrDefault(seed, seed)
        val fertilizer = soil2fertilizer.getOrDefault(soil, soil)
        val water = fertilizer2water.getOrDefault(fertilizer, fertilizer)
        val light = water2light.getOrDefault(water, water)
        val temp = light2temperature.getOrDefault(light, light)
        val humidity = temperature2humidity.getOrDefault(temp, temp)
        val location = humidity2location.getOrDefault(humidity, humidity)
        return location
    }

    fun part1(input: List<String>): Long {




        val seeds:List<Long> = input.get(0).split(": ").drop(1)
            .flatMap { it.split(" ") }
            .map {it.toLong()}

        // seed-to-soil
        input.get(1).split("\n").drop(1)
            .map{it.split(" ")}
            .map { (d,s,l)-> Banana(d.toLong(),s.toLong(),l.toLong()) }
            .map {b -> b.expandRange(seed2soil)}

        // soil-to-fertilizer
        input.get(2).split("\n").drop(1)
            .map{it.split(" ")}
            .map { (d,s,l)-> Banana(d.toLong(),s.toLong(),l.toLong()) }
            .map {b -> b.expandRange(soil2fertilizer)}

        // fertilizer-to-water
        input.get(3).split("\n").drop(1)
            .map{it.split(" ")}
            .map { (d,s,l)-> Banana(d.toLong(),s.toLong(),l.toLong()) }
            .map {b -> b.expandRange(fertilizer2water)}

        //water-to-light
        input.get(4).split("\n").drop(1)
            .map{it.split(" ")}
            .map { (d,s,l)-> Banana(d.toLong(),s.toLong(),l.toLong()) }
            .map {b -> b.expandRange(water2light)}

        //light-to-temperature
        input.get(5).split("\n").drop(1)
            .map{it.split(" ")}
            .map { (d,s,l)-> Banana(d.toLong(),s.toLong(),l.toLong()) }
            .map {b -> b.expandRange(light2temperature)}

        //temperature-to-humidity
        input.get(6).split("\n").drop(1)
            .map{it.split(" ")}
            .map { (d,s,l)-> Banana(d.toLong(),s.toLong(),l.toLong()) }
            .map {b -> b.expandRange(temperature2humidity)}

        //humidity-to-location
        input.get(7).split("\n").drop(1)
            .map{it.split(" ")}
            .map { (d,s,l)-> Banana(d.toLong(),s.toLong(),l.toLong()) }
            .map {b -> b.expandRange(humidity2location)}

//       seeds.sumOf { seed ->
        val total = seeds.map { seed -> getLocationForSeed(seed) }.min()
        return total
    }


    fun part2(input: List<String>): Int {
        return input.count()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput2("Day05_test")
    check(part1(testInput) == 35L)
//    val testInput2 = readInput("Day05_test")
//    check(part2(testInput2) == 30)

//    val input = readInput("Day05")
//    part1(input).println()
//    part2(input).println()
}

data class Banana(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long
) {
    fun expandRange(map: MutableMap<Long, Long>) {
        for (i in 0..rangeLength-1){
            map[sourceRangeStart+i] = destinationRangeStart+i
        }
    }
}