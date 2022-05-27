package com.example.se328nawafalzahemproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SQLITE extends AppCompatActivity {
    EditText Firstname;
    EditText Lastname;
    EditText Phone;
    EditText Email;
    EditText Uni_ID;


    Button Insert,Update,Delete,SelectOption,InsertFromFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        DBHelper dbHelper=new DBHelper(this);



        Firstname=(EditText)findViewById(R.id.EDTfirstName);
        Lastname=(EditText)findViewById(R.id.EDTlastName);
        Phone=(EditText)findViewById(R.id.EDTphoneNumber);
        Email=(EditText)findViewById(R.id.EDTemailAddress);
        Uni_ID=(EditText)findViewById(R.id.EDT_UNIid);

        Insert=(Button)findViewById(R.id.InsertButton);
        Update=(Button)findViewById(R.id.UpdateButton);
        Delete=(Button)findViewById(R.id.DeleteButton);
        SelectOption=(Button)findViewById(R.id.SelectOptions);
        InsertFromFireBase=(Button)findViewById(R.id.InsertFromFireBase);



        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = Firstname.getText().toString();
                String lname = Lastname.getText().toString();
                String phone = Phone.getText().toString();
                String email = Email.getText().toString();
                String uid =Uni_ID.getText().toString();

                if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || phone.isEmpty() || uid.isEmpty()) {

                    Toast.makeText(SQLITE.this, "All field required.", Toast.LENGTH_SHORT).show();
                    return;
                }


                Cursor cursor = dbHelper.selectby_UnvID(Integer.valueOf(uid));
                if(cursor.getCount() >= 1){
                    Toast.makeText(SQLITE.this, "Error Inserting --- University ID is already taken.", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean r =dbHelper.insert(Integer.valueOf(uid),fname,lname,phone,email);
                if (r) {
                    Toast.makeText(SQLITE.this, "Record is Inserted successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SQLITE.this, "Error Inserting", Toast.LENGTH_LONG).show();
                }




            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = Firstname.getText().toString();
                String lname = Lastname.getText().toString();
                String phone = Phone.getText().toString();
                String email = Email.getText().toString();
                String uid =Uni_ID.getText().toString();

                if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || phone.isEmpty() || uid.isEmpty()) {

                    Toast.makeText(SQLITE.this, "All field required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = dbHelper.selectby_UnvID(Integer.valueOf(uid));
                if(cursor.getCount() == 0){
                    Toast.makeText(SQLITE.this, "Error Updating --- University ID is not exist.", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean r =dbHelper.updateUser(Integer.valueOf(uid),fname,lname,phone,email);

                if (r) {
                    Toast.makeText(SQLITE.this, "Record is Updated successfully.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SQLITE.this, "Error Updating.", Toast.LENGTH_LONG).show();
                }


            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid =Uni_ID.getText().toString();

                if (uid.isEmpty()) {
                    Toast.makeText(SQLITE.this, "University ID field required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor cursor = dbHelper.selectby_UnvID(Integer.valueOf(uid));
                if(cursor.getCount() == 0){
                    Toast.makeText(SQLITE.this, "Error Deleting --- University ID is not exist.", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean r = dbHelper.deleteData(Integer.valueOf(uid));
                if (r) {
                    Toast.makeText(SQLITE.this, "Record is Deleted successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SQLITE.this, "Error Deleting", Toast.LENGTH_LONG).show();
                }

            }
        });

        SelectOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SQLITE.this, SQLi_List.class));
            }
        });

    }
}