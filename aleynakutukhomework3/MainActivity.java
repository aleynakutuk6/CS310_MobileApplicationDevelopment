package edu.sabanciuniv.aleynakutukhomework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressDialog prgDialog;
    RecyclerView newsRecView;
    Spinner spCategories;
    List<NewsItem> data;
    String selectedCategory;
    List<Category> dataCategories;
    NewsAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataCategories = new ArrayList<>();
        data = new ArrayList<>();
        newsRecView = findViewById(R.id.newsrec);
        spCategories = findViewById(R.id.spcategories);


        NewsTask tsk = new NewsTask();
        tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/getall");

        adp = new NewsAdapter(data, this, new NewsAdapter.NewsItemClickListener() {
            @Override
            public void newItemClicked(NewsItem selectedNewsItem) {
               Intent i = new Intent(MainActivity.this,NewsDetailsActivity.class);
               i.putExtra("selectedNewsItem",selectedNewsItem);
               startActivity(i);
            }
        });

         newsRecView.setLayoutManager(new LinearLayoutManager(this));
         newsRecView.setAdapter(adp);


        CategoryTask tskCategory = new CategoryTask();
        tskCategory.execute("http://94.138.207.51:8080/NewsApp/service/news/getallnewscategories");

         String[] categories = getResources().getStringArray(R.array.categories);
         ArrayAdapter<String> adpCategories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
         spCategories.setAdapter(adpCategories);

         spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                 NewsTask tsk2 = new NewsTask();
                 int id=0;
                 String currentName="";
                 selectedCategory = spCategories.getSelectedItem().toString();

                 if (selectedCategory.equals("All")) {
                     tsk2.execute("http://94.138.207.51:8080/NewsApp/service/news/getall");
                 }
                 else {
                     for (int j = 0; j < dataCategories.size(); j++) {

                         currentName = dataCategories.get(j).getCategoryName();
                         if (selectedCategory.equals(currentName)) {
                             id = dataCategories.get(j).getCategoryID();
                         }
                     }
                     tsk2.execute("http://94.138.207.51:8080/NewsApp/service/news/getbycategoryid/" + id);
                 }

 //                Toast.makeText(MainActivity.this,selectedCategory,Toast.LENGTH_SHORT).show();
             }
             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {
             }
         });
         getSupportActionBar().setTitle("News");
    }


    class CategoryTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(MainActivity.this);
            prgDialog.setTitle("Loading");
            prgDialog.setMessage("Please wait....");
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String strUrl = strings[0];
            StringBuilder buffer = new StringBuilder();

            try {
                URL url = new URL(strUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            dataCategories.clear();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getInt("serviceMessageCode") == 1){

                    JSONArray arr = object.getJSONArray("items");
                    for(int i=0; i<arr.length();i++){

                        JSONObject current = (JSONObject) arr.get(i);

                        Category item = new Category(current.getInt("id"),
                                current.getString("name"));
                        dataCategories.add(item);
                    }

                }else{

                }
                prgDialog.dismiss();
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
                //e.printStackTrace();
            }
        }

    }




    class NewsTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(MainActivity.this);
            prgDialog.setTitle("Loading");
            prgDialog.setMessage("Please wait....");
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String strUrl = strings[0];
            StringBuilder buffer = new StringBuilder();

            try {
                URL url = new URL(strUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            data.clear();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getInt("serviceMessageCode") == 1){

                    JSONArray arr = object.getJSONArray("items");
                    for(int i=0; i<arr.length();i++){

                        JSONObject current = (JSONObject) arr.get(i);
                            long date = current.getLong("date");
                            Date objDate = new Date(date);
                            NewsItem item = new NewsItem(current.getInt("id"),
                                    current.getString("title"),
                                    current.getString("text"),
                                    current.getString("image"),
                                    objDate);
                            data.add(item);

                    }

                }else{

                }
                Log.i("DEV",String.valueOf(data.size()));
                adp.notifyDataSetChanged();
                prgDialog.dismiss();

            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
                //e.printStackTrace();
            }
        }

    }




}
