package swmm.cmigrate;

/**
 * Created by ning on 11/19/15.
 *
 */
public class test {
    //-----------------------------------------------------------------------------
// Constants
//-----------------------------------------------------------------------------
     double MCOEFF    = 1.49;              // constant in Manning Eq.
     double MEXP      = 1.6666667;         // exponent in Manning Eq.
     double ODETOL    = 0.0001;            // acceptable error for ODE solver

    //-----------------------------------------------------------------------------
// Globally shared variables
//-----------------------------------------------------------------------------
// Volumes (ft3) for a subcatchment over a time step                           //(5.1.008)
    double     Vevap;         // evaporation
    double     Vpevap;        // pervious area evaporation
    double     Vinfil;        // non-LID infiltration
    double     Vinflow;       // non-LID precip + snowmelt + runon + ponded water
    double     Voutflow;      // non-LID runoff to subcatchment's outlet
    double     VlidIn;        // impervious area flow to LID units
    double     VlidInfil;     // infiltration from LID units
    double     VlidOut;       // surface outflow from LID units
    double     VlidDrain;     // drain outflow from LID units
    double     VlidReturn;    // LID outflow returned to pervious area
}
