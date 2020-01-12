package sample.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import sample.Models.SearchForm;
import sample.Models.Attraction;
import sample.Models.Destination;

public class AttractionRecommender {

    public static final int DEFAULT_RADIUS = 7_500;
    public static final String EMPTY_IMAGE_URL = "https://www.logistec.com/wp-content/uploads/2017/12/placeholder.png";

    private static final HashMap<Attraction.Category, String> categorySubcategoryMappings = new HashMap<>();  // TODO Temporary hard-coding
    private static final HashMap<String, Duration> subcategoryDurationMappings = new HashMap<>();
    private static final HashMap<String, Double> subcategoryPriceMapping = new HashMap<>();

    public static void main(String[] args) {
        SearchForm sf = new SearchForm();
        sf.setBudget(10000);
        sf.setDepartureDate(LocalDate.now());
        sf.setReturnDate(LocalDate.now().plus(Period.ofDays(3)));
        sf.setOrigin("asdfasdf");  // TODO actually use this
        sf.addSelectedCategory(Attraction.Category.ENTERTAINMENT);

        Destination dest = new Destination("Kyiv");

        System.out.println(recommendAttractions(sf, dest));
    }

    public static HashMap<Attraction.Category, List<Attraction>> recommendAttractions(
            SearchForm searchForm, Destination destination) {

        String apiKey = KeyTool.getApiKey();

        populateCategorySubcategoryMappings();
        populateSubcategoryDurationMappings();
        populateSubcategoryPriceMapping();

        HashMap<Attraction.Category, List<Attraction>> attractionRecommendations = new HashMap<>();

        for (Attraction.Category category : searchForm.getSelectedCategories()) {

            List<Attraction> attractionsWithCurrentCategory = getAttractionsWithCategory(
                    destination.getLatitude(),
                    destination.getLongitude(),
                    category,
                    categorySubcategoryMappings.get(category),
                    apiKey
            );

            attractionRecommendations.put(category, attractionsWithCurrentCategory);
        }

        return attractionRecommendations;
    }

    private static void populateCategorySubcategoryMappings() {
        categorySubcategoryMappings.put(Attraction.Category.ENTERTAINMENT, "movie_theater");
        categorySubcategoryMappings.put(Attraction.Category.MUSEUM, "museum");
        categorySubcategoryMappings.put(Attraction.Category.RELIGION, "church");
        categorySubcategoryMappings.put(Attraction.Category.OUTDOOR_PARK, "park");
        categorySubcategoryMappings.put(Attraction.Category.NIGHTLIFE, "casino");
        categorySubcategoryMappings.put(Attraction.Category.SHOPPING, "shopping_mall");
    }

    private static void populateSubcategoryDurationMappings() {
        subcategoryDurationMappings.put("movie_theater", Duration.ofMinutes(150));
        subcategoryDurationMappings.put("museum", Duration.ofMinutes(180));
        subcategoryDurationMappings.put("church", Duration.ofMinutes(60));
        subcategoryDurationMappings.put("park", Duration.ofMinutes(240));
        subcategoryDurationMappings.put("casino", Duration.ofMinutes(240));
        subcategoryDurationMappings.put("shopping_mall", Duration.ofMinutes(240));
    }

    private static void populateSubcategoryPriceMapping() {
        subcategoryPriceMapping.put("movie_theater", 12.0);
        subcategoryPriceMapping.put("museum", 21.0);
        subcategoryPriceMapping.put("church", 16.0);
        subcategoryPriceMapping.put("park", 10.0);
        subcategoryPriceMapping.put("casino", 50.0);
        subcategoryPriceMapping.put("shopping_mall", 0.0);
    }

    private static List<Attraction> getAttractionsWithCategory(double lat, double lng, Attraction.Category category,
                                                               String subCategory, String apiKey) {
         String url = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=%f,%f&radius=%d&type=%s&key=%s",
                lat,
                lng,
                DEFAULT_RADIUS,
                subCategory,
                apiKey
         );

         JSONObject response;
        try {
            response = jsonReader(url);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error calling Google APIs :(");
        }

        JSONArray arrayOfResults = (JSONArray) response.get("results");
        List<Attraction> listOfAttractions = new ArrayList<>();
        for (Object attractionObj : arrayOfResults) {
            JSONObject jsonAttraction = (JSONObject) attractionObj;

            JSONArray images = (JSONArray) jsonAttraction.get("photos");

            String imageId;
            if (images == null || images.size() == 0) imageId = null;
            else imageId = (String) (((JSONObject) images.get(0)).get("photo_reference"));

            // TODO add rating data (1-5) which is available w/ jsonAttraction.get("rating")
            Attraction attraction = new Attraction(
                    (String) jsonAttraction.get("vicinity"),
                    (String) jsonAttraction.get("name"),
                    imageId,
                    (double) ((JSONObject) (((JSONObject) (jsonAttraction.get("geometry"))).get("location"))).get("lat"),
                    (double) ((JSONObject) (((JSONObject) (jsonAttraction.get("geometry"))).get("location"))).get("lng"),
                    subcategoryPriceMapping.get(subCategory),  // TODO temporary hard coding
                    category,
                    subCategory,
                    subcategoryDurationMappings.get(subCategory)
            );
            listOfAttractions.add(attraction);
        }

        return listOfAttractions;
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
