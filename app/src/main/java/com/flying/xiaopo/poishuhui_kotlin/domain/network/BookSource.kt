package com.flying.xiaopo.poishuhui_kotlin.domain.network

import com.flying.xiaopo.poishuhui_kotlin.domain.model.Cover
import com.flying.xiaopo.poishuhui_kotlin.getHtml
import org.jsoup.Jsoup
import java.util.*
import com.beust.klaxon.*

/**
 * @author wupanjie
 */
class BookSource : Source<ArrayList<Cover>> {
  val CHAPTER_URL = "http://www.ishuhui.net/ComicBooks/GetChapterList"
  override fun obtain(url: String): ArrayList<Cover> {
    val list = ArrayList<Cover>()

    val html = getHtml(url)
    val doc = Jsoup.parse(html)
    val parser: Parser = Parser()
    val stringBuilder: StringBuilder = StringBuilder(html)
    val jsons: JsonObject = parser.parse(stringBuilder) as JsonObject
    val lists = jsons.obj("Return")?.array<JsonObject>("List")

    val elements = doc.select("ul.chinaMangaContentList").select("li")

     lists?.map {
      val coverUrl = it.string("FrontCover")!!
      val title = it.string("Title")!!
      val id = it.int("Id")!!
      val link = CHAPTER_URL+"?id="+id
      val cover = Cover(coverUrl, title, link)
      list.add(cover)
   }

    return list
  }

}
