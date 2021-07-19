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

/**
  * Ejemplo real world: encriptado de claves en batch asincrónico con volcado hacia la base de datos
  *
  */
object TransactionLoader extends App with JsonSupport with EncryptionUtils {

  // 0. Configs
  val config = ConfigFactory.load()
  val key    = config.getString("encryption.key")

  // 1. infrastructure
  implicit val system = ActorSystem("TransactionLoader")

  // no lo necesitamos para el grafo, pero si para "chainear" sobre el future (`onComplete` method)
  implicit val ec = system.dispatcher

  // 2. path al archivo que vamos a leer
  val file = Paths.get("transactions.txt")

  // 3. session de slick y hook para liberar recursos

  implicit val session = SlickSession.forConfig("slick-postgres")

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
    // terminamos el actor system luego de que nuestro stream finalice (es decir, cuando el Future retorna)
    system.log.info("Shutdown actor system")
    system.terminate()
  }

  // se añade un hook para cerrar la session de slick (es importante liberar recursos!)
  system.registerOnTermination {
    system.log.info(s"Closing slick session")
    session.close()
  }
}
