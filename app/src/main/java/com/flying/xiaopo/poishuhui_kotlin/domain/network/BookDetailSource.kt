package com.flying.xiaopo.poishuhui_kotlin.domain.network

import com.flying.xiaopo.poishuhui_kotlin.domain.model.BookDetail
import com.flying.xiaopo.poishuhui_kotlin.domain.model.BookInfo
import com.flying.xiaopo.poishuhui_kotlin.domain.model.Page
import com.flying.xiaopo.poishuhui_kotlin.getHtml
import org.jsoup.Jsoup
import java.util.*
import com.beust.klaxon.*

/**
 * @author wupanjie
 */
class BookDetailSource : Source<BookDetail> {
   val URL_IMG_CHAPTER = "http://www.ishuhui.net/ComicBooks/ReadComicBooksToIsoV1/"
  override fun obtain(url: String): BookDetail {
    val html = getHtml(url)
    val doc = Jsoup.parse(html)
    val pages = ArrayList<Page>()
    val elements = doc.select("div.volumeControl").select("a")
    val parser: Parser = Parser()
    val stringBuilder: StringBuilder = StringBuilder(html)
    val jsons: JsonObject = parser.parse(stringBuilder) as JsonObject
    val lists = jsons.obj("Return")?.array<JsonObject>("List")


     lists?.map {
        val title = it.string("Title")!!
        val id = it.int("Id")!!
        val link = URL_IMG_CHAPTER+id+".html"
        val page = Page(title, link)
        pages.add(page)
    }

    val returnstr = jsons.obj("Return")
    val updateTime = returnstr?.obj("ParentItem")?.string("RefreshTimeStr")!!
    val detail = returnstr?.obj("ParentItem")?.string("Explain")!!
    val info = BookInfo(updateTime, detail)

    return BookDetail(pages, info)
  }

}
