package com.jarq.system.models.text;

public class TextService implements ITextService {


    public static ITextService getInstance() {
            return new TextService();
        }

    private TextService() {}

    @Override
    public IText createText() {
        return null;
    }
}
