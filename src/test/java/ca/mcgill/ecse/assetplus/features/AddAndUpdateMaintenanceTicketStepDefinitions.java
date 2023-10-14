package ca.mcgill.ecse.assetplus.features;

import java.util.List;
import java.util.Map;
import java.sql.Date;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateMaintenanceTicketStepDefinitions {
	
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
	
  @Given("the following employees exist in the system \\(p16)")
  public void the_following_employees_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
	List<List<String>> rows = dataTable.asList(String.class);
	for (var row : rows) {
		String email = row.get(0);
		String password = row.get(1);
		String name = row.get(2);
		String phoneNumber = row.get(3);
		assetPlus.addEmployee(email, name, password, phoneNumber);
	}
	
  }

  @Given("the following manager exists in the system \\(p16)")
  public void the_following_manager_exists_in_the_system_p16(
	  io.cucumber.datatable.DataTable dataTable) {
	List<Map<String, String>> rows = dataTable.asMaps();
	
	
	for (var row : rows) {
		String email = row.get("email");
		String password = row.get("password");
		User user = User.getWithEmail(email);
		Manager manager = new Manager(email, user.getName(), password, user.getPhoneNumber(), assetPlus);
		assetPlus.setManager(manager);
		
	} 
 
  }

  @Given("the following guests exist in the system \\(p16)")
  public void the_following_guests_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
	List<List<String>> rows = dataTable.asList(String.class);
	for (var row : rows) {
		String email = row.get(0);
		String password = row.get(1);
		String name = row.get(2);
		String phoneNumber = row.get(3);
		assetPlus.addGuest(email, name, password, phoneNumber);
	}
	
  }

  @Given("the following asset types exist in the system \\(p16)")
  public void the_following_asset_types_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {	  
	List<Map<String, String>> rows = dataTable.asMaps();
	for (var row : rows) {
		String name = row.get("name");
		int lifeSpan = Integer.parseInt(row.get("expectedLifeSpan"));
		assetPlus.addAssetType(name, lifeSpan);
	} 
			
  }

  @Given("the following assets exist in the system \\(p16)")
  public void the_following_assets_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
	List<List<String>> rows = dataTable.asList(String.class);
	for (var row : rows) {
		int assetNumber = Integer.parseInt(row.get(0));
		AssetType type = AssetType.getWithName(row.get(1));
		Date purchaseDate = Date.valueOf(row.get(2));
		int floorNumber = Integer.parseInt(row.get(3));
		int roomNumber = Integer.parseInt(row.get(4));
		assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, type);
	}
  }

  @Given("the following tickets exist in the system \\(p16)")
  public void the_following_tickets_exist_in_the_system_p16(
      io.cucumber.datatable.DataTable dataTable) {
	List<List<String>> rows = dataTable.asList(String.class);
	for (var row : rows) {
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
  }

  @When("the user with email {string} attempts to add a new maintenance ticket to the system with id {string}, date {string}, description {string}, and asset number {string} \\(p16)")
  public void the_user_with_email_attempts_to_add_a_new_maintenance_ticket_to_the_system_with_id_date_description_and_asset_number_p16(
      String string, String string2, String string3, String string4, String string5) {
    User ticketRaiser = User.getWithEmail(string);
    int id = Integer.parseInt(string2);
    Date date = Date.valueOf(string3);
    String description = string4;
    int assetNumber = Integer.parseInt(string5);
    SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
    
	assetPlus.addMaintenanceTicket(id, date, description, ticketRaiser);
	MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
	thisTicket.setAsset(asset);
	
	
  }

  @When("the user with email {string} attempts to add a new maintenance ticket to the system with id {string}, date {string}, and description {string} but no asset number \\(p16)")
  public void the_user_with_email_attempts_to_add_a_new_maintenance_ticket_to_the_system_with_id_date_and_description_but_no_asset_number_p16(
      String string, String string2, String string3, String string4) { 
	User ticketRaiser = User.getWithEmail(string);
    int id = Integer.parseInt(string2);
    Date date = Date.valueOf(string3);
    String description = string4;
    
	assetPlus.addMaintenanceTicket(id, date, description, ticketRaiser);
	  
  }

  @When("the manager attempts to update the maintenance ticket with id {string} to ticket raiser {string}, date {string}, and description {string} but no asset number \\(p16)")
  public void the_manager_attempts_to_update_the_maintenance_ticket_with_id_to_ticket_raiser_date_and_description_but_no_asset_number_p16(
      String string, String string2, String string3, String string4) {
    
    int id = Integer.parseInt(string);
    User ticketRaiser = User.getWithEmail(string2);
    Date date = Date.valueOf(string3);
    String description = string4;
    MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
    
    thisTicket.setTicketRaiser(ticketRaiser);
    thisTicket.setRaisedOnDate(date);
    thisTicket.setDescription(description);
	  
	  
  }

  @When("the manager attempts to update the maintenance ticket with id {string} to ticket raiser {string}, date {string}, description {string}, and asset number {string} \\(p16)")
  public void the_manager_attempts_to_update_the_maintenance_ticket_with_id_to_ticket_raiser_date_description_and_asset_number_p16(
      String string, String string2, String string3, String string4, String string5) {
    
    int id = Integer.parseInt(string);
    User ticketRaiser = User.getWithEmail(string2);
    Date date = Date.valueOf(string3);
    String description = string4;
    int assetNumber = Integer.parseInt(string5);
    SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
    MaintenanceTicket thisTicket = MaintenanceTicket.getWithId(id);
    
    thisTicket.setTicketRaiser(ticketRaiser);
    thisTicket.setRaisedOnDate(date);
    thisTicket.setDescription(description);
    thisTicket.setAsset(asset);
	  
  }

  @Then("the number of tickets in the system shall be {string} \\(p16)")
  public void the_number_of_tickets_in_the_system_shall_be_p16(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following tickets shall exist in the system \\(p16)")
  public void the_following_tickets_shall_exist_in_the_system_p16(
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

  @Then("the ticket raised by {string} and with id {string}, date {string}, and description {string} but no asset shall exist in the system \\(p16)")
  public void the_ticket_raised_by_and_with_id_date_and_description_but_no_asset_shall_exist_in_the_system_p16(
      String string, String string2, String string3, String string4) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the ticket raised by {string} and with id {string}, date {string}, description {string}, and asset number {string} shall exist in the system \\(p16)")
  public void the_ticket_raised_by_and_with_id_date_description_and_asset_number_shall_exist_in_the_system_p16(
      String string, String string2, String string3, String string4, String string5) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the system shall raise the error {string} \\(p16)")
  public void the_system_shall_raise_the_error_p16(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
