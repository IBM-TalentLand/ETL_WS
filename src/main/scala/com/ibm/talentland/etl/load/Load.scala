package com.ibm.talentland.etl.load

import com.ibm.talentland.common.utils.AppEnv
import com.ibm.talentland.etl.services.CloudObjectStorage
import org.apache.spark.sql.DataFrame

object Load {
  def load(df: DataFrame): Unit = {
    val cos = new CloudObjectStorage(
      AppEnv.getPropCos("cos.endpoint"),
      AppEnv.getPropCos("cos.accessKey"),
      AppEnv.getPropCos("cos.secretKey")
    )
    cos.putParquetObject("talentland", df)
    cos.getParquetObject("talentland").show()
  }
}
