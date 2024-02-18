fun main() {

    fun getNumber(input:String):Int {
        val pattern = "\\d".toRegex()
        val result = pattern.findAll(input)
        val numbers = result.map { it.value.toInt() }.toList()
        val firstNumber = numbers.first()
        val lastNumber = numbers.last()
        return "$firstNumber$lastNumber".toInt()
    }

    fun getNumberfromWord(line:String):Int{
        val map = hashMapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

        val firstDigit = line.findAnyOf(listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")) ?: (Int.MAX_VALUE to "")
        val lastDigit = line.findLastAnyOf(listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")) ?: (Int.MIN_VALUE to "")
        val firstWord = line.findAnyOf(listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")) ?: (Int.MAX_VALUE to "")
        val lastWord = line.findLastAnyOf(listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")) ?: (Int.MIN_VALUE to "")

        val first = if (firstDigit.first < firstWord.first) firstDigit.second else map[firstWord.second]
        val last = if (lastDigit.first > lastWord.first) lastDigit.second else map[lastWord.second]

        return "$first$last".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, i -> acc + getNumber(i) }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { acc, i -> acc + getNumberfromWord(i) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_2_test")
    check(part2(testInput2) == 293)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
