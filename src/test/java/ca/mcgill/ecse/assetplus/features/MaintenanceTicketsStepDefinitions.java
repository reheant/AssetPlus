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
      SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
      assetPlus.addMaintenanceTicket(id, raisedOnDate, description, raiserUser);
      MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
      thisTicket.setAsset(asset);

      if (row.size() > 9) {
        String stateString = row.get(5);
        String fixedByEmail = row.get(6);
        String timeEstimateString = row.get(7);
        String priorityString = row.get(8);
        boolean approvalRequired = Boolean.parseBoolean(row.get(9));
        setMaintenanceTicketAsState(thisTicket, stateString, fixedByEmail, timeEstimateString,
            priorityString, approvalRequired);
      }

    }
    error = "";
  }

  /**
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
   * @author Julien Audet
   * @param dataTable dataTable of the images that must exist in the system.
   */
  @Given("the following ticket images exist in the system")
  public void the_following_ticket_images_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (int i = 1; i < rows.size(); i++) {
      Map<String, String> row = rows.get(i);
      String imageUrl = row.get("imageUrl");
      int ticketId = Integer.parseInt(row.get("ticketId"));

      MaintenanceTicket maintenanceTicket = assetPlus.getMaintenanceTicket(ticketId);

      maintenanceTicket.addTicketImage(imageUrl);
    }
  }

  /**
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

  @When("the hotel staff attempts to start the ticket {string}")
  public void the_hotel_staff_attempts_to_start_the_ticket(String ticketIdString) {
    int ticketId = Integer.parseInt(ticketIdString);
    callController(AssetPlusStateController.startTicket(ticketId));
  }

  @When("the manager attempts to approve the ticket {string}")
  public void the_manager_attempts_to_approve_the_ticket(String ticketIdString) {
    int ticketId = Integer.parseInt(ticketIdString);
    callController(AssetPlusStateController.approveTicket(ticketId));
  }

  @When("the hotel staff attempts to complete the ticket {string}")
  public void the_hotel_staff_attempts_to_complete_the_ticket(String ticketIdString) {
    int ticketId = Integer.parseInt(ticketIdString);
    callController(AssetPlusStateController.resolveTicket(ticketId));
  }

  @When("the manager attempts to disapprove the ticket {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_ticket_on_date_and_with_reason(
      String ticketIdString, String dateString, String reason) {
    int ticketId = Integer.parseInt(ticketIdString);
    Date date = Date.valueOf(dateString);
    callController(AssetPlusStateController.disapproveTicket(ticketId, date, reason));
  }

  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String ticketIdString, String expectedStateString) {
    int ticketId = Integer.parseInt(ticketIdString);
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);

    String actualStateString = maintenanceTicket.getPossible_stateFullName();
    assertEquals(expectedStateString, actualStateString);
  }

  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String actualError) {
    assertEquals(error, actualError);
  }

  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String ticketIdString) {
    int ticketId = Integer.parseInt(ticketIdString);

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);
    assertNull(maintenanceTicket);
  }

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

  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String ticketIdString, String expectedEmployeeEmail) {
    int ticketId = Integer.parseInt(ticketIdString);
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId);

    HotelStaff actualTicketFixer = maintenanceTicket.getTicketFixer();
    String actualTicketFixerEmail = actualTicketFixer.getEmail();

    assertEquals(expectedEmployeeEmail, actualTicketFixerEmail);
  }

  /**
   * @author Tiffany Miller, Julien Audet
   */
  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String expectedNumTicketsString) {
    int numberOfTickets = assetPlus.getMaintenanceTickets().size();
    int expectedNumTickets = Integer.parseInt(expectedNumTicketsString);
    assertEquals(expectedNumTickets, numberOfTickets);
  }

  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
    Map<String, List<String>> expectedTicketsToMap = new HashMap<>();

    List<List<String>> rows = dataTable.asLists(String.class);

    for (int i = 1; i < rows.size(); i++) {
      List<String> row = rows.get(i);
      String ticketIdString = row.get(0);
      expectedTicketsToMap.put(ticketIdString, row);
    }

    Map<String, List<String>> actualTicketsToMap = getMaintenanceTicketToMapAsStrings();

    assertEquals(expectedTicketsToMap.size(), actualTicketsToMap.size());
    assertEquals(expectedTicketsToMap, actualTicketsToMap);
  }

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

  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String ticketIdString) {
    Map<String, TOMaintenanceTicket> maintenanceTicketToMap = getMaintenanceTicketToMap();
    TOMaintenanceTicket maintenanceTicketTo = maintenanceTicketToMap.get(ticketIdString);
    List<TOMaintenanceNote> maintenanceTicketNoteTos = maintenanceTicketTo.getNotes();

    assertTrue(maintenanceTicketNoteTos.isEmpty());
  }

  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String ticketIdString,
      io.cucumber.datatable.DataTable dataTable) {
    Map<String, TOMaintenanceTicket> maintenanceTicketToMap = getMaintenanceTicketToMap();
    TOMaintenanceTicket maintenanceTicketTo = maintenanceTicketToMap.get(ticketIdString);
    List<String> actualImageUrls = maintenanceTicketTo.getImageURLs();

    List<String> expectedImageUrls = dataTable.asList();
    expectedImageUrls.remove(0); // column names

    assertEquals(expectedImageUrls, actualImageUrls);
  }

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
      String expectedLifeSpanString = String.valueOf(maintenanceTicketTo.getExpectLifeSpanInDays());
      String purchaseDateString = String.valueOf(maintenanceTicketTo.getPurchaseDate());
      String floorNumberString = String.valueOf(maintenanceTicketTo.getFloorNumber());
      String roomNumberString = String.valueOf(maintenanceTicketTo.getRoomNumber());
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
      boolean approvalRequired) {
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
        setInProgressMaintenanceTicketAsResolved(maintenanceTicket);
        break;
      case "Closed":
        setMaintenanceTicketAsAssigned(maintenanceTicket, fixedByEmail, timeToResolveString,
            priorityString, approvalRequired);
        setAssignedMaintenanceTicketAsInProgress(maintenanceTicket);
        setInProgressMaintenanceTicketAsResolved(maintenanceTicket);
        setResolvedMaintenanceticketAsClosed(maintenanceTicket);
        break;
    }
    return;
  }


  private void setMaintenanceTicketAsAssigned(MaintenanceTicket maintenanceTicket,
      String fixedByEmail, String timeToResolveString, String priorityString,
      boolean approvalRequired) {
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

  private void setInProgressMaintenanceTicketAsResolved(MaintenanceTicket maintenanceTicket) {
    maintenanceTicket.resolve("manager@ap.com", maintenanceTicket.getId());
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



}
