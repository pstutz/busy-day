package io.github.coffeeshopcoding

import io.github.coffeeshopcoding.models.GithubEvent
import play.api.libs.json._

import scala.collection.mutable
import scala.io.Source

object Languages extends App {
  val eventFileName = "/langs.json"
  val stream = getClass.getResourceAsStream(eventFileName)
  val lines = Source.fromInputStream(stream).getLines

  val map= mutable.Map.empty[String, Double].withDefaultValue(0.0)

  val langMaps = lines.drop(1).foreach(line => {

    val json: JsValue = Json.parse(line)
    val project = (json \ "project").as[String]
    val langs = (json \ "langs").as[Map[String, Int]]

    val sum = langs.values.sum+1

    langs.foreach({
      case (k, v) => {
        val updated = v.toDouble/sum + map(k)
        map(k) = updated
      }
    })

  })

  map.toSeq.sortBy(_._2).reverse.take(20).foreach({ case (lang, v) => println(s"$lang: $v")})

}
