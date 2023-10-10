package ca.mcgill.ecse.assetplus.controller;

import java.security.InvalidParameterException;
import java.sql.Date;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet4Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  final private static int UNSPECIFIED_ASSET_NUMBER = -1;

  /**
   * @author Julien Audet
   * @param id The id of the maintenance ticket to create. Must be > 0.
   * @param raisedOnDate The date at which the ticket was raised.
   * @param description A description of why the ticket is being raised.
   * @param email The email of the user raising the ticket.
   * @param assetNumber The asset number of the asset in need of maintenance. Must be > 1. Can be -1 to avoid specifying asset. 
   * @return An empty string indicating success. An error message if failure.
   */
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
      String email, int assetNumber) {
    try {
      if (!isValidTicketId(id)) {
        return "Error: An invalid ticket ID was provided.";
      }

      if (!isValidRaisedOnDate(raisedOnDate)) {
        return "Error: An invalid ticket creation date was provided.";
      }

      if (!isValidTicketDescription(description)) {
        return "Error: An invalid ticket description was provided.";
      }

      if (!isValidAssetNumber(assetNumber)) {
        return "Error: An invalid asset number was provided.";
      }

      if (assetPlus == null) {
        return "Error: AssetPlus is not initialized.";
      }

      User aTicketRaiser = User.getWithEmail(email);
      if (aTicketRaiser == null) {
        return "An existing ticket raiser must be specified to create a maintenance ticket.";
      }

      MaintenanceTicket maintenanceTicket =
          new MaintenanceTicket(id, raisedOnDate, description, assetPlus, aTicketRaiser);

      if (assetNumber != UNSPECIFIED_ASSET_NUMBER) {
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

  /**
   * @author Julien Audet
   * @param id The id of the maintenance ticket to create. Must be > 0.
   * @param newRaisedOnDate The date at which the ticket was raised.
   * @param newDescription A description of why the ticket is being raised.
   * @param newEmail The email of the user raising the ticket.
   * @param newAssetNumber The asset number of the asset in need of maintenance. Must be > 1. Can be -1 to avoid specifying asset. 
   * @return An empty string indicating success. An error message if failure.
   */
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
    if (!isValidTicketId(id)) {
      return "Error: An invalid ticket ID was provided.";
    }

    if (!isValidRaisedOnDate(newRaisedOnDate)) {
      return "Error: An invalid ticket creation date was provided.";
    }

    if (!isValidTicketDescription(newDescription)) {
      return "Error: An invalid ticket description was provided.";
    }

    if (!isValidAssetNumber(newAssetNumber)) {
      return "Error: An invalid asset number was provided.";
    }

    if (assetPlus == null) {
      return "Error: AssetPlus is not initialized.";
    }
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

      if (newAssetNumber != UNSPECIFIED_ASSET_NUMBER) {
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

  /**
   * @author Julien Audet
   * @param id The id of the maintenance ticket to create. Must be > 0.
   */
  public static void deleteMaintenanceTicket(int id) {
    if (assetPlus == null) {
      throw new NullPointerException("AssetPlus is not initialized");
    }

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(id);
    if (maintenanceTicket == null) {
      // TODO update to match gherkin message
      throw new InvalidParameterException(
          "Ticket with provided ticket id does not exist in the system");
    }
    boolean ret = assetPlus.removeMaintenanceTicket(maintenanceTicket);
    if (!ret) {
      // TODO update to match gherkin message
      throw new RuntimeException(
          "Error: AssetPlus explicitely rejected deleting maintenance ticket");
    }
  }


  private static boolean isValidTicketId(int id) {
    if (id < 0) {
      return false;
    }
    return true;
  }

  private static boolean isValidRaisedOnDate(Date date) {
    if (date == null) {
      return false;
    }
    return true;
  }

  private static boolean isValidTicketDescription(String description) {
    if (description == null || description.isEmpty()) {
      return false;
    }
    return true;
  }

  private static boolean isValidAssetNumber(int assetNumber) {
    if (assetNumber == UNSPECIFIED_ASSET_NUMBER) {
      return true;
    } else if (assetNumber < 1) {
      return false;
    }
    return true;
  }

}
