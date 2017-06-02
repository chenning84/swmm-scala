package swmm.configdata.jnodes.types;


// FILE INFORMATION


import java.io.File;
import java.util.List;

public class  TFile
{
    public String          name;     // file name
    public int          mode;                 // NO_FILE, SCRATCH, USE, or SAVE
    public int          state;                // current state (OPENED, CLOSED)
    public String file ="";                 // FILE structure pointer
}

