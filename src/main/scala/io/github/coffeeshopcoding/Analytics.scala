package io.github.coffeeshopcoding

import io.github.coffeeshopcoding.models.GithubEvent
import play.api.libs.json._
import scala.io.Source

object Analytics extends App {
  val eventFileName = "/2016-06-22-15.json"
  val stream = getClass.getResourceAsStream(eventFileName)
  val lines = Source.fromInputStream(stream).getLines

  val events = lines.map(line => {
    val json: JsValue = Json.parse(line)

    val eventType = (json \ "type").as[String]
    val userLogin = (json \ "actor" \ "login").as[String]

    GithubEvent(eventType, userLogin)
  })

  println(events.toList.groupBy(_.eventType).mapValues(_.size))

}
