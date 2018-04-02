package com.ibm.talentland.etl.transform

import org.apache.spark.sql.DataFrame

object Transform {

  def transform(table: DataFrame): DataFrame = {
    table.printSchema()

    table.withColumnRenamed("Notes", "Description")

    table.select("ID", "Name").where(table.col("Name").like("%b%"))
  }

  def transform(t1: DataFrame, t2: DataFrame): Unit = {
    val join = t1.join(t2 ,t1.col("ID") ===  t2.col("ID"), "inner")
    join.show(100)
  }

}
