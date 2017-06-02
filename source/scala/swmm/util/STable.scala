package swmm.util

import org.joda.time.DateTime
import swmm.configdata.jnodes.Errorh.ErrorType
import swmm.configdata.jnodes.GlobalContext
import swmm.configdata.jnodes.SwmmEnum.{EvapType, FileUsageType}
import swmm.configdata.jnodes.types.{TTable, TTableEntry}

/**
 * Created by Ning on 11/12/15.
 *
 */
object STable {
  def table_validate(t: TTable ) :Int =
  {
     1
  }
  def table_tseriesInit(t: TTable):Int =
  {
    1
  }
  def  table_getNextEntry(table :TTable, x:DateTime, y:Double) :Boolean ={
    true
  }
  def table_getFirstEntry( table:TTable, x:DateTime,  y:Double):(TTableEntry, Double,Double)=
  //
  //  Input:   table = pointer to a TTable structure
  //  Output:  x = x-value of first table entry
  //           y = y-value of first table entry
  //           returns TRUE if successful, FALSE if not
  //  Purpose: retrieves the first x/y entry in a table.
  //
  //  NOTE: also moves the current position pointer (thisEntry) to the 1st entry.
  //
  {
    var entry: TTableEntry =null
    var x1 = .0
    var y1 = 0.0
    //   TODO: verify to make sure it is not used
    //    if ( table->file.mode == USE_FILE )
    //    {
    //      if ( table->file.file == NULL ) return FALSE
    //      rewind(table->file.file)
    //      return table_getNextFileEntry(table, x, y)
    //    }

    entry = table.firstEntry
    if ( entry!=null )
    {
      x1 = entry.x
      y1 = entry.y
      table.thisEntry = entry

    }
    (entry, x1 , y1)
  }
}
class STable {
  val TRUE:Int =1
  val FALSE:Int =0
    val gInstance =Project.getInstance
    val sGage =gInstance.Gage
    val sCurve =gInstance.Curve
    val sTSeries =gInstance.Tseries


  //=============================================================================
  
  def table_interpolate( x:Double,  x1:Double ,  y1 :Double ,  x2:Double,  y2:Double ) :Double =
//
//  Input:   x = x value being interpolated
//           x1, x2 = x values on either side of x
//           y1, y2 = y values corrresponding to x1 and x2, respectively
//  Output:  returns the y value corresponding to x
//  Purpose: interpolates a y value for a given x value.
//
{
    var dx = x2 - x1
    if ( Math.abs(dx) < 1.0e-20 ) return (y1 + y2) / 2.0
    y1 + (x - x1) * (y2 - y1) / dx
}

  //=============================================================================

  def  table_readCurve( tok:Array[String],  ntoks:Int) :Int =
//
//  Input:   tok[] = array of string tokens
//           ntoks = number of tokens
//  Output:  returns an error code
//  Purpose: reads a tokenized line of data for a curve table.
//
{
//    var    j,  k, k1 = 1
//    var m =false
//    var  x, y =.0
//
//    // --- check for minimum number of tokens
//    if ( ntoks < 3 ) return ErrorUtil.error_setInpError(ErrorType.ERR_ITEMS.ordinal(), "")
//
//    // --- check that curve exists in database
//    j = project_findObject(CURVE, tok(0))
//    if ( j < 0 ) return ErrorUtil.error_setInpError(ErrorType.ERR_NAME.ordinal(), tok(0))
//
//    // --- check if this is first line of curve's data
//    //     (curve's ID will not have been assigned yet)
//    if ( sCurve(j).ID == null )
//    {
//        // --- assign ID pointer & curve type
//        sCurve(j).ID = project_findID(CURVE, tok(0))
//        m = CurveTypeWords(tok(1))
//        if ( !m ) return ErrorUtil.error_setInpError(ErrorType.ERR_KEYWORD.ordinal(), tok(1))
//        sCurve(j).curveType = if (m) 1 else 0
//        k1 = 2
//    }
//
//    // --- start reading pairs of X-Y value tokens
//    for ( k = k1 k < ntoks k = k+2)
//    {
//        if ( k+1 >= ntoks ) return ErrorUtil.error_setInpError(ErrorType.ERR_ITEMS.ordinal(), "")
//         x = tok(k).toDouble
//         y = tok(k+1).toDouble
//
//        table_addEntry(sCurve(j), x, y)
//    }
     0
}

