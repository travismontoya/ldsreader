/* LDSParser.scala
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
package com.tgm.LDSReader

import scala.xml.XML
import org.htmlcleaner.HtmlCleaner
import org.apache.commons.lang3.{StringEscapeUtils => Seu}
import java.net.URL
import java.io.File
import sys.process._

trait LDSReaderUtil {
  val Site = "https://www.lds.org"
  def updateDatabase(): Map[String,String]
  def downloadPDF(url: String)
}

case class LDSParser() extends LDSReaderUtil {
  /*****************************************************************************
   ** Save the parsed pdf link to the local System
   ****************************************************************************/
  private def savePDF(pdfLink: String) {
    val pdfName      = pdfLink.split("/")

    println("Found PDF. Downloading to " + pdfName.last)
    new URL(pdfLink) #> new File(pdfName.last) !!
  }

  /*****************************************************************************
   ** Read the current RSS feed from lds.org
   ****************************************************************************/
  def updateDatabase(): Map[String,String] = {
    print("Updating database! This can take a while...")

    val url         = "https://www.lds.org/tools/rss?lang=eng&location=" +
                      "articles&uri=/prophets-and-apostles/recent-messages"
    val xml         = XML.load(url)
    val items       = xml \\ "channel" \\ "item"
    val titles      = (items \\ "title").map(_.text.trim)
    val links       = (items \\ "link").map(_.text.trim)

    println("Done!")
    (titles zip links).toMap
  }

  /*****************************************************************************
   ** Parse the HTML of the talk link and find the PDF link to download
   ****************************************************************************/
  def downloadPDF(url: String) {
    val htmlCleaner = new HtmlCleaner
    val html        = htmlCleaner.clean(new URL(url))
    val element     = html.getElementsByName("a", true)

    element foreach { e =>
      val linkTitle = e.getAttributeByName("title")
      if (linkTitle != null && linkTitle.equalsIgnoreCase("PDF")) {
        val pdfLink = Seu.unescapeHtml4(e.getAttributeByName("href").toString)
        savePDF(pdfLink)
      }
    }
  }
}
