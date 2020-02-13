package com.example.myapplication4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication4.R;
import com.example.myapplication4.vo.RealmVo;

import io.realm.Realm;

public class ContentActivity extends AppCompatActivity {

    String sub_category;
    Realm realm;
    TextView txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        txtContent = (TextView)findViewById(R.id.txt_content);

        Intent intent = getIntent();
        sub_category = intent.getStringExtra("sub_category");
        setTitle(sub_category);
        realm = Realm.getDefaultInstance();

        txtContent.setText(readRealm().getContent());

    }
    public RealmVo readRealm(){
        RealmVo diaryRealmResults = realm.where(RealmVo.class).equalTo("sub_category",sub_category).findFirst();
        return diaryRealmResults;
    }

}
