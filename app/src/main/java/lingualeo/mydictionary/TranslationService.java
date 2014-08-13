package lingualeo.mydictionary;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.sql.SQLException;

import lingualeo.mydictionary.domain.Word;
import lingualeo.mydictionary.server.TranslationRequest;
import lingualeo.mydictionary.server.YandexTranslationRequest;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
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
            WordsDataSource wordsDataSource = new WordsDataSource(getApplicationContext());
            wordsDataSource.open();
            Word word = new Word(text, translationRequest.getTranslation());
            long newId = wordsDataSource.saveWord(word);
            if (newId != -1) {
                showSuccessMessage(word);
            }
            wordsDataSource.close();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showSuccessMessage(Word word){
        Intent intent = new Intent(this, AllWordsActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("My Dictionary")
                .setContentText("The word '" + word.getText() + "' has been saved in a dictionary").setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, noti);
    }
}

