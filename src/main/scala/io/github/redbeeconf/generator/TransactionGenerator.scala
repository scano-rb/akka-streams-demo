package io.github.redbeeconf.generator

import akka.actor.ActorSystem
import akka.stream.scaladsl.{FileIO, Keep, Source}
import akka.util.ByteString
import com.github.javafaker.Faker
import com.typesafe.config.ConfigFactory
import io.circe.syntax._
import io.github.redbeeconf.json.JsonSupport
import io.github.redbeeconf.models.Transaction

import java.nio.file.Paths
import java.time.LocalDateTime

object TransactionGenerator extends App with JsonSupport {

  implicit val system = ActorSystem("TransactionDataGenerator")
  implicit val ec     = system.dispatcher

  val config = ConfigFactory.load()

  val transactionCount = config.getInt("generator.transaction-count")

  val faker = new Faker()

  val outputFile = Paths.get("annuled-transactions.txt")

  val result = Source(1 to transactionCount)
    .map(
      _ =>
        Transaction(amount = 1000,
                    cardNumber = "xxxx-xxxx-xxxx-xxxx",
                    dateTime = LocalDateTime.now().toString,
                    holder = "pepe",
                    installments = 4,
                    cardType = "visa",
                    email = "pepe"))
    .map(tx => tx.asJson.noSpaces)
    .map(line => ByteString(line + "\n"))
    .toMat(FileIO.toPath(outputFile))(Keep.right)
    .run()

  result.onComplete { _ =>
    system.log.info("Data generated")
    system.terminate()
  }

}
