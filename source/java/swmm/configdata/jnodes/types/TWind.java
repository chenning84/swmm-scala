package swmm.configdata.jnodes.types;


// WINDSPEED OBJECT

  
public class  TWind
    {
       public  int          sType;             // monthly or file data
       public  double[]       aws= new double[12];          // monthly avg. wind speed (mph)

       public  double        ws;              // wind speed (mph)


    }


    