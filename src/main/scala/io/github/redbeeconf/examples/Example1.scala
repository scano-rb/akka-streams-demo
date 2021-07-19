package io.github.redbeeconf.examples

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Flow, Keep, Sink, Source}
import akka.util.ByteString

import java.nio.file.Paths
import scala.concurrent.Future

object Example1 extends App {

  implicit val system           = ActorSystem("RedbeeExample")
  implicit val executionContext = system.dispatcher

  // 1 - Primer stream

  // 1.4 - Ejemplo: creamos un stream y lo corremos sin reutilizar nada

  val result = Source(1 to 100)
    .map(x => x + 1)
    .map(x => x * x * x)
    .map(e => s"num: $e")
    .runWith(Sink.foreach(println))
}
