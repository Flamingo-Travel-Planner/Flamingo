package sample.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchForm {

    private int budget;

    private String origin;

    private LocalDate departureDate, returnDate;

    private List<Attraction.Category> selectedCategories = new ArrayList<>();

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public List<Attraction.Category> getSelectedCategories() {
        return new ArrayList<>(selectedCategories);  // Return a shallow copy to make immutable
    }

    public void addSelectedCategory(Attraction.Category toAdd) {
        selectedCategories.add(toAdd);
    }

    public int getNumSelectedCategories() {
        return selectedCategories.size();
    }
}
