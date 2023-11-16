package ca.mcgill.ecse.assetplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusStateController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MaintenanceTicketsStepDefinitions {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  private String error;
  private List<TOMaintenanceTicket> tOMaintenanceTickets;


  /**
   * Adds the required employees to the system
   * 
   * @author Tiffany Miller, Julien Audet
   * @param dataTable The data table of the employees that must exist in the system.
   */
  @Given("the following employees exist in the system")
  public void the_following_employees_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i < rows.size(); i++) {
      List<String> row = rows.get(i);
      String email = row.get(0);
      String password = row.get(1);
      String name = row.get(2);
      String phoneNumber = row.get(3);
      assetPlus.addEmployee(email, name, password, phoneNumber);
    }
    error = "";
  }

  /**
   * Adds the required managers to the system
   * 
   * @author Tiffany Miller, Julien Audet
   * @param dataTable The data table of the manager that must exist in the system.
   */
  @Given("the following manager exists in the system")
  public void the_following_manager_exists_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (int i = 0; i < rows.size(); i++) {
      Map<String, String> row = rows.get(i);
      String email = row.get("email");
      String password = row.get("password");
      Manager manager = new Manager(email, "manager", password, "(123)456-7890", assetPlus);
      assetPlus.setManager(manager);
    }
    error = "";
  }

  /**
   * Adding the required asset types to the system
   * 
   * @author Tiffany Miller, Julien Audet
   * @param dataTable The data table of the asset types that must exist in the system.
   */
  @Given("the following asset types exist in the system")
  public void the_following_asset_types_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (int i = 0; i < rows.size(); i++) {
      Map<String, String> row = rows.get(i);
      String name = row.get("name");
      int lifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
      assetPlus.addAssetType(name, lifeSpan);
    }
    error = "";
  }

  /**
   * Add the required asssets to the system
   * 
   * @author Tiffany Miller, Julien Audet
   * @param dataTable The data table of the assets that must exist in the system.
   */
  @Given("the following assets exist in the system")
  public void the_following_assets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i < rows.size(); i++) {
      List<String> row = rows.get(i);
      Integer assetNumber = parseAssetNumber(row.get(0));
      AssetType type = AssetType.getWithName(row.get(1));
      Date purchaseDate = Date.valueOf(row.get(2));
      int floorNumber = Integer.parseInt(row.get(3));
      int roomNumber = Integer.parseInt(row.get(4));
      assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, type);
    }
    error = "";
  }

  /**
   * Adds the required tickets to the system
   * 
   * @author Tiffany Miller, Julien Audet
   * @param dataTable The data table of the tickets that must exist in the system.
   */
  @Given("the following tickets exist in the system")
  public void the_following_tickets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i < rows.size(); i++) {
      List<String> row = rows.get(i);
      int id = Integer.parseInt(row.get(0));
      String ticketRaiser = row.get(1);
      Date raisedOnDate = Date.valueOf(row.get(2));
      String description = row.get(3);
      Integer assetNumber = parseAssetNumber(row.get(4));


      User raiserUser = User.getWithEmail(ticketRaiser);

      SpecificAsset asset = null;
      if (assetNumber != null) {
        asset = SpecificAsset.getWithAssetNumber(assetNumber);
      }

      assetPlus.addMaintenanceTicket(id, raisedOnDate, description, raiserUser);
      MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
      thisTicket.setAsset(asset);

      if (row.size() > 9) {
        String stateString = row.get(5);
        String fixedByEmail = row.get(6);
        String timeEstimateString = row.get(7);
        String priorityString = row.get(8);
        Boolean approvalRequired = parseStringTicketPropertyToBoolean(row.get(9));
        setMaintenanceTicketAsState(thisTicket, stateString, fixedByEmail, timeEstimateString,
            priorityString, approvalRequired);
      }

    }
    error = "";
  }

  /**
   * Adds the required notes to the system
   * 
   * @author Julien Audet
   * @param dataTable dataTable of the notes that must exist in the system.
   */
  @Given("the following notes exist in the system")
  public void the_following_notes_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i < rows.size(); i++) {
      List<String> row = rows.get(i);
      String noteTakerEmail = row.get(0);
      int ticketId = Integer.parseInt(row.get(1));
      Date addedOnDate = Date.valueOf(row.get(2));
      String description = row.get(3);

      HotelStaff noteTaker = (HotelStaff) HotelStaff.getWithEmail(noteTakerEmail);
      MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);

      maintenanceTicket.addTicketNote(addedOnDate, description, noteTaker);
    }
    error = "";
  }

  /**
   * Adds the required images to the system
   * 
   * @author Julien Audet
   * @param dataTable dataTable of the images that must exist in the system.
   */
  @Given("the following ticket images exist in the system")
  public void the_following_ticket_images_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (int i = 0; i < rows.size(); i++) {
      Map<String, String> row = rows.get(i);
      String imageUrl = row.get("imageUrl");
      int ticketId = Integer.parseInt(row.get("ticketId"));

      MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);

      maintenanceTicket.addTicketImage(imageUrl);
    }
  }

  /**
   * Sets a ticket to a specified state, and requires approval if needed
   * 
   * @author Julien Audet
   * @param ticketIdString the id of the ticket
   * @param stateString the state of the ticket
   * @param requiresApprovalString whether the ticket requires manager approval
   */
  @Given("ticket {string} is marked as {string} with requires approval {string}")
  public void ticket_is_marked_as_with_requires_approval(String ticketIdString, String stateString,
      String requiresApprovalString) {
    int ticketId = Integer.parseInt(ticketIdString);
    boolean requiresApproval = Boolean.parseBoolean(requiresApprovalString);
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);

    setMaintenanceTicketAsState(maintenanceTicket, stateString, "manager@ap.com", "OneToThreeDays",
        "Low", requiresApproval);
  }

  @Given("ticket {string} is marked as {string}")
  public void ticket_is_marked_as(String ticketIdString, String stateString) {
    int ticketId = Integer.parseInt(ticketIdString);
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);
    setMaintenanceTicketAsState(maintenanceTicket, stateString, "manager@ap.com", "OneToThreeDays",
        "Low", false);
  }

  /**
   * Step definition to retrieve all the maintenance tickets in the system.
   * 
   * @author Julien Audet
   */
  @When("the manager attempts to view all maintenance tickets in the system")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system() {
    tOMaintenanceTickets = AssetPlusFeatureSet6Controller.getTickets();
  }


  /**
   * Assigns the maintenance tickets to an employee. Adds the relevant ticket information, like time
   * estimate, priority, requires approval.
   * 
   * @author Julien Audet
   * @param ticketIdString String containing the id of the ticket to assign
   * @param assigneeEmail String containing the email of the employee to assign
   * @param timeEstimateString String containing the time estimate to set
   * @param priorityString String containing the priority to set
   * @param requiresApprovalString String containing whether whether the ticket requires manager
   *        approval
   */
  @When("the manager attempts to assign the ticket {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_ticket_to_with_estimated_time_priority_and_requires_approval(
      String ticketIdString, String assigneeEmail, String timeEstimateString, String priorityString,
      String requiresApprovalString) {
    int ticketId = Integer.parseInt(ticketIdString);
    MaintenanceTicket.TimeEstimate timeEstimate = parseTimeEstimate(timeEstimateString);
    MaintenanceTicket.PriorityLevel priorityLevel = parsePriorityLevel(priorityString);
    boolean requiresApproval = Boolean.parseBoolean(requiresApprovalString);

    callController(AssetPlusStateController.assignTicket(ticketId, assigneeEmail, timeEstimate,
        priorityLevel, requiresApproval));
  }

  /**
   * Mark ticket as in progress
   * 
   * @author Julien Audet
   * @param ticketIdString String containing the id of the ticket to assign
   */
  @When("the hotel staff attempts to start the ticket {string}")
  public void the_hotel_staff_attempts_to_start_the_ticket(String ticketIdString) {
    int ticketId = Integer.parseInt(ticketIdString);
    callController(AssetPlusStateController.startTicket(ticketId));
  }

  /**
   * Attempts to approve a ticket in the system.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format that needs to be approved.
   */
  @When("the manager attempts to approve the ticket {string}")
  public void the_manager_attempts_to_approve_the_ticket(String ticketIdString) {
    int ticketId = Integer.parseInt(ticketIdString);
    callController(AssetPlusStateController.approveTicket(ticketId));
  }

  /**
   * Attempts to complete a ticket in the system.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format that needs to be completed.
   */
  @When("the hotel staff attempts to complete the ticket {string}")
  public void the_hotel_staff_attempts_to_complete_the_ticket(String ticketIdString) {
    int ticketId = Integer.parseInt(ticketIdString);
    callController(AssetPlusStateController.resolveTicket(ticketId));
  }

  /**
   * Attempts to disapprove a ticket in the system on a specific date and for a specific reason.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format that is to be disapproved.
   * @param dateString The date on which the ticket is disapproved, in string format.
   * @param reason The reason for disapproving the ticket.
   */
  @When("the manager attempts to disapprove the ticket {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_ticket_on_date_and_with_reason(
      String ticketIdString, String dateString, String reason) {
    int ticketId = Integer.parseInt(ticketIdString);
    Date date = Date.valueOf(dateString);
    callController(AssetPlusStateController.disapproveTicket(ticketId, date, reason));
  }

  /**
   * Checks if the ticket is marked with the expected state.
   * 
   * This method asserts whether a ticket, identified by its ID, is marked as a specific state in
   * the system. It compares the actual state of the ticket with the expected state provided.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format.
   * @param expectedStateString The expected state of the ticket in string format.
   */
  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String ticketIdString, String expectedStateString) {
    int ticketId = Integer.parseInt(ticketIdString);
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);

    String actualStateString = maintenanceTicket.getPossible_stateFullName();
    assertEquals(expectedStateString, actualStateString);
  }

  /**
   * Ensures that the system raises a specific error.
   * 
   * This method is used to validate that the system raises an expected error. It compares the
   * actual error raised by the system with the expected error.
   * 
   * @author Julien Audet
   * @param actualError The actual error message raised by the system.
   */
  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String actualError) {
    assertEquals(error, actualError);
  }

  /**
   * Validates that a ticket does not exist in the system.
   * 
   * This method checks that no ticket exists in the system with the given ID. It asserts that the
   * ticket, when queried, returns null, indicating non-existence.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format which should not exist.
   */
  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String ticketIdString) {
    int ticketId = Integer.parseInt(ticketIdString);

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);
    assertNull(maintenanceTicket);
  }

  /**
   * Confirms that a ticket has the specified time estimate, priority, and approval requirement.
   * 
   * This method verifies the properties of a ticket such as estimated time to resolve, priority
   * level, and whether it requires approval. It asserts these properties against the expected
   * values provided.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format.
   * @param expectedTimeEstimateString The expected time estimate for the ticket.
   * @param expectedPriorityString The expected priority level of the ticket.
   * @param expectedRequiresApprovalString String indicating whether the ticket requires approval.
   */
  @Then("the ticket {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_ticket_shall_have_estimated_time_priority_and_requires_approval(
      String ticketIdString, String expectedTimeEstimateString, String expectedPriorityString,
      String expectedRequiresApprovalString) {
    int ticketId = Integer.parseInt(ticketIdString);
    MaintenanceTicket.TimeEstimate expectedTimeEstimate =
        parseTimeEstimate(expectedTimeEstimateString);
    MaintenanceTicket.PriorityLevel expectedPriority = parsePriorityLevel(expectedPriorityString);
    boolean expectedRequiresApproval = Boolean.parseBoolean(expectedRequiresApprovalString);

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);
    MaintenanceTicket.TimeEstimate actualTimeEstimate = maintenanceTicket.getTimeToResolve();
    MaintenanceTicket.PriorityLevel actualPriority = maintenanceTicket.getPriority();
    boolean actualRequiresApproval = maintenanceTicket.hasFixApprover();

    assertEquals(expectedTimeEstimate, actualTimeEstimate);
    assertEquals(expectedPriority, actualPriority);
    assertEquals(expectedRequiresApproval, actualRequiresApproval);
  }

  /**
   * Validates that the ticket is assigned to the expected employee.
   * 
   * This method confirms whether a specific ticket, identified by its ID, is assigned to an
   * employee whose email matches the expected email. It asserts the actual assigned employee's
   * email against the expected one.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format.
   * @param expectedEmployeeEmail The expected email of the employee to whom the ticket is assigned.
   */
  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String ticketIdString, String expectedEmployeeEmail) {
    int ticketId = Integer.parseInt(ticketIdString);
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);

    HotelStaff actualTicketFixer = maintenanceTicket.getTicketFixer();
    String actualTicketFixerEmail = actualTicketFixer.getEmail();

    assertEquals(expectedEmployeeEmail, actualTicketFixerEmail);
  }

  /**
   * Asserts the total number of tickets in the system.
   * 
   * This method checks if the total number of maintenance tickets in the system matches the
   * expected number. It compares the actual number of tickets with the expected number provided as
   * a string.
   * 
   * @author Julien Audet
   * @param expectedNumTicketsString The expected number of tickets in the system in string format.
   */
  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String expectedNumTicketsString) {
    int numberOfTickets = assetPlus.getMaintenanceTickets().size();
    int expectedNumTickets = Integer.parseInt(expectedNumTicketsString);
    assertEquals(expectedNumTickets, numberOfTickets);
  }

  /**
   * Validates the presentation of maintenance tickets against a provided data table.
   * 
   * This method ensures that the maintenance tickets presented in the system match the expected
   * tickets outlined in the provided data table. It compares the actual ticket data with the
   * expected data.
   * 
   * @author Julien Audet
   * @param dataTable The data table containing the expected ticket information.
   */
  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
        
        List<List<String>> rows = dataTable.asLists(String.class);
        Map<String, List<String>> actualTicketsToMap = getMaintenanceTicketToMapAsStrings();   
        
        assertEquals(rows.size()-1, actualTicketsToMap.size());  // -1 since header row

    for (int i = 1; i < rows.size(); i++) {
      List<String> row = rows.get(i);
      String ticketIdString = row.get(0);
      String expectedRequiresManagerApprovalString = row.get(13);
      if (expectedRequiresManagerApprovalString == null || expectedRequiresManagerApprovalString == "null") {
        expectedRequiresManagerApprovalString = "false";
      }
      
      for (int j = 0; j<row.size(); j++){
        if (row.get(i) == null){
          row.set(i, "null");
        }
      }

      List<String> actualTicketTo = actualTicketsToMap.get(ticketIdString);

      for (int j = 0; j<row.size(); j++){
        assertEquals(row.get(i), actualTicketTo.get(i));
      }
      
    }
  }

  /**
   * Verifies the notes associated with a particular ticket.
   * 
   * This method checks the notes of a specific ticket, identified by its ID, against the expected
   * notes outlined in the provided data table. It ensures that each note's details match the
   * expected information.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format.
   * @param dataTable The data table containing the expected notes information.
   */
  @Then("the ticket with id {string} shall have the following notes")
  public void the_ticket_with_id_shall_have_the_following_notes(String ticketIdString,
      io.cucumber.datatable.DataTable dataTable) {

    int ticketId = Integer.parseInt(ticketIdString);
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);
    List<MaintenanceNote> maintenanceNotes = maintenanceTicket.getTicketNotes();

    List<List<String>> rows = dataTable.asLists(String.class);

    for (int i = 1; i < maintenanceNotes.size(); i++) {
      MaintenanceNote actualMaintenanceNote = maintenanceNotes.get(i);
      String actualNoteTakerEmail = actualMaintenanceNote.getNoteTaker().getEmail();
      String actualAddedOnDate = String.valueOf(actualMaintenanceNote.getDate());
      String actualDescription = actualMaintenanceNote.getDescription();


      List<String> expectedMaintenanceTicketNoteAsList = rows.get(i);
      String expectedNoteTakerEmail = expectedMaintenanceTicketNoteAsList.get(0);
      String expectedAddedOnDate = expectedMaintenanceTicketNoteAsList.get(1);
      String expectedDescription = expectedMaintenanceTicketNoteAsList.get(2);

      assertEquals(expectedNoteTakerEmail, actualNoteTakerEmail);
      assertEquals(expectedAddedOnDate, actualAddedOnDate);
      assertEquals(expectedDescription, actualDescription);
    }
  }

  /**
   * Verifies that the specified ticket has no notes attached.
   * 
   * This method checks if a particular ticket, identified by its ID, has no maintenance notes
   * associated with it. It asserts that the list of notes for the ticket is empty.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format.
   */
  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String ticketIdString) {
    Map<String, TOMaintenanceTicket> maintenanceTicketToMap = getMaintenanceTicketToMap();
    TOMaintenanceTicket maintenanceTicketTo = maintenanceTicketToMap.get(ticketIdString);
    List<TOMaintenanceNote> maintenanceTicketNoteTos = maintenanceTicketTo.getNotes();

    assertTrue(maintenanceTicketNoteTos.isEmpty());
  }

  /**
   * Ensures that the ticket contains the expected images.
   * 
   * This method checks if a ticket, identified by its ID, includes a specific set of images. It
   * compares the actual image URLs attached to the ticket with the expected URLs provided in the
   * data table.
   * 
   * @author Julien Audet
   * @param ticketIdString The ticket ID in string format.
   * @param dataTable The data table containing the expected image URLs.
   */
  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String ticketIdString,
      io.cucumber.datatable.DataTable dataTable) {
    Map<String, TOMaintenanceTicket> maintenanceTicketToMap = getMaintenanceTicketToMap();
    TOMaintenanceTicket maintenanceTicketTo = maintenanceTicketToMap.get(ticketIdString);
    List<String> actualImageUrls = maintenanceTicketTo.getImageURLs();

    List<String> expectedImageUrls = dataTable.asList();
    
    assertEquals(expectedImageUrls.size() - 1, actualImageUrls.size());  // -1 for the header row

    for (int i = 0; i<actualImageUrls.size(); i++){
      assertEquals(expectedImageUrls.get(i+1), actualImageUrls.get(i));
    }
  }

  /**
   * Validates that the specified ticket does not have any images.
   * 
   * This method confirms that a ticket, identified by its ID, does not have any images attached. It
   * asserts that the list of image URLs for the ticket is empty.
   * 
   * @author Julien Audet
   * @param tickeIdString The ticket ID in string format.
   */
  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String tickeIdString) {
    Map<String, TOMaintenanceTicket> maintenanceTicketToMap = getMaintenanceTicketToMap();
    TOMaintenanceTicket maintenanceTicketTo = maintenanceTicketToMap.get(tickeIdString);
    List<String> actualImageUrls = maintenanceTicketTo.getImageURLs();

    assertTrue(actualImageUrls.isEmpty());
  }



  private MaintenanceTicket.TimeEstimate parseTimeEstimate(String timeEstimateString) {
    switch (timeEstimateString) {
      case "LessThanADay":
        return MaintenanceTicket.TimeEstimate.LessThanADay;
      case "OneToThreeDays":
        return MaintenanceTicket.TimeEstimate.OneToThreeDays;
      case "ThreeToSevenDays":
        return MaintenanceTicket.TimeEstimate.ThreeToSevenDays;
      case "OneToThreeWeeks":
        return MaintenanceTicket.TimeEstimate.OneToThreeWeeks;
      case "ThreeOrMoreWeeks":
        return MaintenanceTicket.TimeEstimate.ThreeOrMoreWeeks;
      default:
        return null;
    }
  }

  private MaintenanceTicket.PriorityLevel parsePriorityLevel(String priorityLevelString) {
    switch (priorityLevelString) {
      case "Urgent":
        return MaintenanceTicket.PriorityLevel.Urgent;
      case "Normal":
        return MaintenanceTicket.PriorityLevel.Normal;
      case "Low":
        return MaintenanceTicket.PriorityLevel.Low;
      default:
        return null;
    }
  }

  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
    }
  }


  private Map<String, TOMaintenanceTicket> getMaintenanceTicketToMap() {
    Map<String, TOMaintenanceTicket> ticketsToMap = new HashMap<>();

    for (var maintenanceTicketTo : tOMaintenanceTickets) {
      String ticketIdString = String.valueOf(maintenanceTicketTo.getId());
      ticketsToMap.put(ticketIdString, maintenanceTicketTo);
    }
    return ticketsToMap;
  }


  private Map<String, List<String>> getMaintenanceTicketToMapAsStrings() {
    Map<String, List<String>> ticketsToMapAsStrings = new HashMap<>();

    for (var maintenanceTicketTo : tOMaintenanceTickets) {
      String ticketIdString = String.valueOf(maintenanceTicketTo.getId());
      String ticketRaiserEmail = maintenanceTicketTo.getRaisedByEmail();
      String raisedOnDateString = maintenanceTicketTo.getRaisedOnDate().toString();
      String description = maintenanceTicketTo.getDescription();
      String assetName = String.valueOf(maintenanceTicketTo.getAssetName());
      String expectedLifeSpanString =
          parsePositiveIntTicketPropertyToString(maintenanceTicketTo.getExpectLifeSpanInDays());
      String purchaseDateString = String.valueOf(maintenanceTicketTo.getPurchaseDate());
      String floorNumberString =
          parsePositiveIntTicketPropertyToString(maintenanceTicketTo.getFloorNumber());
      String roomNumberString =
          parsePositiveIntTicketPropertyToString(maintenanceTicketTo.getRoomNumber());
      String state = maintenanceTicketTo.getStatus();
      String ticketFixerEmail = maintenanceTicketTo.getFixedByEmail();
      String timeEstimateString = maintenanceTicketTo.getTimeToResolve();
      String priorityString = maintenanceTicketTo.getPriority();
      String requiresManagerApprovalString =
          String.valueOf(maintenanceTicketTo.getApprovalRequired());

      List<String> oneTicketTO = new ArrayList<>();
      oneTicketTO.add(ticketIdString);
      oneTicketTO.add(ticketRaiserEmail);
      oneTicketTO.add(raisedOnDateString);
      oneTicketTO.add(description);
      oneTicketTO.add(assetName);
      oneTicketTO.add(expectedLifeSpanString);
      oneTicketTO.add(purchaseDateString);
      oneTicketTO.add(floorNumberString);
      oneTicketTO.add(roomNumberString);
      oneTicketTO.add(state);
      oneTicketTO.add(ticketFixerEmail);
      oneTicketTO.add(timeEstimateString);
      oneTicketTO.add(priorityString);
      oneTicketTO.add(requiresManagerApprovalString);

      ticketsToMapAsStrings.put(ticketIdString, oneTicketTO);
    }

    return ticketsToMapAsStrings;
  }


  private void setMaintenanceTicketAsState(MaintenanceTicket maintenanceTicket, String state,
      String fixedByEmail, String timeToResolveString, String priorityString,
      Boolean approvalRequired) {
    if (!maintenanceTicket.getPossible_stateFullName().equals("Open")) {
      throw new InvalidParameterException("ticket must start in open state");
    }
    switch (state) {
      case "Open":
        break;
      case "Assigned":
        setMaintenanceTicketAsAssigned(maintenanceTicket, fixedByEmail, timeToResolveString,
            priorityString, approvalRequired);
        break;
      case "InProgress":
        setMaintenanceTicketAsAssigned(maintenanceTicket, fixedByEmail, timeToResolveString,
            priorityString, approvalRequired);
        setAssignedMaintenanceTicketAsInProgress(maintenanceTicket);
        break;
      case "Resolved":
        setMaintenanceTicketAsAssigned(maintenanceTicket, fixedByEmail, timeToResolveString,
            priorityString, approvalRequired);
        setAssignedMaintenanceTicketAsInProgress(maintenanceTicket);
        setInProgressMaintenanceTicketAsResolved(maintenanceTicket, fixedByEmail);
        break;
      case "Closed":
        setMaintenanceTicketAsAssigned(maintenanceTicket, fixedByEmail, timeToResolveString,
            priorityString, approvalRequired);
        setAssignedMaintenanceTicketAsInProgress(maintenanceTicket);
        setInProgressMaintenanceTicketAsResolved(maintenanceTicket, fixedByEmail);
        setResolvedMaintenanceticketAsClosed(maintenanceTicket);
        break;
    }
    return;
  }


  private void setMaintenanceTicketAsAssigned(MaintenanceTicket maintenanceTicket,
      String fixedByEmail, String timeToResolveString, String priorityString,
      Boolean approvalRequired) {
    MaintenanceTicket.PriorityLevel priority = parsePriorityLevel(priorityString);
    MaintenanceTicket.TimeEstimate timeEstimate = parseTimeEstimate(timeToResolveString);

    HotelStaff ticketFixer;
    if (fixedByEmail == null || fixedByEmail.isEmpty()) {
      ticketFixer = (HotelStaff) HotelStaff.getWithEmail("manager@ap.com");
    } else {
      ticketFixer = (HotelStaff) HotelStaff.getWithEmail(fixedByEmail);
    }

    maintenanceTicket.assignStaff(priority, timeEstimate, ticketFixer, maintenanceTicket.getId(),
        "manager@ap.com");

    if (approvalRequired) {
      Manager manager = (Manager) Manager.getWithEmail("manager@ap.com");
      maintenanceTicket.setFixApprover(manager);
    }
    return;
  }

  private void setAssignedMaintenanceTicketAsInProgress(MaintenanceTicket maintenanceTicket) {
    // we're not given a user email to say started to work,
    // and I don't want to create a new user (could mess with the tests)
    if (maintenanceTicket.getPossible_stateFullName().equals("Assigned")) {
      maintenanceTicket.startedToWork("manager@ap.com");
    }
  }

  private void setInProgressMaintenanceTicketAsResolved(MaintenanceTicket maintenanceTicket,
      String fixedByEmail) {
    if (fixedByEmail == null) {
      fixedByEmail = "manager@ap.com";
    }
    maintenanceTicket.resolve(fixedByEmail, maintenanceTicket.getId());
  }

  private void setResolvedMaintenanceticketAsClosed(MaintenanceTicket maintenanceTicket) {
    if (maintenanceTicket.getPossible_stateFullName().equals("Resolved")) {
      maintenanceTicket.approve("manager@ap.com");
    }
  }


  private Integer parseAssetNumber(String assetNumberString) {
    if (assetNumberString == null || assetNumberString.isEmpty()) {
      return null;
    }
    return Integer.parseInt(assetNumberString);
  }


  private String parsePositiveIntTicketPropertyToString(int ticketProperty) {
    if (ticketProperty == -1) {
      return "null";
    }
    return String.valueOf(ticketProperty);
  }

  private Boolean parseStringTicketPropertyToBoolean(String ticketProperty) {
    if (ticketProperty == null) {
      return null;
    } else {
      return Boolean.parseBoolean(ticketProperty);
    }
  }

}
