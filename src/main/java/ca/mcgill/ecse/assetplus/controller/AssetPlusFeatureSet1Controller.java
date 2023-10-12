package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet1Controller {
    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

    /**
     * Updates the password of the manager in the system.
     * @author Nicolas Bolouri
     * @param password The new password for the manager must be >=4 characters, contain !#$, an uppercase, and a lowercase lette.r
     * @return A string message indicating the success or failure of the operation.
     */
    public static String updateManager(String password) {
        try {
            if (!isManagerAvailable() || !isValidPassword(password)) {
                return "Manager not found or invalid password.";
            }
            assetPlus.getManager().setPassword(password);
            return "Manager password updated successfully.";
        } catch (NullPointerException e) {
            return "Error: AssetPlus or Manager is not initialized.";
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    /**
     * Adds a new Employee or Guest to the AssetPlus system with the given details.
     * @author Nicolas Bolouri
     * @param email The email of the new user, following specific constraints for Employees and Guests.
     * @param password The password of the new user, cannot be null or empty.
     * @param name The name of the new user, cannot be null.
     * @param phoneNumber The phone number of the new user, cannot be null.
     * @param isEmployee A boolean indicating whether the new user is an Employee (true) or Guest (false).
     * @return A string message indicating the success or failure of the operation.
     */
    public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber, boolean isEmployee) {
        try {
            String validationResult = validateUserInformation(email, password, name, phoneNumber, isEmployee);
            if (validationResult != null) {
                return validationResult;
            }

            if (isEmployee) {
                assetPlus.addEmployee(email, name, password, phoneNumber);
                return "Employee added successfully.";
            } else {
                assetPlus.addGuest(email, name, password, phoneNumber);
                return "Guest added successfully.";
            }
        } catch (Exception e) {
            return "An error occurred while adding the user: " + e.getMessage();
        }
    }

    /**
     * Updates the details of an existing Employee or Guest in the AssetPlus system.
     * @author Nicolas Bolouri
     * @param email The email of the user to be updated.
     * @param newPassword The new password to be set for the user, cannot be null or empty.
     * @param newName The new name to be set for the user, cannot be null.
     * @param newPhoneNumber The new phone number to be set for the user, cannot be null.
     * @return A string message indicating the success or failure of the operation.
     */
    public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
        try {
            String validationResult = validateUserInformation(email, newPassword, newName, newPhoneNumber, null);
            if (validationResult != null) {
                return validationResult;
            }

            User user = User.getWithEmail(email);
            if (user == null) {
                return "User not found.";
            }

            user.setPassword(newPassword);
            user.setName(newName);
            user.setPhoneNumber(newPhoneNumber);
            return "User updated successfully.";
        } catch (Exception e) {
            return "An error occurred while updating the user: " + e.getMessage();
        }
    }

    private static boolean isManagerAvailable() {
        return assetPlus.getManager() != null;
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() > 3 && password.matches(".*[!#$].*")
                && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
    }

    private static String validateUserInformation(String email, String password, String name, String phoneNumber, Boolean isEmployee) {
        if (email == null || name == null || password == null || phoneNumber == null 
            || email.contains(" ") || !email.matches("^.+@.+\\..+$") 
            || password.isEmpty()) {
            return "Invalid user information.";
        }

        if (isEmployee != null) {
            if (isEmployee && !email.endsWith("@ap.com")) {
                return "Employee email domain must be @ap.com.";
            } else if (!isEmployee && email.endsWith("@ap.com")) {
                return "Guest email domain cannot be @ap.com.";
            }
        }
        return null;
    }
}
