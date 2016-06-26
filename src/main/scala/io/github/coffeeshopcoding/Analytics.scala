package io.github.coffeeshopcoding

import play.api.libs.json._
import scala.io.Source

object Analytics extends App {
  val eventFileName = "/2016-06-22-15.json"
  val stream = getClass.getResourceAsStream(eventFileName)
  val lines = Source.fromInputStream(stream).getLines
  for { line <- lines } {
    val json: JsValue = Json.parse(line)
    println(json)
  }

}
