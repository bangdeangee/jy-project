package com.example.myapplication4.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.adapter.CategoryAdapter;
import com.example.myapplication4.vo.RealmVo;
import com.example.myapplication4.vo.VersionRealmVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private final String url = "http://203.245.41.111/loadhistory.php";
    private final String versionUrl = "http://203.245.41.111/versionhistory.php";
    private static String TAG = "phptest";
    private OrderedRealmCollection<RealmVo> recyclerItems;
    private OrderedRealmCollection<VersionRealmVo> VersionRealmVo;
    private CategoryAdapter mAdapter;
    private int count = -1;

    String mJsonString;
    String mJsonVersion;
    Realm realm;
    List<RealmVo> realmVoList;

    String deviceVersion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("한국사");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        recyclerItems = new RealmList<>();

        mAdapter = new CategoryAdapter(recyclerItems);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String category) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        realm = Realm.getDefaultInstance();

        requestVersion();
//        if (readRealm().size() > 0) {
//            Log.d("데이터있음", Integer.toString(readRealm().size()));
//            recyclerItems = readRealm();
//            mAdapter.setDataList(recyclerItems);
//            mAdapter.notifyDataSetChanged();
//        } else {
//            request();
//            Log.d("데이터없음", "데이터없음");
//        }

    }

    public RealmResults<RealmVo> readRealm() {
        RealmResults<RealmVo> diaryRealmResults = realm.where(RealmVo.class).findAll().distinct("category");
        return diaryRealmResults;
    }

    public RealmResults<VersionRealmVo> versionReadRealm() {
        RealmResults<VersionRealmVo> version = realm.where(VersionRealmVo.class).findAll().distinct("version");
        return version;

    }

    public void request() {
        new HttpTask().execute();
    }

    public void requestVersion() {
        new VersionHttpTask().execute();
    }

    class VersionHttpTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //TODO 버전체크 진행
            mJsonVersion = result;


            saveVersion();


            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = versionUrl;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class HttpTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mJsonString = result;

            showResult();
//            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = url;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    private void showResult() {

        String TAG_ID = "id";
        String TAG_NAME = "name";
        String TAG_COUNTRY = "country";

        realmVoList = new ArrayList<>();
        try {
            final ProgressDialog progressDialog;
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "데이터가 수정되었습니다.\n잠시만 기다려주세요", null, true, true);
            progressDialog.show();
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String num = item.getString("num");
                String main_category = item.getString("main_category");
                String category = item.getString("category");
                String sub_category = item.getString("sub_category");
                String content = item.getString("content");

                final RealmVo realmVo = new RealmVo(num, main_category, category, sub_category, content);



                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(realmVo);
                        realmVoList.add(realmVo);

//                        diaryAdapter.notifyDataSetChanged();

                    }
                });



            }
            recyclerItems = readRealm();
            mAdapter.setDataList(recyclerItems);
            mAdapter.notifyDataSetChanged();
            progressDialog.dismiss();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveVersion() {

        try {
            JSONObject jsonObject = new JSONObject(mJsonVersion);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String version = item.getString("version");
                if (versionReadRealm() != null && versionReadRealm().size() > 0) {
                    deviceVersion = versionReadRealm().get(0).getVersion();
                }
                if (version.compareTo(deviceVersion) > 0) {
                    final VersionRealmVo realmVo = new VersionRealmVo(version);
                    final RealmResults<VersionRealmVo> versionData = realm.where(VersionRealmVo.class).findAll();
                    final RealmResults<RealmVo> data = realm.where(RealmVo.class).findAll();

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            data.deleteAllFromRealm();
                            versionData.deleteAllFromRealm();
                            realm.copyToRealm(realmVo);
                            request();
                        }
                    });

                } else {
                    if (readRealm().size() > 0) {
                        Log.d("데이터있음", Integer.toString(readRealm().size()));
                        recyclerItems = readRealm();
                        mAdapter.setDataList(recyclerItems);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        request();
                        Log.d("데이터없음", "데이터없음");
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void RefreshWindows() {

    }
}
