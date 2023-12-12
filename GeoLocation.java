import java.util.Random;

public class GeoLocation {
    private double lat;
    private double lon;
    private static int numLocations = 0;

    // Konstruktorius be parametru
    public GeoLocation() {
        this.lat = roundTo6DecimalPlaces(randomDoubleInRange(-90, 90));
        this.lon = roundTo6DecimalPlaces(randomDoubleInRange(-180, 180));
        numLocations++;
    }

    // Konstruktorius su parametrais
    public GeoLocation(double lat, double lon) {
        this.lat = roundTo6DecimalPlaces(lat);
        this.lon = roundTo6DecimalPlaces(lon);
        numLocations++;
    }

    // Kopijavimo konstruktorius
    public GeoLocation(GeoLocation location) {
        this.lat = roundTo6DecimalPlaces(location.lat + randomDoubleInRange(-0.1, 0.1));
        this.lon = roundTo6DecimalPlaces(location.lon + randomDoubleInRange(-0.1, 0.1));
        numLocations++;
    }

    // atspausdinimo metodas
    public void print() {
        System.out.println("[" + lat + ", " + lon + "]");
    }

    // Atstumo skaiciavimas naudojant Haversino formule
    public static double distance(GeoLocation location1, GeoLocation location2) {
        double R = 6371.0;
        double dLat = Math.toRadians(location2.lat - location1.lat);
        double dLon = Math.toRadians(location2.lon - location1.lon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(location1.lat)) * Math.cos(Math.toRadians(location2.lat)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return roundTo1DecimalPlace(R * c);
    }

    // grazina reiksme, esancia klases statiniame kintamajame numLocations.leidzia isores kodui gauti informacija kiek GeoLocation objektu buvo sukurta
    public static int getNumLocations() {
        return numLocations;
    }

    // atsitiktins slankiojo kablelio skaicius nuo 0.0 iki 1.0
    private static double randomDoubleInRange(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    // kad gauti 6 skaicius po kablelio
    private static double roundTo6DecimalPlaces(double value) {
        return Math.round(value * 1e6) / 1e6;
    }

    // apvalinimas iki vienos dalies po kablelio
    private static double roundTo1DecimalPlace(double value) {
        return Math.round(value * 10.0) / 10.0;
    }


    public static void main(String[] args) {
        GeoLocation someLocation = new GeoLocation();
        GeoLocation vilnius = new GeoLocation(54.683333, 25.283333);
        GeoLocation kaunas = new GeoLocation(54.897222, 23.886111);
        GeoLocation nearVilnius = new GeoLocation(vilnius);

        someLocation.print();
        vilnius.print();
        nearVilnius.print();

        System.out.println("Number of locations: " + GeoLocation.getNumLocations());

        System.out.println("From Vilnius to Kaunas: " + GeoLocation.distance(vilnius, kaunas));
        System.out.println("From Vilnius to random location: " + GeoLocation.distance(vilnius, someLocation));
        System.out.println("From Vilnius to random neighbour: " + GeoLocation.distance(vilnius, nearVilnius));
    }
}