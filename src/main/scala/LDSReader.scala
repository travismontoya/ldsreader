/* LDSReader.scala
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

import scala.io.StdIn
import LDSParser._

object LDSReader extends LDSParser {
  def printTitle() {
    println("Search recent messages by the Prophet and Apostles")
    println("To download a talk paste the link into the search and press enter")
    print("Updating database! This can take a while...")
  }

  def main(args: Array[String]) {
    printTitle()

    val db = updateDatabase()
    println("Done!")

    while(true) {
      print("\nSearch (Type 'exit' to quit): ")
      val s: Option[String]                 = Some(StdIn.readLine())
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
