package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet1Controller {
    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

    public static String updateManager(String password) {
        try {
            Manager manager = assetPlus.getManager();

            if (manager != null) {
                manager.setPassword(password);
                return "Manager password updated successfully.";
            } else {
                return "Manager not found.";
            }
        } catch (NullPointerException e) {
            return "Error: AssetPlus or Manager is not initialized.";
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber, boolean isEmployee) {
        try {
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

    public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
        try {
            for (Employee employee : assetPlus.getEmployees()) {
                if (employee.getEmail().equals(email)) {
                    employee.setPassword(newPassword);
                    employee.setName(newName);
                    employee.setPhoneNumber(newPhoneNumber);
                    return "Employee updated successfully.";
                }
            }

            for (Guest guest : assetPlus.getGuests()) {
                if (guest.getEmail().equals(email)) {
                    guest.setPassword(newPassword);
                    guest.setName(newName);
                    guest.setPhoneNumber(newPhoneNumber);
                    return "Guest updated successfully.";
                }
            }

            return "User not found.";
        } catch (Exception e) {
            return "An error occurred while updating the user: " + e.getMessage();
        }
    }
}
