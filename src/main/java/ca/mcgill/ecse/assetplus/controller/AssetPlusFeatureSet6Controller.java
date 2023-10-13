package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.controller.*;

public class AssetPlusFeatureSet6Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  // Method 1
  public static void deleteEmployeeOrGuest(String email) {
    User user = User.getWithEmail(email);

    if (user == null) {
      throw new NullPointerException("User not found.");
    }

    user.delete();
  }

  // Method 2
  public static List<TOMaintenanceTicket> getTickets() {
    List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();
    List<TOMaintenanceTicket> maintenanceTicketsTO = new ArrayList<>();

    for (MaintenanceTicket maintenanceTicket : MaintenanceTickets){
      int id = maintenanceTicket.getId();
      Date raisedOnDate = maintenanceTicket.getRaisedOnDate();
      String description = maintenanceTicket.getDescription();

      // INCOMPLETE 
      String raisedByEmail = maintenanceTicket.getTicketRaiser().getEmail(); 
      String assetName = maintenanceTicket.getAsset().getAssetType().getName();
      int expectLifeSpanInDays = maintenanceTicket.getAsset().getExpectLifeSpanInDays(); //Getter?
      Date purchaseDate = maintenanceTicket.getAsset().getPurchaseDate();
      int floorNumber = maintenanceTicket.getAsset().getFloorNumber();
      int roomNumber = maintenanceTicket.getAsset().getRoomNumber();

      List<TicketImage> ticketImages = maintenanceTicket.getTicketImages();
      List<String> imageURLs = new ArrayList<>();

      for (TicketImage ticketImage : ticketImages){
        ImageURLs.add(ticketImage.getImageURL());
      }

      List<MaintenanceNote> notes = maintenanceTicket.getTicketNotes();
      List<TOMaintenanceNote> maintenanceNotesTO = new TOMaintenanceNote[notes.size()];

      int index = 0;
      for (MaintenanceNote maintenanceNote : notes) {
        maintenanceNotesTO[index] = new TOMaintenanceNote(maintenanceNote.getDate(), maintenanceNote.getDescription(), maintenanceNote.getNoteTaker());
        index++;
      }

      TOMaintenanceTicket currTOMaintenanceTicket = new TOMaintenanceTicket(id, raisedOnDate, description, raisedByEmail, assetName, expectLifeSpanInDays, purchaseDate, floorNumber, roomNumber, imageURLs, maintenanceNotesTO);
      maintenanceTicketsTO.add(currTOMaintenanceTicket);
    }

    return maintenanceTicketsTO;
    
  }
}
