package swmm

import swmm.configdata.ConfigObject
import swmm.configdata.inputsection._

/**
 * Created by ning on 5/28/15.
 */

/*
TITLE
OPTIONS
EVAPORATION
RAINGAGES
SUBCATCHMENTS
SUBAREAS
INFILTRATION
JUNCTIONS
OUTFALLS
STORAGE
CONDUITS
WEIRS
XSECTIONS
TRANSECTS
LOSSES
INFLOWS
CURVES
TIMESERIES
REPORT
TAGS
MAP
COORDINATES
VERTICES
Polygons
SYMBOLS
LABELS
 */
object ConfigCollection {
  def parseActualSection(name: String, header: List[String], data: List[String]): DataSection = name match {
    case "OPTIONS" => new ConfigOption(name, header, data) 
    case "EVAPORATION" => new EvaporationSection(name,header,data)
    case "RAINGAGES" => new RainageSection(name,header,data)
    case "SUBCATCHMENTS" => new SubCatchmentSection(name, header, data)
    case "SUBAREAS" => new SubAreaSection(name, header, data)
//    case "EVAPORATION" => new Evaporation(name, header, data) 
    case "INFILTRATION" => new InfiltrationSection(name, header, data) 
    case "JUNCTIONS" => new JunctionSection(name, header, data)
    case "OUTFALLS" => new OutfallSection(name, header, data)
    case "STORAGE" => new StorageSection(name, header, data)
    case "CONDUITS" => new ConduitSection(name, header, data) 
    case "WEIRS" => new WeirSection(name, header, data)
    case "XSECTIONS" => new XSectionSection(name, header, data)
    case "TRANSECTS" => new TransectSection(name, header, data)
    case "LOSSES" => new LossSection(name, header, data)
    case "INFLOWS" => new InflowSection(name, header, data) 
    case "CURVES" => new CurveSection(name, header, data) 
    case "TIMESERIES" => new TimeSeriesSection(name, header, data)
    case "REPORT" => new ReportSection(name, header, data)
    case "TAGS" => new TagSection(name, header, data)
    case "MAP" => new SMapSection(name, header, data)
    case "COORDINATES" => new CoordinatesSection(name, header, data)
    case "VERTICES" => new VerticeSection(name, header, data)
    case "Polygons" => new PolygonSection(name, header, data)
    case "SYMBOLS" => new SSymbolSection(name, header, data)
    case "LABELS" => new LabelSection(name, header, data)
    case _ => new ConfigOption(name, header, data) 
  }

}
