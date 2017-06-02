package swmm.configdata.jnodes.types;


// TEMPERATURE OBJECT


import org.joda.time.DateTime;

public class  TTemp
    {
        public int            dataSource;      // data from time series or file
        public int            tSeries;         // temperature data time series index
        public DateTime      fileStartDate;   // starting date of data read from file
        public double        elev;            // elev. of study area (ft)
        public double        anglat;          // latitude (degrees)
        public double        dtlong;          // longitude correction (hours)

        public double        ta;              // air temperature (deg F)
        public double        tmax;            // previous day's max. temp. (deg F)
        public double        ea;              // saturation vapor pressure (in Hg)
        public double        gamma;           // psychrometric constant
        public double        tanAnglat;       // tangent of latitude angle

 
    }


    