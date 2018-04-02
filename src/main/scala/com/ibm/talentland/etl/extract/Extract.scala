package com.ibm.talentland.etl.extract

import com.ibm.talentland.common.utils.{AppEnv, FileUtils}
import com.ibm.talentland.etl.services.Cloudant
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.Row

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Extract {
  var personBuffer = ListBuffer[Person]()
  var debtsBuffer = ListBuffer[Debts]()

  case class Person(
    id: Long,
    name: String,
    phone: String,
    country: String,
    notes: String,
    date: String
  )

  case class Debts(
    id: Long,
    income: String,
    debt: Long,
    payday_limit: String
  )

  def extract(): (DataFrame, DataFrame) = {
    FileUtils.deleteFile("Person.json", "Debts.json")
    val cloudant = new Cloudant(
      AppEnv.getPropCloudant("cloudant.host"),
      AppEnv.getPropCloudant("cloudant.username"),
      AppEnv.getPropCloudant("cloudant.password")
    )
    (getDFfromJson(cloudant, "Person"), getDFfromJson(cloudant, "Debts"))
  }

  def getDFfromJson(cloudant: Cloudant, nameTable: String): DataFrame = {
    FileUtils.writeJsonFile(
      s"$nameTable.json",
      cloudant.getDocument("etl-workshop", nameTable).asString.body
    )

    val sample = SparkSession.builder().master("local[*]").getOrCreate().read.json(s"$nameTable.json")
    if(nameTable == "Person"){
      sample.foreach(_.getAs[mutable.WrappedArray[GenericRowWithSchema]]("data").foreach(getDFRecords(_)))
      SparkSession.builder().getOrCreate().sqlContext.createDataFrame(personBuffer)
    } else {
      sample.foreach(_.getAs[mutable.WrappedArray[GenericRowWithSchema]]("data").foreach(getDFRecords2(_)))
      SparkSession.builder().getOrCreate().sqlContext.createDataFrame(debtsBuffer)
    }
  }

  def getDFRecords(row: Row): Unit = {
    personBuffer += Person(row.getAs[Long]("ID"), row.getAs[String]("Name"), row.getAs[String]("Phone"), row.getAs[String]("Country"), row.getAs[String]("Notes"), row.getAs[String]("Date"))
  }

  def getDFRecords2(row: Row): Unit = {
    debtsBuffer += Debts(row.getAs[Long]("ID"), row.getAs[String]("Income"), row.getAs[Long]("Debt"), row.getAs[String]("Payday_limit"))
  }
}
