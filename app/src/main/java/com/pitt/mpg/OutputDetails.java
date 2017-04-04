package com.pitt.mpg;


import java.util.ArrayList;
import java.util.HashMap;

public class OutputDetails {

    public HashMap<String, ArrayList<String>> out;

    public OutputDetails(HashMap<String, ArrayList<String>> out) {
        this.out = out;
    }

    public HashMap<String, ArrayList<String>> getOut() {
        return out;
    }

    public void setOut(HashMap<String, ArrayList<String>> out) {
        this.out = out;
    }
}
