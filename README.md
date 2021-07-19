# Akka Streams Demo

Código que acompaña mi exposición sobre dicho tema en la [Redbee Conf #4](http://google.com), donde se da una intro a esta tecnología y un ejemplo "Real World" sobre cómo aplicarla para resolver problemas puntuales de negocio a través de una mini sesion de live coding.

## Requisitos

* `Scala 2.12`
* `Sbt`
* `Docker y docker-compose`

## Ejemplos

* `Example 1`: intro a los componentes fundamentales de un pipeline (de ahora en más: grafo) y creación.

* `Example 2`: variantes del ejemplo anterior que favorecen a la reutilización de componentes.

* `TransactionLoader`: definición de un pipeline de procesamiento que soluciona una problemática real, procesando un archivo y volcando el resultado en una dase de datos a través de un pipeline de procesamiento asincrónico.

### Transaction Loader

Arquitectura del sistema:

![Alt text](diagrams/architecture.png?raw=true "Arquitectura")

Pipeline a implementar:

![Alt text](diagrams/pipeline.png?raw=true "Pipeline")

## Ejecutar el ejemplo

Tener levantado el docker:

```
docker-compose up
```

correr el proceso batch:

```scala
sbt "runMain io.github.redbeeconf.examples.TransactionLoader"
```

## Qué temas quedaron afuera?

Mención a temas que quedaron a fuera y que vale la pena que le pegues un vistazo en caso de querer investigar un poco más esta tecnología:

* Materialización de Streams
* Materialized value
* Manejo de Errores
* Logging
* Testing
* Grafos

## Links/Bibliografía

* [Documentación oficial](https://doc.akka.io/docs/akka/current/stream/stream-quickstart.html)
* [Akka Streams: A motivating example, by Colin Breck](https://blog.colinbreck.com/akka-streams-a-motivating-example/)
* [Akka Streams Getting Started, by me](https://serdeliverance.github.io/blog/blog/akka-streams-getting-started/)

## Adicional

Para generar el archivo `transactions.txt` también usamos un [grafo](src/main/scala/io/github/redbeeconf/generator/TransactionGenerator.scala). Para correr dicho grafo, ejectuar:

``` scala
./generator.sh
```