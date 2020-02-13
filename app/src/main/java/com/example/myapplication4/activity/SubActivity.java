package com.example.myapplication4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.adapter.SubCategoryAdapter;
import com.example.myapplication4.vo.RealmVo;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SubActivity extends AppCompatActivity {
    private OrderedRealmCollection<RealmVo> recyclerItems;
    private SubCategoryAdapter mAdapter;
    private int count = -1;

    String mJsonString;
    Realm realm;
    List<RealmVo> realmVoList;
    String category;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        setTitle(category);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        recyclerItems = new RealmList<>();

        mAdapter = new SubCategoryAdapter( recyclerItems);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SubCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String sub_category) {
                Intent intent = new Intent(SubActivity.this, ContentActivity.class);
                intent.putExtra("sub_category", sub_category);
                startActivity(intent);

            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        realm = Realm.getDefaultInstance();

        if(readRealm().size() > 0){
            Log.d("데이터있음",Integer.toString(readRealm().size()));
            recyclerItems = readRealm();
            mAdapter.setDataList(recyclerItems);
            mAdapter.notifyDataSetChanged();
        }else{
            Log.d("데이터없음","데이터없음");
        }

    }
    public RealmResults<RealmVo> readRealm(){
        RealmResults<RealmVo> diaryRealmResults = realm.where(RealmVo.class).equalTo("category",category).findAll().distinct("sub_category");
        return diaryRealmResults;
    }




}
