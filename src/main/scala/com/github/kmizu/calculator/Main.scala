package com.github.kmizu.calculator

object Main {
  def main(args: Array[String]): Unit = {
    new Parser().parse(args(0))
  }
}
