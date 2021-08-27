package com.geek.android2_1_taskapp.models;

import java.io.Serializable;

public class Task implements Serializable {
    private String text;
    private long createAt;

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Task(String text, long createAt) {
        this.text = text;
        this.createAt = createAt;
    }
}
