
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

// Secondary website crawling, extracts statistics and information of interest for an individual country or ocean website.
// Uses JSoup and regex matching to extract details from website and enter them into Entry class data fields.

public class ProcessEntry {

    private Entry entry;

    private Document document;

    public  ProcessEntry(Entry entry) {

        this.entry = entry;
        try {

            String url = entry.getUrl();
            //System.out.println("URL: " + url);
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parseOcean() {

        parseDepth();
        parseSize();
        parseCoordinates();

    }
    public void parseCountry() {


        parseFlag();
        parseRegion();
        parseBorderNations();
        parseCoastline();
        parsePopulation65();
        parseSexRatio();
        //parsePlugTypes();
        parseElectricity();

    }

//Scrapes Ocean website for bold text "lowest point" and simply returns the
//    subsequent string that follows the heading.
    public void parseDepth() {

        Element depth = document.select("strong:containsOwn(lowest point)").first();

        if (depth != null) {
            String lowestPointText = depth.nextSibling().toString().trim();

            entry.setLowestPoint(lowestPointText);
        }
    }
//parseCoordinates - Similar to parseDepth, searches for the link with text of "Geographic coordinates", navigates
//    to target text. Sets text to the respective field in Entry class.
    public void parseCoordinates() {

        Element coords = document.select("a:contains(Geographic coordinates)").first();
        assert coords != null;

        Element h3 = coords.parent();
        assert h3 != null;
        Element desc = h3.nextElementSibling();

        assert desc != null;

        entry.setCoordinates(desc.text());
    }
//Scrapes for link with text "Area", navigates to target text, and uses regex
//    pattern to extract numeric value of area. Sets float value to respective field in Entry class.
//    For exceptions, sets area to 0 as a failsafe.
    public void parseSize() {

        Element coords = document.select("a:contains(Area)").first();
        assert coords != null;

        Element h3 = coords.parent();
        assert h3 != null;
        Element desc = h3.nextElementSibling();

        assert desc != null;

        String text = desc.text();

        String regex = "\\b\\d+(\\.\\d+)?\\b";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher for the input text
        Matcher matcher = pattern.matcher(text);

        // Find the numeric value and print it
        if (matcher.find()) {

            String number = matcher.group();

            float area = Float.parseFloat(number);

            entry.setOceanArea(area);


        } else {
            entry.setOceanArea(0);;
        }
    }
//Scrapes for link with text "Flag description". Navigates to html to target text.
//    Stores available text to respective field in Entry class. Stores empty string if any catches are triggered

    public void parseFlag() {



        try {

            Element header = document.selectFirst("a:containsOwn(Flag description)");

            assert header != null;
            Element h3 = header.parent();
            assert h3 != null;
            Element desc = h3.nextElementSibling();

            assert desc != null;
            entry.setFlagFeature(desc.text());

        } catch (ValidationException e){

            entry.setFlagFeature("");
        }

        catch (NullPointerException e){

            entry.setFlagFeature("");
        }



    }
    //Scrapes for link with text "Map references". Navigates html to target text. Sets
    //    respective field to available text. Stores empty string if exception is triggered

    public void parseRegion() {

        try {

            Element mapRef = document.selectFirst("a:containsOwn(Map references)");

            assert mapRef != null;
            Element h3 = mapRef.parent();
            assert h3 != null;
            Element region = h3.nextElementSibling();

            assert region != null;
            entry.setRegion(region.text());

        }
        catch (NullPointerException e){

            entry.setRegion("");

        }
    }
//Scrapes for bold text "border countries". Navigate to target string.
//    Use regex pattern match to match country names. While loop, add matched country names to an ArrayList.
//    Store ArrayList to respective Entry field. Store empty array in exceptions.
    public void parseBorderNations() {

        try {

            Element borderList = document.selectFirst("strong:contains(border countries)");

            assert borderList != null;

            Element p = borderList.parent();
            assert p != null;
            String text = p.text();

            String result = text.replaceAll("border countries \\(.*?\\):|total: \\d+(?:,\\d+)* km", "").trim();


            String regex = "[A-Z][a-zA-Z ]+";

            // Compile the pattern
            Pattern pattern = Pattern.compile(regex);

            // Create a matcher for the input text
            Matcher matcher = pattern.matcher(text);


            ArrayList<String> countries = new ArrayList<>();




            while (matcher.find()) {
                String country = matcher.group();
                countries.add(country.trim());
            }

            // Print the country names array
            entry.setBorderCountries(countries);

        }
        catch (NullPointerException e){
            ArrayList<String> empty = new ArrayList<>();
            entry.setBorderCountries(empty);


        }
    }
    //Scrapes for link with text "Coastline". Navigates html to target text.
    //    Use regex to pattern match the numeric coastline value. Remove all non numeric values, and store as float.
    //    Store float to respective Entry field. Set to 0 for exceptions.
    public void parseCoastline() {

        try {

            Element mapRef = document.selectFirst("a:containsOwn(Coastline)");

            assert mapRef != null;
            Element h3 = mapRef.parent();
            assert h3 != null;
            String coastStr = h3.nextElementSibling().text();


            String regex = "\\b(\\d{1,3}(?:,\\d{3})*\\.?\\d*)";


            Pattern pattern = Pattern.compile(regex);


            Matcher matcher = pattern.matcher(coastStr);


            if (matcher.find()) {

                String match = matcher.group(1);


                String clean = match.replaceAll("[^\\d.]", "");

                try {
                    float coastline = Float.parseFloat(clean);
                    entry.setCoastline(coastline);
                } catch (NumberFormatException e) {
                    entry.setCoastline(Float.parseFloat("0"));
                }
            } else {
                entry.setCoastline(Float.parseFloat("0"));
            }

        }
        catch (NullPointerException e){

            entry.setCoastline(Float.parseFloat("0"));
        }

    }
//Scrapes for first instance of bold text "65 years and over". Uses regex pattern matching
//    to find corresponding percentage string. Converted to float and saved to respective Entry field. Set to 0
//    for exceptions.
    public void parsePopulation65() {

        try {

            Element bold = document.selectFirst("strong:contains(65 years and over:)");

            assert bold != null;


            String popStr = bold.nextSibling().toString();

            String regex = "^(.*?)%";


            Pattern pattern = Pattern.compile(regex);


            Matcher matcher = pattern.matcher(popStr);


            if (matcher.find()) {

                String match = matcher.group(1);
                try {
                    entry.setPopulation65(Float.parseFloat(match));

                } catch (NumberFormatException e) {

                    entry.setPopulation65(Float.parseFloat("0"));

                }
            }
            else {
                entry.setPopulation65(Float.parseFloat("0"));


            }

        }
        catch (NullPointerException e){

            entry.setPopulation65(Float.parseFloat("0"));

        }


    }
//Scrapes for first instance of bold text "total population". Navigates html to target text.
//    Uses regex pattern match to find decimal ratio value. Parsed to float and saved to respective value in
//    Entry field. Exceptions set to value of 1.0
    public void parseSexRatio() {

        try {

            Element sexRatios = document.selectFirst("a:contains(Sex ratio)");

            Element h3 = sexRatios.parent();

            Element div = h3.parent();

            Element total = div.selectFirst("strong:contains(total population)");

            String text = total.nextSibling().toString();

            String regex = "\\b(?:[01]\\.\\d{2})\\b";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                String match = matcher.group(0);
                entry.setSexRatio(Float.parseFloat(match));
            } else {
                entry.setSexRatio(1);
            }

        }


        catch(NullPointerException e) {

            entry.setSexRatio(1);

        }
    }
//Scrapes for link "Travel Facts". Saves this link as hyperlink.
//    Uses JSoup to connect to this site, and save as a Document.
//
//    Scrape this site for p tag of text "plug types(s)". Regex pattern match used to scrape string
//    immediately following this text.
//    String is split by commas and saved into type ArrayList<String> to list all plug types used in this
//    country. Resulting ArrayList is saved into respective Entry field. Exceptions saved as empty ArrayLists
    public void parsePlugTypes() {

        try {

            Element travel = document.selectFirst("a:contains(Travel Facts)");

            String link = travel.attr("abs:href");

            Document travelFacts = Jsoup.connect(link).get();

            Element plugs = travelFacts.selectFirst("p:contains(plug types(s):)");

            String plugStr = plugs.text();

            String regex = "plug types\\(s\\):\\s*(.*)";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(plugStr);

            if (matcher.find()) {
                String result = matcher.group(1);

                ArrayList<String> list = new ArrayList<>(Arrays.asList(result.split(",\\s*")));

                entry.setPlugTypes(list);

            } else {
                entry.setPlugTypes(new ArrayList<String>()) ;
            }

        }
        catch (NullPointerException e) {

            entry.setPlugTypes(new ArrayList<String>()) ;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//Scrapes for bold text containing "electrification - total population".
//    Navigates to target text, and uses regex patten match to extract numeric value.
//    Value saved as float and stored to respective Entry field.
    public void parseElectricity() {


        try {
            Element elec = document.selectFirst("strong:contains(electrification - total population:)");

            String str = elec.nextSibling().toString();

            String regex = "\\b\\d+(?:\\.\\d+)?";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);


            if (matcher.find()) {
                String percentage = matcher.group();
                entry.setElectricity(Float.parseFloat(percentage));
            } else {
                entry.setElectricity(100);
            }

        }

        catch (NullPointerException e) {
            entry.setElectricity(100);

        }

    }

}
