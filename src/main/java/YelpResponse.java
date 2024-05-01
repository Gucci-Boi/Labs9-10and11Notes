// YelpAPI response classes to map JSON to Java objects
public class YelpResponse {
    public  Business[] businesses;
}
class Business {
    // these variables represent one business object
    public String name;
    public double rating;
    public Location location;
}
class Location {
    public String city;
    public String address1;

}