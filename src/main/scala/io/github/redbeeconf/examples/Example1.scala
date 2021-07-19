package io.github.redbeeconf.examples

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}

/**
  * Primer grafo
  */
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
