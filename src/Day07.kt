import kotlin.system.measureTimeMillis

fun main() {

    val cards: Map<Char, Int> = mapOf(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'J' to 11,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2,
        '1' to 1
    )

    data class Hand(val cards: String, val bid: Int, val type: Int, var rank: Int = 0) : Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            for (i in 0..this.cards.length) {
                val card = this.cards[i]
                val otherCard = other.cards[i]

                if (card == otherCard) continue
                else if (cards[card]!! > cards[otherCard]!!) return 1
                else if (cards[card]!! < cards[otherCard]!!) return -1
            }
            return 0
        }
    }

    fun getType(cards: String): Int {
        val groupBy = cards.windowed(1, 1)
            .groupBy { it }
            .values
            .map { it.size }

        if (groupBy.contains(5)) return 5
        if (groupBy.contains(4)) return 4
        if (groupBy.contains(3) && groupBy.contains(2)) return 3
        if (groupBy.contains(3)) return 2
        if (groupBy.filter { it == 2 }.size == 2) return 1
        if (groupBy.contains(2)) return 0

        return -1

//        if (groupBy.contains(5)) return "five of a kind"
//        if (groupBy.contains(4)) return "four of a kind"
//        if (groupBy.contains(3) && groupBy.contains(2)) return "full house"
//        if (groupBy.contains(3)) return "three of a kind"
//        if (groupBy.filter { it == 2 }.size == 2) return "two pair"
//        if (groupBy.contains(2)) return "one pair"

//        return "high card"
    }

    fun part1(input: List<String>): Long {

        val result = input.map { l -> l.split(" ") }
            .map { h -> Hand(h[0], h[1].toInt(), getType(h[0])) }
            .groupBy { it.type }
            .values.map { it.sorted() }
            .sortedBy { it[0].type }
            .flatten()
            .mapIndexed { i, hand -> hand.bid * (i + 1) }
            .sum()

        return result.toLong()
    }


    fun part2(input: List<String>): Long {

        return input.size.toLong()

    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440L)
//    val testInput2 = readInput("Day07_test")
//    check(part2(testInput2) == 71503L)

    val input = readInput("Day07")
    measureTimeMillis { part1(input).println() }.also { println("ms: $it") }
//    measureTimeMillis { part2(input).println() }.also { println("ms: $it") }
}