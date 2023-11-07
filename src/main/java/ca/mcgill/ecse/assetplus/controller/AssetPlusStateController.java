package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.*;

import java.sql.Date;

public class AssetPlusStateController {

    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

    /**
     * Assigns a ticket to an employee.
     *
     * @author Luke Freund
     * @param ticketID The unique identifier of the maintenance ticket.
     * @param employeeEmail The email of a hotel staff member.
     * @param timeEstimate The estimated completion time of the maintenance ticket.
     * @param priorityLevel The priority level of the maintenance ticket.
     * @param requiresManagerApproval Indicates if the ticket requires a manager's approval before closing.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String assignTicket(int ticketID, String employeeEmail, TimeEstimate timeEstimate,
                                      PriorityLevel priorityLevel, boolean requiresManagerApproval) {
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            HotelStaff employee = (HotelStaff)User.getWithEmail(employeeEmail);

            var error = "";
            error += assertTicketExists(ticket);
            error += assertEmployeeExists(employee);
            error += assertTicketAssignable(ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }

            Manager manager = assetPlus.getManager();
            ticket.assignStaff(priorityLevel, timeEstimate, employee, ticketID, manager.getEmail());
            if (requiresManagerApproval){
                ticket.setFixApprover(manager);
            }

            return "";
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to assign a ticket" + e.getMessage();
        }

    }

    /**
     * Starts the work on a maintenance ticket.
     *
     * @author Luke Freund
     * @param ticketID The unique identifier of the maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String startTicket(int ticketID){
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            var error = "";
            error += assertTicketExists(ticket);
            error += assertTicketStartable(ticket);
            if (!error.isEmpty()) {
                return error.trim();
            }
            String employeeEmail = ticket.getTicketFixer().getEmail();
            ticket.startedToWork(employeeEmail);

            return "";
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to start a ticket" + e.getMessage();
        }

    }

    /**
     * Completes the work on a maintenance ticket.
     *
     * @author Luke Freund
     * @param ticketID The unique identifier of the maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String completeTicket(int ticketID){
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            var error = "";
            error += assertTicketExists(ticket);
            error += assertTicketCompletable(ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }
            ticket.completed();
            return "";
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to complete a ticket" + e.getMessage();
        }
    }

    public static String disapproveTicket(int ticketID, Date date, String reason){
        return "";
    }

    public static String approveTicket(int ticketID){
        return "";
    }

    /**
     * Validates that the maintenance ticket exists in the system.
     *
     * @author Luke Freund
     * @param ticket The maintenance ticket. Must exist.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertTicketExists(MaintenanceTicket ticket) {
        if (ticket == null) {
            return "Maintenance ticket does not exist.";
        }
        return "";
    }

    /**
     * Validates that the employee exists in the system.
     *
     * @author Luke Freund
     * @param employee The employee. Must exist.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertEmployeeExists(HotelStaff employee){
        if (employee == null){
            return "Staff to assign does not exist.";
        }
        return "";
    }

    /**
     * Validates that the maintenance ticket is assignable.
     *
     * @author Luke Freund
     * @param ticket The maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertTicketAssignable(MaintenanceTicket ticket){
        if (ticket != null){
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Assigned){
                return "The maintenance ticket is already assigned.";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Resolved){
                return "Cannot assign a maintenance ticket which is resolved.";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Closed){
                return "Cannot assign a maintenance ticket which is closed.";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.InProgress) {
                return "Cannot assign a maintenance ticket which is in progress.";
            }
        }
        return "";
    }

    /**
     * Validates that the maintenance ticket can be started.
     *
     * @author Luke Freund
     * @param ticket The maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertTicketStartable(MaintenanceTicket ticket){
        if (ticket != null){
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Open){
                return "Cannot start a maintenance ticket which is open.";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Resolved){
                return "Cannot start a maintenance ticket which is resolved.";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Closed){
                return "Cannot start a maintenance ticket which is closed.";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.InProgress) {
                return "The maintenance ticket is already in progress.";
            }
        }
        return "";
    }

    /**
     * Validates that the maintenance ticket is completable.
     *
     * @author Luke Freund
     * @param ticket The maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertTicketCompletable(MaintenanceTicket ticket){
        if (ticket != null){
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Open){
                return "Cannot complete a maintenance ticket which is open";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Assigned){
                return "Cannot complete a maintenance ticket which is assigned.";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Closed){
                return "The maintenance ticket is already closed.";
            }
            if (ticket.getPossible_state() == MaintenanceTicket.Possible_state.Resolved) {
                return "The maintenance ticket is already resolved.";
            }
        }
        return "";
    }
}
