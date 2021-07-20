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
import io.circe.parser.decode

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
}
