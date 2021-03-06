package com.muneiah.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
EditText stu_name,stu_roll;
RecyclerView recyler;
MyAdapter adapter;
static StudentDataBase dataBase;
Student_Entity entity;
List<Student_Entity> entityList;
static MyViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stu_name=findViewById(R.id.student_name);
        stu_roll=findViewById(R.id.student_rollnumber);
        recyler=findViewById(R.id.rec);
        //Naormal data //dataBase= Room.databaseBuilder(this,StudentDataBase.class,"muni").allowMainThreadQueries().build();
        //for live data
        myViewModel= ViewModelProviders.of(this).get(MyViewModel.class);
            myViewModel.liveData().observe(this, new Observer<List<Student_Entity>>() {
                @Override
                public void onChanged(List<Student_Entity> student_entities) {
                    adapter=new MyAdapter(MainActivity.this,student_entities);
                    recyler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyler.setAdapter(adapter);
                }
            });

    }

    public void saveData(View view) {
        String name=stu_name.getText().toString();
        String rollnumber=stu_roll.getText().toString();
        entity=new Student_Entity();
        entity.setName(name);
        entity.setRollNumber(rollnumber);
       // dataBase.studentDAO().insertData(entity);// for normal db
        //for live data
        myViewModel.insert(entity);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    /*public void retriveData(View view) {
       // entityList=dataBase.studentDAO().retriveData();
        adapter=new MyAdapter(this,entityList);
        recyler.setLayoutManager(new LinearLayoutManager(this));
        recyler.setAdapter(adapter);

    }*/
}
