package com.kingofthehill;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by patrik on 2016-06-18.
 */
public class Saying {

    private long id;

    private String content;

    public Saying() {
        // Jackson deserialization
    }

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

}
