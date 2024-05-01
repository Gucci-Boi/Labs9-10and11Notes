import kong.unirest.Unirest;

public class BusTimeTable {
    public static void main(String[] args) {
        String busTimeURL = "https://svc.metrotransit.org/NexTrip/17940?format=json";
        Bus[] buses = Unirest.get(busTimeURL).asObject(Bus[].class).getBody();

        String busTableTemplate = "%-10s %-40s %-20s\n";
        System.out.printf(busTableTemplate, "Route", "Description", "Arrival Time");
        for (Bus bus: buses) {
            System.out.printf(busTableTemplate, bus.Route, bus.Description, bus.DepartureText);
        }

    }
}
class Bus {
    public String DepartureText;
    public String Route;
    public String Description;
}