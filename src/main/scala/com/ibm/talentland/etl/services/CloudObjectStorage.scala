package com.ibm.talentland.etl.services

import com.ibm.talentland.common.utils.ParquetUtils
import org.apache.spark.sql.{DataFrame, SparkSession}

class CloudObjectStorage(private val endpoint: String, private val accessKey: String, private val secretKey: String) {
  val sc = SparkSession.builder().getOrCreate().sparkContext
  val conf = Map(
    "fs.stocator.scheme.list" -> "cos",
    "fs.cos.impl" -> "com.ibm.stocator.fs.ObjectStoreFileSystem",
    "fs.stocator.cos.impl" -> "com.ibm.stocator.fs.cos.COSAPIClient",
    "fs.stocator.cos.scheme" -> "cos",
    "fs.cos.service.endpoint" -> endpoint,
    "fs.cos.service.v2.signer.type" -> "false",
    "fs.cos.service.access.key" -> accessKey,
    "fs.cos.service.secret.key" -> secretKey
  )
  for ((name, value) <- conf) sc.hadoopConfiguration.set(name, value)

  def putParquetObject(bucket: String, df: DataFrame): Unit = {
    val path = s"cos://${bucket}.service/transform.parquet"
    ParquetUtils.writeParquet(df, path)
  }

  def putParquetObject(bucket: String, parquetName: String, df: DataFrame): Unit = {
    val path = s"cos://${bucket}.service/$parquetName.parquet"
    ParquetUtils.writeParquet(df, path)
  }

  def getParquetObject(bucket: String): DataFrame = {
    val path = s"cos://${bucket}.service/transform.parquet"
    ParquetUtils.readParquet(path)
  }

}