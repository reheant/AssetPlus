package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

import java.util.List;

public class AssetPlusFeatureSet5Controller {

  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    try {
      // constraint - URL must not be empty or null (see AssetPlus constraints.txt)
      if (imageURL == null || imageURL.isEmpty()) {
        return "Image URL must not be empty or null";
      }
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
      if (ticket == null) {
        return String.format("String with ticketID %d not found", ticketID);
      }
      // constraint - two imageURLs of the same ticket cannot be the same (see AssetPlus constraints.txt)
      List<TicketImage> images = ticket.getTicketImages();
      if (images != null) {
        for (TicketImage image : images) {
          if (image.getImageURL().equals(imageURL)) {
            return "Image not added to maintenance ticket - two imageURLs " +
                    "of the same ticket cannot be the same: " + imageURL;
          }
        }
      }

      TicketImage image = new TicketImage(imageURL, ticket);
      return String.format("Added image %s to ticket %d", image, ticketID);
    } catch (Exception e) {
      return "An unexpected error occurred while attempting to add an " +
              "image to the maintenance ticket: " + e.getMessage();
    }
  }

  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
    try {
      // constraint - URL must not be empty or null (see AssetPlus constraints.txt)
      if (imageURL == null || imageURL.isEmpty()) {
        return;
      }
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
      if (ticket == null) {
        return;
      }
      List<TicketImage> images = ticket.getTicketImages();
      if (images != null) {
        for (TicketImage image : images) {
          if (image.getImageURL().equals(imageURL)) {
            image.delete();
            return;
          }
        }
      }
    } catch (Exception e) {
      // Log error
    }
  }

}
