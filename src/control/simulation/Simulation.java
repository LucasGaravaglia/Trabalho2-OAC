package src.control.simulation;

import java.util.ArrayList;

public class Simulation {
    private ArrayList<State> list;

    /**
     * Constructor.
     */
    public Simulation() {
        this.list = new ArrayList<State>();
    }

    /**
     * Store a new state in the states stack.
     * @param memory System primary memory.
     * @param registers CPU Registers.
     * @param signals CPU Signals.
     * @param pc CPU Program Counter.
     */
    public void push(String[] memory, String[] registers, 
            String[] signals, String pc) {
        State state = new State(memory,registers,signals,pc);
        this.list.add(state);
    }

    /**
     * Remove and return the last element of the states stack.
     * @return Removed state.
     * @throws Exception States list empty.
     */
    public State pop() throws Exception{
        if(this.list.isEmpty())
            throw new Exception("States list is empty");
        State s = this.list.get(this.list.size()-1);
        this.list.remove(this.list.size()-1);
        return s;
    }

    /**
     * Clear the states stack.
     */
    public void clearList() {
        this.list.clear();
    }

    /**
     * Get the top element of the states stack.
     * @return
     */
    public State top() {
        State s = this.list.get(this.list.size()-1);
        return s;
    }
}
