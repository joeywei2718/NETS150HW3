import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Scanner;

//Testing document for indivdidual scraping algorithms on individual website.


public class test {
    public static void main(String[] args) throws IOException {

        String url = "https://www.cia.gov/the-world-factbook/countries/belarus/";

        Document doc = null;
        try {

            doc = Jsoup.connect(url).get();


        } catch (IOException e) {
            System.out.println("failed to connect");

        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Select a question number:");
            System.out.println("1. Question 1");
            System.out.println("2. Question 2");
            System.out.println("3. Question 3");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Question 1:");
                    // Code block for Question 1
                    System.out.print("Enter your input: ");
                    String input1 = scanner.next();
                    System.out.println("You entered: " + input1);
                    break;
                case 2:
                    System.out.println("Question 2:");
                    // Code block for Question 2
                    System.out.print("Enter your input: ");
                    String input2 = scanner.next();
                    System.out.println("You entered: " + input2);
                    break;
                case 3:
                    System.out.println("Question 3:");
                    // Code block for Question 3
                    System.out.print("Enter your input: ");
                    String input3 = scanner.next();
                    System.out.println("You entered: " + input3);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
        /*
        assert doc != null;
        Element mapRef = doc.selectFirst("a:containsOwn(Map references)");

        assert mapRef != null;
        Element h3 = mapRef.parent();
        assert h3 != null;
        Element region = h3.nextElementSibling();

        assert region != null;
        System.out.println(region.text());


        Element borderList = doc.selectFirst("strong:contains(border countries)");

        assert borderList != null;

        Element p = borderList.parent();
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
        for (String country : countries) {
            System.out.println(country);
        }


        Element coords = doc.select("a:contains(Area)").first();
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

            System.out.println(area);
        } else {
            System.err.println("Error: No long found found.");
        }




        Element mapRef = doc.selectFirst("a:containsOwn(Coastline)");

        assert mapRef != null;
        Element h3 = mapRef.parent();
        assert h3 != null;
        String coastStr = h3.nextElementSibling().text();

        // Regular expression pattern to capture the first number
        String regex = "\\b(\\d{1,3}(?:,\\d{3})*\\.?\\d*)";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher for the input string
        Matcher matcher = pattern.matcher(coastStr);

        // Find the first match
        if (matcher.find()) {
            // Extract the matched value (first group)
            String match = matcher.group(0);

            // Clean the matched value (remove non-numeric characters)
            String clean = match.replaceAll("[^\\d.]", "");

            try {
                float coastline = Float.parseFloat(clean);
                System.out.println("Coastline: " + coastline);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing coastline value.");
            }
        } else {
            System.out.println("No match found.");
        }



        Element bold = doc.selectFirst("strong:contains(65 years and over:)");

        assert bold != null;


        String popStr = bold.nextSibling().toString();

        String regex = "^(.*?)%";


        Pattern pattern = Pattern.compile(regex);


        Matcher matcher = pattern.matcher(popStr);


        if (matcher.find()) {

            String match = matcher.group(0); // Group 1 contains the text before the %
            System.out.println(match);

        } else {
            System.out.println("No age info found");
        }





        Element sexRatios = doc.selectFirst("a:contains(Sex ratio)");

        Element h3 = sexRatios.parent();

        Element div = h3.parent();

        Element total = div.selectFirst("strong:contains(total population)");

        String text = total.nextSibling().toString();

        String regex = "\\b(?:[01]\\.\\d{2})\\b";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String match = matcher.group(0);
            System.out.println(match);

        } else {


        }




        Element travel = doc.selectFirst("a:contains(Travel Facts)");

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


        } else {
            System.out.println("No match found.");
        }

        */


    }
}