package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class TxDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    /****
     *  integer: 表示整型;
     *  real: 表示浮点型;
     *  text: 表示文本型;
     *  blob:表示二进制类型
     *  primary key: 表示主键;
     *  autoincrement: 表示自增长
     */

    public static final String CREATE_MEDECIN = "create table medecin (id_m integer primary key ,nom text,prenom text,tele integer);";
    public static final String INSERT_MEDECIN = "insert into medecin values(1001,\"zheng\",\"huangkai\",0658913263),(1002,\"wu\",\"zhuoli\",0658914119),(1003,\"tong\",\"hui\",0658912419),(1004,\"mao\",\"jiawei\",0631414119);";
    public static final String CREATE_PATIENT = "create table patient (id_p integer primary key,nom text,prenom text,tele integer, id_m integer, foreign key(id_m) references medecin(id_m));";
    public static final String INSERT_PATIENT = "insert into patient values(2001,\"zheng\",\"hongkun\",0658915419,1001),(2002,\"yu\",\"zhenqing\",0658915432,1001),(2003,\"wang\",\"mingyao\",0658918536,1001),(2004,\"huang\",\"xiuqian\",0658912219,1002),(2005,\"claude\",\"simon\",0634915432,1002),(2006,\"mireille\",\"michet\",0658918536,1002),(2007,\"laurent\",\"wei\",0611412219,1003),(2008,\"chole\",\"thomas\",0632235432,1003),(2009,\"bruno\",\"macron\",0658917856,1003),(2010,\"jackey\",\"chen\",06114412219,1004),(2011,\"mattew\",\"mike\",0639735432,1004),(2012,\"leon\",\"anais\",0658917880,1004);";

    public TxDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {;
        db.execSQL(CREATE_MEDECIN);
        db.execSQL(INSERT_MEDECIN);
        db.execSQL(CREATE_PATIENT);
        db.execSQL(INSERT_PATIENT);
        /*ContentValues cValue = new ContentValues();
        cValue.put("id","123");
        cValue.put("author","zhuoli");
        db.insert("Book",null,cValue);*/
        Toast.makeText(mContext, "创建数据库成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(INSERT_MEDECIN);
    }
}