  //=============================================================================

  def table_readTimeseries( tok:Array[String],  ntoks:Int ):Int =
//
//  Input:   tok[] = array of string tokens
//           ntoks = number of tokens
//  Output:  returns an error code
//  Purpose: reads a tokenized line of data for a time series table.
//
{
//    var    j =0                          // time series index
//    var     k=0                          // token index
//    var     state=0                      // 1: next token should be a date
//                                       // 2: next token should be a time
//                                       // 3: next token should be a value
//    var x, y  =.0                     // time & value table entries
//    var  d :DateTime=null                       // day portion of date/time value
//    var  t:DateTime =null                        // time portion of date/time value
//
//    // --- check for minimum number of tokens
//    if ( ntoks < 3 ) return ErrorUtil.error_setInpError(ErrorType.ERR_ITEMS.ordinal, "")
//
//    // --- check that time series exists in database
//    j = project_findObject(TSERIES, tok(0))
//    if ( j < 0 ) return ErrorUtil.error_setInpError(ErrorType.ERR_NAME.ordinal, tok(0))
//
//    // --- if first line of data, assign ID pointer
//    if ( sTSeries(j).ID == null )
//        sTSeries(j).ID = project_findID(TSERIES, tok(0))
//
//    // --- check if time series data is in an external file
//    if ( tok(1).equals( w_FILE ) )
//    {
//        sTSeries(j).file.name = tok(2)
//        sTSeries(j).file.mode = FileUsageType.USE_FILE.ordinal()
//       // return 0
//    }
//
//    // --- parse each token of input line
//    var x = 0.0
//    k = 1
//    state = 1               // start off looking for a date
//    while ( k < ntoks )
//    {
//        state match
//        {
//          case 1 =>            // look for a date entry
//            if ( datetime_strToDate(tok(k), &d) )
//            {
//                sTSeries(j).lastDate = d
//                k++
//            }
//
//            // --- next token must be a time
//            state = 2
//
//
//          case 2 =>            // look for a time entry
//            if ( k >= ntoks ) return ErrorUtil.error_setInpError(ErrorType.ERR_ITEMS.ordinal, "")
//
//            // --- first check for decimal hours format
//            if ( getDouble(tok(k), &t) ) t /= 24.0
//
//            // --- then for an hrs:min format
//            else if ( !datetime_strToTime(tok(k), &t) )
//                return ErrorUtil.ErrorUtil.error_setInpError(ErrorType.ERR_NUMBER.ordinal, tok(k))
//
//            // --- save date + time in x
//            x = sTSeries(j).lastDate + t
//
//            // --- next token must be a numeric value
//            k++
//            state = 3
//
//
//          case 3 =>
//            // --- extract a numeric value from token
//            if ( k >= ntoks ) return ErrorUtil.error_setInpError(ErrorType.ERR_ITEMS.ordinal(), "")
//            y = tok(k) toDouble
//
//            // --- add date/time & value to time series
//            table_addEntry(sTSeries(j), x, y)
//
//            // --- start over looking first for a date
//            k++
//            state = 1
//
//        }
//    }
     0
}

  //=============================================================================

