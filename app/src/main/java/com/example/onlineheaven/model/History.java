package com.example.onlineheaven.model;

public class History {
    Integer id;
    String history;

    public History(Integer userid, String history) {
        this.id = userid;
        this.history = history;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
