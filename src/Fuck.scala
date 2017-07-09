import java.nio.file.{Files, Paths}

import scala.collection.mutable
import scala.io.Source

object Fuck {

  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("Please provide a program or file name as the first argument")
      System.exit(1)
    }

    if (Files.exists(Paths.get(args(0)))) {
      run(Source.fromFile(args(0)).mkString)
    } else {
      run(args(0))
    }

  }

  def run(program: String): Unit = {
    // Intialize aux variables
    var pc = 0 // program counter
    var cursor = 0 //memory pointer
    var mem = mutable.ArraySeq.fill(300000)(0) // memory

    while (pc < program.length) {
      program.charAt(pc) match {
        case '+' => mem(cursor) += 1
        case '-' => mem(cursor) -= 1
        case '>' => cursor += 1
        case '<' => cursor -= 1
        case '[' =>
          if (mem(cursor) == 0) {
            pc = findJumpIndex(program, pc)
          }
        case ']' => pc = findJumpIndex(program, pc) - 1
        case '.' => print(mem(cursor).toChar)
        case ',' => mem(cursor) = System.in.read()
        case _ => ()
      }

      pc += 1
    }
  }

  def findJumpIndex(program: String, index: Int): Int = findJumpIndex(program, index, 0, LoopDirection(program(index)))

  private def findJumpIndex(program: String, index: Int, prevLoopLevel: Int, direction: (Int) => Int): Int = {
    val loopLevel = NextLoopLevel(prevLoopLevel, program(index))
    loopLevel match {
      case 0 => index
      case _ => findJumpIndex(program, direction(index), loopLevel, direction)
    }
  }

  private val NextLoopLevel = (loopLevel: Int, op: Char) =>
    op match {
      case '[' => loopLevel + 1
      case ']' => loopLevel - 1
      case _ => loopLevel
    }

  private val LoopDirection = (op: Char) =>
    op match {
      case '[' => (x: Int) => x + 1
      case ']' => (x: Int) => x - 1
      case _ => (x: Int) => x
    }

}
