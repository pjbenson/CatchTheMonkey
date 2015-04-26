package com.spring.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")
public class PieChartData {

    private static final List<KeyValue> pieDataList;

    static {
        pieDataList = new ArrayList<PieChartData.KeyValue>();
        pieDataList.add(new KeyValue(new Date(2014, 0, 0), 10, 20, 5));
        pieDataList.add(new KeyValue(new Date(2014, 1, 0), 5, 11, -10));
        pieDataList.add(new KeyValue(new Date(2014, 2, 0), -6, 25, 10));
//        pieDataList.add(new KeyValue("Russia", "17098242"));
//        pieDataList.add(new KeyValue("Canada", "9984670"));
//        pieDataList.add(new KeyValue("USA", "9826675"));
//        pieDataList.add(new KeyValue("China", "9596961"));
//        pieDataList.add(new KeyValue("Brazil", "8514877"));
//        pieDataList.add(new KeyValue("Australia", "7741220"));
//        pieDataList.add(new KeyValue("India", "3287263"));
    }

    public static List<KeyValue> getPieDataList() {
        return pieDataList;
    }

    public static class KeyValue {
        Date key;
        Integer value;
        Integer value2;
        Integer value3;

        public KeyValue(Date key, Integer value, Integer value2, Integer value3) {
            super();
            this.key = key;
            this.value = value;
            this.value2 = value2;
            this.value3 = value3;
        }

        public Date getKey() {
            return key;
        }

        public void setKey(Date key) {
            this.key = key;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

		public Integer getValue2() {
			return value2;
		}

		public void setValue2(Integer value2) {
			this.value2 = value2;
		}

		public Integer getValue3() {
			return value3;
		}

		public void setValue3(Integer value3) {
			this.value3 = value3;
		}

    }

}
