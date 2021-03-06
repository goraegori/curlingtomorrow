package com.gor2.curlingtomorrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.gor2.curlingtomorrow.ui.CameraActivity;
import com.gor2.curlingtomorrow.ui.ManualFrag;
import com.gor2.curlingtomorrow.ui.ResultsFrag;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView navView;
    Fragment manualFrag, resultsFrag;
    public final static int REQUESTCODE = 400;
    private long backBtnTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FragmentManager fm = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.navigation_manual){
                    fm.beginTransaction().replace(R.id.nav_host_fragment,manualFrag).commit();
                }
                else if(itemId == R.id.navigation_camera){
                    MainActivity.this.startActivityForResult(new Intent(MainActivity.this, CameraActivity.class),REQUESTCODE);
                    return false;
                }
                else if(itemId == R.id.navigation_results){
                    fm.beginTransaction().replace(R.id.nav_host_fragment,resultsFrag).commit();
                }
                return true;
            }
        });

        FirebaseApp.initializeApp(getApplicationContext());

        manualFrag = new ManualFrag();
        resultsFrag = new ResultsFrag();
        fm.beginTransaction().replace(R.id.nav_host_fragment,manualFrag).commit();

        getSupportActionBar().hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE){
            if(resultCode == RESULT_OK){
                navView.setSelectedItemId(R.id.navigation_results);
            }
            if(resultsFrag!=null)
                resultsFrag.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            finishAndRemoveTask();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }
}
