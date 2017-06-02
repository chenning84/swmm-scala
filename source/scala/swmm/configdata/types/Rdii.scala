package swmm.configdata.types

import swmm.configdata.jnodes.types.TUnitHyd
import swmm.util.Project

/**
  * Created by ning on 1/2/16.
  */
object Rdii {
  val project = Project.getInstance
  def rdii_readUnitHydParams( tok:Array[String]) =
    //
    //  Input:   tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads parameters of an RDII unit hydrograph from a line of input.
    //
  {
//    int i, j, k, m, g
    var x:Array[Double]=Array.fill(6)(0)

    // --- check that RDII UH object exists in database
    var tUNITHYD:TUnitHyd=null
    var oUNITHYD = project.project_findObject("UNITHYD", tok(0))
    if(oUNITHYD.isDefined)
      tUNITHYD =oUNITHYD.get.asInstanceOf[TUnitHyd]
//    if ( j < 0 ) return error_setInpError(ERR_NAME, tok[0])

    // --- assign UH ID to name in hash table

    //FIXME is this possible ?
//    if ( tUNITHYD.ID == NULL )
//      tUNITHYD.ID = project.project_findID(UNITHYD, tok[0])

    // --- line has 2 tokens assign rain gage to UH object
//    if ( ntoks == 2 )
//    {
//      g = project_findObject(GAGE, tok[1])
//      if ( g < 0 ) return error_setInpError(ERR_NAME, tok[1])
//      UnitHyd[j].rainGage = g
//      return 0
//    }
//    else if ( ntoks < 6 ) return error_setInpError(ERR_ITEMS, "")
     val oGage = project.findGage(tok(1))
    var tGage:TGage =null
    if(oGage.isDefined)
      tGage = oGage.get.asInstanceOf[TGage]
    // --- find which month UH params apply to
//    m = datetime_findMonth(tok(1])
//    if ( m == 0 )
//    {
//      if ( !match(tok(1), w_ALL) )
//      return error_setInpError(ERR_KEYWORD, tok(1])
//    }
//
//    // --- find type of UH being specified
//    k = findmatch(tok(2], UHTypeWords)
//
//    // --- if no type match, try using older UH line format
//    if ( k < 0 ) return readOldUHFormat(j, m, tok, ntoks)
//
//    // --- read the R-T-K parameters
//    for ( i <- 0 to 2  )
//    {
//       x(j) = tok(i+3).toDouble
////      if ( ! getDouble(tok[i+3], &x(i]) )
////      return error_setInpError(ERR_NUMBER, tok[i+3])
//    }
//
//    // --- read the IA parameters if present
////    for (i = 3 i < 6 i++)
//    for (i <- 3 to 5 )
//    {
////      x[i] = 0.0
////      if ( ntoks > i+3 )
////      {
////        if ( ! getDouble(tok[i+3], &x[i]) )
////        return error_setInpError(ERR_NUMBER, tok[i+2])
////      }
//      x(i)=.0
//      x(i) =tok(i+3).toDouble
//    }
//
//    // --- save UH params
//    setUnitHydParams(j, k, m, x)
 //   return 0
  }
  //=============================================================================

  def  setUnitHydParams(tUNITHYD:TUnitHyd,  i:Int,  m:Int,  x:Array[Double])
  //
  //  Input:   j = unit hydrograph index
  //           i = type of UH response (short, medium or long term)
  //           m = month of year (0 = all months)
  //           x = array of UH parameters
  //  Output:  none
  //  Purpose: assigns parameters to a unit hydrograph for a specified month of year.
  //
  {
    var    m1, m2:Int =0                     // start/end month indexes
    var  t,                          // UH time to peak (hrs)
    k,                          // UH k-value
    tBase:Double =.0                      // UH base time (hrs)

    // --- find range of months that share same parameter values
    if ( m == 0 )
    {
      m1 = 0
      m2 = 11
    }
    else
    {
      m1 = m-1
      m2 = m1
    }

    // --- for each month in the range
    for (m<-m1 to m2)
    {
      // --- set UH response ratio, time to peak, & base time
      tUNITHYD.r(m)(i) = x(0)
      t = x(1)
      k = x(2)
      tBase = t * (1.0 + k)                              // hours
      tUNITHYD.tPeak(m)(i) = (t * 3600.0).toLong         // seconds
      tUNITHYD.tBase(m)(i) = (tBase * 3600.0).toLong     // seconds

      // -- set initial abstraction parameters
      tUNITHYD.iaMax(m)(i)   = x(3)
      tUNITHYD.iaRecov(m)(i) = x(4)
      tUNITHYD.iaInit(m)(i)  = x(5)
    }
  }

  //=============================================================================

  def readOldUHFormat(j:Int, m:Int, tok:Array[String]) =
    //
    //  Input:   j = unit hydrograph index
    //           m = month of year (0 = all months)
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads parameters of a set of RDII unit hydrographs from a line of
    //           input.
    //
  {
    var    i, k:Int =0
    var p:Array[Double] =Array.fill(9)(0)
    var x:Array[Double] =Array.fill(6)(0)

    // --- check for proper number of tokens
//    if ( ntoks < 11 ) return error_setInpError(ERR_ITEMS, "")

    // --- read 3 sets of r-t-k values
    for ( i <- 0 to 8  )
    {
//      if ( ! getDouble(tok[i+2], &p[i]) )
//      return error_setInpError(ERR_NUMBER, tok[i+2])
      p(i) =tok(i+2).toDouble
    }

    // --- read initial abstraction parameters
    for (i <- 0 to 2 )
    {
//      x[i+3] = 0.0
//      if ( ntoks > i+11 )
//      {
//        if ( ! getDouble(tok(i+11], &x[i+3]) )
//        return error_setInpError(ERR_NUMBER, tok[i+11])
//      }
      x(i+3) =tok(i+11).toDouble
    }

    // --- save UH parameters
    for ( k <- 0 to 2 )
    {
      for ( i <- 0 to 2 )
      {
        x(i) = p(3*k + i)
        //FIXME NOW
        //setUnitHydParams(j, k, m, x)
      }
    }
   // return 0
  }

}
