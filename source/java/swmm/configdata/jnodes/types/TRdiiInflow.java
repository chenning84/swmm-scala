package swmm.configdata.jnodes.types;


// RDII INFLOW OBJECT

  
    public class  TRdiiInflow
    {
        int           unitHyd;         // index of unit hydrograph
        double        area;            // area of sewershed (ft2)

        public TRdiiInflow(int unitHyd, double area) {
            this.unitHyd = unitHyd;
            this.area = area;
        }
    }


    