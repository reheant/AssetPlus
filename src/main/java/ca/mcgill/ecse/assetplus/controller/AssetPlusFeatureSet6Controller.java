package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet6Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
    * Deletes an Employee or Guest from the AssetPlus system.
    *
    * @author Liam Di Chiro
    * @param email The email of an Employee or Guest, must be of type String, and cannot be null.
    */
  public static void deleteEmployeeOrGuest(String email) {
    if (email == null){
      throw new IllegalArgumentException("Email not found.");
    }

    User user = User.getWithEmail(email);

    if (user == null) {
      throw new IllegalArgumentException("User not found.");
    }

    user.delete();
  }

  /**
    * Returns a list of maintenance tickets from the AssetPlus system.
    *
    * @author Liam Di Chiro
    *
    * @return A List of type TOMaintenanceTicket of all of the maintenance tickets. 
    */
  public static List<TOMaintenanceTicket> getTickets() {
    List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();

    

    List<TOMaintenanceTicket> maintenanceTicketsTO = new ArrayList<>();

    for (MaintenanceTicket maintenanceTicket : maintenanceTickets){
      if (maintenanceTicket == null) {
        throw new IllegalArgumentException("Maintenance Ticket not found.");
      }

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
      List<String> imageURLs = extractImageURLs(ticketImages);


      List<MaintenanceNote> notes = maintenanceTicket.getTicketNotes();
      TOMaintenanceNote[] allNotes = extractMaintenanceNotes(notes);

      TOMaintenanceTicket currTOMaintenanceTicket = new TOMaintenanceTicket(id, raisedOnDate, description, raisedByEmail, assetName, expectLifeSpan, purchaseDate, floorNumber, roomNumber, imageURLs, allNotes);
      maintenanceTicketsTO.add(currTOMaintenanceTicket);
    }

    return maintenanceTicketsTO;
    
  }
  /**
    * Extracts a list of ticket image URLs from a maintenance ticket images.
    *
    * @author Liam Di Chiro
    * @param ticketImages the list of ticket images on a maintenance ticket, must be of type List<TicketImage>.
    * @return A List of type String of all extracted image URLs of all of the images on a maintenance ticket. 
    */
  private static List<String> extractImageURLs(List<TicketImage> ticketImages){
    
    List<String> imageURLs = new ArrayList<>();
    for (TicketImage ticketImage : ticketImages){
      imageURLs.add(ticketImage.getImageURL());
    }
    return imageURLs;
  }
  /**
    * Extracts a list of maintenance notes from a maintenance ticket.
    *
    * @author Liam Di Chiro
    * @param notes the list of maintenance notes to be added on to the maintenance ticket, must be of type List<MaintenanceNote>
    * @return A List of type TOMaintenanceNote of all of the maintenance notes that have been converted to TO maintenance notes. 
    */
  private static TOMaintenanceNote[] extractMaintenanceNotes(List<MaintenanceNote> notes){
    TOMaintenanceNote[] allNotes = new TOMaintenanceNote[notes.size()];

      for (int i = 0; i < notes.size(); i++){
        allNotes[i] = new TOMaintenanceNote(notes.get(i).getDate(), notes.get(i).getDescription(), notes.get(i).getNoteTaker().getEmail());
      }
      return allNotes;
  }
}
