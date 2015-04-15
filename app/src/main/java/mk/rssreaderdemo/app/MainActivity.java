package mk.rssreaderdemo.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {
  URL url = null;
  RssFeed feed;
  ArrayList<RssItem> rssItems;
  private Document doc;
  private ImageView imageView;
  private TextView textView;
  private String imageSrc;
  private String title;
  private String description;
  private ViewPager pager;
  public static List<Document> docsList = new ArrayList<Document>();
  private HtmlCleaner htmlCleaner;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    imageView = (ImageView) findViewById(R.id.image);
    textView = (TextView) findViewById(R.id.text);
    pager = (ViewPager) findViewById(R.id.pager);
    htmlCleaner = new HtmlCleaner();
    CleanerProperties props = htmlCleaner.getProperties();
    props.setCharset("UTF-8");
    props.setAllowHtmlInsideAttributes(false);
    props.setAllowMultiWordAttributes(true);
    props.setRecognizeUnicodeChars(true);
    props.setOmitComments(true);
//    pager.setAdapter(new NewsFragmentAdapter(getSupportFragmentManager()));
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
        url = new URL("http://startuphyderabad.com/feed");
        feed = RssReader.read(url);
        rssItems = feed.getRssItems();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (SAXException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
//      parseTimesOfIndia();
//      parseAndhraWishes();
//      parseTheHindu();
//      parseNdtv();
//      parseDeccanChronicle();
//      parseSifyNews();
//      parseLiveMintNews();
//      parseReutersIndia();
//      parseBBCNews();
//      parseTechTreeNews();
//      parseDigitNews();
//      parseSiliconIndiaNews();
//      parseOneIndiaNews();
//      parseBgrNews();
//      parseTechCircleNews();
//      parseComputerWorld();
//      parseInformationWeekNews();
//      parseZdnetNews();
//      parseBollywoodHungama();
//      parseBollywoodLife();
//      parseMSNNews();
//      parseGoalIndiaNews();
//      parseCaravenMagzineNews();
//      parseSarkariMirrorNews();
      parseStartupHyderabad();

      /*try {
        parseHtmlUsingXpath();
      } catch (Exception e) {
        e.printStackTrace();
      }*/


      return null;
    }

    @Override
    protected void onPostExecute(Object o) {
//      pager.setAdapter(new NewsFragmentAdapter(getSupportFragmentManager()));
    }
  }

  /*private void parseHtmlUsingXpath() throws Exception {
    String stats = "";
    final String BLOG_URL = "http://timesofindia.indiatimes.com/india/2G-scam-Raja-misled-Manmohan-changed-cut-off-date-to-favour-firms-CBI-says/articleshow/46928840.cms";
    // XPath query
    final String XPATH_STATS = "/*//*[@id=\"bellyad\"]/div/div[1]/img";
    // config cleaner properties
    HtmlCleaner htmlCleaner = new HtmlCleaner();
    CleanerProperties props = htmlCleaner.getProperties();
    props.setAllowHtmlInsideAttributes(false);
    props.setAllowMultiWordAttributes(true);
    props.setRecognizeUnicodeChars(true);
    props.setOmitComments(true);

    // create URL object
    // get HTML page root node
    TagNode root = null;
    URL url = new URL(BLOG_URL);
    root = htmlCleaner.clean(url);
    Object[] statsNode = root.evaluateXPath(XPATH_STATS);

    // query XPath
    // process data if found any node
    if (statsNode.length > 0) {
      // I already know there's only one node, so pick index at 0.
      TagNode resultNode = (TagNode) statsNode[0];
      // get text data from HTML node
      String attr = resultNode.getAttributeByName("src");
      stats = resultNode.getText().toString();
      Log.d("test", "head: " + stats);
      Log.d("test", "attr: " + attr);
    }
  }*/

  private void parseStartupHyderabad() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("h2.entry-title").first();
      Element imageEle = doc.select("#main > div.main_content_wrapper > p:nth-child(4) > img,div.entry-content > p:nth-child(4) > img,div.entry-content > p:nth-child(3) img").first();
      Elements descriptionEle = doc.select("div.main_content_wrapper");

      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseSarkariMirrorNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
        docsList.add(doc);
      } catch (SocketTimeoutException socketException) {

      } catch (IOException e) {
        e.printStackTrace();
      }

     Element titleEle = doc.select("#left > div.single > div.active > h1 > a").first();
      Element imageEle = doc.select("div.content div a img.imgf").first();
      Elements descriptionEle = doc.select("div.pf-content");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseCaravenMagzineNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#squeeze > div > div > h2").first();
      Element imageEle = doc.select("div.show-image > div.f-image > a > img").first();
      Elements descriptionEle = doc.select("div.detailBody");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseGoalIndiaNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("div.headlines > h1").first();
      Element imageEle = doc.select("div.top-section.clearfix > header > img").first();
      Elements descriptionEle = doc.select("div.article-text");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseMSNNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("header.collection-headline h1").first();
      Element imageEle = doc.select("#main > article > section > span > span.image > img").first();
      Elements descriptionEle = doc.select("#main > article > section");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseBollywoodLife() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#content > section.main-post > header > h1").first();
      Element imageEle = doc.select("#content > section.main-post > article > img").first();
      Elements descriptionEle = doc.select("article.entry-content.cunlock_main_content");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseBollywoodHungama() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#movie_news_ajax > div > h1").first();
      Element imageEle = doc.select("div[align=center] img.mmb15").first();
      Elements descriptionEle = doc.select("#movie_news_ajax > div > div.mwidth588.mfl.mmt8.minline.moverflow > div.ialignlft.mfnt12 > p");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseZdnetNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("header.storyHeader h1").first();
      Element imageEle = doc.select("div.storyBody > figure:nth-child(4) img,div.relatedContent.alignRight span > img").first();
      Elements descriptionEle = doc.select("div.storyBody");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseInformationWeekNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#article-main > header > h1").first();
      Element imageEle = doc.select("#article-main > div.docimage img,#article-main > div.inlineStoryImage.inlineStoryImageRight img").first();
      Elements descriptionEle = doc.select("div#article-main");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
