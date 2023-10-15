package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.lang.IllegalArgumentException;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet6Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
     * Deletes an Employee or Guest from the AssetPlus system
     * @author Liam Di Chiro
     * @param email the email of an Employee or Guest, must be of type String
     */
  
  public static void deleteEmployeeOrGuest(String email) {
    User user = User.getWithEmail(email);

    if (user == null) {
      throw new IllegalArgumentException("User not found.");
    }

    user.delete();
  }

    /**
     * Returns a list of maintenance tickets from the AssetPlus system
     * @author Liam Di Chiro
     * @return A List of type TOMaintenanceTicket of all of the maintenance tickets 
     */
  public static List<TOMaintenanceTicket> getTickets() {
    List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();
    List<TOMaintenanceTicket> maintenanceTicketsTO = new ArrayList<>();

    for (MaintenanceTicket maintenanceTicket : maintenanceTickets){
      int id = maintenanceTicket.getId();
      Date raisedOnDate = maintenanceTicket.getRaisedOnDate();
      String description = maintenanceTicket.getDescription();
      String raisedByEmail = maintenanceTicket.getTicketRaiser().getEmail(); 
      String assetName = maintenanceTicket.getAsset().getAssetType().getName();
      int expectLifeSpan = maintenanceTicket.getAsset().getAssetType().getExpectedLifeSpan(); 
      Date purchaseDate = maintenanceTicket.getAsset().getPurchaseDate();
      int floorNumber = maintenanceTicket.getAsset().getFloorNumber();
      int roomNumber = maintenanceTicket.getAsset().getRoomNumber();

      List<TicketImage> ticketImages = maintenanceTicket.getTicketImages();
      List<String> imageURLs = new ArrayList<>();

      for (TicketImage ticketImage : ticketImages){
        imageURLs.add(ticketImage.getImageURL());
      }

      List<MaintenanceNote> notes = maintenanceTicket.getTicketNotes();
      TOMaintenanceNote[] allNotes = new TOMaintenanceNote[notes.size()];

      for (int i = 0; i < notes.size(); i++){
        allNotes[i] = new TOMaintenanceNote(notes.get(i).getDate(), notes.get(i).getDescription(), notes.get(i).getNoteTaker().getEmail());
      }

      TOMaintenanceTicket currTOMaintenanceTicket = new TOMaintenanceTicket(id, raisedOnDate, description, raisedByEmail, assetName, expectLifeSpan, purchaseDate, floorNumber, roomNumber, imageURLs, allNotes);
      maintenanceTicketsTO.add(currTOMaintenanceTicket);
    }

    return maintenanceTicketsTO;
    
  }
}
