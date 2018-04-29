package com.jarq.models.repository;

import com.jarq.models.text.Text;

import java.util.List;

public class Repository implements IRepository {

    private int id;

    public Repository(int id) {
        this.id = id;
    }

    @Override
    public void setTexts(List<Text> texts) {

    }

    @Override
    public List<Text> getTexts() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }
}
