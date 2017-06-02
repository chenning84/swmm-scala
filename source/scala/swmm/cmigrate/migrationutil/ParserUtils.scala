package swmm.cmigrate.migrationutil

/**
 * Created by ning on 10/31/15.
  *
 */
object ParserUtils {

  def isCommentLine(line:String) =
  {
    if((line!=null) && (line.startsWith("//")||((line.indexOf("//")<5)&&(line.indexOf("//")>0))))
      true
    else
      false
  }
}
