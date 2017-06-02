package swmm.configdata.jnodes.types;

// SNOWPACK OBJECT
// Snowpack objects describe the state of the snow melt process on each
// of 3 types of snow surfaces.

  
    public class      TSnowpack
    {
        int           snowmeltIndex;   // index of snow melt parameter set
        double[]        fArea=new double[3];        // fraction of total area of each surface
        double[]        wsnow=new double[3];        // depth of snow pack (ft)
        double[]        fw=new double[3];           // depth of free water in snow pack (ft)
        double[]        coldc=new double[3];        // cold content of snow pack
        double[]        ati=new double[3];          // antecedent temperature index (deg F)
        double[]        sba=new double[3];          // initial ASC of linear ADC
        double[]        awe=new double[3];          // initial AWESI of linear ADC
        double []       sbws=new double[3];         // final AWESI of linear ADC
        double[]        imelt=new double[3];        // immediate melt (ft)

        public TSnowpack() {

        }

        public int getSnowmeltIndex() {
            return snowmeltIndex;
        }

        public void setSnowmeltIndex(int snowmeltIndex) {
            this.snowmeltIndex = snowmeltIndex;
        }

        public double[] getfArea() {
            return fArea;
        }

        public void setfArea(double[] fArea) {
            this.fArea = fArea;
        }

        public double[] getWsnow() {
            return wsnow;
        }

        public void setWsnow(double[] wsnow) {
            this.wsnow = wsnow;
        }

        public double[] getFw() {
            return fw;
        }

        public void setFw(double[] fw) {
            this.fw = fw;
        }

        public double[] getColdc() {
            return coldc;
        }

        public void setColdc(double[] coldc) {
            this.coldc = coldc;
        }

        public double[] getAti() {
            return ati;
        }

        public void setAti(double[] ati) {
            this.ati = ati;
        }

        public double[] getSba() {
            return sba;
        }

        public void setSba(double[] sba) {
            this.sba = sba;
        }

        public double[] getAwe() {
            return awe;
        }

        public void setAwe(double[] awe) {
            this.awe = awe;
        }

        public double[] getSbws() {
            return sbws;
        }

        public void setSbws(double[] sbws) {
            this.sbws = sbws;
        }

        public double[] getImelt() {
            return imelt;
        }

        public void setImelt(double[] imelt) {
            this.imelt = imelt;
        }

        public TSnowpack(int snowmeltIndex, double[] fArea, double[] wsnow, double[] fw, double[] coldc,
                         double[] ati, double[] sba, double[] awe, double[] sbws, double[] imelt) {
            this.snowmeltIndex = snowmeltIndex;
            this.fArea = fArea;
            this.wsnow = wsnow;
            this.fw = fw;
            this.coldc = coldc;
            this.ati = ati;
            this.sba = sba;
            this.awe = awe;
            this.sbws = sbws;
            this.imelt = imelt;
        }
    }


    