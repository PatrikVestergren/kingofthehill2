package com.kingofthehill.repository.model;

/**
 * Created by patrik.vestergren on 2016-10-07.
 */
public class BestMinute {

    private final String driver;
    private final long transponder;
    private final String result;

    public BestMinute(String driver, long transponder, String result) {
        this.driver = driver;
        this.transponder = transponder;
        this.result = result;
    }

    public String getDriver() {
        return driver;
    }

    public long getTransponder() {
        return transponder;
    }

    public String getResult() {
        return result;
    }
}
