package swmm.configdata.jnodes.types;


// TREATMENT OBJECT


import com.udojava.evalex.Expression;

public class TTreatment {
    public int treatType;       // treatment equation type: REMOVAL/CONCEN
    public Expression equation;        // treatment eqn. as tokenized math terms
}


