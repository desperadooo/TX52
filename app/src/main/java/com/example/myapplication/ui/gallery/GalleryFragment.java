package com.example.myapplication.ui.gallery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myapplication.R;
import com.example.myapplication.data.TxDatabaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GalleryFragment extends Fragment {
    Handler handler;
    TableLayout med_tab;
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                med_tab = getActivity().findViewById(R.id.table_patient);
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
                            ResultSet rs = stmt.executeQuery("Select * from patient;");
                            while (rs.next()) {
                                String s=String.valueOf(rs.getInt("id_p"))+"|"+rs.getString("nom")+"|"+rs.getString("prenom")+"|"+rs.getString("tele");
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
