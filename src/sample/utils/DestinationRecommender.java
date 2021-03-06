package sample.utils;

import javafx.util.Pair;
import sample.Models.Destination;
import sample.Models.SearchForm;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DestinationRecommender {

    public static final String[] CITIES = {"Toronto", "Havana", "Bogota", "London", "Tokyo", "Istanbul", "New York City",
                                            "Montreal", "Rio de Janeiro", "Las Vegas", "Kyiv"};

    private static double calculateTargetPrice(double budget, int numNights) {
        return (budget - 80 * numNights) * 0.53;
    }

    /**
     * Returns a List of Destinations sorted by their closeness to the optimal price of the plane ticket.
     * The optimal price is calculated based on the budget of the traveller and the number of nights stayed in a hotel.
     * @param searchForm
     * @return
     */
    public static List<Destination> recommendDestinations(SearchForm searchForm) {
        int numNights = (int) ChronoUnit.DAYS.between(searchForm.getDepartureDate(), searchForm.getReturnDate());
        double targetPrice = calculateTargetPrice(searchForm.getBudget(), numNights);

        Destination[] destinations = new Destination[CITIES.length];

        for (int i = 0; i < CITIES.length; i++) {
            destinations[i] = new Destination(CITIES[i]);
        }

        List<Pair<Destination, Double>> destinationsSortedByTargetPrice = Arrays.stream(destinations)
                .filter(destination -> destination.getPrice() <= searchForm.getBudget())
                .map(dest -> new Pair<Destination, Double>(dest, Math.abs(dest.getPrice() - targetPrice)))
                .sorted((p1, p2) -> (int)(p1.getValue() - p2.getValue()))
                .collect(Collectors.toList());

        return destinationsSortedByTargetPrice.stream()
                .map(pair -> pair.getKey())
                .collect(Collectors.toList());
    }

}
