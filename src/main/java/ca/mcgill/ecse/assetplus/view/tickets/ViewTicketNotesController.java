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

    public void initialize() {
        if (currentMaintenanceTicket != null) {
            displayTicketNotes();
        }
    }

    public void setCurrentMaintenanceTicket(TOMaintenanceTicket ticket) {
        this.currentMaintenanceTicket = ticket;
        displayTicketNotes();
    }

    @FXML
    public void onBackToTicketClicked() {
        loadPage("update-ticket.fxml");
    }

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
