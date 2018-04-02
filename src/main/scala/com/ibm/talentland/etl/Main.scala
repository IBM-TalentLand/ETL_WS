package com.ibm.talentland.etl

import com.ibm.talentland.common.utils.Logs
import com.ibm.talentland.etl.extract.Extract
import com.ibm.talentland.etl.load.Load
import com.ibm.talentland.etl.transform.Transform

object Main {
  def main(args: Array[String]): Unit = {
    Logs.disableLogs()
    val data = Extract.extract()
    val df = Transform.transform(data._1)
    //    Transform.transform(data._1,data._2)
    Load.load(df)
  }
}
