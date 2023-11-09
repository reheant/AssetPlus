package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import java.util.List;

public class AssetPlusFeatureSet5Controller {

    /**
     * Adds an image to a maintenance ticket.
     *
     * @author Luke Freund
     * @param imageURL The URL of the image to be added to the maintenance ticket. Must not be empty
     *        or null, must start with either http:// or https://, two imageURLs of the same ticket
     *        cannot be the same.
     * @param ticketID The unique identifier of the maintenance ticket.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

            String error = "";
            error += assertValidImageURL(imageURL);
            error += assertTicketExists(ticket);
            error += assertNoSameImageURL(imageURL, ticket);

            if (!error.isEmpty()) {
                return error.trim();
            }

            // Creates two-way association between image and maintenance ticket.
            TicketImage image = new TicketImage(imageURL, ticket);

            AssetPlusPersistence.save();
        } catch (Exception e) {
            return "An unexpected error occurred while attempting to add an "
                    + "image to the maintenance ticket: " + e.getMessage();
        }
        return "";
    }

    /**
     * Deletes an image from a maintenance ticket.
     *
     * @author Luke Freund
     * @param imageURL The URL of the image to be removed from the maintenance ticket. Must not be
     *        empty or null, must start with either http:// or https://, two imageURLs of the same
     *        ticket cannot be the same.
     * @param ticketID The unique identifier of the maintenance ticket.
     */
    public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

        String error = "";
        error += assertTicketExists(ticket);

        if (!error.isEmpty()) {
            return;
        }

        List<TicketImage> images = ticket.getTicketImages();
        if (images != null) {
            for (TicketImage image : images) {
                if (image.getImageURL().equals(imageURL)) {
                    image.delete();
                    AssetPlusPersistence.save();
                    return;
                }
            }
        }
    }

    /**
     * Validates the imageURL according to specified constraints.
     *
     * @author Luke Freund
     * @param imageURL The URL of the image to validated. Must not be empty or null, must start with
     *        either http:// or https://.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertValidImageURL(String imageURL) {
        if (imageURL == null || imageURL.isEmpty()) {
            return "Image URL cannot be empty";
        }
        if (!(imageURL.startsWith("http://") || imageURL.startsWith("https://"))) {
            return "Image URL must start with http:// or https://";
        }
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
            return "Ticket does not exist";
        }
        return "";
    }

    /**
     * Validates the imageURLs of a ticket according to specified constraints.
     *
     * @author Luke Freund
     * @param imageURL The URL of the image to validated. Two imageURLs of the same ticket cannot be
     *        the same.
     * @param ticket The maintenance ticket. Must exist.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertNoSameImageURL(String imageURL, MaintenanceTicket ticket) {
        if (ticket == null) {
            return "";
        }
        List<TicketImage> images = ticket.getTicketImages();
        if (images != null) {
            for (TicketImage image : images) {
                if (image.getImageURL().equals(imageURL)) {
                    return "Image already exists for the ticket";
                }
            }
        }
        return "";
    }
}
