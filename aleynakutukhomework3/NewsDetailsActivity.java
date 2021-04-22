package edu.sabanciuniv.aleynakutukhomework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class NewsDetailsActivity extends AppCompatActivity {

    NewsItem selectedNewsItem;
    ImageView imgDetail;
    TextView txtDetailText;
    TextView txtDetailTitle;
    TextView txtDetailDate;
    int selectedNewsItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        imgDetail = findViewById(R.id.imgdetail);
        txtDetailText = findViewById(R.id.txtdetail);
        txtDetailTitle = findViewById(R.id.txtdetailtitle);
        txtDetailDate = findViewById(R.id.txtdetaildate);

       selectedNewsItem = (NewsItem)getIntent().getSerializableExtra("selectedNewsItem");
       selectedNewsItemID = selectedNewsItem.getId();

       txtDetailText.setText(selectedNewsItem.getText());
       txtDetailTitle.setText(selectedNewsItem.getTitle());
       txtDetailDate.setText((new SimpleDateFormat("dd/MM/yyy").format(selectedNewsItem.getNewsDate())));
        ImageDownloadTask tsk = new ImageDownloadTask(imgDetail);
        tsk.execute(selectedNewsItem);

       setTitle("News Details");
        ActionBar currentBar = getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24px);

    }

    class ImageDownloadTask extends AsyncTask<NewsItem,Void, Bitmap> {
        ImageView imgView;

        public ImageDownloadTask(ImageView imgView) {
            this.imgView = imgView;
        }

        @Override
        protected Bitmap doInBackground(NewsItem... news) {

            NewsItem current = news[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(current.getImagePath());

                InputStream is = new BufferedInputStream(url.openStream());

                bitmap = BitmapFactory.decodeStream(is);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            imgView.setImageBitmap(bitmap);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.mn_comment)
        {
            Intent i = new Intent(this, CommentsActivity.class);
            i.putExtra("selectedNews",selectedNewsItem);
            startActivity(i);

        }
        else if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return true;
    }
}
