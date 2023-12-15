import kotlin.system.measureTimeMillis

fun main() {

    fun handType(cards: String): HandType {
        val groupBy = cards.windowed(1, 1)
            .groupBy { it }
            .values
            .map { it.size }

        if (groupBy.contains(5)) return HandType.FIVEOFAKIND
        if (groupBy.contains(4)) return HandType.FOUROFAKIND
        if (groupBy.contains(3) && groupBy.contains(2)) return HandType.FULLHOUSE
        if (groupBy.contains(3)) return HandType.THREEOFAKIND
        if (groupBy.filter { it == 2 }.size == 2) return HandType.TWOPAIR
        if (groupBy.contains(2)) return HandType.ONEPAIR

        return HandType.HIGHCARD
    }

    fun handType2(cards: String): HandType {
        val groupBy = cards.filterNot { it == 'J' }
            .windowed(1, 1)
            .groupBy { it }
            .values
            .map { it.size }

        val wildcards = cards.count { it == 'J' }

        when (wildcards) {
            5, 4 -> return HandType.FIVEOFAKIND   // JJJJJ  JJJJA
            3 -> {
                if (groupBy.contains(2)) return HandType.FIVEOFAKIND    // JJJAA
                if (groupBy.contains(1)) return HandType.FOUROFAKIND    // JJJAB
            }

            2 -> {
                if (groupBy.contains(3)) return HandType.FIVEOFAKIND    // JJAAA
                if (groupBy.contains(2)) return HandType.FOUROFAKIND    // JJAAB
                if (groupBy.contains(1)) return HandType.THREEOFAKIND   // JJABC
            }

            1 -> {
                if (groupBy.contains(4)) return HandType.FIVEOFAKIND    // JAAAA
                if (groupBy.contains(3)) return HandType.FOUROFAKIND    // JAAAB
                if (groupBy.filter { it == 2 }.size == 2) return HandType.FULLHOUSE     // JAABB
                if (groupBy.filter { it == 2 }.size == 1) return HandType.THREEOFAKIND  // JAABC
                if (groupBy.contains(1)) return HandType.ONEPAIR   // JABCD
            }

            else -> {
                return handType(cards)
            }
        }
        return HandType.HIGHCARD
    }

    fun play(input: List<String>, gameRules: Map<Char, Int>, handTypeFunction: (String) -> HandType): Long {
        return input.map { l -> l.split(" ") }
            .map { h -> Hand(h[0], h[1].toInt(), handTypeFunction(h[0]), gameRules) }
            .groupBy { it.type }
            .values.map { it.sorted() }
            .sortedBy { it[0].type.value }
            .flatten()
            .mapIndexed { i, hand -> hand.bid * (i + 1) }
            .sum()
            .toLong()
    }

    fun part1(input: List<String>): Long {

        val gameRules: Map<Char, Int> = mapOf(
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
            '2' to 2
        )

        return play(input, gameRules, ::handType)
    }

    fun part2(input: List<String>): Long {

        val gameRules: Map<Char, Int> = mapOf(
            'A' to 14,
            'K' to 13,
            'Q' to 12,
            'T' to 10,
            '9' to 9,
            '8' to 8,
            '7' to 7,
            '6' to 6,
            '5' to 5,
            '4' to 4,
            '3' to 3,
            '2' to 2,
            'J' to 1
        )

        return play(input, gameRules, ::handType2)

    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440L)
    val testInput2 = readInput("Day07_test")
    check(part2(testInput2) == 5905L)

    val input = readInput("Day07")
    measureTimeMillis { part1(input).println() }.also { println("ms: $it") }
    measureTimeMillis { part2(input).println() }.also { println("ms: $it") }
}

enum class HandType(val value: Int) {
    FIVEOFAKIND(6),
    FOUROFAKIND(5),
    FULLHOUSE(4),
    THREEOFAKIND(3),
    TWOPAIR(2),
    ONEPAIR(1),
    HIGHCARD(0)
}

data class Hand(val cards: String, val bid: Int, val type: HandType, val gameRules: Map<Char, Int>) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        for (i in 0..this.cards.length) {
            val card = this.cards[i]
            val otherCard = other.cards[i]

            if (card == otherCard) continue
            else if (gameRules[card]!! > gameRules[otherCard]!!) return 1
            else if (gameRules[card]!! < gameRules[otherCard]!!) return -1
        }
        return 0
    }
}