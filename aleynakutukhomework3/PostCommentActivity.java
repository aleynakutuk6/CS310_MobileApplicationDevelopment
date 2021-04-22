package edu.sabanciuniv.aleynakutukhomework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class PostCommentActivity extends AppCompatActivity {

    ProgressDialog prgDialog;
    EditText txtname;
    EditText txtcomment;
    Button btncomment;
    NewsItem selectedNew;
    int selectedNewsID;
    String txtn;
    String txtc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        txtname = findViewById(R.id.txtpostname);
        txtcomment = findViewById(R.id.txtposttext);
        btncomment = findViewById(R.id.btnpostcomment);
        selectedNew= (NewsItem)getIntent().getSerializableExtra("selectedNew");
        selectedNewsID = selectedNew.getId();


        setTitle("Post Comment");
        ActionBar currentBar = getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24px);
    }

    public void btnCommentClicked(View v){

        txtn = txtname.getText().toString();
        txtc = txtcomment.getText().toString();
        PostCommentTask tsk = new PostCommentTask();
        tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/savecomment");
        //finish();
    }


    class PostCommentTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(PostCommentActivity.this);
            prgDialog.setTitle("Loading");
            prgDialog.setMessage("Please wait....");
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            String urlStr= strings[0];
            StringBuilder buffer = new StringBuilder();

            JSONObject obj = new JSONObject();
            try {
                obj.put("name", txtn );
                obj.put("text",txtc);
                obj.put("news_id", selectedNewsID);

                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-type", "application/json");
                conn.connect();

                DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
                writer.writeBytes(obj.toString());


                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line="";
                    while ((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                }
                else {
                         FragmentManager fm = getSupportFragmentManager();
                         YesNoAlertDialog dialog = new YesNoAlertDialog();
                         dialog.show(fm,"");
                     }

            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent i = new Intent(PostCommentActivity.this, CommentsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("selectedNews",selectedNew);
            startActivity(i);
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return true;
    }



}
