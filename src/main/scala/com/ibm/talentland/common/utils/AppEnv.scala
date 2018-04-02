package com.ibm.talentland.common.utils

import java.io.FileInputStream
import java.util.Properties

object AppEnv {
  private val properties: Properties = new Properties()
  properties.load(
    new FileInputStream(s"${System.getProperty("user.dir")}/properties/app.properties")
  )

  private val cloudantCreds = Map[String, String](
    "cloudant.host" -> properties.getProperty("cloudant.host"),
    "cloudant.username" -> properties.getProperty("cloudant.username"),
    "cloudant.password" -> properties.getProperty("cloudant.password"),
    "cloudant.dbname" -> properties.getProperty("cloudant.dbname")
  )

  private val cosCreds = Map[String, String](
    "cos.endpoint" -> properties.getProperty("cos.endpoint"),
    "cos.accessKey" -> properties.getProperty("cos.accessKey"),
    "cos.secretKey" -> properties.getProperty("cos.secretKey")
  )

  def getPropCloudant(property: String): String = {
    cloudantCreds(property)
  }

  def getPropCos(property: String): String = {
    cosCreds(property)
  }

}
