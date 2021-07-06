package io.github.serdeliverance.repository.tables

import io.github.serdeliverance.models.Transaction
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

/**
  * Slick mapping of the "spstransac" table fields.
  *
  * @param tag Not to be used.
  */
class TransactionTable(tag: Tag) extends Table[Transaction](tag, "transactions") {

  def id: Rep[Int]            = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def amount: Rep[BigDecimal] = column[BigDecimal]("amount")
  def cardNumber: Rep[String] = column[String]("card_number")
  def dateTime: Rep[String]   = column[String]("date_time")
  def holder: Rep[String]     = column[String]("holder")
  def installments: Rep[Int]  = column[Int]("installments")
  def cardType: Rep[String]   = column[Int]("card_type")
  def mail: Rep[String]       = column[Option[String]]("email")

  override def * : ProvenShape[Transaction] =
    ().mapTo[Transaction]
}

object TransactionTable {
  val table = TableQuery[TransactionTable]
}
