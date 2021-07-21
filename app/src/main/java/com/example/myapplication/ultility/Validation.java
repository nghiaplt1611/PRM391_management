package com.example.myapplication.ultility;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Validation {
    /**
     * Create method checkNullData() to check whether the data are null
     *
     * @param strings storing all the strings need to be checked
     * @return the null status
     */
    public static boolean checkNullData(String[] strings){
        for (String s: strings){
            if (s.isEmpty()){
                return true;
            }
        }
        return false;
    }


    public static boolean checkFullNameFormat(String fullName){
        String specialCharacters = "~`!@#$%^&*()-_=+[{]}\\|;:'\"<>,./?*";
        for (int i = 0; i < fullName.length(); i++){
            if (specialCharacters.indexOf(fullName.charAt(i)) != -1 || (fullName.charAt(i) >= 48 && fullName.charAt(i) <= 57)){
                return false;
            }
        }
        return true;
    }

    public static boolean checkEmailFormat(String email){
        final String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return Pattern.matches(emailRegex, email);
    }


    public static boolean checkYearOfBirth(String yearOfBirth){
        if (!CheckConversion.convertToInteger(yearOfBirth))
            return false;

        int birthYear = Integer.parseInt(yearOfBirth);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return birthYear >= 1900 && birthYear <= currentYear;
    }

    public static boolean checkStrongPassword(String password){
        boolean checkDigit = false;
        boolean checkLowercase = false;
        boolean checkUppercase = false;
        boolean checkSpecialCharacter = false;

        if (password.length() < 8)
            return false;

        for (int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if (Character.isDigit(c))
                checkDigit = true;
            else if (Character.isLowerCase(c))
                checkLowercase = true;
            else if (Character.isUpperCase(c))
                checkUppercase = true;
            else if (!Character.isLetterOrDigit(c) && c != 32)
                checkSpecialCharacter = true;
        }
        return checkDigit && checkLowercase && checkUppercase && checkSpecialCharacter;
    }


    /**
     * Create method checkConfirmPassword() to check the similarity of password and confirm password
     *
     * @param password storing the password
     * @param confirmPassword storing the confirm password
     * @return the status of checking password and confirm password
     */
    public static boolean checkConfirmPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }


    public static boolean checkRegisterFormat(String email, String fullName, String yearOfBirth, String password, String confirmPassword){
        return checkEmailFormat(email) && checkFullNameFormat(fullName) && checkYearOfBirth(yearOfBirth) && checkStrongPassword(password) && checkConfirmPassword(password, confirmPassword);
    }

    public static boolean checkUpdateFormat(String fullName, String yearOfBirth){
        return checkFullNameFormat(fullName) && checkYearOfBirth(yearOfBirth);
    }

}

