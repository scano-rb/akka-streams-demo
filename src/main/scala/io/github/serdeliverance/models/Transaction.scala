package io.github.serdeliverance.models

case class Transaction(
    amount: BigDecimal,
    cardNumber: String,
    dateTime: String,
    holder: String,
    installments: Int,
    cardType: String,
    email: String,
    id: Long = 0L)
