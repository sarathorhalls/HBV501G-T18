package hi.HBV501G.kritikin.persistence.entites.DTOs;

public class CompanyDTO {
    private String name;
    private String website;
    private int phoneNumber;
    private String description;
    private String address;
    private String openingHours;

    public CompanyDTO() {
    }

    public CompanyDTO(String name, String website, int phoneNumber, String description, String address, String openingHours) {
        this.name = name;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.address = address;
        this.openingHours = openingHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
