package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;

public class AssetPlusFeatureSet4Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  private static final int UNSPECIFIED_ASSET_NUMBER = -1;

  // assetNumber -1 means that no asset is specified
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
      String email, int assetNumber) {

    var error = "";
    error += assertAssetPlusInitialized();
    error += assertValidTicketId(id);
    error += assertValidRaisedOnDate(raisedOnDate);
    error += assertValidTicketDescription(description);
    error += assertValidAssetNumber(assetNumber);

    User aTicketRaiser = User.getWithEmail(email);
    if (aTicketRaiser == null) {
      error += "The ticket raiser does not exist ";
    }

    if (!error.isEmpty()) {
      return error.trim();
    }

    try {

      MaintenanceTicket maintenanceTicket =
          new MaintenanceTicket(id, raisedOnDate, description, assetPlus, aTicketRaiser);

      if (assetNumber != UNSPECIFIED_ASSET_NUMBER) {
        SpecificAsset aAsset = SpecificAsset.getWithAssetNumber(assetNumber);
        if (aAsset != null) {
          maintenanceTicket.setAsset(aAsset);
        }
      }

    } catch (Exception e) {
      if (e.getMessage().contains("Cannot create due to duplicate id.")) {
        return "Ticket id already exists";
      }

      return "An unexpected error occured: " + e.getMessage();
    }
    return "";
  }

  // newAssetNumber -1 means that no asset is specified
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {

    var error = "";
    error += assertAssetPlusInitialized();
    error += assertValidTicketId(id);
    error += assertValidRaisedOnDate(newRaisedOnDate);
    error += assertValidTicketDescription(newDescription);
    error += assertValidAssetNumber(newAssetNumber);

    User newTicketRaiser = User.getWithEmail(newEmail);
    if (newTicketRaiser == null) {
      error += "The ticket raiser does not exist ";
    }

    MaintenanceTicket existingMaintenanceTicket = MaintenanceTicket.getWithId(id);
    if (existingMaintenanceTicket == null) {
      error += "The ticket does not exist ";
    }

    SpecificAsset newAsset = null;
    if (newAssetNumber != UNSPECIFIED_ASSET_NUMBER) {
      newAsset = SpecificAsset.getWithAssetNumber(newAssetNumber);
    }

    if (!error.isEmpty()) {
      return error.trim();
    }

    try {

      existingMaintenanceTicket.setRaisedOnDate(newRaisedOnDate);
      existingMaintenanceTicket.setTicketRaiser(newTicketRaiser);
      existingMaintenanceTicket.setDescription(newDescription);
      existingMaintenanceTicket.setAsset(newAsset);


    } catch (Exception e) {
      return "An unexpected error occured: " + e.getMessage();
    }
    return "";
  }

  public static void deleteMaintenanceTicket(int id) {

    if (assetPlus == null) {
      throw new NullPointerException("AssetPlus is not initialized ");
    }

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(id);
    if (maintenanceTicket != null) {
      maintenanceTicket.delete();
    }
  }


  /**
   * Asserts that the AssetPlus app was initialized
   * 
   * @return an empty string if AssetPlus was initialized, an error string if AssetPlus was not
   *         initialized.
   */
  private static String assertAssetPlusInitialized() {
    if (assetPlus == null) {
      return "Error: AssetPlus is not initialized.";
    }
    return "";
  }

  /**
   * Asserts that the provided ticket id is valid
   * 
   * @param id the ticket id to validate
   * @return an empty string if the id is valid, an error string if the id is invalid.
   */
  private static String assertValidTicketId(int id) {
    if (id < 0) {
      return "Error: An invalid ticket ID was provided: provided ticket id is negative. ";
    }
    return "";
  }

  /**
   * Asserts that the provided ticket raising date is valid
   * 
   * @param date the date at which the ticket was raised
   * @return an empty string if the date is valid, an error string if the date is invalid.
   */
  private static String assertValidRaisedOnDate(Date date) {
    if (date == null) {
      return "Error: An invalid ticket creation date was provided: provided date is null. ";
    }
    return "";
  }

  /**
   * Asserts that the provided ticket description is valid
   * 
   * @param description the description of the ticket
   * @return an empty string if the description is valid, an error string if the description is
   *         invalid.
   */
  private static String assertValidTicketDescription(String description) {
    if (description == null || description.isEmpty()) {
      return "Ticket description cannot be empty ";
    }
    return "";
  }

  /**
   * Asserts that the provided asset number is valid
   * 
   * @param assetNumber the number of the asset to link to the ticket
   * @return an empty string if the asset number is valid, an error string if the asset number is
   *         invalid.
   */
  private static String assertValidAssetNumber(int assetNumber) {
    if (assetNumber == UNSPECIFIED_ASSET_NUMBER) {
      return "";
    } else if (assetNumber < 1) {
      return "Error: An invalid asset number was provided: assetNumber is < 1";
    }

    SpecificAsset aAsset = SpecificAsset.getWithAssetNumber(assetNumber);
    if (aAsset == null) {
      return "The asset does not exist ";
    }

    return "";
  }

}
