package price_updates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
  public static String get(String addr, String elem) {
    try {
      Document doc = Jsoup.connect(addr).get();
      return doc.select(elem).text();
    } catch (Exception e) {
      return e.toString();
    }
  }
}
