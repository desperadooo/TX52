package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.TxDatabaseHelper;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private TxDatabaseHelper helper;
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}