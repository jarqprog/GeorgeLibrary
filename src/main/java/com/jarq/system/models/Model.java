package com.jarq.system.models;

public abstract class Model {

    private int id = -1;  // every fresh created model has id with value -1 (used in dao)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                '}';
    }
}
