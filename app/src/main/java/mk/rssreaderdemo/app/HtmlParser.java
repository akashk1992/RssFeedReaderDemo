package mk.rssreaderdemo.app;

import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ehc on 10/4/15.
 */
public class HtmlParser {

  private static ArrayList<RssItem> rssItems;
  private static URL url;
  private static RssFeed feed;
  private static Document doc;
  private static String imageSrc;
  private static String title;
  private static String description;

  public static void main(String[] args) throws IOException, SAXException {
    url = new URL("http://www.informationweek.com/rss_simple.asp");
    feed = RssReader.read(url);
    rssItems = feed.getRssItems();
//    parseTimesOfIndia();
//    parseHindustanTimes();
//    parseReutersIndia();
//    parseSiliconIndiaNews();
//    parseInformationWeekNews();
    parseStartupHyderabad();
  }
  private static void parseStartupHyderabad() {
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

  private static void parseTimesOfIndia() {
    String title = null;
    String imageSrc = null;
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#s_content > div.flL.left_bdr > span.arttle > h1,span.arttle h1,div.article h2").first();
      Element imageEle = doc.select("#bellyad > div > div.flL_pos > img").first();
      Elements descriptionEle = doc.select("#artext1 > div,#storydiv > div.section1 > div,div.article > div.content");
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

  private static void parseSiliconIndiaNews() {
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
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }

  public void parseInformationWeekNews(ArrayList<RssItem> rssItems) {
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
//        Log.d("test", "Links: " + rssItems.get(i).getLink());
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#article-main > header > h1").first();
      Element imageEle = doc.select("#article-main > div.docimage img,#article-main > div.inlineStoryImage.inlineStoryImageRight img").first();
      Elements descriptionEle = doc.select("div#article-main");
//      description = descriptionEle.text();
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

  private static void parseHindustanTimes() {
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < rssItems.size(); i++) {
      try {
        doc = Jsoup.connect(rssItems.get(i).getLink()).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Element titleEle = doc.select("#allanswer2 > span > h1").first();
      Element imageEle = doc.select("#fullnews > span > p:nth-child(1) > img").first();
      Elements descriptionEle = doc.select("#fullnews");
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      System.out.println("Title:" + title);
      System.out.println("Image source:" + imageSrc);
      System.out.println("Description:"+ descriptionEle.text());
      for (Element e : descriptionEle) {
        System.out.println(e.text());
      }
    }
  }// parser ends

}
