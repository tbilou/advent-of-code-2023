# advent-of-code-2023

Welcome to the Advent of Code[^aoc] Kotlin project created by [tbilou][github] using the [Advent of Code Kotlin Template][template] delivered by JetBrains.

In this repository, tbilou is about to provide solutions for the puzzles using [Kotlin][kotlin] language.

If you're stuck with Kotlin-specific questions or anything related to this template, check out the following resources:

- [Kotlin docs][docs]
- [Kotlin Slack][slack]
- Template [issue tracker][issues]


[^aoc]:
    [Advent of Code][aoc] – An annual event of Christmas-oriented programming challenges started December 2015.
    Every year since then, beginning on the first day of December, a programming puzzle is published every day for twenty-five days.
    You can solve the puzzle and provide an answer using the language of your choice.

| Day | success?   | Notes                                                                                                                                                                                                                                                                                                                                                  |
|-----|------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 01  | [✓][day01] | [Trebuchet][aoc01]: Implemented the first part using a regex to find all digits in the string, then just taking the first and last element of the list. For the second part I continued down the regex path searching for the words. This however didn't work because of `1onenineqgzcq2eightwonh`... I couldn't find the last `two` with my regex fu. | 
| 02  | [✓][day02] | [Cube Conundrum][aoc02]: Tried to use a regex and find the highest value pair... Failed. Switched to using substrings instead. I'm happy with the solution for part two, filtering by color and getting the highest amount                                                                                                                             | 
 

[aoc01]: https://adventofcode.com/2023/day/1
[aoc02]: https://adventofcode.com/2023/day/2
[day01]: src/Day01.kt
[day02]: src/Day02.kt

[aoc]: https://adventofcode.com
[docs]: https://kotlinlang.org/docs/home.html
[github]: https://github.com/tbilou
[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues
[kotlin]: https://kotlinlang.org
[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up
[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
