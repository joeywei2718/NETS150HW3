import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// First layer of website crawling
// Navigates homepage to assemble allOceans
// Navigates from Home > References > Country Data Codes to assemble the allCountries arrays
// Extracts names and URLs for each country in the CDC site and creates unique Entry object
// Uses JSoup to navigate website links
public class ReadWebsite {

    String link;
    public ReadWebsite(String link) {
        this.link = link;
    }
    public ArrayList<Entry> getOceans() {

        ArrayList<Entry> oceans = new ArrayList<Entry>();

        try {

            Document doc = Jsoup.connect(link).get();

            Elements allLinks = doc.select("a[href]");

            for (Element link:allLinks) {

                String text = link.text();

                if (text.contains("Ocean") && !text.contains("Oceania")) {

                    String url = link.attr("abs:href");

                    Entry current = new Entry(text, url);
                    oceans.add(current);
                }

            }

        } catch (IOException e) {
            System.out.println("Error connecting to Base URL");
            e.printStackTrace();
        }

        return oceans;
    }

    public ArrayList<Entry> getCountries() {

        ArrayList<Entry> countries = new ArrayList<Entry>();

        String CDCUrl = "";

        try {

            Document doc = Jsoup.connect(link + "/references").get();

            Element link = doc.selectFirst("a:containsOwn(Country Data Codes)");

            if (link != null) {

                CDCUrl = link.attr("abs:href");

            }
        } catch (IOException e) {
            System.out.println("Error connecting to References");
            e.printStackTrace();
        }
        try {

            Document CDC = Jsoup.connect(CDCUrl).get();

            Elements CountryLinks = CDC.select("tr.content-row a");

            for (Element link : CountryLinks) {


                try {
                    String url = link.attr("abs:href");
                    String text = link.text();

                    int response = Jsoup.connect(url).execute().statusCode();

                    if (response != 404) {
                        Entry current = new Entry(text, url);
                        countries.add(current);

                    }

                }

                catch (IOException e){
                    System.out.println("Could not read: " + link.text());
                }
            }


        } catch (IOException e) {
            System.out.println("Error connecting to References/Country Data Codes/");
            e.printStackTrace();
        }

        return countries;
    }

}
