/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 46 "../../../../../AssetPlusPersistence.ump"
// line 1 "../../../../../AssetPlusStates.ump"
// line 46 "../../../../../AssetPlus.ump"
public class MaintenanceTicket
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TimeEstimate { LessThanADay, OneToThreeDays, ThreeToSevenDays, OneToThreeWeeks, ThreeOrMoreWeeks }
  public enum PriorityLevel { Urgent, Normal, Low }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, MaintenanceTicket> maintenanceticketsById = new HashMap<Integer, MaintenanceTicket>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceTicket Attributes
  private int id;
  private Date raisedOnDate;
  private String description;
  private TimeEstimate timeToResolve;
  private PriorityLevel priority;

  //MaintenanceTicket State Machines
  public enum Status { Open, Assigned, InProgress, Resolved, Closed }
  private Status status;

  //MaintenanceTicket Associations
  private List<MaintenanceNote> ticketNotes;
  private List<TicketImage> ticketImages;
  private AssetPlus assetPlus;
  private User ticketRaiser;
  private HotelStaff ticketFixer;
  private SpecificAsset asset;
  private Manager fixApprover;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceTicket(int aId, Date aRaisedOnDate, String aDescription, AssetPlus aAssetPlus, User aTicketRaiser)
  {
    raisedOnDate = aRaisedOnDate;
    description = aDescription;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    ticketNotes = new ArrayList<MaintenanceNote>();
    ticketImages = new ArrayList<TicketImage>();
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create maintenanceTicket due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTicketRaiser = setTicketRaiser(aTicketRaiser);
    if (!didAddTicketRaiser)
    {
      throw new RuntimeException("Unable to create raisedTicket due to ticketRaiser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setStatus(Status.Open);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    Integer anOldId = getId();
    if (anOldId != null && anOldId.equals(aId)) {
      return true;
    }
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      maintenanceticketsById.remove(anOldId);
    }
    maintenanceticketsById.put(aId, this);
    return wasSet;
  }

  public boolean setRaisedOnDate(Date aRaisedOnDate)
  {
    boolean wasSet = false;
    raisedOnDate = aRaisedOnDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimeToResolve(TimeEstimate aTimeToResolve)
  {
    boolean wasSet = false;
    timeToResolve = aTimeToResolve;
    wasSet = true;
    return wasSet;
  }

  public boolean setPriority(PriorityLevel aPriority)
  {
    boolean wasSet = false;
    priority = aPriority;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static MaintenanceTicket getWithId(int aId)
  {
    return maintenanceticketsById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public Date getRaisedOnDate()
  {
    return raisedOnDate;
  }

  public String getDescription()
  {
    return description;
  }

  public TimeEstimate getTimeToResolve()
  {
    return timeToResolve;
  }

  public PriorityLevel getPriority()
  {
    return priority;
  }

  public String getStatusFullName()
  {
    String answer = status.toString();
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  public boolean assignHotelStaff(HotelStaff hotelStaff,TimeEstimate timeToResolve,PriorityLevel priority,boolean approvalRequired)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Open:
        // line 5 "../../../../../AssetPlusStates.ump"
        doAssignHotelStaff(hotelStaff, timeToResolve, priority, approvalRequired);
        setStatus(Status.Assigned);
        wasEventProcessed = true;
        break;
      case Assigned:
        // line 15 "../../../../../AssetPlusStates.ump"
        rejectAssign("Assigned");
        setStatus(Status.Assigned);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 26 "../../../../../AssetPlusStates.ump"
        rejectAssign("InProgress");
        setStatus(Status.InProgress);
        wasEventProcessed = true;
        break;
      case Resolved:
        // line 38 "../../../../../AssetPlusStates.ump"
        rejectAssign("Resolved");
        setStatus(Status.Resolved);
        wasEventProcessed = true;
        break;
      case Closed:
        // line 45 "../../../../../AssetPlusStates.ump"
        rejectAssign("Closed");
        setStatus(Status.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startTask()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Open:
        // line 8 "../../../../../AssetPlusStates.ump"
        rejectStart("Open");
        setStatus(Status.Open);
        wasEventProcessed = true;
        break;
      case Assigned:
        setStatus(Status.InProgress);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 29 "../../../../../AssetPlusStates.ump"
        rejectStart("InProgress");
        setStatus(Status.InProgress);
        wasEventProcessed = true;
        break;
      case Resolved:
        // line 41 "../../../../../AssetPlusStates.ump"
        rejectStart("Resolved");
        setStatus(Status.Resolved);
        wasEventProcessed = true;
        break;
      case Closed:
        // line 48 "../../../../../AssetPlusStates.ump"
        rejectStart("Closed");
        setStatus(Status.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean completeTask()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Open:
        // line 9 "../../../../../AssetPlusStates.ump"
        rejectComplete("Open");
        setStatus(Status.Open);
        wasEventProcessed = true;
        break;
      case Assigned:
        // line 18 "../../../../../AssetPlusStates.ump"
        rejectComplete("Assigned");
        setStatus(Status.Assigned);
        wasEventProcessed = true;
        break;
      case InProgress:
        if (isApprovalRequired())
        {
          setStatus(Status.Resolved);
          wasEventProcessed = true;
          break;
        }
        if (!(isApprovalRequired()))
        {
          setStatus(Status.Closed);
          wasEventProcessed = true;
          break;
        }
        break;
      case Resolved:
        // line 42 "../../../../../AssetPlusStates.ump"
        rejectComplete("Resolved");
        setStatus(Status.Resolved);
        wasEventProcessed = true;
        break;
      case Closed:
        // line 49 "../../../../../AssetPlusStates.ump"
        rejectComplete("Closed");
        setStatus(Status.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean disapprove(Date date,String reason)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Open:
        // line 10 "../../../../../AssetPlusStates.ump"
        rejectDisapprove("Open");
        setStatus(Status.Open);
        wasEventProcessed = true;
        break;
      case Assigned:
        // line 19 "../../../../../AssetPlusStates.ump"
        rejectDisapprove("Assigned");
        setStatus(Status.Assigned);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 30 "../../../../../AssetPlusStates.ump"
        rejectDisapprove("InProgress");
        setStatus(Status.InProgress);
        wasEventProcessed = true;
        break;
      case Resolved:
        // line 35 "../../../../../AssetPlusStates.ump"
        doAddMaintenanceNote(date, reason);
        setStatus(Status.InProgress);
        wasEventProcessed = true;
        break;
      case Closed:
        // line 50 "../../../../../AssetPlusStates.ump"
        rejectDisapprove("Closed");
        setStatus(Status.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean approve()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Open:
        // line 11 "../../../../../AssetPlusStates.ump"
        rejectApprove("Open");
        setStatus(Status.Open);
        wasEventProcessed = true;
        break;
      case Assigned:
        // line 20 "../../../../../AssetPlusStates.ump"
        rejectApprove("Assigned");
        setStatus(Status.Assigned);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 31 "../../../../../AssetPlusStates.ump"
        rejectApprove("InProgress");
        setStatus(Status.InProgress);
        wasEventProcessed = true;
        break;
      case Resolved:
        setStatus(Status.Closed);
        wasEventProcessed = true;
        break;
      case Closed:
        // line 51 "../../../../../AssetPlusStates.ump"
        rejectApprove("Closed");
        setStatus(Status.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;
  }
  /* Code from template association_GetMany */
  public MaintenanceNote getTicketNote(int index)
  {
    MaintenanceNote aTicketNote = ticketNotes.get(index);
    return aTicketNote;
  }

  public List<MaintenanceNote> getTicketNotes()
  {
    List<MaintenanceNote> newTicketNotes = Collections.unmodifiableList(ticketNotes);
    return newTicketNotes;
  }

  public int numberOfTicketNotes()
  {
    int number = ticketNotes.size();
    return number;
  }

  public boolean hasTicketNotes()
  {
    boolean has = ticketNotes.size() > 0;
    return has;
  }

  public int indexOfTicketNote(MaintenanceNote aTicketNote)
  {
    int index = ticketNotes.indexOf(aTicketNote);
    return index;
  }
  /* Code from template association_GetMany */
  public TicketImage getTicketImage(int index)
  {
    TicketImage aTicketImage = ticketImages.get(index);
    return aTicketImage;
  }

  public List<TicketImage> getTicketImages()
  {
    List<TicketImage> newTicketImages = Collections.unmodifiableList(ticketImages);
    return newTicketImages;
  }

  public int numberOfTicketImages()
  {
    int number = ticketImages.size();
    return number;
  }

  public boolean hasTicketImages()
  {
    boolean has = ticketImages.size() > 0;
    return has;
  }

  public int indexOfTicketImage(TicketImage aTicketImage)
  {
    int index = ticketImages.indexOf(aTicketImage);
    return index;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetOne */
  public User getTicketRaiser()
  {
    return ticketRaiser;
  }
  /* Code from template association_GetOne */
  public HotelStaff getTicketFixer()
  {
    return ticketFixer;
  }

  public boolean hasTicketFixer()
  {
    boolean has = ticketFixer != null;
    return has;
  }
  /* Code from template association_GetOne */
  public SpecificAsset getAsset()
  {
    return asset;
  }

  public boolean hasAsset()
  {
    boolean has = asset != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Manager getFixApprover()
  {
    return fixApprover;
  }

  public boolean hasFixApprover()
  {
    boolean has = fixApprover != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTicketNotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceNote addTicketNote(Date aDate, String aDescription, HotelStaff aNoteTaker)
  {
    return new MaintenanceNote(aDate, aDescription, this, aNoteTaker);
  }

  public boolean addTicketNote(MaintenanceNote aTicketNote)
  {
    boolean wasAdded = false;
    if (ticketNotes.contains(aTicketNote)) { return false; }
    MaintenanceTicket existingTicket = aTicketNote.getTicket();
    boolean isNewTicket = existingTicket != null && !this.equals(existingTicket);
    if (isNewTicket)
    {
      aTicketNote.setTicket(this);
    }
    else
    {
      ticketNotes.add(aTicketNote);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicketNote(MaintenanceNote aTicketNote)
  {
    boolean wasRemoved = false;
    //Unable to remove aTicketNote, as it must always have a ticket
    if (!this.equals(aTicketNote.getTicket()))
    {
      ticketNotes.remove(aTicketNote);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketNoteAt(MaintenanceNote aTicketNote, int index)
  {  
    boolean wasAdded = false;
    if(addTicketNote(aTicketNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketNotes()) { index = numberOfTicketNotes() - 1; }
      ticketNotes.remove(aTicketNote);
      ticketNotes.add(index, aTicketNote);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketNoteAt(MaintenanceNote aTicketNote, int index)
  {
    boolean wasAdded = false;
    if(ticketNotes.contains(aTicketNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketNotes()) { index = numberOfTicketNotes() - 1; }
      ticketNotes.remove(aTicketNote);
      ticketNotes.add(index, aTicketNote);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketNoteAt(aTicketNote, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTicketImages()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TicketImage addTicketImage(String aImageURL)
  {
    return new TicketImage(aImageURL, this);
  }

  public boolean addTicketImage(TicketImage aTicketImage)
  {
    boolean wasAdded = false;
    if (ticketImages.contains(aTicketImage)) { return false; }
    MaintenanceTicket existingTicket = aTicketImage.getTicket();
    boolean isNewTicket = existingTicket != null && !this.equals(existingTicket);
    if (isNewTicket)
    {
      aTicketImage.setTicket(this);
    }
    else
    {
      ticketImages.add(aTicketImage);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicketImage(TicketImage aTicketImage)
  {
    boolean wasRemoved = false;
    //Unable to remove aTicketImage, as it must always have a ticket
    if (!this.equals(aTicketImage.getTicket()))
    {
      ticketImages.remove(aTicketImage);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketImageAt(TicketImage aTicketImage, int index)
  {  
    boolean wasAdded = false;
    if(addTicketImage(aTicketImage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketImages()) { index = numberOfTicketImages() - 1; }
      ticketImages.remove(aTicketImage);
      ticketImages.add(index, aTicketImage);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketImageAt(TicketImage aTicketImage, int index)
  {
    boolean wasAdded = false;
    if(ticketImages.contains(aTicketImage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTicketImages()) { index = numberOfTicketImages() - 1; }
      ticketImages.remove(aTicketImage);
      ticketImages.add(index, aTicketImage);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketImageAt(aTicketImage, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssetPlus(AssetPlus aAssetPlus)
  {
    boolean wasSet = false;
    if (aAssetPlus == null)
    {
      return wasSet;
    }

    AssetPlus existingAssetPlus = assetPlus;
    assetPlus = aAssetPlus;
    if (existingAssetPlus != null && !existingAssetPlus.equals(aAssetPlus))
    {
      existingAssetPlus.removeMaintenanceTicket(this);
    }
    assetPlus.addMaintenanceTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTicketRaiser(User aTicketRaiser)
  {
    boolean wasSet = false;
    if (aTicketRaiser == null)
    {
      return wasSet;
    }

    User existingTicketRaiser = ticketRaiser;
    ticketRaiser = aTicketRaiser;
    if (existingTicketRaiser != null && !existingTicketRaiser.equals(aTicketRaiser))
    {
      existingTicketRaiser.removeRaisedTicket(this);
    }
    ticketRaiser.addRaisedTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setTicketFixer(HotelStaff aTicketFixer)
  {
    boolean wasSet = false;
    HotelStaff existingTicketFixer = ticketFixer;
    ticketFixer = aTicketFixer;
    if (existingTicketFixer != null && !existingTicketFixer.equals(aTicketFixer))
    {
      existingTicketFixer.removeMaintenanceTask(this);
    }
    if (aTicketFixer != null)
    {
      aTicketFixer.addMaintenanceTask(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setAsset(SpecificAsset aAsset)
  {
    boolean wasSet = false;
    SpecificAsset existingAsset = asset;
    asset = aAsset;
    if (existingAsset != null && !existingAsset.equals(aAsset))
    {
      existingAsset.removeMaintenanceTicket(this);
    }
    if (aAsset != null)
    {
      aAsset.addMaintenanceTicket(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setFixApprover(Manager aFixApprover)
  {
    boolean wasSet = false;
    Manager existingFixApprover = fixApprover;
    fixApprover = aFixApprover;
    if (existingFixApprover != null && !existingFixApprover.equals(aFixApprover))
    {
      existingFixApprover.removeTicketsForApproval(this);
    }
    if (aFixApprover != null)
    {
      aFixApprover.addTicketsForApproval(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    maintenanceticketsById.remove(getId());
    while (ticketNotes.size() > 0)
    {
      MaintenanceNote aTicketNote = ticketNotes.get(ticketNotes.size() - 1);
      aTicketNote.delete();
      ticketNotes.remove(aTicketNote);
    }
    
    while (ticketImages.size() > 0)
    {
      TicketImage aTicketImage = ticketImages.get(ticketImages.size() - 1);
      aTicketImage.delete();
      ticketImages.remove(aTicketImage);
    }
    
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeMaintenanceTicket(this);
    }
    User placeholderTicketRaiser = ticketRaiser;
    this.ticketRaiser = null;
    if(placeholderTicketRaiser != null)
    {
      placeholderTicketRaiser.removeRaisedTicket(this);
    }
    if (ticketFixer != null)
    {
      HotelStaff placeholderTicketFixer = ticketFixer;
      this.ticketFixer = null;
      placeholderTicketFixer.removeMaintenanceTask(this);
    }
    if (asset != null)
    {
      SpecificAsset placeholderAsset = asset;
      this.asset = null;
      placeholderAsset.removeMaintenanceTicket(this);
    }
    if (fixApprover != null)
    {
      Manager placeholderFixApprover = fixApprover;
      this.fixApprover = null;
      placeholderFixApprover.removeTicketsForApproval(this);
    }
  }

  // line 48 "../../../../../AssetPlusPersistence.ump"
   public static  void reinitializeUniqueID(List<MaintenanceTicket> maintenanceTickets){
    maintenanceticketsById.clear();
    for (var maintenanceTicket : maintenanceTickets) {
      maintenanceticketsById.put(maintenanceTicket.getId(), maintenanceTicket);
    }
  }


  /**
   * 
   * Assigns maintenance ticket to a  hotel staff
   * 
   * @author Rehean Thillainathalingam
   * @param priority The priority level of the ticket
   * @param timeToResolve The time estimate of the ticket
   * @param hotelStaff The assigned hotel staff
   * @param approvalRequired Boolean if manager approval is required or not
   */
  // line 66 "../../../../../AssetPlusStates.ump"
   private void doAssignHotelStaff(HotelStaff hotelStaff, TimeEstimate timeToResolve, PriorityLevel priority, boolean approvalRequired){
    MaintenanceTicket ticket = this;
    ticket.setPriority(priority);
    ticket.setTicketFixer(hotelStaff);
    ticket.setTimeToResolve(timeToResolve);
    if (approvalRequired) {
      AssetPlus assetPlus = ticket.getAssetPlus();
      Manager manager = assetPlus.getManager();
      ticket.setFixApprover(manager);
    }
  }


  /**
   * 
   * Assigns maintenance ticket to a  hotel staff
   * 
   * @author Rehean Thillainathalingam
   * @return boolean indicating if the ticket has an approver
   */
  // line 85 "../../../../../AssetPlusStates.ump"
   private boolean isApprovalRequired(){
    MaintenanceTicket ticket = this;
    return ticket.hasFixApprover();
  }


  /**
   * 
   * Assigns maintenance ticket to a  hotel staff
   * 
   * @author Rehean Thillainathalingam
   * @param date Date note was created
   * @param reason Maintenance note description
   */
  // line 97 "../../../../../AssetPlusStates.ump"
   private void doAddMaintenanceNote(Date date, String reason){
    MaintenanceTicket ticket = this;
    HotelStaff hotelStaff = ticket.getTicketFixer();
    ticket.addTicketNote(date, reason, hotelStaff);
  }


  /**
   * 
   * Error handling for transitions  
   * 
   * @author Rehean Thillainathalingam
   * @param state String in which the function was called
   */
  // line 109 "../../../../../AssetPlusStates.ump"
   private void rejectAssign(String state){
    String message = "";
        if (state.equals("Assigned")) {
          message = "The maintenance ticket is already assigned.";
        } else if (state.equals("Resolved")) {
          message = "Cannot assign a maintenance ticket which is resolved.";
        } else if (state.equals("Closed")) {
          message = "Cannot assign a maintenance ticket which is closed.";
        } else if (state.equals("InProgress")) {
          message = "Cannot assign a maintenance ticket which is in progress.";
        }
        throw new RuntimeException(message);
  }


  /**
   * 
   * Error handling for transitions  
   * 
   * @author Rehean Thillainathalingam
   * @param state String in which the function was called
   */
  // line 128 "../../../../../AssetPlusStates.ump"
   private void rejectStart(String state){
    String message = "";
        if (state.equals("Open")) {
          message = "Cannot start a maintenance ticket which is open.";
        } else if (state.equals("Resolved")) {
          message = "Cannot start a maintenance ticket which is resolved.";
        } else if (state.equals("Closed")) {
          message = "Cannot start a maintenance ticket which is closed.";
        } else if (state.equals("InProgress")) {
          message = "The maintenance ticket is already in progress.";
        }
        throw new RuntimeException(message);
  }


  /**
   * 
   * Error handling for transitions  
   * 
   * @author Rehean Thillainathalingam
   * @param state String in which the function was called
   */
  // line 147 "../../../../../AssetPlusStates.ump"
   private void rejectComplete(String state){
    String message = "";
        if (state.equals("Open")) {
          message = "Cannot complete a maintenance ticket which is open.";
        } else if (state.equals("Assigned")) {
          message = "Cannot complete a maintenance ticket which is assigned.";
        } else if (state.equals("Closed")) {
          message = "The maintenance ticket is already closed.";
        } else if (state.equals("Resolved")) {
          message = "The maintenance ticket is already resolved.";
        }
        throw new RuntimeException(message);
  }


  /**
   * 
   * Error handling for transitions  
   * 
   * @author Rehean Thillainathalingam
   * @param state String in which the function was called
   */
  // line 166 "../../../../../AssetPlusStates.ump"
   private void rejectDisapprove(String state){
    String message = "";
        if (state.equals("Open")) {
          message = "Cannot disapprove a maintenance ticket which is open.";
        } else if (state.equals("Assigned")) {
          message = "Cannot disapprove a maintenance ticket which is assigned.";
        } else if (state.equals("Closed")) {
          message = "Cannot disapprove a maintenance ticket which is closed.";
        } else if (state.equals("InProgress")) {
          message = "Cannot disapprove a maintenance ticket which is in progress.";
        }
        throw new RuntimeException(message);
  }


  /**
   * 
   * Error handling for transitions  
   * 
   * @author Rehean Thillainathalingam
   * @param state String in which the function was called
   */
  // line 185 "../../../../../AssetPlusStates.ump"
   private void rejectApprove(String state){
    String message = "";
        if (state.equals("Open")) {
          message = "Cannot approve a maintenance ticket which is open.";
        } else if (state.equals("Assigned")) {
          message = "Cannot approve a maintenance ticket which is assigned.";
        } else if (state.equals("Closed")) {
          message = "The maintenance ticket is already closed.";
        } else if (state.equals("InProgress")) {
          message = "Cannot approve a maintenance ticket which is in progress.";
        }
        throw new RuntimeException(message);
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "raisedOnDate" + "=" + (getRaisedOnDate() != null ? !getRaisedOnDate().equals(this)  ? getRaisedOnDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeToResolve" + "=" + (getTimeToResolve() != null ? !getTimeToResolve().equals(this)  ? getTimeToResolve().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "priority" + "=" + (getPriority() != null ? !getPriority().equals(this)  ? getPriority().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticketRaiser = "+(getTicketRaiser()!=null?Integer.toHexString(System.identityHashCode(getTicketRaiser())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticketFixer = "+(getTicketFixer()!=null?Integer.toHexString(System.identityHashCode(getTicketFixer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "asset = "+(getAsset()!=null?Integer.toHexString(System.identityHashCode(getAsset())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fixApprover = "+(getFixApprover()!=null?Integer.toHexString(System.identityHashCode(getFixApprover())):"null");
  }
}