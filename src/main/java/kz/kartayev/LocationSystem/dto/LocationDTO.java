package kz.kartayev.LocationSystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class LocationDTO {
    @NotEmpty
    @Pattern(regexp = "^\\d+\\s*[A-Z]*\\s[A-Za-z]+,\\s*[A-Za-z]+,\\s*[A-Za-z]+$", message = "FORMAT: Address, City, Country")
    private String location;

    private int owner;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
