package com.github.kmizu.calculator

/**
 * @author Kota Mizushima
 */

sealed abstract class AST {
  val location: Location
}

object AST {
  sealed abstract class Expression

  case class BinaryExpression(location: Location, operator: Operator, lhs: AST.Expression, rhs: AST.Expression) extends Expression

  case class IntNode(location: Location, value: Int) extends Expression
}