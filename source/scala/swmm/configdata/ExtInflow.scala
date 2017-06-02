package swmm.configdata

/**
 * Created by ning on 10/24/15.
 //------------------------------
// DIRECT EXTERNAL INFLOW OBJECT
//------------------------------
struct ExtInflow
{
   int            param;         // pollutant index (flow = -1)
   int            type;          // CONCEN or MASS
   int            tSeries;       // index of inflow time series
   int            basePat;       // baseline time pattern
   double         cFactor;       // units conversion factor for mass inflow
   double         baseline;      // constant baseline value
   double         sFactor;       // time series scaling factor
   struct ExtInflow* next;       // pointer to next inflow data object
};
typedef struct ExtInflow TExtInflow;
 */
class ExtInflow(pParam:Int,pCType:Int,pTSeries:Int,pBasePat:Int) {

  val  param = pParam         // pollutant index (flow = -1)
  val            ctype =pCType         // CONCEN or MASS
//  var            tSeries:Int      // index of inflow time series
//  var            basePat:Int     // baseline time pattern
//  var         cFactor:Double      // units conversion factor for mass inflow
//  var         baseline:Double      // constant baseline value
//  var         sFactor:Double       // time series scaling factor
//  var         cnext:ExtInflow       // pointer to next inflow data object

}
