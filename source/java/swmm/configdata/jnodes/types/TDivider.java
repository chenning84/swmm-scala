package swmm.configdata.jnodes.types;

// FLOW DIVIDER OBJECT

public   class      TDivider extends  TNode
{
   public String         link;              // index of link with diverted flow
   public double      qMin;              // minimum inflow for diversion (cfs)
   public  double      qMax;              // flow when weir is full (cfs)
   public  double      dhMax;             // height of weir (ft)
   public  double      cWeir;             // weir discharge coeff.
   public  String         flowCurve;         // index of inflow v. diverted flow curve


}


    