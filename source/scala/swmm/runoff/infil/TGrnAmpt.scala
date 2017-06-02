package swmm.runoff.infil



import swmm.util.FunctionUtil._
/**
  * Created by ning on 11/6/15.
  */
//-------------------------
// Green-Ampt Infiltration
//-------------------------
class TGrnAmpt extends InfilT {
   var S: Double = .0
   var Ks: Double = .0
   var IMDmax: Double = .0
   var IMD: Double = .0
   var F: Double = .0
   var Fu: Double = .0
   var Lu: Double = .0
   var T: Double = .0
   var Sat: Int = 0
   var FuMAX =.0
   //=============================================================================

   def  setParams( p:Array[Double]):TGrnAmpt=
   //
   //  Input:   infil = ptr. to Green-Ampt infiltration object
   //           p()= array of parameter values
   //  Output:  returns TRUE if parameters are valid, FALSE otherwise
   //  Purpose: assigns Green-Ampt infiltration parameters to a subcatchment.
   //
   {
      val infil = new TGrnAmpt
      var ksat:Double =.0                       // sat. hyd. conductivity in in/hr

     // if ( p(0)< 0.0 || p(1)<= 0.0 || p(2)< 0.0 ) return FALSE               //(5.1.007)
      infil.S      = p(0)/ UCF(RAINDEPTH)   // Capillary suction head (ft)
      infil.Ks     = p(1)/ UCF(RAINFALL)    // Sat. hyd. conductivity (ft/sec)
      infil.IMDmax = p(2)                   // Math.max. init. moisture deficit

      // --- find depth of upper soil zone (ft) using Mein's eqn.
      ksat = infil.Ks * 12.0 * 3600.0
      infil.Lu = 4.0 * Math.sqrt(ksat) / 12.0
     infil
   }

   //=============================================================================

   def  initState(infil:TGrnAmpt)
     //
     //  Input:   infil = ptr. to Green-Ampt infiltration object
     //  Output:  none
     //  Purpose: initializes state of Green-Ampt infiltration for a subcatchment.
     //
   {
      if (infil != null) {
        infil.IMD = infil.IMDmax
        infil.Fu = 0.0                                                           //(5.1.007)
        infil.F = 0.0
        infil.Sat = FALSE
        infil.T = 5400.0 / infil.Lu / evap.recoveryFactor

      }
   }

   def  getState(infil:TGrnAmpt, x:Array[Double])
   {
      x(0)= infil.IMD
      x(1)= infil.F
      x(2)= infil.Fu
      x(3)= infil.Sat
      x(4)= infil.T
   }

   def  setState(infil:TGrnAmpt,  x:Array[Double])=
   {
      infil.IMD = x(0)
      infil.F   = x(1)
      infil.Fu  = x(2)
      infil.Sat = x(3).toInt
      infil.T   = x(4)
   }

   //=============================================================================

   ////  This function was re-written for release 5.1.007  ////                   //(5.1.007)

   def  getInfil(infil:TGrnAmpt, tstep:Double,  irate:Double,
    depth:Double):Double =
     //
     //  Input:   infil = ptr. to Green-Ampt infiltration object
     //           tstep =  time step (sec),
     //           irate = net "rainfall" rate to upper zone (ft/sec)
     //                 = rainfall + snowmelt + runon,
     //                   does not include ponded water (added on below)
     //           depth = depth of ponded water (ft).
     //  Output:  returns infiltration rate (ft/sec)
     //  Purpose: computes Green-Ampt infiltration for a subcatchment
     //           or a storage node.
     //
   {
      // --- find saturated upper soil zone water volume
      FuMAX = infil.IMDmax * infil.Lu

      // --- reduce time until next event
      infil.T -= tstep

      // --- use different procedures depending on upper soil zone saturation
      if ( infil.Sat==1 ) getSatInfil(infil, tstep, irate, depth)
      else                getUnsatInfil(infil, tstep, irate, depth)
   }

   //=============================================================================

   ////  New function added to release 5.1.007  ////                              //(5.1.007)