  def  table_addEntry( table:TTable, x:Double, y:Double):Int =
//
//  Input:   table = pointer to a TTable structure
//           x = x value
//           y = y value
//  Output:  returns TRUE if successful, FALSE if not
//  Purpose: adds a new x/y entry to a table.
//
{
    var entry = new  TTableEntry()
    if ( entry ==null ) return FALSE
    entry.x = x
    entry.y = y
    entry.next = null
    if ( table.firstEntry == null )  table.firstEntry = entry
    else table.lastEntry.next = entry
    table.lastEntry = entry
     TRUE
}

  //=============================================================================

  def    table_deleteEntries(table:TTable)
//
//  Input:   table = pointer to a TTable structure
//  Output:  none
//  Purpose: deletes all x/y entries in a table.
//
{
    var  nextEntry:TTableEntry =null
     var entry = table.firstEntry
    while (entry!=null)
    {
        nextEntry = entry.next
        entry =null
        entry = nextEntry
    }
    table.firstEntry = null
    table.lastEntry  = null
    table.thisEntry  = null

  //FIXME:
//    if (table.file.file)
//    {
//        fclose(table.file.file)
//        table.file.file = null
//    }
}

  //=============================================================================

  def    table_init( table:TTable) =
//
//  Input:   table = pointer to a TTable structure
//  Output:  none
//  Purpose: initializes properties when table is first created.
//
{
    table.id = null
    table.refersTo = EvapType.UNDEFINED
    table.firstEntry = null
    table.lastEntry = null
    table.thisEntry = table.firstEntry
    table.lastDate = 0.0
    table.x1 = 0.0
    table.x2 = 0.0
    table.y1 = 0.0
    table.y2 = 0.0
    table.dxMin = 0.0
    table.file.mode = FileUsageType.NO_FILE.ordinal()
    table.file.file = null
    table.curveType = -1
}

  //=============================================================================

  def   table_validate( table:TTable) :Int =
//
//  Input:   table = pointer to a TTable structure
//  Output:  returns error code
//  Purpose: checks that table's x-values are in ascending order.
//
{
//    var     result =0
//    var x1, x2, y1, y2=.0
//    var dx, dxMin = BIG
//
//    // --- open external file if used as the table's data source
//    if ( table.file.mode == FileUsageType.USE_FILE )
//    {
//        table.file.file = fopen(table.file.name, "rt")
//        if ( table.file.file == null ) return ERR_TABLE_FILE_OPEN
//    }
//
//    // --- retrieve the first data entry in the table
//    result = table_getFirstEntry(table, x1, y1)
//
//    // --- return error condition if external file has no valid data
//    if ( !result && table.file.mode == FileUsageType.USE_FILE ) return ERR_TABLE_FILE_READ
//
//    // --- retrieve successive table entries and check for non-increasing x-values
//    while ( table_getNextEntry(table, x2, y2) )
//    {
//        dx = x2 - x1
//        if ( dx <= 0.0 )
//        {
//            table.x2 = x2
//            return ERR_CURVE_SEQUENCE
//        }
//        dxMin = Math.min(dxMin, dx)
//        x1 = x2
//    }
//    table.dxMin = dxMin
//
//    // --- return error if external file could not be read completely
//    if ( table.file.mode == FileUsageType.USE_FILE && !feof(table.file.file) )
//        return ERR_TABLE_FILE_READ
     0
}

  //=============================================================================

  def  table_getFirstEntry(table:TTable, x:Double, y:Double ):Int =
//
//  Input:   table = pointer to a TTable structure
//  Output:  x = x-value of first table entry
//           y = y-value of first table entry
//           returns TRUE if successful, FALSE if not
//  Purpose: retrieves the first x/y entry in a table.
//
//  NOTE: also moves the current position pointer (thisEntry) to the 1st entry.
//
{

//    var x = 0
//    var y = 0.0
//
//    if ( table.file.mode == FileUsageType.USE_FILE )
//    {
//        if ( table.file.file == null ) return FALSE
//        rewind(table.file.file)
//        return table_getNextFileEntry(table, x, y)
//    }
//
//    var  entry = table.firstEntry
//    if ( entry!=null )
//    {
//        x = entry.x
//        y = entry.y
//        table.thisEntry = entry
//        return TRUE
//    }
//    else return FALSE
  1
}

