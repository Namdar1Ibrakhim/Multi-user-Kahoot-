package com.example.projectnetwork;

public class Fillin extends Question{
    String al;
    public String toString(){
        al= getDescription() + "\n"+ getAnswer();
        return al;
    }}
