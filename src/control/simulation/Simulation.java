package src.control.simulation;

import java.util.ArrayList;
import src.gui.data.Data;

public class Simulation {
    private ArrayList<Data> list;

    /**
     * Constructor.
     */
    public Simulation() {
        this.list = new ArrayList<Data>();
    }

    /**
     * Store a new state in the states list.
     * @param memory System primary memory.
     * @param registers CPU Registers.
     * @param signals CPU Signals.
     * @param pc CPU Program Counter.
     */
    public void pushState(String[] memory, String[] registers, 
            String[] signals, String pc) {
        Data state = new Data();
        for (String line : memory) state.setModelMemory(line);
        for (String line : registers) state.setModelMemory(line);
        state.setSignals(signals);
        state.setPc(pc);
        this.list.add(state);
    }

    /**
     * Remove the last state of the states list.
     */
    public void popState() {
        if(!this.list.isEmpty()) this.list.remove(this.list.size()-1);
    }

    /**
     * Return the actual processor state.
     * @return Current processor state(PC + Registers + Memory + Signals).
     * @throws Exception States list empty.
     */
    public Data getCurrentState() throws Exception {
        if(this.list.isEmpty())
            throw new Exception("The states list is empty");
        return this.list.get(this.list.size()-1);
    }
}