  //=============================================================================

  def  table_getNextEntry(table :TTable, x:Double, y:Double) :Int =
//
//  Input:   table = pointer to a TTable structure
//  Output:  x = x-value of next table entry
//           y = y-value of next table entry
//           returns TRUE if successful, FALSE if not
//  Purpose: retrieves the next x/y entry in a table.
//
//  NOTE: also updates the current position pointer (thisEntry).
//
{


//    if ( table.file.mode == FileUsageType.USE_FILE )
//        return table_getNextFileEntry(table, x, y)
//
//    var entry = table.thisEntry.next
//    if ( entry !=null)
//    {
//        x = entry.x
//        y = entry.y
//        table.thisEntry = entry
//        return TRUE
//    }
//    else return FALSE
  1
}

  //=============================================================================

  ////  Revised for release 5.1.008  ////                                        //(5.1.008)

  def  table_lookup( table:TTable,  x:Double):Double =
//
//  Input:   table = pointer to a TTable structure
//           x = an x-value
//  Output:  returns a y-value
//  Purpose: retrieves the y-value corresponding to an x-value in a table,
//           using interploation if necessary.
//
//  NOTE: if x is below the first table entry, then the first y-value is
//        returned if x is above the last entry, then the last y-value is
//        returned.
//
{
    var  x1,y1,x2,y2=.0
    

    var entry = table.firstEntry
    if ( entry == null ) return 0.0
    x1 = entry.x
    y1 = entry.y
    if ( x <= x1 ) return y1
    while ( entry.next!=null )
    {
        entry = entry.next
        x2 = entry.x
        y2 = entry.y
        if ( x <= x2 ) return table_interpolate(x, x1, y1, x2, y2)
        x1 = x2
        y1 = y2
    }
     y1
}

  //=============================================================================

  ////  Revised for release 5.1.008.  ////                                       //(5.1.008)

  def  table_getSlope(table:TTable, x:Double):Double =
//
//  Input:   table = pointer to a TTable structure
//           x = an x-value
//  Output:  returns the slope of the curve at x
//  Purpose: retrieves the slope of the curve at the line segment containing x.
//
{
    var  x1,y1,x2,y2 =.0
    var  dx=.0
    

    var entry = table.firstEntry
    if ( entry == null ) return 0.0
    x1 = entry.x
    y1 = entry.y
    x2 = x1
    y2 = y1
   while(entry.next !=null)
    {
        entry = entry.next
        x2 = entry.x
        y2 = entry.y
      //FIXME  if ( x <= x2 ) break
        x1 = x2
        y1 = y2
    }
    dx = x2 - x1
    if ( dx == 0.0 ) return 0.0
    (y2 - y1) / dx
}

  //=============================================================================

  ////  Revised for release 5.1.008.  ////                                       //(5.1.008)

  def  table_lookupEx(table:TTable, x:Double):Double =
//
//  Input:   table = pointer to a TTable structure
//           x = an x-value
//  Output:  returns a y-value
//  Purpose: retrieves the y-value corresponding to an x-value in a table,
//           using interploation if necessary within the table and linear
//           extrapolation outside of the table.
//
{
    var  x1,y1,x2,y2=.0
    var  s = 0.0


    var entry = table.firstEntry
    if (entry == null ) return 0.0
    x1 = entry.x
    y1 = entry.y
    if ( x <= x1 )
    {
        if (x1 > 0.0 ) return x/x1*y1
        else return y1
    }
    while ( entry.next!=null )
    {
        entry = entry.next
        x2 = entry.x
        y2 = entry.y
        if ( x2 != x1 ) s = (y2 - y1) / (x2 - x1)
        if ( x <= x2 ) return table_interpolate(x, x1, y1, x2, y2)
        x1 = x2
        y1 = y2
    }
    if ( s < 0.0 ) s = 0.0
    y1 + s*(x - x1)
}

