fun main() {


    fun getNumbers(line: String): List<EnginePart> {
        var numbers: MutableList<EnginePart> = mutableListOf()
        var startIndex = -1
        for ((index, value) in line.withIndex()) {
            if (value.isDigit() && startIndex < 0) {
                startIndex = index
            } else if (!value.isDigit() && startIndex > -1) {
                numbers.add(EnginePart(startIndex, index - 1, line.substring(startIndex, index).toInt(), line.length))
                startIndex = -1
            }
        }
        if (startIndex > -1) {
            numbers.add(
                EnginePart(
                    startIndex,
                    line.length - 1,
                    line.substring(startIndex, line.length).toInt(),
                    line.length
                )
            )
        }
        return numbers
    }

    // look at the surroundings of a number
    fun partNumber(row: String, topRow: String?, bottomRow: String?, number: EnginePart): EnginePart? {
        val symbols = listOf("&", "*", "#", "%", "$", "-", "@", "=", "+", "/")

        val left = symbols.contains(row.get(number.prev()).toString())
        val right = symbols.contains(row.get(number.next()).toString())
        val foundInTop = topRow?.substring(number.prev(), number.next() + 1)
            ?.indexOfAny(symbols) // substring end before the end index
        val foundInBottom = bottomRow?.substring(number.prev(), number.next() + 1)
            ?.indexOfAny(symbols) // substring end before the end index

        return if (left || right || (foundInTop != null && foundInTop > -1) || (foundInBottom != null && foundInBottom > -1)) number else null
    }

    fun part1(input: List<String>): Int {

        var sum = 0
        for ((lineNumber, line) in input.withIndex()) {
            val numbers = getNumbers(line)
            for (number in numbers) {
                val topRow = input.getOrNull(lineNumber - 1)
                var bottomRow = input.getOrNull(lineNumber + 1)
                partNumber(line, topRow, bottomRow, number)?.let {
                    sum += it.number
                }
            }
        }
        return sum
    }


    fun gearLocation(row: String, topRow: String?, bottomRow: String?, number: EnginePart, lineNumber: Int): GearLocation? {
        val symbols = listOf("*")

        //Left and Right
        if (symbols.contains(row.get(number.prev()).toString())) return GearLocation("left", lineNumber, number.prev())
        if (symbols.contains(row.get(number.next()).toString())) return GearLocation("right", lineNumber, number.next())

        val foundInTop = topRow?.substring(number.prev(), number.next() + 1)?.indexOfAny(symbols)
        if (foundInTop != null && foundInTop > -1) return GearLocation("top", lineNumber - 1, number.prev() + foundInTop)

        val foundInBottom = bottomRow?.substring(number.prev(), number.next() + 1)?.indexOfAny(symbols)
        if (foundInBottom != null && foundInBottom > -1) return GearLocation(
            "bottom",
            lineNumber + 1,
            number.prev() + foundInBottom
        )

        return null
    }


    fun part2(input: List<String>): Int {

        val candidates: MutableList<Gear> = mutableListOf()
        for ((lineNumber, line) in input.withIndex()) {
            val numbers = getNumbers(line)
            for (number in numbers) {
                val topRow = input.getOrNull(lineNumber - 1)
                var bottomRow = input.getOrNull(lineNumber + 1)
                gearLocation(line, topRow, bottomRow, number, lineNumber)?.let {
                    candidates.add(Gear(number, it))
                }

            }
        }

        val multiplications = candidates.groupBy { it.symbol?.line to it.symbol?.index }
            .filter { it.value.size == 2 }
            .map { it.value }
            .map { it.fold(1) { acc, gear -> gear.number.number * acc } }

        return multiplications.sum()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    val testInput2 = readInput("Day03_test")
    check(part2(testInput2) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

data class GearLocation(
    val location: String,
    val line: Int,
    val index: Int
)

data class EnginePart(
    val startIndex: Int,
    val endIndex: Int,
    val number: Int,
    val size: Int
) {

    fun prev(): Int {
        return if (startIndex == 0) 0 else startIndex - 1
    }

    fun next(): Int {
        return if (endIndex == size - 1) endIndex else endIndex + 1
    }

}

data class Gear(
    val number: EnginePart,
    val symbol: GearLocation?
)