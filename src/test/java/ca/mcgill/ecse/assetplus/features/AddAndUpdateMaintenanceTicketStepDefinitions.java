package ca.mcgill.ecse.assetplus.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateMaintenanceTicketStepDefinitions {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  private String error;
  private int errorCntr;

  /**
   * @author Tiffany Miller
   * @param dataTable The data table of the employees that must exist in the system.
   */
  @Given("the following employees exist in the system \\(p16)")
  public void the_following_employees_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i<rows.size(); i++) {
      List<String> row = rows.get(i);
      String email = row.get(0);
      String password = row.get(1);
      String name = row.get(2);
      String phoneNumber = row.get(3);
      assetPlus.addEmployee(email, name, password, phoneNumber);
    }
    error = "";
    errorCntr = 0;

  }

  /**
   * @author Tiffany Miller
   * @param dataTable The data table of the manager that must exist in the system.
   */
  @Given("the following manager exists in the system \\(p16)")
  public void the_following_manager_exists_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (int i = 0; i<rows.size(); i++) {
      Map<String, String> row = rows.get(i);
      String email = row.get("email");
      String password = row.get("password");
      System.out.println("first row: " +  email + password + " eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
      Manager manager =
          new Manager(email, "manager", password, "(123)456-7890", assetPlus);
      assetPlus.setManager(manager);
    }
    error = "";
    errorCntr = 0;

  }

  /**
   * @author Tiffany Miller
   * @param dataTable The data table of the guests that must exist in the system.
   */
  @Given("the following guests exist in the system \\(p16)")
  public void the_following_guests_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i<rows.size(); i++) {
      List<String> row = rows.get(i);
      String email = row.get(0);
      System.out.println("email: " + email + " ###################################################################################################################");
      String password = row.get(1);
      String name = row.get(2);
      String phoneNumber = row.get(3);
      assetPlus.addGuest(email, name, password, phoneNumber);
    }
    error = "";
    errorCntr = 0;

  }

  /**
   * @author Tiffany Miller
   * @param dataTable The data table of the asset types that must exist in the system.
   */
  @Given("the following asset types exist in the system \\(p16)")
  public void the_following_asset_types_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (int i = 0; i<rows.size(); i++) {
      Map<String, String> row = rows.get(i);
      String name = row.get("name");
      int lifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
      assetPlus.addAssetType(name, lifeSpan);
    }
    error = "";
    errorCntr = 0;

  }

  /**
   * @author Tiffany Miller
   * @param dataTable The data table of the assets that must exist in the system.
   */
  @Given("the following assets exist in the system \\(p16)")
  public void the_following_assets_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i<rows.size(); i++) {
      List<String> row = rows.get(i);
      int assetNumber = Integer.parseInt(row.get(0));
      AssetType type = AssetType.getWithName(row.get(1));
      Date purchaseDate = Date.valueOf(row.get(2));
      int floorNumber = Integer.parseInt(row.get(3));
      int roomNumber = Integer.parseInt(row.get(4));
      assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, type);
    }
    error = "";
    errorCntr = 0;
  }

  /**
   * @author Tiffany Miller
   * @param dataTable The data table of the tickets that must exist in the system.
   */
  @Given("the following tickets exist in the system \\(p16)")
  public void the_following_tickets_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i<rows.size(); i++) {
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
    errorCntr = 0;
  }

  /**
   * @author Tiffany Miller
   * @param string The email of the user that wants to add a maintenance ticket.
   * @param string2 The new maintenance ticket id.
   * @param string3 The new maintenance ticket date
   * @param string4 The new maintenance ticket description.
   * @param string5 The new maintenance ticket's asset number.
   */
  @When("the user with email {string} attempts to add a new maintenance ticket to the system with id {string}, date {string}, description {string}, and asset number {string} \\(p16)")
  public void the_user_with_email_attempts_to_add_a_new_maintenance_ticket_to_the_system_with_id_date_description_and_asset_number_p16(
      String string, String string2, String string3, String string4, String string5) {

    String email = string;
    int id = Integer.parseInt(string2);
    Date date = Date.valueOf(string3);
    String description = string4;
    int assetNumber = Integer.parseInt(string5);

    callController(AssetPlusFeatureSet4Controller.addMaintenanceTicket(id, date, description, email,
        assetNumber));

  }

  /**
   * @author Tiffany Miller
   * @param string The email of the user that wants to add a maintenance ticket.
   * @param string2 The new maintenance ticket id.
   * @param string3 The new maintenance ticket date
   * @param string4 The new maintenance ticket description.
   */
  @When("the user with email {string} attempts to add a new maintenance ticket to the system with id {string}, date {string}, and description {string} but no asset number \\(p16)")
  public void the_user_with_email_attempts_to_add_a_new_maintenance_ticket_to_the_system_with_id_date_and_description_but_no_asset_number_p16(
      String string, String string2, String string3, String string4) {

    String email = string;
    int id = Integer.parseInt(string2);
    Date date = Date.valueOf(string3);
    String description = string4;
    int assetNumber = -1;

    callController(AssetPlusFeatureSet4Controller.addMaintenanceTicket(id, date, description, email,
        assetNumber));

  }

  /**
   * @author Tiffany Miller
   * @param string The id of the maintenance ticket the manager wants to update.
   * @param string2 Updated maintenance ticket raiser.
   * @param string3 Updated maintenance ticket date.
   * @param string4 Updated maintenance ticket asset description.
   */
  @When("the manager attempts to update the maintenance ticket with id {string} to ticket raiser {string}, date {string}, and description {string} but no asset number \\(p16)")
  public void the_manager_attempts_to_update_the_maintenance_ticket_with_id_to_ticket_raiser_date_and_description_but_no_asset_number_p16(
      String string, String string2, String string3, String string4) {

    int id = Integer.parseInt(string);
    String email = string2;
    Date date = Date.valueOf(string3);
    String description = string4;
    int assetNumber = -1;

    callController(AssetPlusFeatureSet4Controller.updateMaintenanceTicket(id, date, description,
        email, assetNumber));

  }

  /**
   * @author Tiffany Miller
   * @param string The id of the maintenance ticket the manager wants to update.
   * @param string2 Updated maintenance ticket raiser.
   * @param string3 Updated maintenance ticket date.
   * @param string4 Updated maintenance ticket description.
   * @param string5 Updated maintenance ticket asset number.
   */
  @When("the manager attempts to update the maintenance ticket with id {string} to ticket raiser {string}, date {string}, description {string}, and asset number {string} \\(p16)")
  public void the_manager_attempts_to_update_the_maintenance_ticket_with_id_to_ticket_raiser_date_description_and_asset_number_p16(
      String string, String string2, String string3, String string4, String string5) {

    int id = Integer.parseInt(string);
    String email = string2;
    Date date = Date.valueOf(string3);
    String description = string4;
    int assetNumber = Integer.parseInt(string5);

    callController(AssetPlusFeatureSet4Controller.updateMaintenanceTicket(id, date, description,
        email, assetNumber));
  }

  /**
   * @author Tiffany Miller
   * @param string The number of maintenance tickets that is expected in the system.
   */
  @Then("the number of tickets in the system shall be {string} \\(p16)")
  public void the_number_of_tickets_in_the_system_shall_be_p16(String string) {
    int numberOfTickets = assetPlus.getMaintenanceTickets().size();
    int expectedNumTickets = Integer.parseInt(string);
    assertEquals(expectedNumTickets, numberOfTickets);

  }

  /**
   * @author Tiffany Miller
   * @param dataTable Data table of maintenance tickets that is expected in the system.
   */
  @Then("the following tickets shall exist in the system \\(p16)")
  public void the_following_tickets_shall_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {

    List<List<String>> expectedTickets = new ArrayList<List<String>>();
    List<List<String>> actualTickets = new ArrayList<List<String>>();
    List<List<String>> rows = dataTable.asLists(String.class);
    for (int i = 1; i<rows.size(); i++) {
      List<String> row = rows.get(i);
      List<String> oneTicket = new ArrayList<String>();
      String id = row.get(0);
      String ticketRaiser = row.get(1);
      String raisedOnDate = row.get(2);
      String description = row.get(3);
      String assetNumber = row.get(4);

      oneTicket.add(id);
      oneTicket.add(ticketRaiser);
      oneTicket.add(raisedOnDate);
      oneTicket.add(description);
      oneTicket.add(assetNumber);

      expectedTickets.add(oneTicket);
    }

    List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();
    assertNotNull(maintenanceTickets);

    for (var ticket : maintenanceTickets) {
      assertNotNull(ticket);
      List<String> oneTicket = new ArrayList<String>();
      String id = String.valueOf(ticket.getId());
      String ticketRaiser = ticket.getTicketRaiser().getEmail();
      String raisedOnDate = ticket.getRaisedOnDate().toString();
      String description = ticket.getDescription();
      String assetNumber = String.valueOf(ticket.getAsset().getAssetNumber());

      oneTicket.add(id);
      oneTicket.add(ticketRaiser);
      oneTicket.add(raisedOnDate);
      oneTicket.add(description);
      oneTicket.add(assetNumber);

      actualTickets.add(oneTicket);
    }

    assertTrue(expectedTickets.size() == actualTickets.size());
    if (expectedTickets.size() == actualTickets.size()) {
      for (int i = 0; i < expectedTickets.size(); i++) {
        for (int j = 0; j < expectedTickets.get(i).size(); j++) {
          assertEquals(expectedTickets.get(i).get(j), actualTickets.get(i).get(j));

        }
      }
    }

  }

  /**
   * @author Tiffany Miller
   * @param string The email of the person who raised the ticket.
   * @param string2 The id of the new maintenance ticket raised.
   * @param string3 The date of the new maintenance ticket raised.
   * @param string4 The description of the new maintenance ticket raised.
   */
  @Then("the ticket raised by {string} and with id {string}, date {string}, and description {string} but no asset shall exist in the system \\(p16)")
  public void the_ticket_raised_by_and_with_id_date_and_description_but_no_asset_shall_exist_in_the_system_p16(
      String string, String string2, String string3, String string4) {

    String email = string;
    User ticketRaiser = User.getWithEmail(email);
    int id = Integer.valueOf(string2);
    Date date = Date.valueOf(string3);
    String description = string4;

    MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
    assertNotNull(thisTicket);
    assertEquals(ticketRaiser, thisTicket.getTicketRaiser());
    assertEquals(date, thisTicket.getRaisedOnDate());
    assertEquals(description, thisTicket.getDescription());
    assertTrue(!thisTicket.hasAsset());

  }

  /**
   * @author Tiffany Miller
   * @param string The email of the person who raised the ticket.
   * @param string2 The id of the new maintenance ticket raised.
   * @param string3 The date of the new maintenance ticket raised.
   * @param string4 The description of the new maintenance ticket raised.
   * @param string5 The asset number associated to the new maintenance ticket raised.
   */
  @Then("the ticket raised by {string} and with id {string}, date {string}, description {string}, and asset number {string} shall exist in the system \\(p16)")
  public void the_ticket_raised_by_and_with_id_date_description_and_asset_number_shall_exist_in_the_system_p16(
      String string, String string2, String string3, String string4, String string5) {

    String email = string;
    User ticketRaiser = User.getWithEmail(email);
    int id = Integer.valueOf(string2);
    Date date = Date.valueOf(string3);
    String description = string4;
    int assetNumber = Integer.valueOf(string5);
    SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);

    MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
    assertNotNull(thisTicket);
    assertEquals(ticketRaiser, thisTicket.getTicketRaiser());
    assertEquals(date, thisTicket.getRaisedOnDate());
    assertEquals(description, thisTicket.getDescription());
    assertEquals(asset, thisTicket.getAsset());
  }

  /**
   * @author Tiffany Miller
   * @param string The error string that should be raised.
   */
  @Then("the system shall raise the error {string} \\(p16)")
  public void the_system_shall_raise_the_error_p16(String string) {
    assertEquals(string, error);
    assertEquals(1, errorCntr);
  }

  /**
   * @author Tiffany Miller
   * @param result The error result after calling controller method.
   */
  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
      errorCntr += 1;
    }
  }
}
