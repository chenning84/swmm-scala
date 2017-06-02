package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class TimeSeriesSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
/*
   [TIMESERIES]
;;Name             Date       Time       Value
;;-------------------------------------------------
  INFLOW@1SB1      08/11/1998 00:00      0.00000
  INFLOW@1SB1                 00:05      39.60000
  INFLOW@1SB1                 00:10      425.10000
  INFLOW@1SB1                 00:15      634.10000
  INFLOW@1SB1                 00:20      591.00000
  INFLOW@1SB1      08/11/1998 00:25      441.00000
  INFLOW@1SB1                 00:30      313.00000
  INFLOW@1SB1                 00:35      255.00000
  INFLOW@1SB1                 00:40      220.00000
  INFLOW@1SB1                 00:45      197.00000
  INFLOW@1SB1      08/11/1998 00:50      180.00000
  INFLOW@1SB1                 00:55      168.00000
  INFLOW@1SB1                 01:00      170.00000
  INFLOW@1SB1                 01:05      153.00000
  INFLOW@1SB1                 01:10      87.00000
  INFLOW@1SB1      08/11/1998 01:15      55.00000

    */
   override var indices = List(0,2, 19,30,41,100)
   override var dataIndice = List(0, 19,30,41,100)
}
