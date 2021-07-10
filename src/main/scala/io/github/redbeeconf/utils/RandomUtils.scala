package io.github.redbeeconf.utils

import scala.util.Random

trait RandomUtils {

  private lazy val random = new Random()

  def randomIntInRage(min: Int, max: Int): Int =
    min + random.nextInt((max - min) + 1)
}
