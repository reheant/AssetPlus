package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;

import java.sql.Date;

public class AssetPlusStateController {

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

    public static String disapproveTicket(int ticketID, Date date, String reason){
        return "";
    }

    public static String approveTicket(int ticketID){
        return "";
    }
}
