package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;

import java.sql.Date;

public class AssetPlusStateController {
    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();


    public static String assignTicket(int ticketID, String employeeEmail, MaintenanceTicket.TimeEstimate timeEstimate,
                                      MaintenanceTicket.PriorityLevel priorityLevel, boolean requiresManagerApproval){
        return "";
    }

    public static String startTicket(int ticketID){
        return "";
    }

    public static String completeTicket(int ticketID){
        return "";
    }

 /**
     * Disapproves a maintenance ticket in the system.
     * 
     * @author Nicolas Bolouri
     * @param ticketID The ID of the maintenance ticket to be disapproved.
     * @param date The date of disapproval.
     * @param reason The reason for disapproval.
     * @return A string message indicating the failure of the operation or an empty string if the operation was successful.
     */
    public static String disapproveTicket(int ticketID, Date date, String reason) {
        String error = "";
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

        if (ticket == null) {
            error += "Maintenance ticket does not exist. ";
            return error;
        }

        switch (ticket.getPossible_state()) {
            case Open:
                error += "Cannot disapprove a maintenance ticket which is open. ";
                break;
            case Assigned:
                error += "Cannot disapprove a maintenance ticket which is assigned. ";
                break;
            case Closed:
                error += "Cannot disapprove a maintenance ticket which is closed. ";
                break;
            case InProgress:
                error += "Cannot disapprove a maintenance ticket which is in progress. ";
                break;
            case Resolved:
                if (!ticket.disapprove(assetPlus.getManager().getEmail())) {
                    error += "Disapproval failed. ";
                }
                break;
        }

        return error;
    }

    /**
     * Approves a maintenance ticket in the system.
     * 
     * @author Nicolas Bolouri
     * @param ticketID The ID of the maintenance ticket to be approved.
     * @return A string message indicating the failure of the operation or an empty string if the operation was successful.
     */
    public static String approveTicket(int ticketID) {
        String error = "";
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

        if (ticket == null) {
            error += "Maintenance ticket does not exist. ";
            return error;
        }

        switch (ticket.getPossible_state()) {
            case Open:
                error += "Cannot approve a maintenance ticket which is open. ";
                break;
            case Assigned:
                error += "Cannot approve a maintenance ticket which is assigned. ";
                break;
            case Closed:
                error += "The maintenance ticket is already closed. ";
                break;
            case InProgress:
                error += "Cannot approve a maintenance ticket which is in progress. ";
                break;
            case Resolved:
                // Approval logic for resolved tickets
                if (!ticket.approve(assetPlus.getManager().getEmail())) {
                    error += "Approval failed. ";
                }
                break;
        }

        return error;
    }
}
