package com.ibm.talentland.common.utils

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object ParquetUtils {

  /**
    * Writes a Parquet file to object storage
    *
    * @param dataFrame [[DataFrame]] the data to write
    * @param path      [[String]] the destination path within the bucket
    */
  def writeParquet(dataFrame: DataFrame, path: String): Unit = {
    this.writeParquet(dataFrame, path, SaveMode.Overwrite)
  }

  /**
    * Writes a Parquet file to object storage
    *
    * @param dataFrame [[DataFrame]] the data to write
    * @param path      [[String]] the destination path within the bucket
    * @param saveMode  [[SaveMode]] Specifies the behavior when data already exists
    */
  def writeParquet(dataFrame: DataFrame, path: String, saveMode: SaveMode): Unit = {
    if (dataFrame.rdd.isEmpty()) return

    dataFrame.coalesce(10).write.mode(saveMode).parquet(path)

    println(s"Parquet file written to path: $path")
  }

  /**
    * Reads a parquet file at the specified path
    *
    * @param path [[String]] the path to the parquet file(s)
    * @return [[DataFrame]] a DataFrame containing the parquet data
    */
  def readParquet(path: String): DataFrame = {

    var dataFrame: DataFrame = null
    try {
      dataFrame = SparkSession.builder().getOrCreate().read.option("mergeSchema", "true").parquet(path)
    } catch {
      case e: Throwable => println(e.getMessage)
    }
    dataFrame.dropDuplicates()
  }

}
