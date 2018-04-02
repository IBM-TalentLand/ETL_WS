package com.ibm.talentland.common.utils

import java.io._
import java.nio.file.{Files, Paths}
import java.nio.file.StandardOpenOption._
import java.util.Properties

import scala.io.Source

/**
  * The FileUtilities program implements an application that
  * reads a file in the input directory and return the complete
  * query.
  *
  * @author ricardo.rodriguez.haro1@ibm.com
  * @version 1.0
  * @since 2017-08-28
  */
object FileUtils {

  /**
    * Read a file and return the query contained in file.
    *
    * @param fileName the file that contains a sql query
    * @return the sql string contained in the file
    */
  def readSQLFile(fileName: String): String = {

    val aux = s"${System.getProperty("user.dir")}/src/main/$fileName"

    var query: String = ""
    for (line <- Source.fromFile(aux).getLines()) {
      query += line + " "
    }
    query
  }

  def readJsonFile(filePath: String): Unit = {

  }

  def writeJsonFile(filePath: String, str: String): Unit = {
    val data = str.getBytes

    try {
      val out = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath), CREATE, APPEND))
      try
        out.write(data, 0, data.length)
      catch {
        case x: IOException =>
          System.err.println(x)
      } finally if (out != null) out.close()
    }
  }

  def readPropertiesFile(fileName: String): Properties = {

    val properties = new Properties()
    val input: InputStream = new FileInputStream(s"${System.getProperty("user.dir")}/src/main/properties/$fileName")
    properties.load(input)

    properties
  }

  def readConfigurationFile(fileName: String): Properties = {
    val properties = new Properties()
    val input: InputStream = new FileInputStream(s"${System.getProperty("user.dir")}/config/$fileName")
    properties.load(input)

    properties
  }

  def deleteFile(path: String*): Unit = {
    path.foreach(new File(_).delete())
  }
}
