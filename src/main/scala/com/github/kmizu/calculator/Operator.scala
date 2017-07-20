package com.github.kmizu.calculator

sealed abstract class Operator(image: String)
object Operator {
  case object ADD extends Operator("+")
  case object SUB extends Operator("-")
  case object MUL extends Operator("*")
  case object DIV extends Operator("/")
}
