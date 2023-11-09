package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet1Controller {

  public static String updateManager(String password) {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

  public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber,
        boolean isEmployee) {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

  public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

  /**
   * Checks if a manager is available in the AssetPlus system.
   * 
   * @author Nicolas Bolouri
   * @return An error message if the manager is not found, or an empty string if the manager is
   *         available.
   */
  private static String assertManagerAvailable() {
    return assetPlus.hasManager() ? "" : "Manager not found";
  }

  /**
   * Validates the user's password, name, and phone number according to specified constraints.
   * 
   * @author Nicolas Bolouri
   * @param password The user's password to be validated. Constraints are derived from the
   *        {@link #validatePassword(String)} method.
   * @param name The user's name to be validated. Constraints are derived from the
   *        {@link #validateName(String)} method.
   * @param phoneNumber The user's phone number to be validated. Constraints are derived from the
   *        {@link #validatePhoneNumber(String)} method.
   * @return A specific error message if any of the details are invalid, or an empty string if all
   *         the details are valid.
   */
  private static String validateUserDetails(String password, String name, String phoneNumber) {
    String error = "";
    error += validatePassword(password) + validateName(name) + validatePhoneNumber(phoneNumber);

    return error;
  }

  /**
   * Validates the email of an Employee or Guest according to specific constraints.
   * 
   * @author Nicolas Bolouri
   * @param email The email to be validated.
   * @param isEmployee A Boolean indicating whether the email belongs to an Employee (true) or Guest
   *        (false); can be null.
   * @return A specific error message if the email is invalid, or an empty string if the email is
   *         valid.
   */
  private static String validateEmployeeOrGuestEmail(String email, Boolean isEmployee) {
    if (email == null)
      return "Email cannot be null";
    if (email.isEmpty())
      return "Email cannot be empty";
    if (email.equals("manager@ap.com"))
      return "Email cannot be manager@ap.com";
    if (email.contains(" "))
      return "Email must not contain any spaces";

    int atIndex = email.indexOf("@");
    int lastAtindex = email.lastIndexOf("@");
    int dotIndex = email.lastIndexOf(".");
    if (atIndex <= 0 || atIndex != lastAtindex || atIndex >= dotIndex - 1
        || dotIndex >= email.length() - 1) {
      return "Invalid email";
    }

    if (isEmployee != null) {
      if (isEmployee && !email.endsWith("@ap.com"))
        return "Email domain must be @ap.com";
      if (!isEmployee && email.endsWith("@ap.com"))
        return "Email domain cannot be @ap.com";
    }
    return "";
  }

  /**
   * Validates the password according to basic constraints.
   * 
   * @author Nicolas Bolouri
   * @param password The password to be validated.
   * @return A specific error message if the password is invalid, or an empty string if the password
   *         is valid.
   */
  private static String validatePassword(String password) {
    if (password == null)
      return "Password cannot be null";
    if (password.isEmpty())
      return "Password cannot be empty";
    return "";
  }

  /**
   * Validates the password of the manager according to specific constraints.
   * 
   * @author Nicolas Bolouri
   * @param password The password to be validated.
   * @return A specific error message if the password is invalid, or an empty string if the password
   *         is valid.
   */
  private static String validateManagerPassword(String password) {
    String error = validatePassword(password);
    if (!error.isEmpty())
      return error;
    if (password.length() <= 3)
      return "Password must be at least four characters long";
    if (!password.matches(".*[!#$].*"))
      return "Password must contain one character out of !#$";
    if (!password.matches(".*[a-z].*"))
      return "Password must contain one lower-case character";
    if (!password.matches(".*[A-Z].*"))
      return "Password must contain one upper-case character";
    return "";
  }

  /**
   * Validates the name according to basic constraints.
   * 
   * @author Nicolas Bolouri
   * @param name The name to be validated.
   * @return A specific error message if the name is invalid, or an empty string if the name is
   *         valid.
   */
  private static String validateName(String name) {
    return name != null ? "" : "Name cannot be null";
  }

  /**
   * Validates the phone number according to basic constraints.
   * 
   * @author Nicolas Bolouri
   * @param phoneNumber The phone number to be validated.
   * @return A specific error message if the phone number is invalid, or an empty string if the
   *         phone number is valid.
   */
  private static String validatePhoneNumber(String phoneNumber) {
    return phoneNumber != null ? "" : "Phone number cannot be null";
  }
}
