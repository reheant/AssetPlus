package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.controller.*;

public class AssetPlusFeatureSet6Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();


  public static void deleteEmployeeOrGuest(String email) {
    List<Employee> employees = assetPlus.getEmployees();

    List<Guest> guests = assetPlus.getGuests();

    try{
      if (email.endsWith("@ap.com"));
        for (Employee employee : employees){
          if (employee.getEmail() == email){
            employee.delete();
          }
        }
      for (Guest guest : guests){
        if (guest.getEmail() == email){
          guest.delete();
        }
      }
    }
    catch (Exception e) {
      System.out.println("Employee/Guest could not be deleted.");
    }
  }
  // returns all tickets
  public static List<TOMaintenanceTicket> getTickets() {
    List<MaintenanceTicket> MaintenanceTickets = assetPlus.getMaintenanceTickets();
    List<TOMaintenanceTicket> TOMaintenanceTickets = new ArrayList<>();

    for (MaintenanceTicket maintenanceTicket : MaintenanceTickets){
      int Id = maintenanceTicket.getId();
      var RaisedOnDate = maintenanceTicket.getRaisedOnDate();
      String Description = maintenanceTicket.getDescription();
      String RaisedByEmail = maintenanceTicket.getRaisedByEmail(); //Getter?
      String AssetName = maintenanceTicket.getAssetType().getName(); //GEtter?
      int ExpectLifeSpanInDays = maintenanceTicket.getAssetType().getExpectLifeSpanInDays(); //Getter?
      var PurchaseDate = maintenanceTicket.getAsset().getPurchaseDate();
      int FloorNumber = maintenanceTicket.getAsset().getFloorNumber();
      int RoomNumber = maintenanceTicket.getAsset().getRoomNumber();

      List<TicketImage> TicketImages = maintenanceTicket.getTicketImages();
      List<String> ImageURLs = new ArrayList<>();

      for (TicketImage ticketImage : TicketImages){
        ImageURLs.add(ticketImage.getImageURL());
      }

      List<MaintenanceNote> Notes = maintenanceTicket.getTicketNotes();
      List<TOMaintenanceNote> TOMaintenanceNotes = new ArrayList<>();

      for (MaintenanceNote maintenanceNote : Notes){
        TOMaintenanceNotes.add(maintenanceNote.getDate());
        TOMaintenanceNotes.add(maintenanceNote.getDescription());
        TOMaintenanceNotes.add(maintenanceNote.getNoteTaker());
      }

      TOMaintenanceTicket currTOMaintenanceTicket = new TOMaintenanceTicket(Id, null, Description, RaisedByEmail, AssetName, ExpectLifeSpanInDays, null, FloorNumber, RoomNumber, ImageURLs, null);
      TOMaintenanceTickets.add(currTOMaintenanceTicket);

    }
    
  }
  

  private static AssetType assetType;
  public static AssetType getAssetType(){
    if (assetType == null) {
      assetType = new AssetType(null, 0, assetPlus);
    }
    return assetType;
  }

}
