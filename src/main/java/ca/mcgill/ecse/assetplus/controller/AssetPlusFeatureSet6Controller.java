package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

public class AssetPlusFeatureSet6Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
   * Deletes an Employee or Guest from the AssetPlus system.
   *
   * @author Liam Di Chiro
   * @param email The email of an Employee or Guest, must be of type String, and cannot be null.
   */
  public static void deleteEmployeeOrGuest(String email) {
    if (email == null) {
      return;
    }
    User user = User.getWithEmail(email);
    if (user == null || user instanceof Manager) {
      return;
    }
    user.delete();
    AssetPlusPersistence.save();
  }

  /**
   * Returns a list of maintenance tickets from the AssetPlus system.
   *
   * @author Liam Di Chiro
   * @return A List of type TOMaintenanceTicket of all of the maintenance tickets.
   */
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
      String status = maintenanceTicket.getStatusFullName();
      String fixedByEmail = null;
      String timeToResolve = null;
      String priority = null;
      boolean approvalRequired = maintenanceTicket.hasFixApprover();
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
      if (maintenanceTicket.getTicketFixer() != null) {
        fixedByEmail = maintenanceTicket.getTicketFixer().getEmail();
      }
      if (maintenanceTicket.getTimeToResolve() != null) {
        timeToResolve = maintenanceTicket.getTimeToResolve().toString();
      }
      if (maintenanceTicket.getPriority() != null) {
        priority = maintenanceTicket.getPriority().toString();
      }

      List<TicketImage> ticketImages = maintenanceTicket.getTicketImages();
      List<String> imageURLs = extractImageURLs(ticketImages);


      List<MaintenanceNote> notes = maintenanceTicket.getTicketNotes();
      TOMaintenanceNote[] allNotes = extractMaintenanceNotes(notes);

      TOMaintenanceTicket currTOMaintenanceTicket =
          new TOMaintenanceTicket(id, raisedOnDate, description, raisedByEmail, status,
              fixedByEmail, timeToResolve, priority, approvalRequired, assetName, expectLifeSpan,
              purchaseDate, floorNumber, roomNumber, imageURLs, allNotes);

      maintenanceTicketsTO.add(currTOMaintenanceTicket);
    }

    AssetPlusPersistence.save();
    return maintenanceTicketsTO;

  }

  public static TOMaintenanceTicket getTicketWithId(int id) {
    List<TOMaintenanceTicket> allMaintenanceTickets = getTickets();
    for (TOMaintenanceTicket ticket: allMaintenanceTickets){
      if (ticket.getId() == id){
        return ticket;
      }
    }
    return null;
  }

  public static int getMaxTicketId() {
    int max = -1;
    List<TOMaintenanceTicket> allMaintenanceTickets = getTickets();
    for (TOMaintenanceTicket ticket: allMaintenanceTickets){
      if (ticket.getId() > max){
        max = ticket.getId();
      }
    }
    return max;
  }

  /**
   * Extracts a list of ticket image URLs from a maintenance ticket images.
   *
   * @author Liam Di Chiro
   * @param ticketImages the list of ticket images on a maintenance ticket, must be of type
   *        List<TicketImage>.
   * @return A List of type String of all extracted image URLs of all of the images on a maintenance
   *         ticket.
   */
  private static List<String> extractImageURLs(List<TicketImage> ticketImages) {
    if (ticketImages == null) {
      throw new IllegalArgumentException("Ticket image not found.");
    }

    List<String> imageURLs = new ArrayList<>();
    for (TicketImage ticketImage : ticketImages) {

      imageURLs.add(ticketImage.getImageURL());
    }
    return imageURLs;
  }

  /**
   * Extracts a list of maintenance notes from a maintenance ticket.
   *
   * @author Liam Di Chiro
   * @param notes the list of maintenance notes to be added on to the maintenance ticket, must be of
   *        type List<MaintenanceNote>
   * @return A List of type TOMaintenanceNote of all of the maintenance notes that have been
   *         converted to TO maintenance notes.
   */
  private static TOMaintenanceNote[] extractMaintenanceNotes(List<MaintenanceNote> notes) {
    if (notes == null) {
      throw new IllegalArgumentException("Ticket image not found.");
    }

    TOMaintenanceNote[] allNotes = new TOMaintenanceNote[notes.size()];
    for (int i = 0; i < notes.size(); i++) {
      allNotes[i] = new TOMaintenanceNote(notes.get(i).getDate(), notes.get(i).getDescription(),
          notes.get(i).getNoteTaker().getEmail());
    }

    return allNotes;
  }
}
