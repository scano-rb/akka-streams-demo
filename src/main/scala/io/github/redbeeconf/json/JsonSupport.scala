package io.github.redbeeconf.json

import io.circe.{Decoder, Encoder, Printer}
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.github.redbeeconf.models.Transaction

trait JsonSupport {

  implicit val customConfig: Configuration =
    Configuration.default.withSnakeCaseMemberNames

  implicit val customPrinter: Printer =
    Printer.noSpaces.copy(dropNullValues = true)

  implicit val transactionEncoder: Encoder[Transaction] = deriveConfiguredEncoder
  implicit val transactionDecoder: Decoder[Transaction] = deriveConfiguredDecoder
}
