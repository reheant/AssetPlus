/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 1 "AssetPlusStates.ump"
public class TicketStatus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TicketStatus State Machines
  public enum InProgress { completed, Resolved }
  private InProgress inProgress;
  public enum Closed {  }
  private Closed closed;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TicketStatus()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public String getInProgressFullName()
  {
    String answer = inProgress.toString();
    return answer;
  }

  public String getClosedFullName()
  {
    String answer = closed.toString();
    return answer;
  }

  public InProgress getInProgress()
  {
    return inProgress;
  }

  public Closed getClosed()
  {
    return closed;
  }

  private boolean __autotransition1__()
  {
    boolean wasEventProcessed = false;
    
    InProgress aInProgress = inProgress;
    switch (aInProgress)
    {
      case completed:
        setInProgress(InProgress.Resolved);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setInProgress(InProgress aInProgress)
  {
    inProgress = aInProgress;

    // entry actions and do activities
    switch(inProgress)
    {
      case completed:
        __autotransition1__();
        break;
    }
  }

  public void delete()
  {}

  // line 26 "AssetPlusStates.ump"
   private void setTicketDetails(PriorityLevel priority, TimeEstimate timeEstimate, HotelStaff ticketFixer, int ticketID){
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
        ticket.setPriority(priority);
        ticket.setTimeToResolve(timeEstimate);
        ticket.setTicketFixer(ticketFixer);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 3 "AssetPlusStates.ump"
  Open 
  {
    assignStaff [isManager()] / {
            setTicketDetails(PriorityLevel priority, TimeEstimate timeEstimate, ticketFixer, ticketID);
        }
  }

// line 9 "AssetPlusStates.ump"
  Assigned 
  {
    startedToWork [isHotelStaff()] -> InProgress;
  }

// line 17 "AssetPlusStates.ump"
  Resolved 
  {
    [!requireManagerApproval()] -> Closed;
        approve [isManager()] -> Closed;
        disapprove [isManager()] -> InProgress;
  }

  
}