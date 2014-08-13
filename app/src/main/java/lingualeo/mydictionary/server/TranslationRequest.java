package lingualeo.mydictionary.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Zatsepin on 13.08.2014.
 */
public abstract class TranslationRequest {

    public void execute() {
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            URL url = new URL(getUrl());
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String response = readStream(in);
            processResponse(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private String readStream(BufferedReader inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String readString;
        while ((readString = inputStream.readLine()) != null) {
            sb.append(readString);
        }
        return sb.toString();
    }

    abstract void processResponse(String response);

    abstract String getUrl();

    public abstract String getTranslation();
}
