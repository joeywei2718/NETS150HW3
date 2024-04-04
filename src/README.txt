Joey Wei
NETS 1500
HW - 3, CIA Factbook Web Crawler

Part 1: Theory

    1.) Yes, Java does have a URI class.
        - A URI class is used to identify any type of digital resource, whether on the internet or not. It serves as an
        identifier. A URL class is a subset of URI, that also includes information on where it is to be accessed, or a digital location tag. This usually
        includes a protocol, such as HTTP or HTTPS

        - URI's cannot be changed after they are created, since they are tied to the identity of the resource. URL's can
        be changed, since they are locators, the location of the object that they target can change, and thus, the URL itself can change as well.

    2.) In this case, the compiler will throw a MalformedURLException, because the given url will not pass the constructor because it does not have a protocol
        component to the URL. The constructor will typically expect an "http://" or "https://" at the beginning of the URL, and when this is missing, it will throw an exception.


    3.)

        public String getRedirectURL() {
                try {
                    int responseCode = httpConnection.getResponseCode();

                    //check if the response code is in range
                    if (responseCode >= 300) {
                        //get redirected URL from the location header
                        String redirectedURL = httpConnection.getHeaderField("Location");
                        System.out.println(redirectedURL);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null; // Return null if there was no redirection
            }


       This code checks a URL's response code, and if it returns a redirect code, extracts the redirected URL from the "location"
       tag of the header

2.) Programming

======================
HOW TO USE:
======================
Run the Main.java module.

The program will then begin to scrape the websites.
The program then will print the websites on CDC Reference page that it was not able to reach. These correspond to links that lead to a 404 on the website.
When the program is done scraping, the question interface will appear.

Enter a number to select a specific question.

The question will be printed. Input your desired parameter to the question.

Program solution will be printed, followed by the question interface.
!!!!!!ANSWER WILL APPEAR ABOVE THE QUESTION INTERFACE WHEN PRINTED!!!!

Enter number for next question to be answered.

Program overall approach:

The functional process of the program is as follows. First, the program navigates to Home page and assembles allOceans
ArrayList, with just the Name and URL.

Then, it navigates to Country Data Codes, and assembles the allCountries ArrayList. However, not all links on the
CDC website contain active URLs, so rows without a corresponding URL found are printed when assembling the allCountries.
Similar to allOceans, the entries are just Name and URL.

Then, the allOceans and allCountries are scraped for the target information using the methods found in ProcessEntry class.
After being processed, all the necessary data fields in each Entry class are filled and available. Processing algorithms
are split into ocean and country, to make code more efficient and reduce unnecessary method calls.

From this, the Main method can extract the data from each Entry and answer the questions accordingly.

The Main method then prompts the users to ask questions and performs calculations from the fully filled allCountries and allOceans
ArrayLists.

Program overall approach:

The functional process of the program is as follows. First, the program navigates to Home page and assembles allOceans
ArrayList, with just the Name and URL.

Then, it navigates to Country Data Codes, and assembles the allCountries ArrayList. However, not all links on the
CDC website contain active URLs, so rows without a corresponding URL found are printed when assembling the allCountries.
Similar to allOceans, the entries are just Name and URL.

Then, the allOceans and allCountries are scraped for the target information using the methods found in ProcessEntry class.
After being processed, all the necessary data fields in each Entry class are filled and available. Processing algorithms
are split into ocean and country, to make code more efficient and reduce unnecessary method calls.

From this, the Main method can extract the data from each Entry and answer the questions accordingly.

The Main method then prompts the users to ask questions and performs calculations from the fully filled allCountries and allOceans
ArrayLists.

ProcessEntry Method Algorithms:

    ------
    parseDepth - Scrapes Ocean website for bold text "lowest point" and simply returns the
    subsequent string that follows the heading.
    ------
    parseCoordinates - Similar to parseDepth, searches for the link with text of "Geographic coordinates", navigates
    to target text. Sets text to the respective field in Entry class.
    ------
    parseSize - Scrapes for link with text "Area", navigates to target text, and uses regex
    pattern to extract numeric value of area. Sets float value to respective field in Entry class.
    For exceptions, sets area to 0 as a failsafe.
    ------
    parseFlag - Scrapes for link with text "Flag description". Navigates to html to target text.
    Stores available text to respective field in Entry class. Stores empty string if any catches are triggered
    ------
    parseRegion - Scrapes for link with text "Map references". Navigates html to target text. Sets
    respective field to available text. Stores empty string if exception is triggered
    ------
    parseBorderNations - Scrapes for bold text "border countries". Navigate to target string.
    Use regex pattern match to match country names. While loop, add matched country names to an ArrayList.
    Store ArrayList to respective Entry field. Store empty array in exceptions.
    ------
    parseCoastline - Scrapes for link with text "Coastline". Navigates html to target text.
    Use regex to pattern match the numeric coastline value. Remove all non numeric values, and store as float.
    Store float to respective Entry field. Set to 0 for exceptions.
    ------
    parsePopulation65 - Scrapes for first instance of bold text "65 years and over". Uses regex pattern matching
    to find corresponding percentage string. Converted to float and saved to respective Entry field. Set to 0
    for exceptions.
    ------
    parseSexRatio - Scrapes for first instance of bold text "total population". Navigates html to target text.
    Uses regex pattern match to find decimal ratio value. Parsed to float and saved to respective value in
    Entry field. Exceptions set to value of 1.0
    ------
    parsePlugTypes - Scrapes for link "Travel Facts". Saves this link as hyperlink.
    Uses JSoup to connect to this site, and save as a Document.

    Scrape this site for p tag of text "plug types(s)". Regex pattern match used to scrape string
    immediately following this text.
    String is split by commas and saved into type ArrayList<String> to list all plug types used in this
    country. Resulting ArrayList is saved into respective Entry field. Exceptions saved as empty ArrayLists
    ------
    parseElectricity - Scrapes for bold text containing "electrification - total population".
    Navigates to target text, and uses regex patten match to extract numeric value.
    Value saved as float and stored to respective Entry field.

Main Method Algorithms:

    ======
    1.) Input ocean name is compared against all ocean names in allOceans Array. When match to existing ocean Entry is found,
    lowest point is printed.
    ======
    2.) Search term is saved as string. Loop through allCountries, and see if search term is present in
    country's flag description field. If yes, print name.
    ======
    3.) Track smallest number of border countries and corresponding country iteratively.
    Loop through allCountries. If region field matches the input field, AND the size of the borderCountries array is smaller
    than the current smallest size, replace counter LeastBorders field and spaceholder countryName field to current country.

    Resulting values will be the country with the least border countries in a certain region.
    ======
    4.) Sort allOceans array by respective Area field in Entry class.
    Create dictionary to store ordinal values and corresponding integer values.
    Reference ordinal string to find integer value. Use integer value to access desired index in the
    sorted allOceans ArrayList. Print the Name, Size, and Coordinates of the target ocean.
    ======
    5.) Loop through allCountries array. If input region matches with country region field, AND country
    coastline is greater than the current value, store coastline and corresponding country in holder variables.

    After loop, largest coastline country in a region is known. Print out the respective percentage of population
    over 65 years for this respective country.
    ======
    6.) Create an ArrayList of the plug types in the USA, hard coded.
    Initialize an ArrayList<Entry> to store all the country Entry of a certain region.
    Loop through allCountries. If country region value is same as inputted region, add to
    the initialized Array.

    Loop through all countries in Region ArrayList. Create a new ProcessEntry object specifically to call the
    parsePlugTypes() function. NOTE - Because this function navigates to another website, we only want to call it
    when necessary. Thus, it is only called on country Entry that are in the target region.

    For each country in the region, generate an ArrayList of the plug types available in this country.

    Remove plugs available in the US from all of these ArrayLists, to have the plugs not available in the US.

    If there are any plugs still remaining in the ArrayList, loop through them and call the helper function
    to create a Dictionary object to store the plug type and add the Country name to the value area of the dictionary.

    Helper function takes in a plug type and a country. Dictionary is structured as plug types as keys, and ArrayList<String>
    of country names as value. Create an ArrayList to store the country parameter. If a plug is not present in the Dictionary
     yet, put the plug and coutry ArrayList in the dictionary. If the plug type is already present, simply add the Country name
     to the corresponding value to the plug key.

     Print the completed dictionary once all countries in the region ArrayList have been looped through.
    ======
    7.) Set up dummy variables for the central country, ratio, and max ratio country.
    Loop through allCountries, and locate the country Entry that corresponds to the input country name.

    Extract the borderCountries ArrayList<String> from this central country.

    Loop through this borderCountries ArrayList, and for each entry, extract the corresponding Entry object for each coutnry
    name in the array.

    Loop through this array of Entry, extractig the corresponding sexRatio field and compare to find the largest
    magnitude in absolute value from a difference of 1.
    ======
    8.) Loop through allCountries array. For countries that match the Region in the input text, and have a lower electrification
    rate than the dummy variable, replace the value and country Entry.

    After looping, output coutry, region, and respective electrification rate.

