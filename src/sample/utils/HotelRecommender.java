package sample.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.Models.Attraction;
import sample.Models.Destination;
import sample.Models.Hotel;
import sample.Models.SearchForm;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotelRecommender {
    public static final String TYPE = "lodging";
    public static final int DEFAULT_RADIUS = 15_000;
    public static final int MAX_IMAGE_DIMENSION = 600;
    public static final String EMPTY_IMAGE_URL = "../media/placeholder.jpg";

    public static void main(String[] args) {
        SearchForm sf = new SearchForm();
        sf.setBudget(10000);
        sf.setDepartureDate(LocalDate.now());
        sf.setReturnDate(LocalDate.now().plus(Period.ofDays(3)));
        sf.setOrigin("asdfasdf");  // TODO actually use this
        sf.addSelectedCategory(Attraction.Category.ENTERTAINMENT);
        sf.addSelectedCategory(Attraction.Category.NIGHTLIFE);

        Destination dest = new Destination("toronto");

        HashMap<Attraction.Category, List<Attraction>> test = AttractionRecommender.recommendAttractions(sf, dest);

        System.out.println(recommendHotel(sf, dest, test));


    }

    public static List<Hotel> recommendHotel(SearchForm searchForm, Destination destination,
                                             HashMap<Attraction.Category, List<Attraction>> recommendedAttraction) {

        List<Attraction> selectedAttraction = new ArrayList<>();

        for (int i = 0; i < searchForm.getSelectedCategories().size(); i++) {
            selectedAttraction.addAll(recommendedAttraction.get(searchForm.getSelectedCategories().get(i)));
        }

        String apiKey = KeyTool.getApiKey();

        double totalLat = 0;
        double totalLng = 0;

        for (Attraction attraction : selectedAttraction) {
            totalLat += attraction.getLatitude();
            totalLng += attraction.getLongitude();
        }

        double averageLat = totalLat / selectedAttraction.size();
        double averageLng = totalLng / selectedAttraction.size();

        String url = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location=%f,%f&radius=%d&type=%s&key=%s",
                averageLat,
                averageLng,
                DEFAULT_RADIUS,
                TYPE,
                apiKey
        );

        return getHotels(url, apiKey);
    }

    public static List<Hotel> getHotels(String url, String apiKey) {
        JSONObject response;
        try {
            response = jsonReader(url);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error calling Google APIs :(");
        }

        JSONArray arrayOfResults = (JSONArray) response.get("results");
        List<Hotel> listOfHotels = new ArrayList<>();
        for (Object hotelObj : arrayOfResults) {
            JSONObject jsonHotel = (JSONObject) hotelObj;

            JSONArray images = (JSONArray) jsonHotel.get("photos");

            String imageUrl;
            if (images == null || images.size() == 0) {
                imageUrl = EMPTY_IMAGE_URL;
            }
            else {
                String imageId = (String) (((JSONObject) images.get(0)).get("photo_reference"));
                imageUrl = getImageUrl(imageId, apiKey);
            }

            // TODO add rating data (1-5) which is available w/ jsonAttraction.get("rating")
            Hotel hotel = new Hotel(
                    (String) jsonHotel.get("vicinity"),
                    (String) jsonHotel.get("name"),
                    imageUrl,
                    (double) ((JSONObject) (((JSONObject) (jsonHotel.get("geometry"))).get("location"))).get("lat"),
                    (double) ((JSONObject) (((JSONObject) (jsonHotel.get("geometry"))).get("location"))).get("lng"),
                    15 // TODO temporary hard coding
            );
            listOfHotels.add(hotel);
        }

        return listOfHotels;
    }

    private static String getImageUrl(String imageId, String apiKey) {
        return String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=%d&photoreference=%s&key=%s",
                MAX_IMAGE_DIMENSION, imageId, apiKey);
    }

    private static JSONObject jsonReader(String url) throws IOException, ParseException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return (JSONObject) new JSONParser().parse(jsonText);
        } finally {
            is.close();
        }
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
