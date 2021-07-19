package io.github.redbeeconf.examples

import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.{Slick, SlickSession}
import akka.stream.scaladsl.{FileIO, Framing}
import akka.util.ByteString
import com.typesafe.config.ConfigFactory
import io.circe.parser.decode
import io.github.redbeeconf.db.TransactionTable.transactionTable
import io.github.redbeeconf.models.Transaction
import io.github.redbeeconf.utils.{EncryptionUtils, JsonSupport}

import java.nio.file.Paths
import slick.jdbc.PostgresProfile.api._

object TransactionLoader extends App with JsonSupport with EncryptionUtils {

  // 0. Configs
  val config = ConfigFactory.load()
  val key    = config.getString("encryption.key")

  // 1. infrastructure
  implicit val system = ActorSystem("TransactionLoader")

  implicit val ec = system.dispatcher

  // 2. path al archivo que vamos a leer
  val file = Paths.get("transactions.txt")

  // 3. session de slick y hook para liberar recursos

  implicit val session = SlickSession.forConfig("slick-postgres")

  system.registerOnTermination {
    system.log.info(s"Closing slick session")
    session.close()
  }

  // 4. el grafo

  val result = FileIO
    .fromPath(file)
    .via(Framing.delimiter(ByteString("\n"), 256, true))
    .map(_.utf8String)
    .map(decode[Transaction](_))
    .collect {
      case Right(parsedTx) => parsedTx
    }
    .map(tx => tx.copy(cardNumber = encrypt(key, tx.cardNumber)))
    .runWith(Slick.sink(tx => transactionTable += tx))

  // liberamos recursos
  result.onComplete { _ =>
    system.log.info("Shutdown actor system")
    system.terminate()
  }
}