Answers:
    1 - Puerto Rico Trench -8,605 m
    2 - Australia
        Bosnia and Herzegovina
        Brazil
        Burundi
        Cabo Verde
        Cayman Islands
        China
        Comoros
        Cook Islands
        Curacao
        Dominica
        Egypt
        El Salvador
        Equatorial Guinea
        French Polynesia
        Grenada
        Honduras
        Hong Kong
        Iraq
        Kosovo
        Macau
        Micronesia, Federated States of
        New Zealand
        Nicaragua
        Niue
        Papua New Guinea
        Philippines
        Saint Kitts and Nevis
        Samoa
        Sao Tome and Principe
        Singapore
        Slovenia
        Solomon Islands
        Syria
        Tajikistan
        Tokelau
        Turkmenistan
        Tuvalu
        United States
        Uzbekistan
        Venezuela
        Yemen
    3 - Taiwan, 0
    4 - Ocean:Atlantic Ocean, Coordinates: 0 00 N, 25 00 W, Size (millions of sq Miles): 85.133
    5 - Country with largest coastline : Brazil,
        Coastline Length: 7491.0 km,
        % of population greater than 65 YO: 10.51%
    6 - C: [Algeria, Angola, Burkina Faso, Burundi, Cabo Verde, Cameroon, Central African Republic, Chad, Comoros, Congo, Democratic Republic of the, Congo, Republic of the, Cote d'Ivoire, Djibouti, Egypt, Equatorial Guinea, Eritrea, Ethiopia, Gabon, Guinea, Guinea-Bissau, Liberia, Libya, Madagascar, Mali, Mauritania, Mauritius, Morocco, Mozambique, Rwanda, Sao Tome and Principe, Senegal, Somalia, South Sudan, Sudan, Togo, Tunisia, Zambia]
        D: [Botswana, Ghana, Namibia, Niger, Nigeria, Senegal, Sierra Leone, South Africa, South Sudan, Sudan, Tanzania, Zambia, Zimbabwe]
        E: [Burkina Faso, Burundi, Cameroon, Central African Republic, Chad, Comoros, Congo, Democratic Republic of the, Congo, Republic of the, Cote d'Ivoire, Djibouti, Equatorial Guinea, Madagascar, Mali, Morocco, Senegal, Tunisia]
        F: [Algeria, Angola, Cabo Verde, Chad, Egypt, Ethiopia, Guinea, Liberia, Mozambique, Sao Tome and Principe, Senegal]
        G: [Benin, Botswana, Ethiopia, Gambia, The, Ghana, Kenya, Malawi, Mauritius, Niger, Nigeria, Seychelles, Sierra Leone, Tanzania, Uganda, Zambia, Zimbabwe]
        J: [Rwanda]
        K: [Guinea]
        L: [Eritrea, Libya]
        M: [Eswatini, Lesotho, Mozambique, Namibia, South Africa]
        N: [South Africa]
    7 - Country bordering: Russia
        Country with most disproporationate ratio: Lithuania
        Sex ratio:0.86
    8 - Lowest Electrification Rate in: Africa
        Country: South Sudan
        Electrification Rate: 7.7



