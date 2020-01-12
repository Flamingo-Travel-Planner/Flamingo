package sample.Models;

import java.time.LocalDate;

public class SearchForm {

    private int budget;

    private String origin;

    private LocalDate departureDate, returnDate;

    private boolean museums, nightlife, religion, shopping, entertainment, outdoor;

    public int getBudget() {
        return budget; }

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

    public boolean isMuseums() {
        return museums;
    }

    public void setMuseums(boolean museums) {
        this.museums = museums;
    }

    public boolean isNightlife() {
        return nightlife;
    }

    public void setNightlife(boolean nightlife) {
        this.nightlife = nightlife;
    }

    public boolean isReligion() {
        return religion;
    }

    public void setReligion(boolean religion) {
        this.religion = religion;
    }

    public boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    public boolean isEntertainment() {
        return entertainment;
    }

    public void setEntertainment(boolean entertainment) {
        this.entertainment = entertainment;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
    }
}
