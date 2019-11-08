package com.example.agalix;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.agalix.ui.main.SectionsPagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserMainActivity extends AppCompatActivity {

    ListView myPDFListView;
    DatabaseReference databaseReference;
    List<UploadPDF> uploadPDFS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pdf__files);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        myPDFListView=(ListView)findViewById(R.id.myListView);
        uploadPDFS=new ArrayList<>();

        viewAllFiles();

        myPDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UploadPDF uploadPDF=uploadPDFS.get(position);


//                Intent intent=new Intent();
//                intent.setType(Intent.ACTION_VIEW);
//                System.out.println("hhhhhhhh"+uploadPDF.getUrl());
//                intent.setData(Uri.parse(uploadPDF.getUrl()));
//                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(uploadPDF.getUrl()));
//                startActivity(intent);
                System.out.println("hhhhhhhh"+uploadPDF.getUrl());

                Intent intent = new Intent(getBaseContext(), StreamActivity.class);
                intent.putExtra("url", uploadPDF.getUrl());
                startActivity(intent);

            }
        });

    }
    public void viewAllFiles(){
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    UploadPDF uploadPDF=postSnapshot.getValue(UploadPDF.class);
                    uploadPDFS.add(uploadPDF);

                }
                String[] uploads=new String[uploadPDFS.size()];
                for(int i=0;i<uploads.length;i++){
                    uploads[i]=uploadPDFS.get(i).getName();

                }
                System.out.println("ffff");

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                        uploads){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){

                        View view=super.getView(position,convertView,parent);
                        TextView myText=(TextView) view.findViewById(android.R.id.text1);

                        myText.setTextColor(Color.BLACK);
                        return view;
                    }



                };



                myPDFListView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
