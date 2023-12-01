package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
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
     * @param requiresManagerApproval Indicates if the ticket requires a manager's approval before
     *        closing.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String assignTicket(int ticketID, String employeeEmail, TimeEstimate timeEstimate,
            PriorityLevel priorityLevel, boolean requiresManagerApproval) {
        try {
            var error = "";
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            HotelStaff employee;
            if (employeeEmail.equals("manager@ap.com")){
                employee = assetPlus.getManager();
            } else if (employeeEmail.contains("@ap.com")) {
                employee = (HotelStaff) User.getWithEmail(employeeEmail);
            }
            else{
                error = "Not an employee email. ";
                employee = null;
            }
            
            error += assertEmployeeExists(employee);
            error += assertTicketExists(ticket);
            error += assertTicketAssignable(ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }

            Manager manager = assetPlus.getManager();
            ticket.assignHotelStaff(employee, timeEstimate, priorityLevel, requiresManagerApproval);
            if (requiresManagerApproval) {
                ticket.setFixApprover(manager);
            }
            AssetPlusPersistence.save();
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to assign a ticket"
                    + e.getMessage();
        }
        return "";
    }
    /**
     * Assigns tickets with all string value inputs.
     *
     * @author Luke Freund
     * @param ticketId The unique identifier of the maintenance ticket.
     * @param email The email of a hotel staff member.
     * @param timeEstimateStr The estimated completion time of the maintenance ticket as a string.
     * @param priorityLevelStr The priority level of the maintenance ticket as a string.
     * @param requiresManagerApproval Indicates if the ticket requires a manager's approval 
     * @return An empty string indicating success. An error message if failure.
     */
    public static String assignTicketWithStringEnums(int ticketId, String email, String timeEstimateStr, String priorityLevelStr, boolean requiresManagerApproval) {
        MaintenanceTicket.TimeEstimate timeEstimate = convertStringToTimeEstimate(timeEstimateStr);
        MaintenanceTicket.PriorityLevel priorityLevel = convertStringToPriorityLevel(priorityLevelStr);

        // Now call the original assignTicket method with the correct enum types
        return assignTicket(ticketId, email, timeEstimate, priorityLevel, requiresManagerApproval);
    }

    /**
     * Starts the work on a maintenance ticket.
     *
     * @author Luke Freund
     * @param ticketID The unique identifier of the maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String startTicket(int ticketID) {
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            var error = "";

            error += assertTicketExists(ticket);
            error += assertTicketStartable(ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }

            HotelStaff ticketFixer = ticket.getTicketFixer();
            error += assertUserExistsAndIsEmployee(ticketFixer);

            if (!error.isEmpty()) {
                return error.trim();
            }

            ticket.startTask();

            AssetPlusPersistence.save();
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to start a ticket"
                    + e.getMessage();
        }
        return "";
    }

    /**
     * Completes the work on a maintenance ticket.
     *
     * @author Luke Freund
     * @param ticketId The unique identifier of the maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String resolveTicket(int ticketId) {
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
            var error = "";

            error += assertTicketExists(ticket);
            error += assertTicketCompletable(ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }

            HotelStaff ticketFixer = ticket.getTicketFixer();
            error += assertUserExistsAndIsEmployee(ticketFixer);

            if (!error.isEmpty()) {
                return error.trim();
            }

            ticket.completeTask();

            AssetPlusPersistence.save();
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to complete a ticket"
                    + e.getMessage();
        }
        return "";
    }

    /**
     * Disapproves a maintenance ticket in the system.
     * 
     * @author Nicolas Bolouri
     * @param ticketID The ID of the maintenance ticket to be disapproved.
     * @param date The date of disapproval.
     * @param reason The reason for disapproval.
     * @return A string message indicating the failure of the operation or an empty string if the
     *         operation was successful.
     */
    public static String disapproveTicket(int ticketID, Date date, String reason) {
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            var error = "";

            error += assertTicketExists(ticket);
            error += assertTicketDisapprovable(ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }

            if (reason == null || reason.trim().isEmpty()) {
                return "Note required for disapproval";
            }

            Manager manager = assetPlus.getManager();
            ticket.disapprove(date, reason);
            ticket.addTicketNote(date, reason, manager);
            AssetPlusPersistence.save();
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to disapprove a ticket"
                    + e.getMessage();
        }
        return "";
    }

    /**
     * Approves a maintenance ticket in the system.
     * 
     * @author Nicolas Bolouri
     * @param ticketID The ID of the maintenance ticket to be approved.
     * @return A string message indicating the failure of the operation or an empty string if the
     *         operation was successful.
     */
    public static String approveTicket(int ticketID) {
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            var error = "";

            error += assertTicketExists(ticket);
            error += assertTicketApprovable(ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }

            ticket.approve();
            AssetPlusPersistence.save();
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to disapprove a ticket"
                    + e.getMessage();
        }
        return "";
    }
    /**
     * Converts time estimate String to a TimeEstimate value.
     *
     * @author Luke Freund
     * @param timeEstimateStr time estimate as a string
     * @return TimeEstimate value of the entered string.
     */
    private static MaintenanceTicket.TimeEstimate convertStringToTimeEstimate(String timeEstimateStr) {
        return MaintenanceTicket.TimeEstimate.valueOf(timeEstimateStr);
    }
    /**
     * Converts priority level String to a PriorityLevel value.
     *
     * @author Luke Freund
     * @param priorityLevelStr priority level as a string
     * @return PriorityLevel value of the entered string.
     */
    private static MaintenanceTicket.PriorityLevel convertStringToPriorityLevel(String priorityLevelStr) {
        return MaintenanceTicket.PriorityLevel.valueOf(priorityLevelStr);
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
    private static String assertEmployeeExists(HotelStaff employee) {
        if (employee == null) {
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
    private static String assertTicketAssignable(MaintenanceTicket ticket) {
        if (ticket != null) {
            if (ticket.getStatus() == MaintenanceTicket.Status.Assigned) {
                return "The maintenance ticket is already assigned.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Resolved) {
                return "Cannot assign a maintenance ticket which is resolved.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Closed) {
                return "Cannot assign a maintenance ticket which is closed.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.InProgress) {
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
    private static String assertTicketStartable(MaintenanceTicket ticket) {
        if (ticket != null) {
            if (ticket.getStatus() == MaintenanceTicket.Status.Open) {
                return "Cannot start a maintenance ticket which is open.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Resolved) {
                return "Cannot start a maintenance ticket which is resolved.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Closed) {
                return "Cannot start a maintenance ticket which is closed.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.InProgress) {
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
    private static String assertTicketCompletable(MaintenanceTicket ticket) {
        if (ticket != null) {
            if (ticket.getStatus() == MaintenanceTicket.Status.Open) {
                return "Cannot complete a maintenance ticket which is open.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Assigned) {
                return "Cannot complete a maintenance ticket which is assigned.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Closed) {
                return "The maintenance ticket is already closed.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Resolved) {
                return "The maintenance ticket is already resolved.";
            }
        }
        return "";
    }

    /**
     * Checks if a maintenance ticket can be disapproved based on its current state.
     *
     * @author Nicolas Bolouri
     * @param ticket The maintenance ticket to be checked.
     * @return An empty string indicating the ticket can be disapproved. An error message if the
     *         ticket cannot be disapproved.
     */
    private static String assertTicketDisapprovable(MaintenanceTicket ticket) {
        if (ticket != null) {
            if (ticket.getStatus() == MaintenanceTicket.Status.Open) {
                return "Cannot disapprove a maintenance ticket which is open.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Assigned) {
                return "Cannot disapprove a maintenance ticket which is assigned.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Closed) {
                return "Cannot disapprove a maintenance ticket which is closed.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.InProgress) {
                return "Cannot disapprove a maintenance ticket which is in progress.";
            }
        }
        return "";
    }

    /**
     * Validates that the maintenance ticket is approvable.
     * 
     * @author Nicolas Bolouri
     * @param ticket The maintenance ticket to be checked.
     * @return An empty string indicating the ticket can be approved. An error message if the ticket
     *         cannot be approved.
     */
    private static String assertTicketApprovable(MaintenanceTicket ticket) {
        if (ticket != null) {
            if (ticket.getStatus() == MaintenanceTicket.Status.Open) {
                return "Cannot approve a maintenance ticket which is open.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Assigned) {
                return "Cannot approve a maintenance ticket which is assigned.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.Closed) {
                return "The maintenance ticket is already closed.";
            }
            if (ticket.getStatus() == MaintenanceTicket.Status.InProgress) {
                return "Cannot approve a maintenance ticket which is in progress.";
            }
        }
        return "";
    }

    /**
     * Validates if the provided email address belongs to a hotel employee.
     * 
     * @author Nicolas Bolouri
     * @param employeeEmail The email address to be validated.
     * @return An empty string if the email is a valid employee email. An error message stating the
     *         error otherwise.
     */
    private static String assertUserIsEmployee(String employeeEmail) {
        if (!employeeEmail.endsWith("@ap.com")) {
            return "Email is not a valid employee email ";
        }

        if (employeeEmail.equals("manager@ap.com")) {
            return "";
        }
        HotelStaff employee = (HotelStaff) User.getWithEmail(employeeEmail);
        return assertEmployeeExists(employee);
    }

    /**
     * Validates that the specified HotelStaff object represents an existing employee.
     * 
     * @author Nicolas Bolouri
     * @param employee The HotelStaff object to be validated.
     * @return An empty string if the employee exists, or an error message if not.
     */
    private static String assertUserExistsAndIsEmployee(HotelStaff employee) {
        if (employee == null) {
            return "Staff does not exist.";
        }

        return assertUserIsEmployee(employee.getEmail());
    }

}
