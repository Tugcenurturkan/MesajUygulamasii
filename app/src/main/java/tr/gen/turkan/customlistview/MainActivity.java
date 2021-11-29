package tr.gen.turkan.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread redirect = new Thread(){
            @Override
            public  void run(){
                try {
                    sleep(2000);
                    Intent i = new Intent(getApplicationContext(),RehberActivity.class);
                    startActivity(i);
                    finish();
                    super.run();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        redirect.start();

    }
}