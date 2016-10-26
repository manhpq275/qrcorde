package asia.ienter.qrscaner.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import asia.ienter.qrscaner.utils.Config;
import asia.ienter.qrscaner.models.QRData;
import asia.ienter.qrscaner.R;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        QRData.CreateRootFolder();
        Log.d("MainActivity", Config.RootFolder);
        new QRData().getData();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, HistoryScan.class);
                startActivity(intent);
            }
        },2000);
    }
}