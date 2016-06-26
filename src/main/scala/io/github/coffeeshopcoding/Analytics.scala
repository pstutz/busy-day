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
    val repoName = (json \ "repo" \ "name").asOpt[String]

    GithubEvent(eventType, userLogin, repoName)
  })

  println(events.toList.groupBy(_.repo).mapValues(_.size).toList.sortBy(-1 * _._2))

}
