import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Class created to store information about an individual ocean or country. Fields correspond to variables or
// data of interest for the program.

public class Entry {

    private static Map<String, Entry> entryMap = new HashMap<>();
    private String name;
    private String url;
    private String lowestPoint;
    private int area;
    private String coordinates;

    private String flagFeatures;

    private ArrayList<String> borderCountries;

    private String region;

    private float oceanArea;

    private float coastline;

    private float population65;

    private float sexRatio;

    private float electricity;

    private ArrayList<String> plugTypes;
    public Entry(String name, String url) {

        this.name = name;
        this.url = url;
        entryMap.put(name,this);
    }



    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    
    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public String getLowestPoint() {return lowestPoint;}

    public void setLowestPoint(String lowestPoint) {this.lowestPoint = lowestPoint;}

    public int getArea() {return area;}

    public void setArea(int area) {this.area = area;}

    public String getCoordinates() {return coordinates;}

    public void setCoordinates(String coordinates) {this.coordinates = coordinates;}

    public void setFlagFeature(String features) { this.flagFeatures = features; }

    public String getFlagFeature() { return flagFeatures; }

    public void setBorderCountries(ArrayList<String> borderCountries) {this.borderCountries = borderCountries;}

    public ArrayList<String> getBorderCountries() {return borderCountries;}

    public void setRegion(String region) {this.region = region;}
    public String getRegion() {return region;}

    public float getOceanArea() {return oceanArea;}

    public void setOceanArea(float area) {this.oceanArea = area;}

    public float getCoastline() {return coastline;}

    public void setCoastline(float coastline) {this.coastline = coastline;}

    public float getPopulation65() {return population65;}

    public void setPopulation65(float pop) {this.population65 = pop;}

    public float getSexRatio() {return sexRatio;}

    public void setSexRatio(float ratio) {this.sexRatio = ratio;}

    public ArrayList<String> getPlugTypes (){return plugTypes;}

    public void setPlugTypes(ArrayList<String> plugs) {this.plugTypes = plugs;}

    public float getElectricity() {return electricity;}

    public void setElectricity(float electricity) {this.electricity = electricity;}

    public static Entry getEntry(String name) {

        return entryMap.get(name);
    }


}