//      Log.d("test", "Title: " + title);
//      Log.d("test", "Image Source: " + imageSrc);
//      Log.d("test", "Description: " + description);
    }
  }

  private void parseComputerWorld() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#page-wrapper > section > article > header > h1").first();
      Element imageEle = doc.select("#page-wrapper > section > article > figure > img").first();
      Elements descriptionEle = doc.select("#drr-container");
      description = descriptionEle.text();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
    }
  }

  private void parseTechCircleNews() {
    String imageSrc = null;
    String title = null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("h3.post-title.entry-title").first();
      Element imageEle = doc.select("div.wp-caption.alignright img").first();
      Elements descriptionEle = doc.select("div.post-body.entry-content");
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + descriptionEle.text());
    }
  }

  private void parseBgrNews() {
    String imageSrc = null;
    String title = null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("h1.entry-title").first();
      Element imageEle = doc.select("div.post-img-wrap.bgr-style-normal img").first();
      Elements descriptionEle = doc.select("div.text-column");
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + descriptionEle.text());
    }
  }

  private void parseOneIndiaNews() {
    String imageSrc = null;
    String title = null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements titleEle = doc.select("h1.heading,#container > section > div > div.leftpanel > h1");
      Elements imageEle = doc.select("div.big_center_img img");
      Elements descriptionEle = doc.select("article");
//      if (imageEle != null)
//        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + descriptionEle.text());
    }
  }

  private void parseSiliconIndiaNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect("http://news.siliconindia.com/technology/Samsung-Plans-to-Take-Bitcoin-Technology-Beyond-Currency-Use-nid-181379-cid-2.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+sitechnews+%28siliconindia-tech%29&utm_content=FeedBurner").get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#allanswer2 h1").first();
      Element imageEle = doc.select("#fullnews > span > p:nth-child(1) > img").first();
      Elements descriptionEle = doc.select("#fullnews");
