package com.kingofthehill.repository.model;

public class Transponder {

    private final long transponder;
    private final String driver;

    public Transponder(long transponder, String driver) {
        this.transponder = transponder;
        this.driver = driver;
    }

    public long getTransponder() {
        return transponder;
    }

    public String getDriver() {
        return driver;
    }
}
