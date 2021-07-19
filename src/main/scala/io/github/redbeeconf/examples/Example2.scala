package io.github.redbeeconf.examples

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Flow, Keep, Sink, Source}
import akka.util.ByteString

import java.nio.file.Paths
import scala.concurrent.Future

/**
  * Ejemplo donde se muestra la reutilización de componentes de los diferentes elementos que
  * componen un stream
  */
object Example2 extends App {

  implicit val system = ActorSystem("Example2")

  // 2 reusando componentes y conectandolos

  val sourceList: Source[Int, NotUsed] = Source(1 to 1000)

  val addOne = Flow[Int].map(x => x + 1)

  val cube = Flow[Int].map(x => x * x * x)

  val toLine = Flow[Int].map(number => s"num: $number")

  val sinkPrintln = Sink.foreach(println)

  // conectamos todo y lo ejecutamos

  val graph1 = sourceList
    .via(addOne)
    .via(cube)
    .via(toLine)
    .to(sinkPrintln)

  graph1.run()

  // 2.1 definimos un nuevo grafo reutilizando componentes

  val lineToBytes = Flow[String].map(line => ByteString(s"$line\n"))

  val sinkToFile = FileIO.toPath(Paths.get("result.txt"))

  val graph2 = sourceList
    .via(addOne)
    .via(cube)
    .via(toLine)
    .via(lineToBytes)
    .toMat(sinkToFile)(Keep.right)

  val result2: Future[IOResult] = graph2.run()

  // 2.3 más reutilización

  // o mejor... si vemos que ciertas transformaciones se repiten mucho y pueden ser reutilizadas...

  val numberConverter = addOne.via(cube).via(toLine)

  // combinamos line
  val sinkStringToFile: Sink[String, NotUsed] = lineToBytes.to(sinkToFile)

  sourceList.via(numberConverter).to(sinkStringToFile).run()
}
