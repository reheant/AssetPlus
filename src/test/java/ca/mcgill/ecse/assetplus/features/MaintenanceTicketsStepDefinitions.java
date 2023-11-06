package ca.mcgill.ecse.assetplus.features;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
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
      int assetNumber = Integer.parseInt(row.get(0));
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
      int assetNumber = Integer.parseInt(row.get(4));
      User raiserUser = User.getWithEmail(ticketRaiser);
      SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
      assetPlus.addMaintenanceTicket(id, raisedOnDate, description, raiserUser);
      MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
      thisTicket.setAsset(asset);
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
      MaintenanceTicket maintenanceTicket = assetPlus.getMaintenanceTicket(ticketId);

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
   * @param string 
   * @param string2
   * @param string3
   */
  @Given("ticket {string} is marked as {string} with requires approval {string}")
  public void ticket_is_marked_as_with_requires_approval(String ticketIdString, String stateString,
      String requiresApprovalString) {
    int ticketId = Integer.parseInt(ticketIdString);
    // some parsing to do with stateString?
    boolean requiresApproval = Boolean.parseBoolean(requiresApprovalString);

    MaintenanceTicket maintenanceTicket = assetPlus.getMaintenanceTicket(ticketId);

    maintenanceTicket//.setState(stateString);  // TODO implement states

  }

  @Given("ticket {string} is marked as {string}")
  public void ticket_is_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the manager attempts to view all maintenance tickets in the system")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the manager attempts to assign the ticket {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_ticket_to_with_estimated_time_priority_and_requires_approval(
      String string, String string2, String string3, String string4, String string5) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the hotel staff attempts to start the ticket {string}")
  public void the_hotel_staff_attempts_to_start_the_ticket(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the manager attempts to approve the ticket {string}")
  public void the_manager_attempts_to_approve_the_ticket(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the hotel staff attempts to complete the ticket {string}")
  public void the_hotel_staff_attempts_to_complete_the_ticket(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the manager attempts to disapprove the ticket {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_ticket_on_date_and_with_reason(String string,
      String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_ticket_shall_have_estimated_time_priority_and_requires_approval(String string,
      String string2, String string3, String string4) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket with id {string} shall have the following notes")
  public void the_ticket_with_id_shall_have_the_following_notes(String string,
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String string,
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
