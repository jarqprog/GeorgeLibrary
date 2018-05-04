package com.jarq.system.service.text;

import com.jarq.system.models.text.IText;
import com.jarq.system.service.IService;

public interface ITextService extends IService {

    IText createText();
}
