package ce.jsonverial;

/**
 * Created by Computer Engineer on 05/20/16.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class WebService extends AsyncTask<Void, Void, Void> {
    String index;
    private static final int TIMEOUT_MILLISEC = 0;
    public String mesajlar;

    BufferedReader in = null;
    String data = null;

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @SuppressWarnings("deprecation")
    public void webService(String url, String HttpMethod, ArrayList<NameValuePair> alinanDegerler,
                           Class kullanilanActivite) {

        try {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpClient client = new DefaultHttpClient(httpParams);


            if (HttpMethod == "GET") {

                HttpClient httpclient = new DefaultHttpClient();

                HttpGet request = new HttpGet();
                URI website = new URI(url);
                request.setURI(website);
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();

                    String result = RestClient.convertStreamToString(instream);
                    mesajlar = result;
                }

            } else {
                HttpPost request = new HttpPost(url);
                request.setEntity(new UrlEncodedFormEntity(alinanDegerler));
                //Web Service in yazıldığı tarafta istenildiği durumlarda Header a gönderilmesi gerekenler
                /*request.setHeader("where", "mobile");
                request.setHeader("Authorization","");*/
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();

                    String result = RestClient.convertStreamToString(instream);
                    mesajlar = result;
                }
            }
        } catch (Throwable t) {
            mesajlar = "Hata";
            Log.i("Hata neden var = ",t.toString());

        }
    }
}
