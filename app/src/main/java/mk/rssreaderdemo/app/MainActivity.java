package mk.rssreaderdemo.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
  URL url = null;
  RssFeed feed;
  ArrayList<RssItem> rssItems;
  private Document doc;
  private ImageView imageView;
  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    imageView = (ImageView) findViewById(R.id.image);
    textView = (TextView) findViewById(R.id.text);
    new RssAsyncTask().execute();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private class RssAsyncTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
      try {
        // pass source rss feed, eg: sports rss feed -- Andhra wishes
        url = new URL("http://gadgets.ndtv.com/rss/reviews");
        feed = RssReader.read(url);
        rssItems = feed.getRssItems();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (SAXException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
//      parseAndhraWishes();
//      parseTheHindu();
      parseNdtv();
      return null;
    }

    @Override
    protected void onPostExecute(Object o) {
      // Picasso.with(getApplicationContext()).load(imageSrc).into(imageView);
    }
  }

  private void parseAndhraWishes() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("p[style]").first();
      Element imageEle = doc.select("center img").first();
      Element titleEle = doc.select("h1").first();
      if (imageEle != null && titleEle != null && descriptionEle != null) {
        String imageSrc = imageEle.absUrl("src");
        String title = titleEle.text();
        String description = descriptionEle.text();
        Log.d("test", "Title: " + title);
        Log.d("test", "description: " + description);
        Log.d("test", "ImageSrc: " + imageSrc);
      }
    }
  }

  private void parseHyderabadStartUps() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("p[style]").first();
      Element imageEle = doc.select("center img").first();
      Element titleEle = doc.select("h1").first();
      if (imageEle != null && titleEle != null && descriptionEle != null) {
        String imageSrc = imageEle.absUrl("src");
        String title = titleEle.text();
        String description = descriptionEle.text();
        Log.d("test", "Title: " + title);
        Log.d("test", "description: " + description);
        Log.d("test", "ImageSrc: " + imageSrc);
      }
    }
  }

  private void parseTimesOfIndia() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("p[style]").first();
      Element imageEle = doc.select("center img").first();
      Element titleEle = doc.select("h1").first();
      if (imageEle != null && titleEle != null && descriptionEle != null) {
        String imageSrc = imageEle.absUrl("src");
        String title = titleEle.text();
        String description = descriptionEle.text();
        Log.d("test", "Title: " + title);
        Log.d("test", "description: " + description);
        Log.d("test", "ImageSrc: " + imageSrc);
      }
    }
  }

  private void parseTheHindu() {
    String imageSrc=null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("p[class=body]").first();
      Element imageEle = doc.select("img[class=main-image]").first();
      Element titleEle = doc.select("h1").first();
      if(imageEle!=null)
      imageSrc = imageEle.absUrl("src");
      String title = titleEle.text();
      String description = descriptionEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "description: " + description);
        Log.d("test", "ImageSrc: " + imageSrc);
    }
  }

  private void parseDeccanChronicle(){
    String imageSrc=null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("p[class=body]").first();
      Element imageEle = doc.select("img[class=main-image]").first();
      Element titleEle = doc.select("h1").first();
      if(imageEle!=null)
        imageSrc = imageEle.absUrl("src");
      String title = titleEle.text();
      String description = descriptionEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "description: " + description);
      Log.d("test", "ImageSrc: " + imageSrc);
    }
  }

  private void parseHindustanTimes() {
    String imageSrc=null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("p[class=body]").first();
      Element imageEle = doc.select("img[class=main-image]").first();
      Element titleEle = doc.select("h1").first();
      if(imageEle!=null)
        imageSrc = imageEle.absUrl("src");
      String title = titleEle.text();
      String description = descriptionEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "description: " + description);
      Log.d("test", "ImageSrc: " + imageSrc);
    }
  }

  private void parseNdtv() {
    String imageSrc=null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("div#ins_storybody,div.fullContent p,div.stry-para div p,div#ContentPlaceHolder1_FullstoryCtrl_fulldetails").first();
      Element imageEle = doc.select("img#story_image_main,div.col-md-10 div.ndmv-common-img-wrapper img,div.stry-ft-img img,img#ContentPlaceHolder1_FullstoryCtrl_mainstoryimage").first();
      Element titleEle = doc.select("h1").first();
      if(imageEle!=null)
        imageSrc = imageEle.absUrl("src");
      String title = titleEle.text();
      String description = descriptionEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "description: " + description);
      Log.d("test", "ImageSrc: " + imageSrc);
    }
  }


}

