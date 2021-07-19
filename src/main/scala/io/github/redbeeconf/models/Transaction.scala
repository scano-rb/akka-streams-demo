package io.github.redbeeconf.models

case class Transaction(
    id: Option[Long],
    amount: BigDecimal,
    cardNumber: String,
    dateTime: String,
    holder: String,
    installments: Int,
    cardType: String,
    status: String,
    email: String)
