package lingualeo.mydictionary.domain;

/**
 * Created by Zatsepin on 13.08.2014.
 */
public class Word {
    private String mTranslation;
    private String mText;

    public Word(){
    }

    public Word(String text, String translation) {
        this.mTranslation = translation;
        this.mText = text;
    }

    public String getTranslation() {
        return mTranslation;
    }

    public void setTranslation(String mTranslation) {
        this.mTranslation = mTranslation;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }
}
