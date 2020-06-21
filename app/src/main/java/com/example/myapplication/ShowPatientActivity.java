package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication.ui.home.HomeFragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowPatientActivity extends AppCompatActivity {
    Handler handler;
    TableLayout med_tab;
    Button btn;
    EditText prenom;
    EditText nom;
    EditText tele;
    Integer patientid;
    public static final String EXTRA_MESSAGE="";

    public void refresh(){
        onCreate(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patient);
        Intent intent = getIntent();
        String message = intent.getStringExtra(HomeFragment.EXTRA_MESSAGE);
        btn = findViewById(R.id.btn);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        tele=findViewById(R.id.tele);
        med_tab = findViewById(R.id.table_medecin_patient);
        handler = new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg){
                if(msg.what==1){
                    String s = (String)msg.obj;
                    String[] arr=s.split("\\|");
                    TableRow med_row=new TableRow(ShowPatientActivity.this);
                    med_row.setPadding(25,25,25,25);
                    med_row.setBackgroundColor(Color.parseColor("#87CEEB"));
                    TextView id=new TextView(ShowPatientActivity.this);
                    id.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    id.setPadding(5,0,5,0);
                    id.setText(arr[0]);
                    TextView nom=new TextView(ShowPatientActivity.this);
                    nom.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    nom.setPadding(5,0,5,0);
                    nom.setText(arr[1]);
                    TextView prenom=new TextView(ShowPatientActivity.this);
                    prenom.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    prenom.setPadding(5,0,5,0);
                    prenom.setText(arr[2]);
                    TextView tele=new TextView(ShowPatientActivity.this);
                    tele.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    tele.setPadding(5,0,5,0);
                    tele.setText(arr[3]);
                    med_row.addView(id);
                    med_row.addView(nom);
                    med_row.addView(prenom);
                    med_row.addView(tele);
                    med_tab.addView(med_row);
                }
                return false;
            }
        });

        Connection conn = null;
        Thread t = new Thread(){
            public void run(){
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://" +
                            "192.168.1.47/tx52?useUnicode=true&serverTimezone=UTC","root","");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("Select * from patient where id_m="+message+";");
                    while (rs.next()) {
                        String s=String.valueOf(rs.getInt("id_p"))+"|"+
                                rs.getString("nom")+"|"+
                                rs.getString("prenom")+"|"+
                                rs.getString("tele");
                        Message msg = new Message();
                        msg.what=1;
                        msg.obj=s;
                        handler.sendMessage(msg);
                    }
                    ResultSet rs2 = stmt.executeQuery("Select id_p from patient order by id_p desc");
                    while(rs2.next()){
                        patientid=rs2.getInt("id_p")+1;
                        break;
                    }
                    stmt.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nom.getText().toString()!=""&&prenom.getText().toString()!=""&&tele.getText().toString()!=""){
                    Thread t1 = new Thread(){
                        public void run(){
                            try {
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection conn1 = DriverManager.getConnection("jdbc:mysql://" +
                                        "192.168.1.47/tx52?useUnicode=true&serverTimezone=UTC","root","");
                                Statement stmt1 = conn1.createStatement();
                                int rs1 = stmt1.executeUpdate("Insert into patient(id_p,nom,prenom,tele,id_m) values("+patientid.toString()+",'"+nom.getText().toString()+"','"+prenom.getText().toString()+"',"+tele.getText().toString()+","+message+")");
                                Intent intent = new Intent(v.getContext(), ShowPatientActivity.class);
                                intent.putExtra(EXTRA_MESSAGE,message);
                                startActivity(intent);
                            } catch (SQLException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    t1.start();
                }
            }
        });
    }
}
