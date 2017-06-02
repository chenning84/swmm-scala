package swmm.routing

/**
 * Created by ning on 10/24/15.
 */
class DynWave {

  //-----------------------------------------------------------------------------
  //     Constants
  //-----------------------------------------------------------------------------
  val MINTIMESTEP =  0.001;   // min. time step (sec)            //(5.1.008)
  val OMEGA       =  0.5;     // under-relaxation parameter

  //  Constants moved here from project.c  //                                    //(5.1.008)
 val DEFAULT_SURFAREA  = 12.566; // Min. nodal surface area (~4 ft diam.)
 val DEFAULT_HEADTOL   = 0.005;  // Default head tolerance (ft)
  val    DEFAULT_MAXTRIALS = 8;      // Max. trials per time step


  //-----------------------------------------------------------------------------
  //  Data Structures
  //-----------------------------------------------------------------------------
  class TXnode
    {
//      char    converged;                 // TRUE if iterations for a node done
//      double  newSurfArea;               // current surface area (ft2)
//      double  oldSurfArea;               // previous surface area (ft2)
//      double  sumdqdh;                   // sum of dqdh from adjoining links
//      double  dYdT;                      // change in depth w.r.t. time (ft/sec)
    }

  //-----------------------------------------------------------------------------
  //  Shared Variables
  //-----------------------------------------------------------------------------
  var VariableStep :Double   =0.0        // size of variable time step (sec)
  var  xnode:TXnode =null                  // extended nodal information

 var  Omega:Double =0.0                  // actual under-relaxation parameter
  var   Steps:Int       =0           // number of Picard iterations

  //-----------------------------------------------------------------------------
  //  Function declarations
  //-----------------------------------------------------------------------------
  def   initRoutingStep(): Unit =
  {

  }
  def   initNodeStates(): Unit =
  {

  }
  def   findBypassedLinks(): Unit =   {        }
  def   findLimitedLinks(): Unit =   {        }

  def   findLinkFlows( dt:Double): Unit =
  {

  }
  def   isTrueConduit( link:Int):Unit =
  {

  }
  def   findNonConduitFlow( link:Int,  dt:Double): Unit =   {        }
  def   findNonConduitSurfArea( link:Int): Unit =   {        }
  def getModPumpFlow( link:Int,  q:Double,  dt:Double): Unit =   {        }
  def   updateNodeFlows( link:Int): Unit =   {        }

  def    findNodeDepths( dt:Double): Unit =   {        }
  def   setNodeDepth(node:Int,  dt:Double): Unit =   {        }
  def getFloodedDepth( node :Int, canPond:Int,  dV:Double,  yNew:Double,
     yMax:Double,  dt:Double): Unit =   {        }

  def getVariableStep( maxStep:Double): Unit =   {        }
// def getLinkStep( tMin:Double, int *minLink): Unit =   {        }
//  def getNodeStep( tMin:Double, int *minNode): Unit =   {        }

}
