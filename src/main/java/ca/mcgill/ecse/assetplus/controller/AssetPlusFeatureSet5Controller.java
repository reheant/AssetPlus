package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

import java.security.InvalidParameterException;
import java.util.List;

public class AssetPlusFeatureSet5Controller {

    /**
     * @param imageURL The URL of the image to be added to the maintenance ticket. Must not be empty or null, must
     *                 start with either http:// or https://, two imageURLs of the same ticket cannot be the same.
     * @param ticketID The unique identifier of the maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     * @author Luke Freund
     */
    public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

            String error = "";
            error += assertValidImageURL(imageURL);
            error += assertTicketExists(ticket, ticketID);
            error += assertNoSameImageURL(imageURL, ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }

            // Creates two-way association between image and maintenance ticket.
            TicketImage image = new TicketImage(imageURL, ticket);

            return "";
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to add an " +
                    "image to the maintenance ticket: " + e.getMessage();
        }
    }

    /**
     * @param imageURL The URL of the image to be removed from the maintenance ticket. Must not be empty or null, must
     *                 start with either http:// or https://, two imageURLs of the same ticket cannot be the same.
     * @param ticketID The unique identifier of the maintenance ticket.
     * @author Luke Freund
     */
    public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

        String error = "";
        error += assertTicketExists(ticket, ticketID);

        if (!error.isEmpty()) {
            throw new InvalidParameterException(
                    "Ticket with provided ticket id does not exist in the system");
        }

        List<TicketImage> images = ticket.getTicketImages();
        if (images != null) {
            for (TicketImage image : images) {
                if (image.getImageURL().equals(imageURL)) {
                    image.delete();
                    return;
                }
            }
        }
    }

    private static String assertValidImageURL(String imageURL) {
        if (imageURL == null || imageURL.isEmpty()) {
            return "Image URL must not be empty or null";
        }
        if (!imageURL.startsWith("http://") && !imageURL.startsWith("http://")) {
            return "Image URL must start with either http:// or https://";
        }
        return "";
    }

    private static String assertTicketExists(MaintenanceTicket ticket, int ticketID) {
        if (ticket == null) {
            return String.format("String with ticketID %d not found", ticketID);
        }
        return "";
    }

    private static String assertNoSameImageURL(String imageURL, MaintenanceTicket ticket) {
        if (ticket == null) {
            return "";
        }
        List<TicketImage> images = ticket.getTicketImages();
        if (images != null) {
            for (TicketImage image : images) {
                if (image.getImageURL().equals(imageURL)) {
                    return "Image not added to maintenance ticket - two imageURLs " +
                            "of the same ticket cannot be the same: " + imageURL;
                }
            }
        }
        return "";
    }

}
