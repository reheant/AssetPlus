package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet1Controller {

  public static String updateManager(String password) {
    AssetPlus assetPlus = new AssetPlus();  
    Manager manager = assetPlus.getManager();

    if (manager != null) {
      manager.setPassword(password);  
      return "Manager password updated successfully.";
    } else {
      return "Manager not found.";
    }
  }

  public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber, boolean isEmployee) {
    AssetPlus assetPlus = new AssetPlus(); 

    if (isEmployee) {
      assetPlus.addEmployee(email, name, password, phoneNumber);
      return "Employee added successfully.";
    } else {
      assetPlus.addGuest(email, name, password, phoneNumber);
      return "Guest added successfully.";
    }
  }

  public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
    AssetPlus assetPlus = new AssetPlus();  

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
  }
}
