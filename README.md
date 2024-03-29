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

| Day | success?   | Notes                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
|-----|------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 01  | [✓][day01] | [Trebuchet][aoc01]: Implemented the first part using a regex to find all digits in the string, then just taking the first and last element of the list. For the second part I continued down the regex path searching for the words. This however didn't work because of `1onenineqgzcq2eightwonh`... I couldn't find the last `two` with my regex fu.                                                                                                     | 
| 02  | [✓][day02] | [Cube Conundrum][aoc02]: Tried to use a regex and find the highest value pair... Failed. Switched to using substrings instead. I'm happy with the solution for part two, filtering by color and getting the highest amount                                                                                                                                                                                                                                 | 
| 03  | [✓][day03] | [Gear Ratios][aoc03]: What a mess this day was. I managed to complete part 1, but went down a rabbit hole for part 2. Thought of a simpler solution for part 2 before falling asleep.                                                                                                                                                                                                                                                                      | 
| 04  | [✓][day04] | [Scratchcards][aoc04]: Using kotlin standard lib proved to be very helpful for today's puzzle. I ended up storing both lists first to perform the intersect but refactored it later to use reduce. When I was refactoring I understood that the calculations was about powers of 2                                                                                                                                                                         | 
| 05  | [✓][day05] | [If You Give A Seed A Fertilizer][aoc05]: My first implementation was to simply expand all the maps, so that getting the answer would be a simple lookup. Given the size of the "real" input I didn't have enough memory. So I didn't expand the maps and instead search them. This allowed me to solve part1. For part two just computing all the numbers was taking a long time and going through the entire search space one at a time would take days. | 
| 06  | [✓][day06] | [Wait For It][aoc06]: After spending days on day 5 I solved this in less than 1h ¯\\_ (ツ)_/¯                                                                                                                                                                                                                                                                                                                                                               | 
| 07  | [✓][day07] | [Camel Cards][aoc07]: The solution is a bit messy, but I like that I only had to write another function for part 2. The parsing and transforming of the input using function chaining is impossible to understand... Also it makes no sense to have the rules in each hand.                                                                                                                                                                                | 
| 08  | [✓][day08] | [Haunted Wasteland][aoc08]: First part was trivial. Infinite loop running through the hashmap until the solution is found. For part 2 I tried bruteforcing it but failed. Will revisit this later                                                                                                                                                                                                                                                          | 
| 09  | [✓][day09] | [Mirage Maintenance][aoc09]: Implemented it quick and dirty with mutability. It was easier than expected.                                                                                                                                                                                                                                                                                                                                                  | 
| 10  | [✓][day10] | [Mirage Maintenance][aoc10]: Created a matrix class with a bunch of functions. In the end ended up just following the signs. For part two implemented it the mathematical way with shoelace and Picks theorem. I'll also try implementing it the CS way                                                                                                                                                                                                    | 
 

[aoc01]: https://adventofcode.com/2023/day/1
[aoc02]: https://adventofcode.com/2023/day/2
[aoc03]: https://adventofcode.com/2023/day/3
[aoc04]: https://adventofcode.com/2023/day/4
[aoc05]: https://adventofcode.com/2023/day/5
[aoc06]: https://adventofcode.com/2023/day/6
[aoc07]: https://adventofcode.com/2023/day/7
[aoc08]: https://adventofcode.com/2023/day/8
[aoc09]: https://adventofcode.com/2023/day/9
[day01]: src/Day01.kt
[day02]: src/Day02.kt
[day03]: src/Day03.kt
[day04]: src/Day04.kt
[day05]: src/Day05_1.kt
[day06]: src/Day06.kt
[day07]: src/Day07.kt
[day08]: src/Day08.kt
[day09]: src/Day09.kt

[aoc]: https://adventofcode.com
[docs]: https://kotlinlang.org/docs/home.html
[github]: https://github.com/tbilou
[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues
[kotlin]: https://kotlinlang.org
[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up
[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
