package edu.sabanciuniv.aleynakutukhomework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class CommentsActivity extends AppCompatActivity {

    RecyclerView commentsRecView;
    ProgressDialog prgDialog;
    List<CommentItem> dataComments;
    CommentAdapter adp2;
    NewsItem selectedNews;
    int selectedNewsID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        dataComments = new ArrayList<>();
        commentsRecView = findViewById(R.id.commentsrec);
        selectedNews= (NewsItem)getIntent().getSerializableExtra("selectedNews");
        selectedNewsID = selectedNews.getId();

        CommentTask tsk = new CommentTask();
        tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/getcommentsbynewsid/" + selectedNewsID);

        adp2 = new CommentAdapter(dataComments, this);

        commentsRecView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecView.setAdapter(adp2);

        setTitle("Comments");
        ActionBar currentBar = getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24px);

    }


    class CommentTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(CommentsActivity.this);
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
            dataComments.clear();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getInt("serviceMessageCode") == 1){

                    JSONArray arr = object.getJSONArray("items");
                    for(int i=0; i<arr.length();i++){

                        JSONObject current = (JSONObject) arr.get(i);
                        CommentItem item = new CommentItem( current.getInt("id"),
                                current.getString("name"),
                                current.getString("text"));
                        dataComments.add(item);
                    }

                }else{

                }
                Log.i("DEV",String.valueOf(dataComments.size()));
                adp2.notifyDataSetChanged();
                prgDialog.dismiss();

            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
                //e.printStackTrace();
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comments_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.mn_addcomment)
        {
            Intent i = new Intent(this, PostCommentActivity.class);
            i.putExtra("selectedNew",selectedNews);
            startActivity(i);

        }else if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return true;
    }


}
