package actors.child

import model.dto.AnswerDto
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class DuckDuckGoActor(url: String) : ChildActor(url) {

    override fun process(query: String, number: Int): MutableList<AnswerDto> {
        val doc: Document?
        return try {
            doc = Jsoup.connect(url + query).ignoreContentType(true).get()
            val results: Elements = doc.getElementById("links")!!.getElementsByClass("results_links")
            results
                .stream()
                .limit(number.toLong())
                .map {
                    val title: Element =
                        it.getElementsByClass("links_main").first()!!.getElementsByTag("a").first()!!
                    AnswerDto(title.text(), title.attr("href"))

                }.toList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}