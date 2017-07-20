package com.github.kmizu.calculator

import scala.util.parsing.combinator.{PackratParsers, RegexParsers}
import scala.util.parsing.input.{CharSequenceReader, Position, Reader}
import AST._

/**
 * @author Kota Mizushima
 */
class Parser {
  private object CalculatorParser extends RegexParsers with PackratParsers {
    override def skipWhitespace = false

    private def not[T](p: => PackratParser[T], msg: String): PackratParser[Unit] = {
      not(p) | failure(msg)
    }

    lazy val % : PackratParser[Location] = Parser{ reader => Success(reader.pos, reader) }.map { position =>
      Location(position.line, position.column)
    }

    lazy val EOF: PackratParser[String] = not(elem(".", (ch: Char) => ch != CharSequenceReader.EofCh), "EOF Expected") ^^ {
      _.toString
    }

    lazy val program: PackratParser[AST.Expression] = expression <~ EOF

    lazy val expression: PackratParser[AST.Expression] = additive

    lazy val additive: PackratParser[AST.Expression] = (
      % ~ additive ~ "+" ~ multitive ^^ { case loc ~ l ~ _ ~ r => AST.BinaryExpression(loc, Operator.ADD, l, r) }
    | % ~ additive ~ "-" ~ multitive ^^ { case loc ~ l ~ _ ~ r => AST.BinaryExpression(loc, Operator.SUB, l, r) }
    | multitive
    )

    lazy val multitive: PackratParser[AST.Expression] = (
      % ~ multitive ~ "*" ~ primary ^^ { case loc ~ l ~ _ ~ r => AST.BinaryExpression(loc, Operator.MUL, l, r) }
    | % ~ multitive ~ "/" ~ primary ^^ { case loc ~ l ~ _ ~ r => AST.BinaryExpression(loc, Operator.DIV, l, r) }
    | primary
    )

    lazy val primary: PackratParser[AST.Expression] = integerLiteral | "(" ~> expression <~ ")"

    lazy val integerLiteral: PackratParser[AST.Expression] = % ~ """[1-9][0-9]*|0""".r ^^ {
      case location ~ value => AST.IntNode(location, value.toInt)
    }
  }

  import CalculatorParser._

  def parse(input: String): AST.Expression = {
    parseAll(program, input) match {
      case Success(expression: AST.Expression, _) => expression
      case Failure(m, n) => throw new Exception(n.pos + ":" + m)
      case Error(m, n) => throw new Exception(n.pos + ":" + m)
    }
  }
}