  //=============================================================================

  ////  Revised for release 5.1.008.  ////                                       //(5.1.008)

  def table_intervalLookup(table:TTable, x:Double):Double =
//
//  Input:   table = pointer to a TTable structure
//           x = an x-value
//  Output:  returns a y-value
//  Purpose: retrieves the y-value corresponding to the first table entry
//           whose x-value is > x.
//
{


    var entry = table.firstEntry
    if (entry == null ) return 0.0
    if ( x < entry.x ) return entry.y
   while(entry.next !=null)
    {
        entry = entry.next
        if ( x < entry.x ) return entry.y
    }
    entry.y
}

  //=============================================================================

  ////  Revised for release 5.1.008.  ////                                       //(5.1.008)

  def  table_inverseLookup(table:TTable, y:Double) :Double =
//
//  Input:   table = pointer to a TTable structure
//           y = a y-value
//  Output:  returns an x-value
//  Purpose: retrieves the x-value corresponding to an y-value in a table,
//           using interploation if necessary.
//
//  NOTE: if y is below the first table entry, then the first x-value is
//        returned if y is above the last entry, then the last x-value is
//        returned.
//
{
    var x1,y1,x2,y2 =.0

    var entry = table.firstEntry
    if (entry == null ) return 0.0
    x1 = entry.x
    y1 = entry.y
    if ( y <= y1 ) return x1
   while(entry.next !=null)
    {
        entry = entry.next
        x2 = entry.x
        y2 = entry.y
        if ( y <= y2 ) return table_interpolate(y, y1, x1, y2, x2)
        x1 = x2
        y1 = y2
    }
    x1
}

  //=============================================================================

  ////  Revised for release 5.1.008.  ////                                       //(5.1.008)

  def   table_getMaxY(table:TTable, x:Double):Double =
//
//  Input:   table = pointer to a TTable structure
//           x = an x-value
//  Output:  returns the maximum y-value for x-values below x.
//  Purpose: finds the largest y value in the initial non-decreasing
//           portion of a table that appear before value x.
//
{
    var  entry = table.firstEntry
    var ymax = entry.y
    while ( x > entry.x && entry.next!=null )
    {
        entry = entry.next
        if ( entry.y < ymax ) return ymax
        ymax = entry.y
    }
    0.0
}

  //=============================================================================

  ////  Revised for release 5.1.008.  ////                                       //(5.1.008)

  def  table_getArea( table:TTable, x:Double):Double =
//
//  Input:   table = pointer to a TTable structure
//           x = an value
//  Output:  returns area value
//  Purpose: finds area under a tabulated curve from 0 to x
//           requires that table's x values be non-negative
//           and non-decreasing.
//
//  The area within each interval i of the table is given by:
//     Integral{ y(x)*dx } from x(i) to x
//  where y(x) = y(i) + s*dx
//        dx = x - x(i)
//        s = [y(i+1) - y(i)] / [x(i+1) - x(i)]
//  This results in the following expression for a(i):
//     a(i) = y(i)*dx + s*dx*dx/2
//
{
    var  x1, x2=.0
    var  y1, y2=.0
    var  dx ,dy = 0.0
    var a, s = 0.0
    

    // --- get area up to first table entry
    //     and see if x-value lies in this interval
    var entry = table.firstEntry
    if (entry == null ) return 0.0
     x1 = entry.x
     y1 = entry.y
    if ( x1 > 0.0 ) s = y1/x1
    if ( x <= x1 ) return s*x*x/2.0
    a = y1*x1/2.0
    
    // --- add next table entry to area until target x-value is bracketed
    while ( entry.next !=null )
    {
        entry = entry.next
        x2 = entry.x
        y2 = entry.y
        dx = x2 - x1
        dy = y2 - y1
        if ( x <= x2 )
        {
            if ( dx <= 0.0 ) return a
            y2 = table_interpolate(x, x1, y1, x2, y2)
            return a + (x - x1) * (y1 + y2) / 2.0
        }
        a += (y1 + y2) * dx / 2.0
        x1 = x2
        y1 = y2
    }

    // --- extrapolate area if table limit exceeded
    if ( dx > 0.0 ) s = dy/dx
    else s = 0.0
    dx = x - x1
     a + y1*dx + s*dx*dx/2.0
}

