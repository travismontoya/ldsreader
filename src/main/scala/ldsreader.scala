/* ldsreader.scala
 * Copyright (C) 2016  Travis Montoya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tgm.ldsreader

import scala.xml.XML
import scala.io.StdIn
import org.htmlcleaner.HtmlCleaner
import org.apache.commons.lang3.StringEscapeUtils
import java.net.URL
import java.io.File
import sys.process._

object ldsreader {
  def updateDatabase(): Map[String,String] = {
    val url    = "https://www.lds.org/tools/rss?lang=eng&location=articles" +
                 "&uri=/prophets-and-apostles/recent-messages"
    val xml    = XML.load(url)
    val items  = xml \\ "channel" \\ "item"
    val titles = (items \\ "title").map(_.text.trim)
    val links  = (items \\ "link").map(_.text.trim)

    (titles zip links).toMap
  }

  def downloadPDF(url: String) {
    val htmlCleaner = new HtmlCleaner
    val cleanerProp = htmlCleaner.getProperties
    val html        = htmlCleaner.clean(new URL(url))
    val element     = html.getElementsByName("a", true)

    for (elem <- element) {
      val linkTitle = elem.getAttributeByName("title")
      if (linkTitle != null && linkTitle.equalsIgnoreCase("PDF")) {
        val pdfLink = StringEscapeUtils.unescapeHtml4(
          elem.getAttributeByName("href").toString)
        val pdfName = pdfLink.split("/")

        println("Found PDF. Downloading to " + pdfName.last)
        new URL(pdfLink) #> new File(pdfName.last) !!
      }
    }
  }

  def main(args: Array[String]) {
    println("Search recent messages by the Prophet and Apostles")
    println("To download a talk paste the link into the search and press enter")
    print("Updating database! This can take a while...")

    val pl = "https://www.lds.org"
    val db = updateDatabase()

    println("Done!")

    while(true) {
      print("\nSearch (Type 'exit' to quit): ")
      val s: Option[String] = Some(StdIn.readLine())
      s match {
        case s if s.get.trim.isEmpty        => None
        case s if s.get.trim.startsWith(pl) => downloadPDF(s.get)
        case Some("exit")                   => System.exit(1)
        case _ => db.filterKeys(_.toUpperCase.contains(s.get.trim.toUpperCase))
                               .foreach(x => println(x._1 + "\n" + x._2 + "\n"))
      }
    }
  }
}
