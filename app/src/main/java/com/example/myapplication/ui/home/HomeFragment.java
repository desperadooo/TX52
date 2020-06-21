package com.example.myapplication.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.ShowPatientActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HomeFragment extends Fragment {
    public static final String EXTRA_MESSAGE="";
    Handler handler;
    TableLayout med_tab;
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                //显示数据库表格
//                TxDatabaseHelper helper = new TxDatabaseHelper(getActivity(),"tx52.db",null,1);
//                SQLiteDatabase db = helper.getWritableDatabase();
//                Cursor cursor = db.query("medecin", null, null, null, null, null, null);
                med_tab = getActivity().findViewById(R.id.table_medecin);
                handler = new Handler(new Handler.Callback(){
                    @Override
                    public boolean handleMessage(Message msg){
                        if(msg.what==1){
                            String s = (String)msg.obj;
                            String[] arr=s.split("\\|");
                            TableRow med_row=new TableRow(getActivity());
                            med_row.setPadding(25,25,25,25);
                            med_row.setBackgroundColor(Color.parseColor("#87CEEB"));
                            TextView id=new TextView(getActivity());
                            id.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            id.setPadding(5,0,5,0);
                            id.setText(arr[0]);
                            id.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), ShowPatientActivity.class);
                                    String message = id.getText().toString();
                                    intent.putExtra(EXTRA_MESSAGE,message);
                                    startActivity(intent);
                                }
                            });
                            TextView nom=new TextView(getActivity());
                            nom.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            nom.setPadding(5,0,5,0);
                            nom.setText(arr[1]);
                            TextView prenom=new TextView(getActivity());
                            prenom.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            prenom.setPadding(5,0,5,0);
                            prenom.setText(arr[2]);
                            TextView tele=new TextView(getActivity());
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
                Thread t = new Thread(){
                    public void run(){
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.47/tx52?useUnicode=true&serverTimezone=UTC","root","");
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("Select * from medecin;");
                            while (rs.next()) {
                                String s=String.valueOf(rs.getInt("id_m"))+"|"+rs.getString("nom")+"|"+rs.getString("prenom")+"|"+rs.getString("tele");
                                Message msg = new Message();
                                msg.what=1;
                                msg.obj=s;
                                handler.sendMessage(msg);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();

            }
        });

        return root;
    }
}
