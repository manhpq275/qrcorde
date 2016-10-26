package asia.ienter.qrscaner.activities;

import android.Manifest;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import asia.ienter.qrscaner.R;
import asia.ienter.qrscaner.adapters.HistoryAdapter;
import asia.ienter.qrscaner.models.HistoryClass;
import asia.ienter.qrscaner.models.QRData;

/**
 * Created by phamquangmanh on 10/25/16.
 */
public class HistoryScan extends AppCompatActivity {


    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    ListView historyListView;
    HistoryAdapter historyAdapter;

    TextView btnScan,btnDelete;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_history_scan);

        btnScan = (TextView)findViewById(R.id.tvScan);
        btnDelete = (TextView)findViewById(R.id.tvDelete);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(FullScannerActivity.class);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(QRData.historyList==null) return;
                if(QRData.historyList.size()==0) return;
                new AlertDialog.Builder(HistoryScan.this)
                        .setTitle("Cảnh báo")
                        .setMessage("Bạn chắc chắn muốn xoá?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                new QRData().deleteData();
                                    QRData.historyList.clear();
                                    historyAdapter.notifyDataSetChanged();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        new QRData().getData();
        historyListView = (ListView)findViewById(R.id.historyListView);
        historyAdapter = new HistoryAdapter(this,QRData.historyList);
        historyListView.setAdapter(historyAdapter);

    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
