package com.example.mysongapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private musicAdapter adapter;
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        runtimePermission();


    }
    public void runtimePermission()
    {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                displaySongs();
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
             permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    public ArrayList<File> findSongs(File file)
    {
        ArrayList<File> arrayList=new ArrayList<>();

        File[] files=file.listFiles();

        for (File singleFile: files){
             if(singleFile.isDirectory() && !singleFile.isHidden() )
             {
                  arrayList.addAll(findSongs(singleFile));
             }
             else
             {
                 if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav"))
                 {
                     arrayList.add(singleFile);
                 }
             }
        }
        return arrayList;
    }
   void displaySongs()
    {
        final ArrayList<File> songs = findSongs(Environment.getExternalStorageDirectory());
        items=new String[songs.size()];
        for(int i=0;i<songs.size();i++){
            items[i]=songs.get(i).getName().replace(".mp3","").replace(".wav","");

        }
        adapter = new musicAdapter(new musicUtils(items,songs),this);

    }
}