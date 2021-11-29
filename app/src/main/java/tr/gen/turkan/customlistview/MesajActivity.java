package tr.gen.turkan.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

public class MesajActivity extends AppCompatActivity {

    private EditText mesaj;
    private Button gonder;
    private RadioButton rb_akrabaMesaj, rb_arkadasMesaj, rb_isMesaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj);

        mesaj = (EditText) findViewById(R.id.et_mesaj);
        gonder = (Button) findViewById(R.id.btn_gonder);

        rb_akrabaMesaj = (RadioButton) findViewById(R.id.rb_akrabaMesaj);
        rb_arkadasMesaj = (RadioButton) findViewById(R.id.rb_arkadasMesaj);
        rb_isMesaj = (RadioButton) findViewById(R.id.rb_isMesaj);
        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        mesajGonder();
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            }
        });
    }

    private void mesajGonder(){
        String sms = mesaj.getText().toString().trim();
        String telNumara="" ;



        if (rb_akrabaMesaj.isChecked()==true){
            SharedPreferences sharedPreferences = getSharedPreferences("Akraba", MODE_PRIVATE);
            String json = sharedPreferences.getString("Akraba","");
            Object[] a=json.split("\"");
            telNumara = a[7].toString();
            Toast.makeText(getApplicationContext(), a[7].toString()+" numaralarına mesaj gönderildi", Toast.LENGTH_LONG).show();
        }
        else if (rb_arkadasMesaj.isChecked()==true){
            SharedPreferences sharedPreferences = getSharedPreferences("Arkadas", MODE_PRIVATE);
            String json = sharedPreferences.getString("Arkadas","");
            Object[] a=json.split("\"");
            telNumara = a[7].toString();
            Toast.makeText(getApplicationContext(), a[7].toString()+" numaralarına mesaj gönderildi", Toast.LENGTH_LONG).show();
        }
        else if (rb_isMesaj.isChecked()==true){
            SharedPreferences sharedPreferences = getSharedPreferences("Is", MODE_PRIVATE);
            String json = sharedPreferences.getString("Is","");
            Object[] a=json.split("\"");
            telNumara = a[7].toString();
            Toast.makeText(getApplicationContext(), a[7].toString()+" numaralarına mesaj gönderildi", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Kişi grubu seçilmedi.", Toast.LENGTH_SHORT).show();


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telNumara,null,sms,null,null);
            Toast.makeText(this,"Mesaj gönderildi", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Mesaj gönderilirken hata oluştu", Toast.LENGTH_SHORT).show();
        }
    }
}