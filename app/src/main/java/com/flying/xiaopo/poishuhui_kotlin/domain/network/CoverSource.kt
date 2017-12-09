package com.flying.xiaopo.poishuhui_kotlin.domain.network

import com.flying.xiaopo.poishuhui_kotlin.domain.model.Cover
import com.flying.xiaopo.poishuhui_kotlin.getHtml
import com.flying.xiaopo.poishuhui_kotlin.log
import org.jsoup.Jsoup
import java.util.*
import com.beust.klaxon.*
/**
 * @author wupanjie
 */
class CoverSource : Source<ArrayList<Cover>> {
  val LAST_CHAPTER_URL = "http://www.ishuhui.net/ComicBooks/GetLastChapterForBookIds?idjson="
  override fun obtain(url: String): ArrayList<Cover> {
    val list = ArrayList<Cover>()

    val html = getHtml(url)
    val parser: Parser = Parser()
    val stringBuilder: StringBuilder = StringBuilder(html)
    val jsons: JsonObject = parser.parse(stringBuilder) as JsonObject
    val lists = jsons.obj("Return")?.array<JsonObject>("List")
    val doc = Jsoup.parse(html)
    val elements = doc.select("ul.mangeListBox").select("li")
    lists?.map {
      val coverUrl = it.string("FrontCover")!!//element.select("img").attr("src")
      val title = it.string("Title")!!//element.select("h1").text() + "\n" + element.select("h2").text()
      val id = it.int("Id")!!
      val link = LAST_CHAPTER_URL+"["+id+"]"
      val cover = Cover(coverUrl, title, link)
      list.add(cover)
    }

    return list
  }

}
