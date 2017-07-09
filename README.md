# fuck-scala
Brainfuck interpreter in Scala

# How to run

The interpreter accepts either a complete program

    scala src/Fuck.scala ">++++[-<+>]"


or a file name

    echo ">++++[-<+>]" > program.bf
    scala src/Fuck.scala program.bf
