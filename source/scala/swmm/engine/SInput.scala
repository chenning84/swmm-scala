package swmm.engine

import swmm.configdata.jnodes.GlobalContext
import swmm.util.Project

/**
  *
  * Created by ning on 11/7/15.
  *
  */
object SInput{
  val gContext = Project.getInstance
  def input_readData(): Unit =
  {

  }


  //=============================================================================

  def input_countObjects() =
    //
    //  Input:   none
    //  Output:  returns error code
    //  Purpose: reads input file to determine number of system objects.
    //
  {
    val Nobjects  =SInput.gContext.Nobjects
    val Nnodes  =SInput.gContext.Node
    val Nlinks  =SInput.gContext.Nlinks
    var  line:String=""             // line from input data file     
    var  wLine:String=""            // working copy of input line   
    var  tok:String=""                        // first string token of line          
    var   sect: Int = -1
    var newsect=0          // input data sections          
    var   errcode = 0                 // error code
    var   errsum = 0                  // number of errors found                   
    var   i=0
    var  lineCount = .0
    

//    // --- initialize number of objects & set default values
//    if ( ErrorCode ) return ErrorCode
//    error_setInpError(0, "")
//    for (i = 0 i < MAX_OBJ_TYPES i++) Nobjects(i) = 0
//    for (i = 0 i < MAX_NODE_TYPES i++) Nnodes(i) = 0
//    for (i = 0 i < MAX_LINK_TYPES i++) Nlinks(i) = 0
//
//    // --- make pass through data file counting number of each object
//    while ( fgets(line, MAXLINE, Finp.file) != NULL )
//    {
//      // --- skip blank lines & those beginning with a comment
//      lineCount++
//      strcpy(wLine, line)           // make working copy of line
//      tok = strtok(wLine, SEPSTR)   // get first text token on line
//      if ( tok == NULL ) continue
//      if ( tok == '' ) continue
//
//      // --- check if line begins with a new section heading
//      if ( tok == '[' )
//      {
//        // --- look for heading in list of section keywords
//        newsect = findmatch(tok, SectWords)
//        if ( newsect >= 0 )
//        {
//          sect = newsect
//          continue
//        }
//        else
//        {
//          sect = -1
//          errcode = ERR_KEYWORD
//        }
//      }
//
//      // --- if in OPTIONS section then read the option setting
//      //     otherwise add object and its ID name (tok) to project
//      if ( sect == s_OPTION ) errcode = readOption(line)
//      else if ( sect >= 0 )   errcode = addObject(sect, tok)
//
//      // --- report any error found
//      if ( errcode )
//      {
//        report_writeInputErrorMsg(errcode, sect, line, lineCount)
//        errsum++
//        if (errsum >= MAXERRS ) break
//      }
//    }
//
//    // --- set global error code if input errors were found
//    if ( errsum > 0 ) ErrorCode = ERR_INPUT
//    return ErrorCode
  }

}
