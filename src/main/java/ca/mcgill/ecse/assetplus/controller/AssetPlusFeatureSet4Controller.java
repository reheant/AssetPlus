package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet4Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  // assetNumber -1 means that no asset is specified
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
      String email, int assetNumber) {
    try {
      // TODO id positive, id checks
      // TODO raisedOnDate valid (or already checked)
      // TODO description valid (not empty or null)
      // TODO email valid
      // TODO assetNumber valid

      if (assetPlus == null) {
        return "Error: AssetPlus is not initialized.";
      }

      User aTicketRaiser = User.getWithEmail(email);
      if (aTicketRaiser == null) {
        return "An existing ticket raiser must be specified to create a maintenance ticket.";
      }

      MaintenanceTicket maintenanceTicket =
          new MaintenanceTicket(id, raisedOnDate, description, assetPlus, aTicketRaiser);

      if (assetNumber != -1) {
        SpecificAsset aAsset = SpecificAsset.getWithAssetNumber(assetNumber);
        if (aAsset == null) {
          return "Error: An inexisting asset number was provided.";
        }
        maintenanceTicket.setAsset(aAsset);
      }

      assetPlus.addMaintenanceTicket(maintenanceTicket);

    } catch (Exception e) {
      return "An unexpected error occured: " + e.getMessage();
    }
    return "";
  }

  // newAssetNumber -1 means that no asset is specified
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
    // TODO raisedOnDate valid (or already checked)
    // TODO description valid (not empty or null)
    // TODO email valid
    // TODO assetNumber valid
    try {
      MaintenanceTicket existingMaintenanceTicket = MaintenanceTicket.getWithId(id);
      if (existingMaintenanceTicket == null) {
        return "Error: Inexisting maintenance ticket id provided.";
      }

      if (newRaisedOnDate != null
          && !newRaisedOnDate.equals(existingMaintenanceTicket.getRaisedOnDate())) {
        existingMaintenanceTicket.setRaisedOnDate(newRaisedOnDate);
      }

      if (newDescription != null
          && !newDescription.equals(existingMaintenanceTicket.getDescription())) {
        existingMaintenanceTicket.setDescription(newDescription);
      }

      if (newEmail != null
          && !newEmail.equals(existingMaintenanceTicket.getTicketRaiser().getEmail())) {
        User newTicketRaiser = User.getWithEmail(newEmail);
        if (newTicketRaiser != null) {
          existingMaintenanceTicket.setTicketRaiser(newTicketRaiser);
        }
      }

      if (newAssetNumber != -1) {
        SpecificAsset newAsset = SpecificAsset.getWithAssetNumber(newAssetNumber);
        if (newAsset != null) {
          existingMaintenanceTicket.setAsset(newAsset);
        }
      }
    } catch (Exception e) {
      return "An unexpected error occured: " + e.getMessage();
    }
    return "";
  }

  public static void deleteMaintenanceTicket(int id) {
    if (assetPlus == null) {
      throw new NullPointerException("AssetPlus is not initialized");
    }

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(id);
    if (maintenanceTicket == null){
      // TODO should throw or just do nothing? Ticket is already not there...
    }
    assetPlus.removeMaintenanceTicket(maintenanceTicket);
    // TODO what if different assetplus? does this count as "in the end it's not there so all is ok"?

  }

}
