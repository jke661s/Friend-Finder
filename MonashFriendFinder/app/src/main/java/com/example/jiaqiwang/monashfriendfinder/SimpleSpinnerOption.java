package com.example.jiaqiwang.monashfriendfinder;


/**
 * Created by Jiaqi Wang on 5/2/2017.
 */
public class SimpleSpinnerOption {

    private String name;

    private Object value;

    public SimpleSpinnerOption(){
        this.name="";
        this.value="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}