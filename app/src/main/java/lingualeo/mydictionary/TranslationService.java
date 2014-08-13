package lingualeo.mydictionary;

import android.app.IntentService;
import android.content.Intent;

import lingualeo.mydictionary.domain.Word;
import lingualeo.mydictionary.server.TranslationRequest;
import lingualeo.mydictionary.server.YandexTranslationRequest;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class TranslationService extends IntentService {

    final static String EXTRA_TEXT = "extra_word";

    public TranslationService() {
        super("TranslationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String text = intent.getStringExtra(EXTRA_TEXT);
            TranslationRequest translationRequest = new YandexTranslationRequest(text);
            translationRequest.execute();
            WordsDao wordsDao = new WordsDao(getApplicationContext());
            wordsDao.open();
            wordsDao.saveWord(new Word(text,translationRequest.getTranslation()));
            wordsDao.close();
        }
    }

}
