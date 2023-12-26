package com.example.tobeytravelplanner.objects;

import java.util.HashMap;

public class HotelSearchResult {
    private String HotelBookingCode;
    private HashMap<Object,Object>HotelInfo;
    private HashMap<Object,Object>MinHotelPrice;
    boolean IsPkgProperty;
    boolean IsPackageRate;
    boolean MappedHotel;
    boolean IsHalal;

    public String getHotelBookingCode() {
        return HotelBookingCode;
    }

    public void setHotelBookingCode(String hotelBookingCode) {
        HotelBookingCode = hotelBookingCode;
    }

    public HashMap<Object, Object> getHotelInfo() {
        return HotelInfo;
    }

    public void setHotelInfo(HashMap<Object, Object> hotelInfo) {
        HotelInfo = hotelInfo;
    }

    public HashMap<Object, Object> getMinHotelPrice() {
        return MinHotelPrice;
    }

    public void setMinHotelPrice(HashMap<Object, Object> minHotelPrice) {
        MinHotelPrice = minHotelPrice;
    }

    public boolean isPkgProperty() {
        return IsPkgProperty;
    }

    public void setPkgProperty(boolean pkgProperty) {
        IsPkgProperty = pkgProperty;
    }

    public boolean isPackageRate() {
        return IsPackageRate;
    }

    public void setPackageRate(boolean packageRate) {
        IsPackageRate = packageRate;
    }

    public boolean isMappedHotel() {
        return MappedHotel;
    }

    public void setMappedHotel(boolean mappedHotel) {
        MappedHotel = mappedHotel;
    }

    public boolean isHalal() {
        return IsHalal;
    }

    public void setHalal(boolean halal) {
        IsHalal = halal;
    }
}
