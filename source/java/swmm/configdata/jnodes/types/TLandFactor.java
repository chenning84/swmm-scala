package swmm.configdata.jnodes.types;


// LAND AREA LANDUSE FACTOR


import org.joda.time.DateTime;

public class      TLandFactor
    {
        double        fraction;        // fraction of land area with land use
        double       buildup;         // array of buildups for each pollutant
        DateTime lastSwept;       // date/time of last street sweeping
    }


    