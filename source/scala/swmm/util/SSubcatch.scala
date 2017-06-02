package swmm.util

import swmm.configdata.jnodes.GlobalContext

/**
  * Created by ning on 11/19/15.
  *
  */
object  SSubcatch {
 val MCOEFF: Double = 1.49
 val MEXP: Double = 1.6666667
 val ODETOL: Double = 0.0001

 var Vevap: Double = .0
 var Vpevap: Double = .0
 var Vinfil: Double = .0
 var Vinflow: Double = .0
 var Voutflow: Double = .0
 var VlidIn: Double = .0
 var VlidInfil: Double = .0
 var VlidOut: Double = .0
 var VlidDrain: Double = .0
 var VlidReturn: Double = .0
  var gInstance= Project.getInstance
  def  subcatch_readParams(j:Int,  tok:List[String],  ntoks:Int):Int=
    //
    //  Input:   j = subcatchment index
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads subcatchment parameters from a tokenized  line of input data.
    //
    //  Data has format:
    //    Name  RainGage  Outlet  Area  %Imperv  Width  Slope CurbLength  Snowpack
    //
  {
    var     i, k, m=0
    var  id:String=""
//    var x:Array[String]
//
//
//
//
//    // --- check that named subcatch exists
//    id = project_findID(SUBCATCH, tok[0]);
//    if ( id == NULL ) return error_setInpError(ERR_NAME, tok[0]);
//
//    // --- check that rain gage exists
//    k = project_findObject(GAGE, tok[1]);
//    if ( k < 0 ) return error_setInpError(ERR_NAME, tok[1]);
//    x[0] = k;
//
//    // --- check that outlet node or subcatch exists
//    m = project_findObject(NODE, tok[2]);
//    x[1] = m;
//    m = project_findObject(SUBCATCH, tok[2]);
//    x[2] = m;
//    if ( x[1] < 0.0 && x[2] < 0.0 )
//    return error_setInpError(ERR_NAME, tok[2]);
//
//    // --- read area, %imperv, width, slope, & curb length
//    for ( i = 3; i < 8; i++)
//    {
//      if ( ! getDouble(tok[i], &x[i]) || x[i] < 0.0 )
//      return error_setInpError(ERR_NUMBER, tok[i]);
//    }
//
//    // --- if snowmelt object named, check that it exists
//    x[8] = -1;
//    if ( ntoks > 8 )
//    {
//      k = project_findObject(SNOWMELT, tok[8]);
//      if ( k < 0 ) return error_setInpError(ERR_NAME, tok[8]);
//      x[8] = k;
//    }
//
//    // --- assign input values to subcatch's properties
//    Subcatch[j].ID = id;
//    Subcatch[j].gage        = (int)x[0];
//    Subcatch[j].outNode     = (int)x[1];
//    Subcatch[j].outSubcatch = (int)x[2];
//    Subcatch[j].area        = x[3] / UCF(LANDAREA);
//    Subcatch[j].fracImperv  = x[4] / 100.0;
//    Subcatch[j].width       = x[5] / UCF(LENGTH);
//    Subcatch[j].slope       = x[6] / 100.0;
//    Subcatch[j].curbLength  = x[7];
//
//    // --- create the snow pack object if it hasn't already been created
//    if ( x[8] >= 0 )
//    {
//      if ( !snow_createSnowpack(j, (int)x[8]) )
//      return error_setInpError(ERR_MEMORY, "");
//    }
    return 0;
  }


}
