package io.github.redbeeconf.repository.tables

import io.github.redbeeconf.models.Transaction
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class TransactionTable(tag: Tag) extends Table[Transaction](tag, "transactions") {

  def id: Rep[Long]           = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def amount: Rep[BigDecimal] = column[BigDecimal]("amount")
  def cardNumber: Rep[String] = column[String]("card_number")
  def dateTime: Rep[String]   = column[String]("date_time")
  def holder: Rep[String]     = column[String]("holder")
  def installments: Rep[Int]  = column[Int]("installments")
  def cardType: Rep[String]   = column[String]("card_type")
  def email: Rep[String]      = column[String]("email")

  def * : ProvenShape[Transaction] =
    (amount, cardNumber, dateTime, holder, installments, cardType, email, id).mapTo[Transaction]
}

object TransactionTable {
  val table = TableQuery[TransactionTable]
}