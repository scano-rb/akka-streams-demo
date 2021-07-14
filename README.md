# Akka Streams Demo

Código que acompaña mi exposición sobre dicho tema en la [Redbee Conf #4](http://google.com), donde se da una intro a esta tecnología y un ejemplo "Real World" sobre cómo aplicarla para resolver problemas puntuales de negocio a través de una mini sesion de live coding.

## Requisitos

* `Scala 2.12`
* `Sbt`
* `Docker y docker-compose`

## Ejemplos

* `example 1`: intro a los componentes fundamentales de un pipeline en Stream. Creación de un stream y variantes del mismo que favorecen a la reutilización de componentes.

* `TransactionLoader`: definición de un pipeline de procesamiento que soluciona una problemática real, procesando un archivo y volcando el resultado en una dase de datos a través de un pipeline de procesamiento asincrónico.

### Transaction Loader

// TODO grafico

## Qué temas quedaron afuera?

Mención a temas que quedaron a fuera y que vale la pena que le pegues un vistazo en caso de querer investigar un poco más esta tecnología:

* Materialización de Streams
* Materialized value
* Manejo de Errores
* Logging
* Testing
* Grafos

## Links/Bibliografía