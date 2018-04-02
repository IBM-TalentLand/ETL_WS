package com.ibm.talentland.common.utils

import org.apache.log4j.{Level, Logger}

/**
  * Created by Administrator on 9/27/2017.
  */
object Logs {

  def disableLogs(): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    Logger.getLogger("debug").setLevel(Level.OFF)
    Logger.getRootLogger.setLevel(Level.OFF)
    println("LOGS ARE DISABLED")
  }
}
