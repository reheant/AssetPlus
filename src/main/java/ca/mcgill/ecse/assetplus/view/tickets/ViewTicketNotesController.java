package ca.mcgill.ecse.assetplus.view.tickets;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class ViewTicketNotesController {

    @FXML
    private AnchorPane mainContentArea;

    @FXML
    private TextArea ticketNotes;

    private TOMaintenanceTicket currentMaintenanceTicket;

    /**
     * Initializes default values on the page
     *
     * @author Luke Freund
     */
    public void initialize() {
        if (currentMaintenanceTicket != null) {
            displayTicketNotes();
        }
    }

    /**
     * Keeps track of the current maintenance ticket in memory
     *
     * @author Luke Freund
     * @param ticket The transfer object containing the current maintenance ticket
     * @param ticket
     */
    public void setCurrentMaintenanceTicket(TOMaintenanceTicket ticket) {
        this.currentMaintenanceTicket = ticket;
        displayTicketNotes();
    }

    /**
     * Actions to take when clicking the back to tickets button
     *
     * @author Luke Freund
     */
    @FXML
    public void onBackToTicketClicked() {
        loadPage("update-ticket.fxml");
    }

    /**
     * Displays the ticket notes on a UI window.
     *
     * @author Luke Freund
     */
    private void displayTicketNotes() {
        List<TOMaintenanceNote> notes = currentMaintenanceTicket.getNotes();
        StringBuilder notesText = new StringBuilder();

        for (TOMaintenanceNote note : notes) {
            notesText.append("Note taker: ").append(note.getNoteTakerEmail()).append("\n");
            notesText.append(note.getDescription()).append("\n");
            notesText.append("Date: ").append(note.getDate()).append("\n\n");
        }

        ticketNotes.setText(notesText.toString());
    }

    /**
     * Loads the required page. For some pages, sets default values.
     *
     * @author Luke Freund
     * @param fxmlFile The relative path to the .fxml file to load
     */
    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/mcgill/ecse/assetplus/view/tickets/" + fxmlFile));
            Node page = loader.load();
            if (fxmlFile.equals("update-ticket.fxml")) {
                TicketUpdateController ticketUpdateController = loader.getController();
                ticketUpdateController.setTicketId(this.currentMaintenanceTicket.getId());
                ticketUpdateController.reinitialize();
            }

            mainContentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
