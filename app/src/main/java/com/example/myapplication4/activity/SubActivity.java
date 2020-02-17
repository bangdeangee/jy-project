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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

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

    private AdView mAdView_category;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView_category = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView_category.loadAd(adRequest);

        // 광고가 제대로 로드 되는지 테스트 하기 위한 코드입니다.
        mAdView_category.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // 광고가 문제 없이 로드시 출력됩니다.
                Log.d("@@@", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                // 광고 로드에 문제가 있을시 출력됩니다.
                Log.d("@@@", "onAdFailedToLoad " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

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
