package mk.rssreaderdemo.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
  private Elements descriptionEle;
  private String description;
  private Document doc;

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
    headingTextView=(TextView) view.findViewById(R.id.frag_heading);

    Bundle bundle = getArguments();
    int pos = bundle.getInt("pos");
    //sarkari mirror ...
    if (pos < MainActivity.docsList.size()) {
      doc = MainActivity.docsList.get(pos);
//      parseElementsSarkariNaukari();
      parseElementsStartupsHyderabad();
      if (imageEle != null)
        imageSrc = imageEle.absUrl("src");
      if (titleEle != null)
        title = titleEle.text();
      Log.d("test", "Title: " + title);
      Log.d("test", "Image Source: " + imageSrc);
      Log.d("test", "Description: " + description);
      Picasso.with(getActivity()).load(imageSrc).placeholder(R.drawable.news_placeholder).into(fragImageView);
      fragTextView.setText(description);
      headingTextView.setText(title);
      return view;
    } else return null;

  }

  private void parseElementsSarkariNaukari() {
    titleEle = doc.select("#left > div.single > div.active > h1 > a").first();
    imageEle = doc.select("div.content div a img.imgf").first();
    descriptionEle = doc.select("div.pf-content");
    description = descriptionEle.text();
  }

  private void parseElementsStartupsHyderabad(){
    titleEle = doc.select("h2.entry-title").first();
    imageEle = doc.select("#main > div.main_content_wrapper > p:nth-child(4) > img,div.entry-content > p:nth-child(4) > img,div.entry-content > p:nth-child(3) img").first();
    descriptionEle = doc.select("div.main_content_wrapper");
    description = descriptionEle.text();
  }
}
