/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 19 "../../../../../AssetPlus.ump"
public class MaintenanceAccount extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceAccount Associations
  private List<TicketAssignment> ticketAssignments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceAccount(String aEmail, String aPassword, AssetPlus aAssetPlus)
  {
    super(aEmail, aPassword, aAssetPlus);
    ticketAssignments = new ArrayList<TicketAssignment>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public TicketAssignment getTicketAssignment(int index)
  {
    TicketAssignment aTicketAssignment = ticketAssignments.get(index);
    return aTicketAssignment;
  }

  public List<TicketAssignment> getTicketAssignments()
  {
    List<TicketAssignment> newTicketAssignments = Collections.unmodifiableList(ticketAssignments);
    return newTicketAssignments;
  }

  public int numberOfTicketAssignments()
  {
    int number = ticketAssignments.size();
    return number;
  }

  public boolean hasTicketAssignments()
  {
    boolean has = ticketAssignments.size() > 0;
    return has;
  }

  public int indexOfTicketAssignment(TicketAssignment aTicketAssignment)
  {
    int index = ticketAssignments.indexOf(aTicketAssignment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTicketAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TicketAssignment addTicketAssignment(MaintenanceTicket aMaintenanceTicket)
  {
    return new TicketAssignment(aMaintenanceTicket, this);
  }

  public boolean addTicketAssignment(TicketAssignment aTicketAssignment)
  {
    boolean wasAdded = false;
    if (ticketAssignments.contains(aTicketAssignment)) { return false; }
    MaintenanceAccount existingMaintenanceAccount = aTicketAssignment.getMaintenanceAccount();
    boolean isNewMaintenanceAccount = existingMaintenanceAccount != null && !this.equals(existingMaintenanceAccount);
    if (isNewMaintenanceAccount)
    {
      aTicketAssignment.setMaintenanceAccount(this);
    }
    else
    {
      ticketAssignments.add(aTicketAssignment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicketAssignment(TicketAssignment aTicketAssignment)
  {
    boolean wasRemoved = false;
    //Unable to remove aTicketAssignment, as it must always have a maintenanceAccount
    if (!this.equals(aTicketAssignment.getMaintenanceAccount()))
    {
      ticketAssignments.remove(aTicketAssignment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketAssignmentAt(TicketAssignment aTicketAssignment, int index)
  {  
    boolean wasAdded = false;
    if(addTicketAssignment(aTicketAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketAssignments()) { index = numberOfTicketAssignments() - 1; }
      ticketAssignments.remove(aTicketAssignment);
      ticketAssignments.add(index, aTicketAssignment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketAssignmentAt(TicketAssignment aTicketAssignment, int index)
  {
    boolean wasAdded = false;
    if(ticketAssignments.contains(aTicketAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketAssignments()) { index = numberOfTicketAssignments() - 1; }
      ticketAssignments.remove(aTicketAssignment);
      ticketAssignments.add(index, aTicketAssignment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketAssignmentAt(aTicketAssignment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=ticketAssignments.size(); i > 0; i--)
    {
      TicketAssignment aTicketAssignment = ticketAssignments.get(i - 1);
      aTicketAssignment.delete();
    }
    super.delete();
  }

}