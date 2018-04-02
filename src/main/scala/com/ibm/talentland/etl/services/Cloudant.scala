package com.ibm.talentland.etl.services

import org.apache.spark.sql.{DataFrame, SparkSession}

import scalaj.http.{Http, HttpRequest}

class Cloudant(private val host: String, private val username: String, private val password: String) {

  SparkSession
    .builder()
    .master("local[*]")
    .config("cloudant.host", host)
    .config("cloudant.username", username)
    .config("cloudant.password", password)
    .getOrCreate()

  def getAllDocuments(db: String): DataFrame = {
    val df = SparkSession.builder.getOrCreate().read.format("org.apache.bahir.cloudant").load(db)
    df
  }

  def getAllDocs(db: String): HttpRequest = {
    Http(s"https://$username:$password@$host/$db/_all_docs")
      .header("Content-Type", "application/json")
      .header("User-Agent", "spark-cloudant")
      .auth(username, password)
  }

  def getAttachment(db: String, documentId: String, attachment: String): HttpRequest = {
    Http(s"https://$username:$password@$host/$db/$documentId/$attachment")
      .header("Content-Type", "application/json")
      .header("User-Agent", "spark-cloudant")
      .auth(username, password)
  }

  def getDocument(db: String, documentId: String): HttpRequest = {
    Http(s"https://$username:$password@$host/$db/$documentId")
      .header("Content-Type", "application/json")
      .header("User-Agent", "spark-cloudant")
      .auth(username, password)
  }

}
