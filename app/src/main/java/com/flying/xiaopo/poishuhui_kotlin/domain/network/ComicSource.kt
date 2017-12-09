package com.flying.xiaopo.poishuhui_kotlin.domain.network

import com.flying.xiaopo.poishuhui_kotlin.domain.model.Comic
import com.flying.xiaopo.poishuhui_kotlin.getHtml
import org.jsoup.Jsoup
import java.util.*
import com.beust.klaxon.*
import com.flying.xiaopo.poishuhui_kotlin.log

/**
 * @author wupanjie
 */
class ComicSource : Source<ArrayList<Comic>> {

   val URL_IMG_CHAPTER = "http://www.ishuhui.net/ComicBooks/ReadComicBooksToIsoV1/"
   override fun obtain(url: String): ArrayList<Comic> {
      val html = getHtml(url)
      val parser: Parser = Parser()
      val stringBuilder: StringBuilder = StringBuilder(html)
      val jsons: JsonObject = parser.parse(stringBuilder) as JsonObject

      val slist = jsons.obj("Return")?.array<JsonObject>("List")
      val list = ArrayList<Comic>()

      /*slist?.map {*/
         val it = slist?.get(0)
         val id = it?.int("Id")!!
         val comicurl = URL_IMG_CHAPTER+id+".html"
         val html2 = getHtml(comicurl)
         val doc = Jsoup.parse(html2)
         val elements = doc.select("body").select("img")

         for (element in elements) {
            var comicUrl: String
            val temp = element.attr("src")
            if (temp.contains(".png") || temp.contains(".jpg") || temp.contains(".JPEG")) {
               comicUrl = temp
            } else if (!"".equals(element.attr("data-original"))) {
               comicUrl = element.attr("data-original")
            } else {
               continue
            }

            val comic = Comic(comicUrl)

            list.add(comic)
         }
      /*}*/
      return list
   }
}
