import kotlin.system.measureTimeMillis

fun main() {

    fun getNextNode(instruction: Char, currentNode: List<String>): String {
        when (instruction) {
            'L' -> return currentNode[0]
            'R' -> return currentNode[1]
        }
        return ""
    }

    fun part1(input: List<String>): Long {
        val instructions = input.get(0)

        // key -> [node, node]
        val network = input.drop(2)
            .map {
                val (key, nodesString) = it.split(" = ")
                val nodes = nodesString.drop(1).dropLast(1).split(", ")
                key to nodes
            }
            .associateBy({ it.first }, { it.second })

        var node = network["AAA"]
        var counter = 0
        var pointer = instructions.length
        while (true) {
            pointer = if ((pointer + 1) >= instructions.length) 0 else pointer + 1
            val instruction = instructions[pointer]
            val nextNode = getNextNode(instruction, node!!)
            println(nextNode)

            if (nextNode == "ZZZ") {
                counter++
                break
            } else {
                node = network[nextNode]
                counter++
            }
        }

        return counter.toLong()
    }

    fun part2(input: List<String>): Long {


        return 0L

    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 6L)
//    val testInput2 = readInput("Day08_test")
//    check(part2(testInput2) == 6L)

    val input = readInput("Day08")
    measureTimeMillis { part1(input).println() }.also { println("ms: $it") }
//    measureTimeMillis { part2(input).println() }.also { println("ms: $it") }
}