  //=============================================================================

  ////  Revised for release 5.1.008.  ////                                       //(5.1.008)

  def   table_getInverseArea(table:TTable, a:Double):Double =
//
//  Input:   table = pointer to a TTable structure
//           a = an area value
//  Output:  returns an x value
//  Purpose: finds x value for given area under a curve.
//
//  Refer to table_getArea function to see how area is computed.
//
{
    var x1, x2 =.0
    var y1, y2=.0
    var dx , dy = 0.0
    var a1, a2, s=.0

    // --- see if target area is below that of 1st table entry
    var entry = table.firstEntry
    if (entry == null ) return 0.0
    x1 = entry.x
    y1 = entry.y
    a1 = y1*x1/2.0
    if ( a1 >= a )
    {
        if ( y1 > 0.0 ) return Math.sqrt(2.0 * a * x1/y1)
        else return 0.0
    }

    // --- add next table entry to area until target area is bracketed
   while(entry.next !=null)
    {
        entry = entry.next
        x2 = entry.x
        y2 = entry.y
        dx = x2 - x1
        dy = y2 - y1
        a2 = a1 + y1*dx + dy*dx/2.0
        if ( a <= a2 )
        {
            if ( dx <= 0.0 ) return x1
            if ( dy == 0.0 )
            {
                if ( a2 == a1 ) return x1
                else return x1 + dx * (a - a1) / (a2 - a1)
            }

            // --- if y decreases with x then replace point 1 with point 2
            if ( dy < 0.0 )
            {
                x1 = x2
                y1 = y2
                a1 = a2
            }

            s = dy/dx
            dx = (Math.sqrt(y1*y1 + 2.0*s*(a - a1)) - y1) / s
            return x1 + dx
        }
        x1 = x2
        y1 = y2
        a1 = a2
    }

    // --- extrapolate area if table limit exceeded
    if ( dx == 0.0 || dy == 0.0 )
    {
        if ( y1 > 0.0 ) dx = (a - a1) / y1
        else dx = 0.0
    }
    else
    {
        s = dy/dx
        dx = (Math.sqrt(y1*y1 + 2.0*s*(a-a1)) - y1) / s
        if (dx < 0.0) dx = 0.0
    }
    x1 + dx
}

  //=============================================================================

  def    table_tseriesInit(table:TTable)=
//
//  Input:   table = pointer to a TTable structure
//  Output:  none
//  Purpose: initializes the time bracket within a time series table.
//
{
    table_getFirstEntry(table, table.x1, table.y1)
    table.x2 = table.x1
    table.y2 = table.y1
    table_getNextEntry(table, table.x2, table.y2)
}

  //=============================================================================