   def  getUnsatInfil(infil:TGrnAmpt, tstep:Double,  irate:Double,
    depth:Double):Double=
     //
     //  Input:   infil = ptr. to Green-Ampt infiltration object
     //           tstep =  runoff time step (sec),
     //           irate = net "rainfall" rate to upper zone (ft/sec)
     //                 = rainfall + snowmelt + runon,
     //                   does not include ponded water (added on below)
     //           depth = depth of ponded water (ft).
     //  Output:  returns infiltration rate (ft/sec)
     //  Purpose: computes Green-Ampt infiltration when upper soil zone is
     //           unsaturated.
     //
   {
      //var ia:Double =.0
     var  c1, F2, dF, Fs, kr, ts:Double=.0
      var ks = infil.Ks * adjust.hydconFactor                               //(5.1.008)

      // --- get available infiltration rate (rainfall + ponded water)
      var ia = irate + depth / tstep
      if ( ia < 0 ) ia = 0.0

      // --- no rainfall so recover upper zone moisture
      if ( ia == 0.0 )
      {
         if ( infil.Fu <= 0.0 ) return 0.0
         kr = infil.Lu / 90000.0 * evap.recoveryFactor
         dF = kr * FuMAX * tstep
         infil.F -= dF
         infil.Fu -= dF
         if ( infil.Fu <= 0.0 )
         {
            infil.Fu = 0.0
            infil.F = 0.0
            infil.IMD = infil.IMDmax
            return 0.0
         }

         // --- if new wet event begins then reset IMD & F
         if ( infil.T <= 0.0 )
         {
            infil.IMD = (FuMAX - infil.Fu) / infil.Lu
            infil.F = 0.0
         }
         return 0.0
      }

      // --- rainfall does not exceed Ksat
      if ( ia <= ks )                                                            //(5.1.008)
      {
         dF = ia * tstep
         infil.F += dF
         infil.Fu += dF
         infil.Fu = Math.min(infil.Fu, FuMAX)
         if ( infil.T <= 0.0 )
         {
            infil.IMD = (FuMAX - infil.Fu) / infil.Lu
            infil.F = 0.0
         }
         return ia
      }

      // --- rainfall exceeds Ksat renew time to drain upper zone
      infil.T = 5400.0 / infil.Lu / evap.recoveryFactor

      // --- find volume needed to saturate surface layer
      Fs = ks * (infil.S + depth) * infil.IMD / (ia - ks)                     //(5.1.008)

      // --- surface layer already saturated
      if ( infil.F > Fs )
      {
         infil.Sat = TRUE
         return  getSatInfil(infil, tstep, irate, depth)
      }

      // --- surface layer remains unsaturated
      if ( infil.F + ia*tstep < Fs )
      {
         dF = ia * tstep
         infil.F += dF
         infil.Fu += dF
         infil.Fu = Math.min(infil.Fu, FuMAX)
         return ia
      }

      // --- surface layer becomes saturated during time step
      // --- compute portion of tstep when saturated
      ts  = tstep - (Fs - infil.F) / ia
      if ( ts <= 0.0 ) ts = 0.0

      // --- compute new total volume infiltrated
      c1 = (infil.S + depth) * infil.IMD
      F2 =  getF2(Fs, c1, ks, ts)                                        //(5.1.008)
      if ( F2 > Fs + ia*ts ) F2 = Fs + ia*ts

      // --- compute infiltration rate
      dF = F2 - infil.F
      infil.F = F2
      infil.Fu += dF
      infil.Fu = Math.min(infil.Fu, FuMAX)
      infil.Sat = TRUE
       dF / tstep
   }

   //=============================================================================

   ////  New function added to release 5.1.007  ////                              //(5.1.007)

   def getSatInfil(infil:TGrnAmpt,  tstep:Double,  irate:Double,
      depth:Double):Double =
     //
     //  Input:   infil = ptr. to Green-Ampt infiltration object
     //           tstep =  runoff time step (sec),
     //           irate = net "rainfall" rate to upper zone (ft/sec)
     //                 = rainfall + snowmelt + runon,
     //                   does not include ponded water (added on below)
     //           depth = depth of ponded water (ft).
     //  Output:  returns infiltration rate (ft/sec)
     //  Purpose: computes Green-Ampt infiltration when upper soil zone is
     //           saturated.
     //
   {
      var ia, c1, dF, F2:Double =.0
      val ks = infil.Ks * adjust.hydconFactor                               //(5.1.008)

      // --- get available infiltration rate (rainfall + ponded water)
      ia = irate + depth / tstep
      if ( ia < 0 ) return 0.0

      // --- re-set new event recovery time
      infil.T = 5400.0 / infil.Lu / evap.recoveryFactor

      // --- solve G-A equation for new cumulative infiltration volume (F2)
      c1 = (infil.S + depth) * infil.IMD
      F2 = getF2(infil.F, c1, ks, tstep)                               //(5.1.008)
      dF = F2 - infil.F

      // --- all available water infiltrates -- set saturated state to false
      if ( dF > ia * tstep )
      {
         dF = ia * tstep
         infil.Sat = FALSE
      }

      // --- update total infiltration and upper zone moisture deficit
      infil.F += dF
      infil.Fu += dF
      infil.Fu = Math.min(infil.Fu, FuMAX)
      dF / tstep
   }

   //=============================================================================

   ////  This function was re-written for release 5.1.007.  ////                  //(5.1.007)

   def getF2( f1:Double,  c1:Double,  ks:Double,  ts:Double):Double =
     //
     //  Input:   f1 = old infiltration volume (ft)
     //           c1 = head * moisture deficit (ft)
     //           ks = sat. hyd. conductivity (ft/sec)
     //           ts = time step (sec)
     //  Output:  returns infiltration volume at end of time step (ft)
     //  Purpose: computes new infiltration volume over a time step
     //           using Green-Ampt formula for saturated upper soil zone.
     //
   {
     // int    i
      var f2 = f1
      var f2MIN:Double=.0
      var df2:Double =.0
      var c2:Double =.0

      // --- find Math.min. infil. volume
      f2MIN = f1 + ks * ts

      // --- use Math.min. infil. volume for 0 moisture deficit
      if ( c1 == 0.0 ) return f2MIN

      // --- use direct form of G-A equation for small time steps
      //     and c1/f1 < 100
      if ( ts < 10.0 && f1 > 0.01 * c1 )
      {
         f2 = f1 + ks * (1.0 + c1/f1) * ts
         return Math.max(f2, f2MIN)
      }

      // --- use Newton-Raphson method to solve integrated G-A equation
      //     (convergence limit reduced from that used in previous releases)
      c2 = c1 * Math.log(f1 + c1) - ks * ts

     // for ( i = 1 i <= 20 i++ )
      for( i  <- 1 to 20)
      {
         df2 = (f2 - f1 - c1 * Math.log(f2 + c1) + c2) / (1.0 - c1 / (f2 + c1) )
         if ( Math.abs(df2) < 0.00001 )
         {
            return Math.max(f2, f2MIN)
         }
         f2 -= df2
      }
      f2MIN
   }


}