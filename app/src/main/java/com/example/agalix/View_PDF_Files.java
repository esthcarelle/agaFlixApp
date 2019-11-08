package com.example.agalix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class View_PDF_Files extends AppCompatActivity {

    ListView myPDFListView;
    DatabaseReference databaseReference;
    List<UploadPDF> uploadPDFS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pdf__files);
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
//
//                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
//                        android.R.id.text1,uploads){
//                        @Override
//                        public View getView(int position, View convertView, ViewGroup parent){
//
//                            View view=super.getView(position,convertView,parent);
//                            TextView myText=(TextView) view.findViewById(android.R.id.text1);
////                            TextView myText2=(TextView) view.findViewById(android.R.id.text2);
////
////                            myText2.setTextColor(Color.BLACK);
//                            myText.setTextColor(Color.BLACK);
//                                    return view;
//                }
                MyAdapter adapter=new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,uploads,"gg"){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){

                        View view=super.getView(position,convertView,parent);
                        TextView myText=(TextView) view.findViewById(android.R.id.text1);
//                            TextView myText2=(TextView) view.findViewById(android.R.id.text2);
//
//                            myText2.setTextColor(Color.BLACK);
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
