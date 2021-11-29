package tr.gen.turkan.customlistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RehberActivity extends AppCompatActivity {
    public static final int izin = 1; //izni kontrol etmek için tanımladık else kısmında kontol edecek
    ListView listview;
    Button btn_mesajaGec;
    RadioButton rb_akraba, rb_arkadas, rb_is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehber);

        listview = findViewById(R.id.rehber);
        btn_mesajaGec = (Button) findViewById(R.id.btn_mesajaGec);

        rb_akraba = (RadioButton) findViewById(R.id.rb_akraba);
        rb_arkadas = (RadioButton) findViewById(R.id.rb_arkadas);
        rb_is = (RadioButton) findViewById(R.id.rb_is);

        Intent intent = getIntent();
        ArrayList<RehberKisiler> kisiler = new ArrayList<RehberKisiler>();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) //izin kontrol ediliyor
                == PackageManager.PERMISSION_GRANTED) {


            //telefonun rehberine erişebilmek için cursor kullanıyoruz. satırları tek tek okumamızı sağlayacak.
            Cursor telrehber = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (telrehber.moveToNext()) {
                //telefonda erişelecek yerleri belirtiyoruz.
                @SuppressLint("Range") String isim = telrehber.getString(telrehber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                @SuppressLint("Range") String numara = telrehber.getString(telrehber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                @SuppressLint("Range") String contactID = telrehber.getString(telrehber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));


                RehberKisiler kisiBilgileri = new RehberKisiler();

                kisiBilgileri.setName(isim);
                kisiBilgileri.setTelNumber(numara);
                kisiBilgileri.setPhoto(ContactPhoto(contactID));
                kisiler.add(kisiBilgileri);
            }

            telrehber.close();
            RehberAdapter rehberAdapter = new RehberAdapter(this, kisiler);
            if (listview != null) {
                listview.setAdapter(rehberAdapter);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS}, //izin verilmemişse tekrar izin istiyoruz.
                    izin);
        }

        btn_mesajaGec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(RehberActivity.this,MesajActivity.class);
                startActivity(i);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                if (rb_akraba.isChecked()==true){
                    SharedPreferences sharedPreferences = getSharedPreferences("Akraba", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    Object[] array = kisiler.toArray();
                    String json = gson.toJson(array[position]);
                    editor.putString("Akraba",json);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Akraba grubuna eklendi", Toast.LENGTH_LONG).show();
                }
                else if (rb_arkadas.isChecked()==true){
                    SharedPreferences sharedPreferences = getSharedPreferences("Arkadas", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    Object[] array = kisiler.toArray();
                    String json = gson.toJson(array[position]);
                    editor.putString("Arkadas",json);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Arkadaş grubuna eklendi", Toast.LENGTH_LONG).show();
                }
                else if (rb_is.isChecked()==true){
                    SharedPreferences sharedPreferences = getSharedPreferences("Is", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    Object[] array = kisiler.toArray();
                    String json = gson.toJson(array[position]);
                    editor.putString("Is",json);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"İş grubuna eklendi", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Lütfen kişinin grubunu seçiniz.", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });


    }

    //fotoğrafı çekebilmek için bu fonksiyonu yazıyoruz.
    public Bitmap ContactPhoto(String contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactId)); //tekrar telefon rehberine ulaşıyoruz.
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY); //telefon hafızasına ulaşıyoruz.
        Cursor cursor = getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null); //varsa eğer fotoğrafına ulaştık
        if (cursor != null && cursor.getCount() > 0) { //fotoğrafın olup olmadığını kontrol ediyoruz.
            cursor.moveToNext();
            byte[] data = cursor.getBlob(0);
            if (data != null)
                return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
            else
                return null;
        }
        cursor.close();
        return null;
    }
}
