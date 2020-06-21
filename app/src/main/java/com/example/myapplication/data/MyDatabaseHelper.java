package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    //我们希望创建一个名为BookStore.db的数据库, 然后在数据库中新建一张Book表, 其中有id(主键), 作者, 价格,页数和书名. SQL建表语句如下:
    /****
     *  integer: 表示整型;
     *  real: 表示浮点型;
     *  text: 表示文本型;
     *  blob:表示二进制类型
     *  primary key: 表示主键;
     *  autoincrement: 表示自增长
     */

    public static final String CREATE_BOOK = "create table Book (" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "page integer, " +
            "name text)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL(CREATE_BOOK);
        ContentValues cValue = new ContentValues();
        cValue.put("id","123");
        cValue.put("author","zhuoli");
        db.insert("Book",null,cValue);
        Toast.makeText(mContext, "创建数据库成功", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

