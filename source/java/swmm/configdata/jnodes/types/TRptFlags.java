package swmm.configdata.jnodes.types;

// REPORTING FLAGS STRUCTURE

      public class      TRptFlags
    {
        public int          report;          // TRUE if results report generated
        public int          input;           // TRUE if input summary included
        public int          subcatchments;   // TRUE if subcatchment results reported
        public int          nodes;           // TRUE if node results reported
        public int          links;           // TRUE if link results reported
        public int          continuity;      // TRUE if continuity errors reported
        public int          flowStats;       // TRUE if routing link flow stats. reported
        public int          nodeStats;       // TRUE if routing node depth stats. reported
        public int          controls;        // TRUE if control actions reported
        public int           linesPerPage;    // number of lines printed per page


    }


    