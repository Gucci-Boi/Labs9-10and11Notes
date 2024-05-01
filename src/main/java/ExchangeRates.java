import kong.unirest.Unirest;

import java.util.Map;
import java.util.Objects;

import static input.InputUtils.doubleInput;

public class ExchangeRates {
    public static void main(String[] args) {
        String url = "https://exchange-rates-1150.herokuapp.com/latest";
        Map<String, Object> queryParameters = Map.of("base", "USD", "symbols", "EUR");
        RateResponse response = Unirest.get(url)
                .queryString(queryParameters)
                .asObject(RateResponse.class)
                .getBody();

        String dateRequested = response.date;
        double rate = response.rates.EUR;
        double amountOfDollars = doubleInput("How many US Dollars would you like to convert to Euros? ");
        double euroEquivalent = amountOfDollars * rate;
        System.out.println("The exchange rate between USD and EUR on " + dateRequested + " is " + rate);
        System.out.println("$" + amountOfDollars + " is equivalent to " + euroEquivalent + " Euros.");
    }
}

class RateResponse {
    public String base;
    public String date;
    public Rates rates;

}

class Rates {
    public double EUR;
}