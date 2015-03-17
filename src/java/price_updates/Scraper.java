package price_updates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
  private static String get(String addr, String elem) {
    try {
      Document doc = Jsoup.connect(addr).get();
      return doc.select(elem).text();
    } catch (Exception e) {
      return e.toString();
    }
  }

  private static double calcPrice(String priceStr, int quantity) {
    return Double.parseDouble(priceStr) * (double)quantity;
  }

  public static String getRaspberryPi(int quantity) {
    String addr = "http://www.amazon.com/s/&url=search-alias=aps&field-keywords=raspberry%20pi%202?tag=duckduckgo-20";
    String priceStr = get(addr, "span.a-color-price").split(" ")[0];
    priceStr = priceStr.substring(priceStr.indexOf("$")+1);
    return Double.toString(calcPrice(priceStr, quantity));
  }

  public static String getParallella(int quantity) {
    String addr = "http://www.digikey.com/product-search/en?keywords=1554-1001-ND&WT.z_slp_buy=Adapteva_Parallella";
    String priceStr = get(addr, "table#pricing td[align=right]").split(" ")[0];
    return Double.toString(calcPrice(priceStr, quantity));
  }

  public static String getBeagleBone(int quantity) {
    String addr = "https://www.adafruit.com/products/1876";
    String priceStr = get(addr, "#prod-price");
    priceStr = priceStr.split(" ")[0].substring(priceStr.indexOf("$")+1);
    return Double.toString(calcPrice(priceStr, quantity));
  }
}
