package com.example.wmshw;

import com.example.wmshw.model.ViolationCard;

import java.util.ArrayList;
import java.util.List;

public class ViolationsListData {
    private static List<ViolationCard> list = new ArrayList<>();

    public static List<ViolationCard> getList() {
        return list;
    }

    public static void setList(List<ViolationCard> list) {
        ViolationsListData.list = list;
    }

    public static class SearchCriteria {
        private static String plugedNumber;
        private static String driver;
        private static String location;
        private static String fromDate;
        private static String toDate;

        public static String getPlugedNumber() {
            return plugedNumber;
        }

        public static void setPlugedNumber(String plugedNumber) {
            SearchCriteria.plugedNumber = plugedNumber;
        }

        public static String getDriver() {
            return driver;
        }

        public static void setDriver(String driver) {
            SearchCriteria.driver = driver;
        }

        public static String getLocation() {
            return location;
        }

        public static void setLocation(String location) {
            SearchCriteria.location = location;
        }

        public static String getFromDate() {
            return fromDate;
        }

        public static void setFromDate(String fromDate) {
            SearchCriteria.fromDate = fromDate;
        }

        public static String getToDate() {
            return toDate;
        }

        public static void setToDate(String toDate) {
            SearchCriteria.toDate = toDate;
        }
    }

}
