package io.github.serdeliverance.generator

import akka.stream.scaladsl.Source
import com.github.javafaker.Faker
import com.typesafe.config.ConfigFactory

object TransactionGenerator extends App {

  val config = ConfigFactory.load()

  val transactionCount = config.getString("generator.transaction-count")

  val faker = new Faker()

  Source(1 to transactionCount)
}
