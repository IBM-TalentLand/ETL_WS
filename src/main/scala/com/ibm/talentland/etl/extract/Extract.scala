package com.ibm.talentland.etl.extract

import com.ibm.talentland.common.utils.{AppEnv, FileUtils}
import com.ibm.talentland.etl.services.Cloudant
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.Row

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Extract {
  var personBuffer = ListBuffer[SampleA]()
  var debtsBuffer = ListBuffer[SampleB]()

  case class SampleA(
    var ID: Long,
    var Name: String,
    var Phone: String,
    var Country: String,
    var Notes: String,
    var Date: String
  )

  case class SampleB(
    var ID: Long,
    var Income: String,
    var Debt: Long,
    var Payday_Limit: String
  )

  def extract(): (DataFrame, DataFrame) = {
    FileUtils.deleteFile("sample1.json", "sample2.json")
    val cloudant = new Cloudant(
      AppEnv.getPropCloudant("cloudant.host"),
      AppEnv.getPropCloudant("cloudant.username"),
      AppEnv.getPropCloudant("cloudant.password")
    )
    (getDFfromJson(cloudant, 1), getDFfromJson(cloudant, 2))
  }

  def getDFfromJson(cloudant: Cloudant, ntable: Int): DataFrame = {
    FileUtils.writeJsonFile(
      s"sample$ntable.json",
      cloudant.getDocument("etl-workshop", s"Sample_$ntable").asString.body
    )

    val sample = SparkSession.builder().master("local[*]").getOrCreate().read.json(s"sample$ntable.json")
    if(ntable == 1){
      sample.foreach(_.getAs[mutable.WrappedArray[GenericRowWithSchema]]("data").foreach(getDFRecords(_)))
      SparkSession.builder().getOrCreate().sqlContext.createDataFrame(personBuffer)
    } else {
      sample.foreach(_.getAs[mutable.WrappedArray[GenericRowWithSchema]]("data").foreach(getDFRecords2(_)))
      SparkSession.builder().getOrCreate().sqlContext.createDataFrame(debtsBuffer)
    }
  }

  def getDFRecords(row: Row): Unit = {
    personBuffer += SampleA(row.getAs[Long]("ID"), row.getAs[String]("Name"), row.getAs[String]("Phone"), row.getAs[String]("Country"), row.getAs[String]("Notes"), row.getAs[String]("Date"))
  }

  def getDFRecords2(row: Row): Unit = {
    debtsBuffer += SampleB(row.getAs[Long]("ID"), row.getAs[String]("Income"), row.getAs[Long]("Debt"), row.getAs[String]("Payday_limit"))
  }
}
