package lingualeo.mydictionary.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Zatsepin on 13.08.2014.
 */
public class YandexTranslationRequest extends TranslationRequest {
    private final static String YANDEX_API_KEY = "trnsl.1.1.20140813T174212Z.8bd5238628be5892.98bffc2ccbb18ed09bf81790e1bdb4f890fb102d";
    private final String mText;
    private String mTranslation;

    public YandexTranslationRequest(String text) {
        mText = text;
    }

    @Override
    void processResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            JSONArray translations = json.getJSONArray("text");
            if (translations.length() > 0){
                //TODO: save just one translation
                mTranslation = translations.getString(0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getUrl() {
        String encodedText = null;
        try {
            encodedText = URLEncoder.encode(mText, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + YANDEX_API_KEY
                + "&text=" + encodedText + "&lang=ru-en";
    }

    @Override
    public String getTranslation() {
        return mTranslation;
    }
}
