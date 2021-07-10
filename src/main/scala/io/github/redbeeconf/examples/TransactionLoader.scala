package io.github.redbeeconf.examples

import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.{Slick, SlickSession}
import akka.stream.scaladsl.FileIO
import io.circe.parser.decode
import io.github.redbeeconf.models.Transaction
import io.github.redbeeconf.repository.tables.TransactionTable.transactionTable
import io.github.redbeeconf.utils.JsonSupport

import java.nio.file.Paths

import slick.jdbc.PostgresProfile.api._

object TransactionLoader extends App with JsonSupport {

  // 1. infrastructure
  implicit val system = ActorSystem("TransactionLoader")

  implicit val ec = system.dispatcher

  // 2. path al archivo que vamos a leer
  val file = Paths.get("transactions.txt")

  // 3. session de slick y hook para liberar recursos

  implicit val session = SlickSession.forConfig("slick-postgres")
  system.registerOnTermination(session.close())

  // 4. el grafo

  val result = FileIO
    .fromPath(file)
    .map(_.utf8String)
    .map(decode[Transaction](_))
    .collect {
      case Right(parsedTx) => parsedTx
    }
    .map(tx => tx.copy(cardNumber = extractLast4digits(tx.cardNumber)))
    .runWith(Slick.sink(tx => transactionTable += tx))

  // liberamos recursos
  result.onComplete { _ =>
    system.log.info("Shutdown actor system")
    system.terminate()
  }

  // TODO
  def extractLast4digits(cardNumber: String): String =
    s"xxxx-xxxx-xxxx-${cardNumber.substring(cardNumber.length - 4)}"
}
