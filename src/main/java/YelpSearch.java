import kong.unirest.Unirest;

import java.util.Map;

import static input.InputUtils.stringInput;

public class YelpSearch {
    public static void main(String[] args) {
        String yelpURL = "https://api.yelp.com/v3/businesses/search";
        String YELP_API_KEY = System.getenv("YELP_API_KEY");

        String typeOfRestaurant = stringInput("What type of restaurant would you like to look for? ");

        Map<String, Object> yelpQuery = Map.of(
                "term", "pizza", // type of restaurant
                "location", "Minneapolis, MN", // place/area
                "categories", "restaurants",
                "price", "1");  // lowest price

        YelpResponse response = Unirest.get(yelpURL)
                .header("Authorization", "Bearer " + YELP_API_KEY)
                .queryString(yelpQuery)
                .asObject(YelpResponse.class)
                .getBody();

        System.out.println(response);
        for (Business bus: response.businesses) {
            System.out.println(bus.name);
            System.out.println("Rating: " + bus.rating);
            System.out.println(bus.location.address1 + ", " + bus.location.city);
        }


    }
}
