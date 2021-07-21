package com.example.myapplication.ultility;

public class CheckConversion {
    public static boolean convertToInteger(String s){
        try {
            int result = Integer.parseInt(s);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
