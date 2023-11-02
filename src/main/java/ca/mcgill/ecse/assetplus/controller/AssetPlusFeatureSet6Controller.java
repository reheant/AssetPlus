package ca.mcgill.ecse.assetplus.controller;

import java.util.List;

public class AssetPlusFeatureSet6Controller {

  public static void deleteEmployeeOrGuest(String email) {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

  // returns all tickets
  public static List<TOMaintenanceTicket> getTickets() {
    List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();
    List<TOMaintenanceTicket> maintenanceTicketsTO = new ArrayList<>();

    for (MaintenanceTicket maintenanceTicket : maintenanceTickets) {
      if (maintenanceTicket == null) {
        throw new IllegalArgumentException("Maintenance Ticket not found.");
      }

      int id = maintenanceTicket.getId();
      Date raisedOnDate = maintenanceTicket.getRaisedOnDate();
      String description = maintenanceTicket.getDescription();
      String raisedByEmail = maintenanceTicket.getTicketRaiser().getEmail();
      String assetName = null;
      int expectLifeSpan = -1;
      Date purchaseDate = null;
      int floorNumber = -1;
      int roomNumber = -1;
      if (maintenanceTicket.getAsset() != null) {
        assetName = maintenanceTicket.getAsset().getAssetType().getName();
        expectLifeSpan = maintenanceTicket.getAsset().getAssetType().getExpectedLifeSpan();
        purchaseDate = maintenanceTicket.getAsset().getPurchaseDate();
        floorNumber = maintenanceTicket.getAsset().getFloorNumber();
        roomNumber = maintenanceTicket.getAsset().getRoomNumber();
      }

      List<TicketImage> ticketImages = maintenanceTicket.getTicketImages();
      List<String> imageURLs = extractImageURLs(ticketImages);


      List<MaintenanceNote> notes = maintenanceTicket.getTicketNotes();
      TOMaintenanceNote[] allNotes = extractMaintenanceNotes(notes);

      TOMaintenanceTicket currTOMaintenanceTicket =
          new TOMaintenanceTicket(id, raisedOnDate, description, raisedByEmail, assetName,
              expectLifeSpan, purchaseDate, floorNumber, roomNumber, imageURLs, allNotes);
      maintenanceTicketsTO.add(currTOMaintenanceTicket);
    }

    return maintenanceTicketsTO;

  }

}
