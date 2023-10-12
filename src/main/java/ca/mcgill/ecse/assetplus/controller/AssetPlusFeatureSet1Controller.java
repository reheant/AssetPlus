package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet1Controller {
    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

    /**
     * Updates the password of the manager in the system.
     * @author Nicolas Bolouri
     * @param password The new password for the manager must be >=4 characters, contain !#$, an uppercase, and a lowercase letter.
     * @return A string message indicating the failure of the operation or an empty string if the operation was successful.
     */
    public static String updateManager(String password) {
        var error = "";
        error = assertManagerAvailable() + validateManagerPassword(password);

        if (!error.isEmpty()) {
            return error.trim();
        }

        try {
            assetPlus.getManager().setPassword(password);
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
        return "";
    }

    /**
     * Adds a new Employee or Guest to the AssetPlus system with the given details.
     * @author Nicolas Bolouri
     * @param email The email of the new user, following specific constraints for Employees and Guests.
     * @param password The password of the new user, cannot be null or empty.
     * @param name The name of the new user, cannot be null.
     * @param phoneNumber The phone number of the new user, cannot be null.
     * @param isEmployee A boolean indicating whether the new user is an Employee (true) or Guest (false).
     * @return A string message indicating the failure of the operation or an empty string if the operation was successful.
     */
    public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber, boolean isEmployee) {
        var error = "";
        error = validateEmployeeOrGuestEmail(email, isEmployee) + validatePassword(password) + validateName(name) + validatePhoneNumber(phoneNumber);

        if (!error.isEmpty()) {
            return error.trim();
        }

        try {
            if (isEmployee) {
                assetPlus.addEmployee(email, name, password, phoneNumber);
            } else {
                assetPlus.addGuest(email, name, password, phoneNumber);
            }
        } catch (Exception e) {
            return "An error occurred while adding the user: " + e.getMessage();
        }
        return "";
    }

    /**
     * Updates the details of an existing Employee or Guest in the AssetPlus system.
     * @author Nicolas Bolouri
     * @param email The email of the user to be updated.
     * @param newPassword The new password to be set for the user, cannot be null or empty.
     * @param newName The new name to be set for the user, cannot be null.
     * @param newPhoneNumber The new phone number to be set for the user, cannot be null.
     * @return A string message indicating the failure of the operation or an empty string if the operation was successful.
     */
    public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
        var error = "";
        error = validateEmployeeOrGuestEmail(email, null) + validatePassword(newPassword) + validateName(newName) + validatePhoneNumber(newPhoneNumber);

        if (!error.isEmpty()) {
            return error.trim();
        }

        try {
            User user = User.getWithEmail(email);
            if (user == null) {
                return "User not found.";
            }

            user.setPassword(newPassword);
            user.setName(newName);
            user.setPhoneNumber(newPhoneNumber);
        } catch (Exception e) {
            return "An error occurred while updating the user: " + e.getMessage();
        }
        return "";
    }

    private static String assertManagerAvailable() {
        return assetPlus.hasManager() ? "" : "Manager not found. ";
    }

    private static String validateEmployeeOrGuestEmail(String email, Boolean isEmployee) {
        if (email == null) {
            return "Email cannot be null. ";
        }
        
        if (email.contains(" ")) {
            return "Email must not contain spaces. ";
        }
        
        int atIndex = email.indexOf("@");
        int lastAtindex = email.lastIndexOf("@");
        int dotIndex = email.lastIndexOf(".");
        
        if (atIndex <= 0 || atIndex != lastAtindex || atIndex >= dotIndex - 1 || dotIndex >= email.length() - 1) {
            return "Invalid email format. ";
        }
    
        if (isEmployee != null) {
            if (isEmployee && !email.endsWith("@ap.com")) {
                return "Employee email domain must be @ap.com. ";
            } else if (!isEmployee && email.endsWith("@ap.com")) {
                return "Guest email domain cannot be @ap.com. ";
            }
        }

         if (email.equals("manager@ap.com")) {
            return "Employee or guest email cannot be equal to manager@ap.com. ";
        }
        
        return "";
    }

    private static String validatePassword(String password) {
        if (password == null) {
            return "Password cannot be null. ";
        }
        if (password.isEmpty()) {
            return "Password cannot be empty. ";
        }
        return "";
    }

    private static String validateManagerPassword(String password) {
        String error = validatePassword(password);

        if (!error.isEmpty()) {
            return error;
        }
        if (password.length() <= 3) {
            return "Password must be at least 4 characters long. ";
        }
        if (!password.matches(".*[!#$].*")) {
            return "Password must contain at least one of the special characters: !, #, $. ";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter. ";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter. ";
        }
        return "";
    }

    private static String validateName(String name) {
        return name != null ? "" : "Name cannot be null. ";
    }

    private static String validatePhoneNumber(String phoneNumber) {
        return phoneNumber != null ? "" : "Phone number cannot be null. ";
    }
}