package swmm.util

/**
  * Created by ning on 11/11/15.
  */
object FPrintFUtil {
  def fprintf(file:String,line:String)=
  {
    file.concat(line)
  }
  def fprintf(file:String,fmtString:String,line:Any*)=
  {
    file.concat(String.format(fmtString,line))
  }

}
