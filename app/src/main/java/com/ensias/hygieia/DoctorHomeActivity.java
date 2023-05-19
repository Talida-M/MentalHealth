package com.ensias.hygieia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ensias.hygieia.Common.Common;
import com.ensias.hygieia.fireStoreApi.DatabaseHelper;
import com.ensias.hygieia.fireStoreApi.DoctorHelper;
import com.ensias.hygieia.fireStoreApi.UserHelper;
//import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DoctorHomeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    static String doc;
    Button SignOutBtn2;
    Button BtnRequst;
    Button listPatients;
    Button appointementBtn;

    private DatabaseHelper databaseHelper;

    @OnClick(R.id.profile)
    void profileBtnClick(){
        Intent k = new Intent(DoctorHomeActivity.this, ProfileDoctorActivity.class);
        startActivity(k);
    }
    Unbinder unbinder;

    @OnClick(R.id.myCalendarBtn)
    void myCalendarOnclick() {
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
        Intent k = new Intent(DoctorHomeActivity.this, MyCalendarDoctorActivity.class);
        startActivity(k);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home); //ici layout de page d'acceuil MEDECIN
        unbinder = ButterKnife.bind(this,this);
        //Common.CurreentDoctor = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        //Common.CurrentUserType = "doctor";
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        UserHelper.init(database);
        DoctorHelper.init(database);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("channel_description");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        SharedPreferences sharedPrefs = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        final String doctorID = sharedPrefs.getString("email", "");
        if(doctorID != "" || doctorID != null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 1200, // Start after 2 minutes
                    1200, // Repeat every 2 minutes
                    pendingIntent
            );
        }
        listPatients = findViewById(R.id.listPatients);
        BtnRequst=findViewById(R.id.btnRequst);
        SignOutBtn2=findViewById(R.id.signOutBtn);
        appointementBtn = findViewById(R.id.appointement);
        SignOutBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
                SharedPreferences shared = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = shared.edit();
                edit.remove("email");
                edit.remove("rol");
                edit.remove("profile_image");
                edit.remove("nume");
                edit.apply();
                Toast.makeText(getApplicationContext(), "You exit your account", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        BtnRequst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(DoctorHomeActivity.this, ConfirmedAppointmensActivity.class);
                startActivity(k);
            }
        });
        listPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(DoctorHomeActivity.this, MyPatientsActivity.class);
                startActivity(k);
            }
        });
        appointementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // doc = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                //showDatePickerDialog(v.getContext());
                Intent k = new Intent(DoctorHomeActivity.this, DoctorAppointementActivity.class);
                startActivity(k);
            }
        });

    }

    public void showDatePickerDialog(Context wf){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                wf,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "month_day_year: " + month + "_" + dayOfMonth + "_" + year;
        openPage(view.getContext(),doc,date);
    }

    private void openPage(Context wf, String d,String day){
        Intent i = new Intent(wf, AppointementActivity.class);
        i.putExtra("key1",d+"");
        i.putExtra("key2",day);
        i.putExtra("key3","doctor");
        wf.startActivity(i);
    }
}
