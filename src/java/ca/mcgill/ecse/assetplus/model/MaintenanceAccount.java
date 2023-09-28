/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 24 "../../../../../AssetPlus.ump"
public class MaintenanceAccount extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceAccount Associations
  private List<MaintenanceTicket> assignedTickets;
  private List<TicketAssignment> ticketAssignments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceAccount(String aEmail, String aPassword, AssetPlus aAssetPlus)
  {
    super(aEmail, aPassword, aAssetPlus);
    assignedTickets = new ArrayList<MaintenanceTicket>();
    ticketAssignments = new ArrayList<TicketAssignment>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public MaintenanceTicket getAssignedTicket(int index)
  {
    MaintenanceTicket aAssignedTicket = assignedTickets.get(index);
    return aAssignedTicket;
  }

  public List<MaintenanceTicket> getAssignedTickets()
  {
    List<MaintenanceTicket> newAssignedTickets = Collections.unmodifiableList(assignedTickets);
    return newAssignedTickets;
  }

  public int numberOfAssignedTickets()
  {
    int number = assignedTickets.size();
    return number;
  }

  public boolean hasAssignedTickets()
  {
    boolean has = assignedTickets.size() > 0;
    return has;
  }

  public int indexOfAssignedTicket(MaintenanceTicket aAssignedTicket)
  {
    int index = assignedTickets.indexOf(aAssignedTicket);
    return index;
  }
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
  public static int minimumNumberOfAssignedTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addAssignedTicket(MaintenanceTicket aAssignedTicket)
  {
    boolean wasAdded = false;
    if (assignedTickets.contains(aAssignedTicket)) { return false; }
    assignedTickets.add(aAssignedTicket);
    if (aAssignedTicket.indexOfAssignedStaff(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAssignedTicket.addAssignedStaff(this);
      if (!wasAdded)
      {
        assignedTickets.remove(aAssignedTicket);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeAssignedTicket(MaintenanceTicket aAssignedTicket)
  {
    boolean wasRemoved = false;
    if (!assignedTickets.contains(aAssignedTicket))
    {
      return wasRemoved;
    }

    int oldIndex = assignedTickets.indexOf(aAssignedTicket);
    assignedTickets.remove(oldIndex);
    if (aAssignedTicket.indexOfAssignedStaff(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAssignedTicket.removeAssignedStaff(this);
      if (!wasRemoved)
      {
        assignedTickets.add(oldIndex,aAssignedTicket);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssignedTicketAt(MaintenanceTicket aAssignedTicket, int index)
  {  
    boolean wasAdded = false;
    if(addAssignedTicket(aAssignedTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedTickets()) { index = numberOfAssignedTickets() - 1; }
      assignedTickets.remove(aAssignedTicket);
      assignedTickets.add(index, aAssignedTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssignedTicketAt(MaintenanceTicket aAssignedTicket, int index)
  {
    boolean wasAdded = false;
    if(assignedTickets.contains(aAssignedTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedTickets()) { index = numberOfAssignedTickets() - 1; }
      assignedTickets.remove(aAssignedTicket);
      assignedTickets.add(index, aAssignedTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssignedTicketAt(aAssignedTicket, index);
    }
    return wasAdded;
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
    ArrayList<MaintenanceTicket> copyOfAssignedTickets = new ArrayList<MaintenanceTicket>(assignedTickets);
    assignedTickets.clear();
    for(MaintenanceTicket aAssignedTicket : copyOfAssignedTickets)
    {
      aAssignedTicket.removeAssignedStaff(this);
    }
    for(int i=ticketAssignments.size(); i > 0; i--)
    {
      TicketAssignment aTicketAssignment = ticketAssignments.get(i - 1);
      aTicketAssignment.delete();
    }
    super.delete();
  }

}