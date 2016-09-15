package ce.jsonverial;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    WebService wb = new WebService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button verileri_al = (Button) findViewById(R.id.verileri_al);
        final TextView text_veriler = (TextView) findViewById(R.id.text_veriler);

        verileri_al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = "http://api2.fatihtuzen.com/finans/g/?birim=USDTRY,USDSEK,TRYUSD,EURGBP";
                wb.webService(link, "GET", null,MainActivity.class);
                try {
                    String deger ="";
                    JSONArray json_dizi = new JSONArray(wb.mesajlar);
                    for (int i = 0; i < json_dizi.length(); i++) {
                        JSONObject json_nesnesi = json_dizi.getJSONObject(i);
                        deger +="Sıra : "+json_nesnesi.getString("sira")+
                                "\nBirim : "+json_nesnesi.getString("birim")+
                                "\nDeğer : "+json_nesnesi.getString("deger") + "\n\n";
                    }
                    text_veriler.setText(deger);
                }catch (Exception hata){
                    Toast.makeText(MainActivity.this, "Hata :"+hata, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
