package com.jarq.models.repository;

import com.jarq.models.text.Text;

import java.util.List;

public interface IRepository {

    void setTexts(List<Text> texts);
    List<Text> getTexts();
    int getId();
}