  def  table_tseriesLookup(table:TTable, x:Double, extend:Char):Double =
//
//  Input:   table = pointer to a TTable structure
//           x = a date/time value
//           extend = TRUE if time series extended on either end
//  Output:  returns a y-value
//  Purpose: retrieves the y-value corresponding to a time series date,
//           using interploation if necessary.
//
//  NOTE: if extend is FALSE and date x is outside the range of the table
//        then 0 is returned if TRUE then the first or last value is
//        returned.
//
{
    // --- x lies within current time bracket
    if ( table.x1 <= x
    &&   table.x2 >= x
    &&   table.x1 != table.x2 )
    return table_interpolate(x, table.x1, table.y1, table.x2, table.y2)

    // --- x lies before current time bracket:
    //     move to start of time series
    if ( table.x1 == table.x2 || x < table.x1 )
    {
        table_getFirstEntry(table, table.x1, table.y1)
        if ( x < table.x1 )
        {
            if ( extend == TRUE ) return table.y1
            else return 0
        }
    }

    // --- x lies beyond current time bracket:
    //     update start of next time bracket
    table.x1 = table.x2
    table.y1 = table.y2

    // --- get end of next time bracket
    while ( table_getNextEntry(table, table.x2, table.y2)==1 )
    {
        // --- x lies within the bracket
        if ( x <= table.x2 )
            return table_interpolate(x, table.x1, table.y1,
                                        table.x2, table.y2)
        // --- otherwise move to next time bracket
        table.x1 = table.x2
        table.y1 = table.y2
    }

    // --- return last value or 0 if beyond last data value
    if ( extend == TRUE ) {
      table.y1
    }
    else {
      0.0
    }
}

  //=============================================================================

  def  table_getNextFileEntry(table:TTable, x:Double, y:Double):Int =
//
//  Input:   table = pointer to a TTable structure
//           x = pointer to a date (as decimal days)
//           y = pointer to a time series value
//  Output:  updates values of x and y
//           returns TRUE if successful, FALSE if not
//  Purpose: retrieves the next date and value for a time series
//           table stored in an external file.
//
{
  //FIXME: Most like has been donw by me
//    var line:String =""
//    var   code=0
//    if ( table.file.file == null ) return FALSE
//    while ( !feof(table.file.file) && fgets(line, MAXLINE, table.file.file) != null )
//    {
//        code = table_parseFileLine(line, table, x, y)
//        if ( code < 0 ) continue      //skip blank & comment lines
//        return code
//    }
    FALSE
}

  //=============================================================================

  def   table_parseFileLine( line:String, table:TTable, x:Double, y:Double):Int =
//
//  Input:   table = pointer to a TTable structure
//           x = pointer to a date (as decimal days)
//           y = pointer to a time series value
//  Output:  updates values of x and y
//           returns -1 if line was a comment, 
//           TRUE if line successfully parsed,
//           FALSE if line could not be parsed
//  Purpose: parses a line of time series data from an external file.
//
{
  //FIXME: Mostly like has been done by me
//    var    n=0
//    var  s1,          s2,          s3 =""
//    var tStr=""              // time as string
//    var yStr =""              // value as string
//    var yy =.0               // value as double
//    var d:DateTime=null              // day portion of date/time value
//    var t:DateTime=null              // time portion of date/time value
//
//    // --- get 3 string tokens from line and check if its a comment
//    n = sscanf(line, "%s %s %s", s1, s2, s3)
//
//    // --- return if line is blank or is a comment
//    tStr = strtok(line, SEPSTR)
//    if ( tStr == null || *tStr == '' ) return -1
//
//    // --- line only has a time and a value
//    if ( n == 2 )
//    {
//        // --- calendar date is same as last recorded date
//        d = table.lastDate
//        tStr = s1
//        yStr = s2
//    }
//
//    // --- line has date, time and a value
//    else if ( n == 3 )
//    {
//        // --- convert date string to numeric value
//        if ( !datetime_strToDate(s1, &d) ) return FALSE
//
//        // --- update last recorded calendar date
//        table.lastDate = d
//        tStr = s2
//        yStr = s3
//    }
//    else return FALSE
//
//    // --- convert time string to numeric value
//    if ( getDouble(tStr, &t) ) t /= 24.0
//    else if ( !datetime_strToTime(tStr, &t) ) return FALSE
//
//    // --- convert value string to numeric value
//    if ( !getDouble(yStr, &yy) ) return FALSE
//
//    // --- assign values to current date and value
//    *x = d + t
//    *y = yy
//    return TRUE
  0
}


}
