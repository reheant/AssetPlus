package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

import java.util.Objects;

public class AssetPlusFeatureSet5Controller {

  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {

    MaintenanceTicket ticket;
    try {
      ticket = MaintenanceTicket.getWithId(ticketID);
    } catch (Exception e) {
      return "Ticket not found";
    }
    TicketImage image = new TicketImage(imageURL, ticket);
    return String.format("Added image %s to ticket %d", image, ticketID);
  }

  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
    MaintenanceTicket ticket;
    try {
      ticket = MaintenanceTicket.getWithId(ticketID);
    } catch (Exception e) {
      // Log error
      return;
    }
    for (TicketImage image: ticket.getTicketImages()){
      if (image.getImageURL().equals(imageURL)){
        image.delete();
        return;
      }
    }
  }

}