//      String d1 = descriptionEle.text();
//      String description = d1.substring(0, d1.indexOf("also read")-1);
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description:");
      for (Element e : descriptionEle) {
        Log.d("test", e.text());
      }
    }
  }

  private void parseDigitNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("h1.h1h").first();
      Element imageEle = doc.select("#page-wrap > div > div > div > img").first();
      Elements descriptionEle = doc.select("div.inside-container");
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + descriptionEle.text());
    }
  }

  private void parseTechTreeNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("h2.story-title").first();
      Element imageEle = doc.select("div.preview-img img").first();
      Element descriptionEle = doc.select("div.story-content").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + descriptionEle.text());
    }
  }

  private void parseReutersIndia() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements titleEle = doc.select("#articleContent > div > div > div > h1.article-headline,h1.article-headline");
      Element imageEle = doc.select("div.related-photo-container img,div.module-slide-media img,#photoFullSize > div img").first();
      Element descriptionEle = doc.select("span#articleText").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");

      if (titleEle != null) {
        title = titleEle.text();
        for (Element e : titleEle) {
          Log.d("test", "Tit:" + e.text());
        }
      }
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
//      Log.d("test", "Description: " + descriptionEle.text());
      /*for (Element e : descriptionEle) {
        System.out.println(e.text());
//      textView.setText(e.text());
      }*/
    }
  }

  private void parseLiveMintNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("h1.sty_head_38").first();
      Element imageEle = doc.select("div.sty_main_pic_sml1 img").first();
      Elements descriptionEle = doc.select("div.text");
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + descriptionEle.text());
    }
  }

  private void parseSifyNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#artContent > div.fullstory-heading-wrapper > h1").first();
      Element imageEle = doc.select("#artContent > div:nth-child(7) > img").first();
      Element descriptionEle = doc.select("#contentDiv").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + descriptionEle.text());
    }
  }

  private void parseBBCNews() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("div.story-body h1,h1.story-body__h1").first();
      Element imageEle = doc.select("figure.media-landscape.full-width.has-caption.lead img").first();
      Elements descriptionEle = doc.select("div.story-body__inner");
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:" + descriptionEle.text());
    }
  }

  private void parseMidDayNews() {
    String imageSrc = null;
    String title = null;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("div.article_detail h1").first();
      Element imageEle = doc.select("div.article_detail span img").first();
      Elements descriptionEle = doc.select("div.article_detail > span");
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:" + descriptionEle.text());
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  private void parseEconomicTimes() {
    String imageSrc = null;
    String title = null;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#pageContent > article > h1,#mod-article-header > h1").first();
      Element imageEle = doc.select("div.articleImg figure img,#mod-article-image-link > img").first();
      Elements descriptionEle = doc.select("#mod-a-body-first-para,#mod-a-body-after-first-para,div.normal");
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:" + descriptionEle.text());
      /*for (Element e : descriptionEle) {
        System.out.println(e.text());
      }*/
    }
  }

  private void parseDeccanHerald() {
    String imageSrc = null;
    String title = null;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements descriptionEle = doc.select("div.newsText p");
      Element imageEle = doc.select("figure.floatLeftImg img").first();
      Element titleEle = doc.select("#main > section > div.newsText > h1").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:");
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  private void parseTheStatesmanNews() {
    String imageSrc = null;
    String title = null;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements descriptionEle = doc.select("#contentStory");
      Element imageEle = doc.select("#ctl00_ContentPlaceHolder2_divStory img").first();
      Element titleEle = doc.select("#ctl00_ContentPlaceHolder2_dvhead > div").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:");
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  private void parseFirstSpotNews() {
    String imageSrc = null;
    String title = null;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements descriptionEle = doc.select("div.fullCont1");
      Element imageEle = doc.select("noscript img").first();
      Element titleEle = doc.select("h1.artTitle").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:");
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  private void parseDNANews() {
    String title = null;
    String imageSrc = null;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements descriptionEle = doc.select("div.content-story");
      Element imageEle = doc.select("article > div.content-story > figure > div > div > ul > li > img").first();
      Element titleEle = doc.select("h1.pageheading").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:");
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  private void parseWebIndia123() {
    String imageSrc = null;
    String title = null;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements descriptionEle = doc.select("div.news");
      Element imageEle = doc.select("div.entry.entry-content > p:nth-child(1) > a > img").first();
      Element titleEle = doc.select("div.head_line").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:");
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  private void parseAsianNews() {
    String imageSrc = null;
    String title = null;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements descriptionEle = doc.select("div.content p");
      Element imageEle = doc.select("div.content > div > div > a > img").first();
      Element titleEle = doc.select("#page-title").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:");
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  private void parseIBNLive() {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }
      Elements descriptionEle = doc.select("#photo p,#artile_lbox > div.article_tbox p");
      Element imageEle = doc.select("#photo > img,#artile_lbox > div.article_img > img,#photo > img:nth-child(4)").first();
      Element titleEle = doc.select("#aleft_box > h1").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:");
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  private void parseTimesOfIndia() {
    URL url;
    TagNode node;
    try {
      url = new URL("http://timesofindia.indiatimes.com/india/2G-scam-Raja-misled-Manmohan-changed-cut-off-date-to-favour-firms-CBI-says/articleshow/46928840.cms");
      URLConnection conn = url.openConnection();
      InputStream in = conn.getInputStream();
      node = htmlCleaner.clean(url);
      Object[] objects = node.evaluateXPath("//*[@id=\"s_content\"]/div[1]/span[1]/h1");
      for (Object o : objects) {
        Log.d("test", "nodes: " + o.toString());
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XPatherException e) {
      e.printStackTrace();
    }

//    Element titleEle = doc.getElementsByTag("h1").first();
//    Elements descEle = doc.getElementsByClass("Normal");
//    Element e = descEle.get(0);
//      if (imageEle != null)
//        imageSrc = imageEle.absUrl("src");
//    if (titleEle != null)
//      title = titleEle.text();
//    Log.d("test", "Title: " + title);
//    Log.d("test", "Image source: " + imageSrc);
//    Log.d("test", "Description: " + e.text());
//    }
  }

  private void parseAndhraWishes() {
    String title = null;
    String imageSrc = null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("p[style],div.article-content").first();
      Element imageEle = doc.select("center img,div.article-content > center > img").first();
      Element titleEle = doc.select("h1,div.article-content > div > h1").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:" + descriptionEle.text());
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

  private void parseTheHindu() {
    String imageSrc = null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element descriptionEle = doc.select("p[class=body]").first();
      Element imageEle = doc.select("img[class=main-image]").first();
      Element titleEle = doc.select("h1").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      String title = titleEle.text();
      String description = descriptionEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "description: " + description);
      Log.d("test", "ImageSrc: " + imageSrc);
    }
  }

  private void parseHindustanTimes() {
    String imageSrc = null;
    String title = null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements descriptionEle = doc.select("section.story_content > div.sty_txt");
      Element imageEle = doc.select("div.news_photo img").first();
      Element titleEle = doc.select("h1").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description");
      for (Element e : descriptionEle) {
        Log.d("test", " " + e.text());
      }
    }
  }

  private void parseDeccanChronicle() {
    String imageSrc = null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Elements descriptionEle = doc.select("p[class=body],#node-article-211336 > div.content.clearfix div");
      Element imageEle = doc.select("img[class=main-image],div.field.field-name-field-image.field-type-image.field-label-hidden  div  div  img").first();
      Element titleEle = doc.select("h1,h1#page-title").first();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      String title = titleEle.text();
      for (Element e : descriptionEle) {
        Log.d("test1", "" + e.data());
      }
      String description = descriptionEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "description: " + description);
      Log.d("test", "ImageSrc: " + imageSrc);
    }
  }

  private void parseNdtv() {
    String imageSrc = null;
    try {
      doc = Jsoup.connect("http://www.ndtv.com/people/a-man-who-loved-to-fly-set-on-a-mysterious-deadly-course-749967").get();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Element descriptionEle = doc.select("div#ins_storybody,div.fullContent p,div.stry-para div p,div#ContentPlaceHolder1_FullstoryCtrl_fulldetails").first();
    Element imageEle = doc.select("img#story_image_main,div.col-md-10 div.ndmv-common-img-wrapper img,div.stry-ft-img img,img#ContentPlaceHolder1_FullstoryCtrl_mainstoryimage").first();
    Element titleEle = doc.select("div.storytile h1,h1 span#ContentPlaceHolder1_FullstoryCtrl_title").first();
    if (imageEle != null)
      imageSrc = imageEle.absUrl("src");
    String title = titleEle.text();
    String description = descriptionEle.text();
    Log.d("test", "Title: " + title);
    Log.d("test", "description: " + description);
    Log.d("test", "ImageSrc: " + imageSrc);
  }

  private class NewsFragmentAdapter extends FragmentPagerAdapter {
    public NewsFragmentAdapter(FragmentManager supportFragmentManager) {
      super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
      Log.d("test", " " + i);
      return new NewsFragment().newInstance(i);
    }


    @Override
    public int getCount() {
      return rssItems.size();
    }
  }
}

