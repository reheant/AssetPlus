/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 106 "model.ump"
// line 193 "model.ump"
public class TicketAssignment
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TimeEstimate { LessThanOneDay, BetweenOneAndThreeDays, BetweenOneAndSevenDays, BetweenOneAndThreeWeeks, MoreThanThreeWeeks }
  public enum TicketPriority { Low, Normal, Urgent }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TicketAssignment Attributes
  private boolean requiresManagerApproval;
  private TicketPriority priority;
  private TimeEstimate timeEstimate;

  //TicketAssignment Associations
  private MaintenanceTicket maintenanceTicket;
  private MaintenanceAccount maintenanceAccount;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetMaintenanceTicket;
  private boolean canSetMaintenanceAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TicketAssignment(boolean aRequiresManagerApproval, TicketPriority aPriority, TimeEstimate aTimeEstimate, MaintenanceTicket aMaintenanceTicket, MaintenanceAccount aMaintenanceAccount)
  {
    cachedHashCode = -1;
    canSetMaintenanceTicket = true;
    canSetMaintenanceAccount = true;
    requiresManagerApproval = aRequiresManagerApproval;
    priority = aPriority;
    timeEstimate = aTimeEstimate;
    boolean didAddMaintenanceTicket = setMaintenanceTicket(aMaintenanceTicket);
    if (!didAddMaintenanceTicket)
    {
      throw new RuntimeException("Unable to create ticketAssignment due to maintenanceTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMaintenanceAccount = setMaintenanceAccount(aMaintenanceAccount);
    if (!didAddMaintenanceAccount)
    {
      throw new RuntimeException("Unable to create ticketAssignment due to maintenanceAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRequiresManagerApproval(boolean aRequiresManagerApproval)
  {
    boolean wasSet = false;
    requiresManagerApproval = aRequiresManagerApproval;
    wasSet = true;
    return wasSet;
  }

  public boolean setPriority(TicketPriority aPriority)
  {
    boolean wasSet = false;
    priority = aPriority;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimeEstimate(TimeEstimate aTimeEstimate)
  {
    boolean wasSet = false;
    timeEstimate = aTimeEstimate;
    wasSet = true;
    return wasSet;
  }

  public boolean getRequiresManagerApproval()
  {
    return requiresManagerApproval;
  }

  public TicketPriority getPriority()
  {
    return priority;
  }

  public TimeEstimate getTimeEstimate()
  {
    return timeEstimate;
  }
  /* Code from template association_GetOne */
  public MaintenanceTicket getMaintenanceTicket()
  {
    return maintenanceTicket;
  }
  /* Code from template association_GetOne */
  public MaintenanceAccount getMaintenanceAccount()
  {
    return maintenanceAccount;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasSet = false;
    if (!canSetMaintenanceTicket) { return false; }
    if (aMaintenanceTicket == null)
    {
      return wasSet;
    }

    MaintenanceTicket existingMaintenanceTicket = maintenanceTicket;
    maintenanceTicket = aMaintenanceTicket;
    if (existingMaintenanceTicket != null && !existingMaintenanceTicket.equals(aMaintenanceTicket))
    {
      existingMaintenanceTicket.removeTicketAssignment(this);
    }
    if (!maintenanceTicket.addTicketAssignment(this))
    {
      maintenanceTicket = existingMaintenanceTicket;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setMaintenanceAccount(MaintenanceAccount aMaintenanceAccount)
  {
    boolean wasSet = false;
    if (!canSetMaintenanceAccount) { return false; }
    if (aMaintenanceAccount == null)
    {
      return wasSet;
    }

    MaintenanceAccount existingMaintenanceAccount = maintenanceAccount;
    maintenanceAccount = aMaintenanceAccount;
    if (existingMaintenanceAccount != null && !existingMaintenanceAccount.equals(aMaintenanceAccount))
    {
      existingMaintenanceAccount.removeTicketAssignment(this);
    }
    if (!maintenanceAccount.addTicketAssignment(this))
    {
      maintenanceAccount = existingMaintenanceAccount;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    TicketAssignment compareTo = (TicketAssignment)obj;
  
    if (getMaintenanceTicket() == null && compareTo.getMaintenanceTicket() != null)
    {
      return false;
    }
    else if (getMaintenanceTicket() != null && !getMaintenanceTicket().equals(compareTo.getMaintenanceTicket()))
    {
      return false;
    }

    if (getMaintenanceAccount() == null && compareTo.getMaintenanceAccount() != null)
    {
      return false;
    }
    else if (getMaintenanceAccount() != null && !getMaintenanceAccount().equals(compareTo.getMaintenanceAccount()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getMaintenanceTicket() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getMaintenanceTicket().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getMaintenanceAccount() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getMaintenanceAccount().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetMaintenanceTicket = false;
    canSetMaintenanceAccount = false;
    return cachedHashCode;
  }

  public void delete()
  {
    MaintenanceTicket placeholderMaintenanceTicket = maintenanceTicket;
    this.maintenanceTicket = null;
    if(placeholderMaintenanceTicket != null)
    {
      placeholderMaintenanceTicket.removeTicketAssignment(this);
    }
    MaintenanceAccount placeholderMaintenanceAccount = maintenanceAccount;
    this.maintenanceAccount = null;
    if(placeholderMaintenanceAccount != null)
    {
      placeholderMaintenanceAccount.removeTicketAssignment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "requiresManagerApproval" + ":" + getRequiresManagerApproval()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "priority" + "=" + (getPriority() != null ? !getPriority().equals(this)  ? getPriority().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeEstimate" + "=" + (getTimeEstimate() != null ? !getTimeEstimate().equals(this)  ? getTimeEstimate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "maintenanceTicket = "+(getMaintenanceTicket()!=null?Integer.toHexString(System.identityHashCode(getMaintenanceTicket())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "maintenanceAccount = "+(getMaintenanceAccount()!=null?Integer.toHexString(System.identityHashCode(getMaintenanceAccount())):"null");
  }
}