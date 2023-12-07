import kotlin.math.pow

fun main() {

    // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    // Parse the string to create two lists [41,48,83,86,17] and [83,86,6,31,17,9,48,53]
    // Uses reduce with intersect to get the lucky winning numbers from the second list
    fun parseScratchCardsAndReturnCountOfWinningNumers(card: String): Int {
        return card.substringAfter(":")
            .split("|")
            .map { s -> """\d+""".toRegex().findAll(s).map { it.value.toInt() }.toList() }
            .reduce { winningNumbers, myNumbers -> myNumbers.intersect(winningNumbers.toSet()).toList() }
            .size
    }

    // Turns out you can solve it with powers of two of 2^(n-1)
    fun calculatePoints(line: String): Int {
        return 2.0.pow(parseScratchCardsAndReturnCountOfWinningNumers(line) - 1).toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { calculatePoints(it) }
    }


    fun part2(input: List<String>): Int {
        val map = input.map { line -> parseScratchCardsAndReturnCountOfWinningNumers(line) }
        // Going for a very inefficient approach: keep adding the elements to a mutable list and keep a pointer going through it
        // [1*,2,3,4,5,6] -> [1,2*,3,4,5,6,2,3,4,5] -> [1,2,3*,4,5,6,2,3,4,5,3,4]
        var pointer = 0
        var numbers = (1..input.size).toMutableList()
        while (true) {
            if (pointer == numbers.size - 1) break

            val ticketNumber = numbers[pointer]
            val copies = map[ticketNumber - 1]
            val range = (ticketNumber + 1).rangeTo(ticketNumber + copies)
            numbers.addAll(range.toList())
            pointer++
        }

        val answers = (1..input.size).map { n -> numbers.count { it == n } }
        return answers.sum()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    val testInput2 = readInput("Day04_test")
    check(part2(testInput2) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}