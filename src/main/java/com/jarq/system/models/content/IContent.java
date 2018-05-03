package com.jarq.system.models.content;

import com.jarq.system.models.Identifiable;

public interface IContent extends Identifiable {

    int getTextId();

    String getFilepath();

    String getCreationDate();

}
