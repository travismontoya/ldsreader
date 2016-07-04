/* ldsreader.scala
 * Sunday, July 3, 2016
 * @author Travis Montoya
*/
import scala.xml._
import scala.io.StdIn

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

  def main(args: Array[String]) {
    print("Updating database! This can take a while...")

    // Retrieve the current messages
    val db = updateDatabase()

    println("Done!")

    while(true) {
      print("\nSearch (Type 'exit' to quit): ")
      val s: Option[String] = Some(StdIn.readLine())
      s match {
        case s if s.get.trim.isEmpty => None
        case Some("exit")            => System.exit(1)
        case _ => db.filterKeys(_.toUpperCase.contains(s.get.trim.toUpperCase))
                               .foreach(x => println(x._1 + "\n" + x._2 + "\n"))
      }
    }
  }
}
