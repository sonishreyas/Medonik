package com.example.forever.meddie.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forever.meddie.helper.Doctor;
import com.example.forever.meddie.Adapters.DoctorAdapter;
import com.example.forever.meddie.helper.DoctorDatabaseSource;
import com.example.forever.meddie.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity {

    private ListView mListView;
    private DoctorAdapter doctorAdapter;
    private ArrayList<Doctor> doctors;
    private DoctorDatabaseSource doctorDatabaseSource;
    String email_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        TextView emptyText = (TextView) findViewById(R.id.empty);
        mListView = (ListView) findViewById(R.id.doctorList);
        mListView.setEmptyView(emptyText);
        doctorDatabaseSource = new DoctorDatabaseSource(this);
        doctors = doctorDatabaseSource.getAllDoctor(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        Intent intent = getIntent();
        email_id = intent.getStringExtra("email");
       Toast.makeText(getApplicationContext(), email_id, Toast.LENGTH_SHORT).show();

        doctorAdapter = new DoctorAdapter(this, doctors,email_id);
        mListView.setAdapter(doctorAdapter);
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int rowId= doctors.get(position).getDocId();
                startActivity(new Intent(DoctorListActivity.this,DoctorDetailsActivity.class)
                .putExtra("doctorObj",doctors));
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.only_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,LoginFireActivity.class));
        this.finish();
        /*Intent loginscreen=new Intent(this,LoginActivity.class);
        loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginscreen.putExtra("email",email_id);
        startActivity(loginscreen);
        this.finish();*/
        return super.onOptionsItemSelected(item);
    }

    public void goAddDoctor(View view) {
        Intent i = new Intent(this, AddDoctorActivity.class);
        i.putExtra("email",email_id);
        startActivity(i);
    }
}
