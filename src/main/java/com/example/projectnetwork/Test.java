package com.example.projectnetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test extends Question{
    private int numOfOptionsww;
    String[] optionsww;
    public List<Character> labelsse = new ArrayList<>();

    public Test(int i) {
        this.numOfOptionsww=i;
        for (int j=0;j<numOfOptionsww;j++){
            labelsse.add((char) ('A'+j));
        }}
    public void setOptions(String[] rac) {
        optionsww = new String[numOfOptionsww];
        List<String> listerew = new ArrayList<>();

        for (int i = 0; i < rac.length; i++) {  // отправим массив question без 1ого элемента
            listerew.add(rac[i]);}
        Collections.shuffle(listerew);

        for (int i = 0; i < rac.length; i++) {
            this.optionsww[i] = listerew.get(i) ;}
    }

    public String getOptionAt(int ind) {
        return optionsww[ind];
    }

    public String toString() {
        String reswq = getDescription() + "\n";
        for (int i = 0; i < optionsww.length; i++) {
            reswq += optionsww[i] + "\n";
        }
        return reswq;
    }
}

