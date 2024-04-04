import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.max;

// Main program file. Initializes web crawling, data storage, and user interface
// Handles storage and manipulation of extracted data
// Handles question and answer interface
// Run this file to start program

public class Main {

    public final static String FACTBOOK_URL = "https://www.cia.gov/the-world-factbook/";
    static HashMap<String, ArrayList<String>> dictPlugs = new HashMap<>();
    public static void addToPlugDict(String country, String plug) {

        ArrayList<String> countryArr = new ArrayList<>();
        countryArr.add(country);

        if (dictPlugs.containsKey(plug)) {

            dictPlugs.get(plug).addAll(countryArr);
        }
        else {
            dictPlugs.put(plug, countryArr);
        }

    };

    public static void main(String[] args) {

        ReadWebsite data = new ReadWebsite(FACTBOOK_URL);

        ArrayList<Entry> allOceans = data.getOceans();

        ArrayList<Entry> allCountries = data.getCountries();

        for (Entry x : allOceans) {

            ProcessEntry current = new ProcessEntry(x);
            current.parseOcean();

        }



        for (Entry x : allCountries) {
            ProcessEntry current = new ProcessEntry(x);
            current.parseCountry();

        }

        Scanner input = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("Select Question");
            System.out.println("1 - Lowest Point in Ocean");
            System.out.println("2 - Flag Details");
            System.out.println("3 - Least Border Countries");
            System.out.println("4 - Coordinates of Ocean by Size");
            System.out.println("5 - Largest Coastline and 65 Year Old Population");
            System.out.println("6 - Groups of plug types other than USA by plug type");
            System.out.println("7 - Disproportionate Sex Ratio of Border Countries");
            System.out.println("8 - Lowest Electrification Rate in a Region");
            System.out.println("9 - Exit Program");
            System.out.println("Enter Selection:");

            int select = input.nextInt();
            input.nextLine();

            switch (select) {

                case 1:
                    //Input ocean name is compared against all ocean names in allOceans Array. When match to existing ocean Entry is found,
                    //lowest point is printed.
                    System.out.println("1.) Lowest Point in the _?");
                    String oceanChoice = input.nextLine();

                    for (Entry x: allOceans) {

                        String oceanName = x.getName();

                        if (oceanChoice.equalsIgnoreCase(oceanName)){

                            System.out.println(x.getLowestPoint());
                            break;
                        }

                    }
                    break;

                case 2:
                    //Search term is saved as string. Loop through allCountries, and see if search term is present in
                    //country's flag description field. If yes, print name.
                    System.out.println("2.) List all countries with flags containing _.");
                    String flagSearch = input.nextLine();

                    for (Entry x: allCountries) {

                        try {

                            if (x.getFlagFeature().contains(flagSearch)) {
                                System.out.println(x.getName());
                            }
                        }

                        catch (NullPointerException ignored) {
                        }

                    }
                    break;
                case 3:
                    //Track smallest number of border countries and corresponding country iteratively.
                    //Loop through allCountries. If region field matches the input field, AND the size of the borderCountries array is smaller
                    //than the current smallest size, replace counter LeastBorders field and spaceholder countryName field to current country.
                    //
                    //Resulting values will be the country with the least border countries in a certain region.
                    System.out.println("3.) Which country in _ has the least amount of border countries?");
                    String borderSearch = input.nextLine();
                    int LeastBorders = 999;
                    String countryName = "";

                    for (Entry x: allCountries) {

                        try {

                            if (x.getRegion().equalsIgnoreCase(borderSearch) && x.getBorderCountries().size() <= LeastBorders) {

                                LeastBorders = x.getBorderCountries().size();
                                countryName = x.getName();
                            }


                        }

                        catch (NullPointerException ignored) {
                        }

                    }
                    System.out.println(countryName);
                    System.out.println(LeastBorders);
                    break;

                case 4:
                    //Sort allOceans array by respective Area field in Entry class.
                    //Create dictionary to store ordinal values and corresponding integer values.
                    //Reference ordinal string to find integer value. Use integer value to access desired index in the
                    //sorted allOceans ArrayList. Print the Name, Size, and Coordinates of the target ocean.
                    System.out.println("4.) What are the geographic coordinates of the _ largest ocean (by area)?");

                    System.out.println("use lowercase sequential words (first, second, third, etc.)");

                    allOceans.sort(Comparator.comparingDouble(Entry::getOceanArea).reversed());



                    HashMap<String, Integer> dict = new HashMap<>();
                    dict.put("first", 0);
                    dict.put("second", 1);
                    dict.put("third", 2);
                    dict.put("fourth", 3);
                    dict.put("fifth", 4);
                    dict.put("sixth", 5);

                    String sizeOcean = input.nextLine();
                    if (dict.containsKey(sizeOcean)) {
                        int value = dict.get(sizeOcean) * 2;
                        String oceanName = allOceans.get(value).getName();
                        String oceanLocation = allOceans.get(value).getCoordinates();
                        String oceanSize = Float.toString(allOceans.get(value).getOceanArea());
                        System.out.println("Ocean:" + oceanName + ", Coordinates: " + oceanLocation + ", Size (millions of sq Miles): " + oceanSize);

                    } else {
                        System.out.println("not a valid input");
                    }
                    break;


                case 5:
                    //Loop through allCountries array. If input region matches with country region field, AND country
                    //coastline is greater than the current value, store coastline and corresponding country in holder variables.
                    //
                    //After loop, largest coastline country in a region is known. Print out the respective percentage of population
                    //over 65 years for this respective country.
                    System.out.println("5.) In the country in _ with the largest coastline, \n " +
                            "what percentage of the population is 65 and over?");

                    String regPop = input.nextLine();
                    float coastTracker = -999;
                    Entry largestCoast = null;

                    for (Entry x: allCountries) {

                        if (x.getRegion().equalsIgnoreCase(regPop) && x.getCoastline() >= coastTracker) {

                            coastTracker = x.getCoastline();
                            largestCoast = x;


                        }
                    }

                    System.out.println("Country with largest coastline : "+ largestCoast.getName() + ",\n" +
                            "Coastline Length: " + largestCoast.getCoastline() + " km," + "\n" +
                            "% of population greater than 65 YO: " + largestCoast.getPopulation65() + "%");
                    break;
                case 6:
                    //Create an ArrayList of the plug types in the USA, hard coded.
                    //Initialize an ArrayList<Entry> to store all the country Entry of a certain region.
                    //Loop through allCountries. If country region value is same as inputted region, add to
                    //the initialized Array.
                    //
                    //Loop through all countries in Region ArrayList. Create a new ProcessEntry object specifically to call the
                    //parsePlugTypes() function. NOTE - Because this function navigates to another website, we only want to call it
                    //when necessary. Thus, it is only called on country Entry that are in the target region.
                    //
                    //For each country in the region, generate an ArrayList of the plug types available in this country.
                    //
                    //Remove plugs available in the US from all of these ArrayLists, to have the plugs not available in the US.
                    //
                    //If there are any plugs still remaining in the ArrayList, loop through them and call the helper function
                    //to create a Dictionary object to store the plug type and add the Country name to the value area of the dictionary.
                    //
                    //Helper function takes in a plug type and a country. Dictionary is structured as plug types as keys, and ArrayList<String>
                    //of country names as value. Create an ArrayList to store the country parameter. If a plug is not present in the Dictionary
                    // yet, put the plug and coutry ArrayList in the dictionary. If the plug type is already present, simply add the Country name
                    // to the corresponding value to the plug key.
                    //
                    // Print the completed dictionary once all countries in the region ArrayList have been looped through.
                    System.out.println("6.) Give a list of countries in _ that have a different Plug Type to USA.\n" +

                            "In your output, state the plug type and group countries together with the same plug type.");

                    String regionPlug = input.nextLine();

                    ArrayList<String> USAPlugs = new ArrayList<>();
                    USAPlugs.add("A");
                    USAPlugs.add("B");


                    ArrayList<Entry> Region = new ArrayList<Entry>();



                    for (Entry x : allCountries) {

                        if (x.getRegion().equalsIgnoreCase(regionPlug)) {

                            Region.add(x);

                        }

                    }

                    for (Entry x : Region) {

                        ProcessEntry process = new ProcessEntry(x);
                        process.parsePlugTypes();
                        ArrayList<String> plugs = x.getPlugTypes();

                        plugs.removeAll(USAPlugs);

                        if (!plugs.isEmpty()) {

                            for (String plug : plugs) {

                                addToPlugDict(x.getName(), plug);

                            }

                        }

                    }

                    for (String key : dictPlugs.keySet()) {
                        System.out.println(key + ": " + dictPlugs.get(key));
                    }
                    break;

                case 7:
                    //Set up dummy variables for the central country, ratio, and max ratio country.
                    //Loop through allCountries, and locate the country Entry that corresponds to the input country name.
                    //
                    //Extract the borderCountries ArrayList<String> from this central country.
                    //
                    //Loop through this borderCountries ArrayList, and for each entry, extract the corresponding Entry object for each coutnry
                    //name in the array.
                    //
                    //Loop through this array of Entry, extractig the corresponding sexRatio field and compare to find the largest
                    //magnitude in absolute value from a difference of 1.


                    System.out.println("7.) Among all the countries that _ borders, which country has the most disproportionate sex\n" +
                            "ratio of the total population?");

                    String borderPop = input.nextLine();
                    Entry centralCountry = null;

                    float ratio = 0.00F;
                    Entry maxCountry = null;

                    for (Entry x : allCountries) {

                        if (x.getName().equalsIgnoreCase(borderPop)) {

                            centralCountry = x;

                        }
                    }

                    ArrayList<String> borderCountryStrings = centralCountry.getBorderCountries();
                    ArrayList<Entry> borderCountries = new ArrayList<>();


                    for (String x:  borderCountryStrings) {

                        if (Entry.getEntry(x) != null) {
                            Entry country = Entry.getEntry(x);
                            borderCountries.add(country);
                        }

                    }

                    for (Entry x : borderCountries) {

                        if (abs(x.getSexRatio() - 1) > ratio) {



                            ratio = abs(x.getSexRatio() - 1);
                            maxCountry = x;


                        }
                    }

                    System.out.println("Country bordering: " + centralCountry.getName() + "\n" +

                            "Country with most disproporationate ratio: " + maxCountry.getName() + "\n" +

                            "Sex ratio:" + maxCountry.getSexRatio());

                    break;


                case 8:
                    //Loop through allCountries array. For countries that match the Region in the input text, and have a lower electrification
                    //rate than the dummy variable, replace the value and country Entry.
                    //
                    //After looping, output coutry, region, and respective electrification rate.


                    System.out.println("8.) Lowest Electrification Country in the Region _");

                    String regionElec = input.nextLine();

                    float elecRate = 100;
                    Entry lowestElec = null;

                    for (Entry x : allCountries) {

                        if (x.getRegion().equalsIgnoreCase(regionElec) && x.getElectricity() < elecRate) {

                            elecRate = x.getElectricity();
                            lowestElec = x;


                        }

                    }

                    System.out.println("Lowest Electrification Rate in: "  + lowestElec.getRegion() + "\n" +

                            "Country: " + lowestElec.getName() + "\n" +

                            "Electrification Rate: " + lowestElec.getElectricity());

                    break;
                case 9:

                    System.out.println("Closing Program...");
                    running = false;
            }


        }

        input.close();
    }
}