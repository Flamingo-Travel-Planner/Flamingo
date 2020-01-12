package sample.Models;

import java.util.List;

public class AppData {

    private int budget;

    private List<Destination> recommendationsList;

    private Destination selectedTrip;

    private SearchForm searchForm;

    public int getBudget() { return budget; }

    public void setBudget(int budget) { this.budget = budget; }

    public List<Destination> getRecommendationsList() {
        return recommendationsList;
    }

    public void setRecommendationsList(List<Destination> recommendationsList) { this.recommendationsList = recommendationsList; }

    public Destination getSelectedTrip() {
        return selectedTrip;
    }

    public void setSelectedTrip(Destination selectedTrip) {
        this.selectedTrip = selectedTrip;
    }

    public SearchForm getSearchForm() { return searchForm; }

    public void setSearchForm(SearchForm searchForm) { this.searchForm = searchForm; }
}
