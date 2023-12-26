package com.example.tobeytravelplanner.objects;

import com.google.gson.Gson;

import java.util.List;

public class HotelInfo {

    private String HotelCode;
    private String HotelName;
    private String Description;
    private List<String> HotelFacilities;
    private List<String> Images;
    private String Address;
    private String PinCode;
    private String CityId;
    private String CountryName;
    private String PhoneNumber;
    private String FaxNumber;
    private String Map;
    private int HotelRating;
    private String CityName;
    private String CountryCode;
    private String CheckInTime;
    private String CheckOutTime;

    public static HotelInfo fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, HotelInfo.class);
    }

    public String getHotelCode() {
        return HotelCode;
    }

    public void setHotelCode(String hotelCode) {
        HotelCode = hotelCode;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<String> getHotelFacilities() {
        return HotelFacilities;
    }

    public void setHotelFacilities(List<String> hotelFacilities) {
        HotelFacilities = hotelFacilities;
    }

    public List<String> getImages() {
        return Images;
    }

    public void setImages(List<String> images) {
        Images = images;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return FaxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        FaxNumber = faxNumber;
    }

    public String getMap() {
        return Map;
    }

    public void setMap(String map) {
        Map = map;
    }

    public int getHotelRating() {
        return HotelRating;
    }

    public void setHotelRating(int hotelRating) {
        HotelRating = hotelRating;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getCheckInTime() {
        return CheckInTime;
    }

    public void setCheckInTime(String checkInTime) {
        CheckInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return CheckOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        CheckOutTime = checkOutTime;
    }

    @Override
    public String toString() {
        return "HotelInfo{" +
                "HotelCode='" + HotelCode + '\'' +
                ", HotelName='" + HotelName + '\'' +
                ", Description='" + Description + '\'' +
                ", HotelFacilities=" + HotelFacilities +
                ", Images=" + Images +
                ", Address='" + Address + '\'' +
                ", PinCode='" + PinCode + '\'' +
                ", CityId='" + CityId + '\'' +
                ", CountryName='" + CountryName + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", FaxNumber='" + FaxNumber + '\'' +
                ", Map='" + Map + '\'' +
                ", HotelRating=" + HotelRating +
                ", CityName='" + CityName + '\'' +
                ", CountryCode='" + CountryCode + '\'' +
                ", CheckInTime='" + CheckInTime + '\'' +
                ", CheckOutTime='" + CheckOutTime + '\'' +
                '}';
    }
}
