package mk.rssreaderdemo.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by ehc on 14/4/15.
 */
public class NewsFragment extends Fragment {

  private ArrayList<String> links;
  private String imageSrc;
  private String title;
  private ImageView fragImageView;
  private TextView fragTextView;
  private TextView headingTextView;
  private Element titleEle;
  private Element imageEle;
  private Elements descElements;
  private String description;
  private Document doc;
  private StringBuilder stringBuilder;

  public Fragment newInstance(int pos) {
    Bundle bundle = new Bundle();
    bundle.putInt("pos", pos);
    NewsFragment newsFragment = new NewsFragment();
    newsFragment.setArguments(bundle);
    return newsFragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.news_fragments, container, false);
    fragImageView = (ImageView) view.findViewById(R.id.frag_image);
    fragTextView = (TextView) view.findViewById(R.id.frag_text);
    headingTextView = (TextView) view.findViewById(R.id.frag_heading);

    stringBuilder = new StringBuilder();
    Bundle bundle = getArguments();
    int pos = bundle.getInt("pos");
    if (pos < MainActivity.docsList.size()) {
      doc = MainActivity.docsList.get(pos);
//      parseTimesOf india();
//      parseElementsStartupsHyderabad();
      parseElementsTimesOfIndia();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Picasso.with(getActivity()).load(imageSrc).placeholder(R.drawable.news_placeholder).into(fragImageView);
//      fragTextView.setText(stringBuilder);
//      fragTextView.setText(description);
      fragTextView.setText(Html.fromHtml(description));
      headingTextView.setText(title);
      return view;
    } else return null;
  }

  private void parseElementsTimesOfIndia() {
    titleEle = doc.select("#s_content > div.flL.left_bdr > span.arttle > h1,span.arttle h1,div.article h2").first();
    imageEle = doc.select("#bellyad > div > div.flL_pos > img,#articleimg1").first();
//    Element de = doc.select("#artext1 > div,#storydiv > div.section1 > div,div.article > div.content,div.normal").first();
    descElements = doc.select("#artext1 > div,#storydiv > div.section1 > div,div.article > div.content,div.normal");

    String descriptionHtml = descElements.html();
    description = Jsoup.clean(descriptionHtml, Whitelist.basic());
    Log.d("test0", description);
    /*description = Jsoup.parse(description.replaceAll("(?i)</tr>", "</tr> br2n ")
        .replaceAll("(?i)<br[^>]*>", "br2n")
        .replaceAll("(?i)<p>", "<p> br2n ")
        .replaceAll("(?i)</p>", "</p> br2n "))
        .html();*/
//    Log.d("test1", "" + description);
//    description = Jsoup.clean(description, Whitelist.simpleText());
//    Log.d("test2", "" + description);

//    description = description.replaceAll("br2n", "\n");
    /************************/
  }

  /*private void parseElementsSarkariNaukari() {
    titleEle = doc.select("#left > div.single > div.active > h1 > a").first();
    imageEle = doc.select("div.content div a img.imgf").first();
    descElements = doc.select("div.pf-content");
    description = descElements.text();
  }

  private void parseElementsStartupsHyderabad() {
    titleEle = doc.select("h2.entry-title").first();
    imageEle = doc.select("#main > div.main_content_wrapper > p:nth-child(4) > img,div.entry-content > p:nth-child(4) > img,div.entry-content > p:nth-child(3) img").first();
    descElements = doc.select("div.main_content_wrapper").select("p");
//    description = descElements.text();
    for (Element e : descElements) {
      String text = e.text();
      Log.d("test", text);
      if (text != "" || text != null)
        stringBuilder = stringBuilder.append(text + "\n");
    }
//    description = descElements.html();
//    Document doc1 = Jsoup.parse(description);
//    description= description.replaceAll("<p>","<pre>\\n</pre>");
  }*/
}


//String text = Jsoup.parse(htmlText.replaceAll("(?i)<br[^>]*>", "br2n")).text();
//System.out.println(text.replaceAll("br2n", "\n"));
//    pr.println(text.replaceAll("br2n", "\n"));

//String text = Jsoup.parse(htmlText.replaceAll("(?i)</tr>", "</tr> br2n ").replaceAll("(?i)<br[^>]*>", "br2n")).replaceAll("(?i)<p>", "<p> br2n ").replaceAll("(?i)</p>", "</p> br2n ").text();
//System.out.println(text.replaceAll("br2n", "\n"));