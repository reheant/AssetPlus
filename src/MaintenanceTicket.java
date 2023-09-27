/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.sql.Date;
import java.util.*;

// line 71 "model.ump"
// line 163 "model.ump"
public class MaintenanceTicket
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TimeEstimate { LessThanOneDay, BetweenOneAndThreeDays, BetweenOneAndSevenDays, BetweenOneAndThreeWeeks, MoreThanThreeWeeks }
  public enum TicketStatus { Open, Resolved, Closed }
  public enum TicketPriority { Low, Normal, Urgent }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceTicket Attributes
  private String description;
  private TicketStatus status;
  private Date creationDate;

  //Autounique Attributes
  private int id;

  //MaintenanceTicket Associations
  private Asset asset;
  private List<MaintenanceNote> progressNotes;
  private List<ImageLink> images;
  private List<MaintenanceAccount> assignedStaff;
  private AssetPlus assetPlus;
  private List<TicketAssignment> ticketAssignments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceTicket(String aDescription, TicketStatus aStatus, Date aCreationDate, AssetPlus aAssetPlus)
  {
    description = aDescription;
    status = aStatus;
    creationDate = aCreationDate;
    id = nextId++;
    progressNotes = new ArrayList<MaintenanceNote>();
    images = new ArrayList<ImageLink>();
    assignedStaff = new ArrayList<MaintenanceAccount>();
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create ticket due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    ticketAssignments = new ArrayList<TicketAssignment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(TicketStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreationDate(Date aCreationDate)
  {
    boolean wasSet = false;
    creationDate = aCreationDate;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }

  public TicketStatus getStatus()
  {
    return status;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Asset getAsset()
  {
    return asset;
  }

  public boolean hasAsset()
  {
    boolean has = asset != null;
    return has;
  }
  /* Code from template association_GetMany */
  public MaintenanceNote getProgressNote(int index)
  {
    MaintenanceNote aProgressNote = progressNotes.get(index);
    return aProgressNote;
  }

  public List<MaintenanceNote> getProgressNotes()
  {
    List<MaintenanceNote> newProgressNotes = Collections.unmodifiableList(progressNotes);
    return newProgressNotes;
  }

  public int numberOfProgressNotes()
  {
    int number = progressNotes.size();
    return number;
  }

  public boolean hasProgressNotes()
  {
    boolean has = progressNotes.size() > 0;
    return has;
  }

  public int indexOfProgressNote(MaintenanceNote aProgressNote)
  {
    int index = progressNotes.indexOf(aProgressNote);
    return index;
  }
  /* Code from template association_GetMany */
  public ImageLink getImage(int index)
  {
    ImageLink aImage = images.get(index);
    return aImage;
  }

  public List<ImageLink> getImages()
  {
    List<ImageLink> newImages = Collections.unmodifiableList(images);
    return newImages;
  }

  public int numberOfImages()
  {
    int number = images.size();
    return number;
  }

  public boolean hasImages()
  {
    boolean has = images.size() > 0;
    return has;
  }

  public int indexOfImage(ImageLink aImage)
  {
    int index = images.indexOf(aImage);
    return index;
  }
  /* Code from template association_GetMany */
  public MaintenanceAccount getAssignedStaff(int index)
  {
    MaintenanceAccount aAssignedStaff = assignedStaff.get(index);
    return aAssignedStaff;
  }

  public List<MaintenanceAccount> getAssignedStaff()
  {
    List<MaintenanceAccount> newAssignedStaff = Collections.unmodifiableList(assignedStaff);
    return newAssignedStaff;
  }

  public int numberOfAssignedStaff()
  {
    int number = assignedStaff.size();
    return number;
  }

  public boolean hasAssignedStaff()
  {
    boolean has = assignedStaff.size() > 0;
    return has;
  }

  public int indexOfAssignedStaff(MaintenanceAccount aAssignedStaff)
  {
    int index = assignedStaff.indexOf(aAssignedStaff);
    return index;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
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
  /* Code from template association_SetOptionalOneToMany */
  public boolean setAsset(Asset aAsset)
  {
    boolean wasSet = false;
    Asset existingAsset = asset;
    asset = aAsset;
    if (existingAsset != null && !existingAsset.equals(aAsset))
    {
      existingAsset.removeTicket(this);
    }
    if (aAsset != null)
    {
      aAsset.addTicket(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProgressNotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceNote addProgressNote(String aContent, Date aDate)
  {
    return new MaintenanceNote(aContent, aDate, this);
  }

  public boolean addProgressNote(MaintenanceNote aProgressNote)
  {
    boolean wasAdded = false;
    if (progressNotes.contains(aProgressNote)) { return false; }
    MaintenanceTicket existingTicket = aProgressNote.getTicket();
    boolean isNewTicket = existingTicket != null && !this.equals(existingTicket);
    if (isNewTicket)
    {
      aProgressNote.setTicket(this);
    }
    else
    {
      progressNotes.add(aProgressNote);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProgressNote(MaintenanceNote aProgressNote)
  {
    boolean wasRemoved = false;
    //Unable to remove aProgressNote, as it must always have a ticket
    if (!this.equals(aProgressNote.getTicket()))
    {
      progressNotes.remove(aProgressNote);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addProgressNoteAt(MaintenanceNote aProgressNote, int index)
  {  
    boolean wasAdded = false;
    if(addProgressNote(aProgressNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProgressNotes()) { index = numberOfProgressNotes() - 1; }
      progressNotes.remove(aProgressNote);
      progressNotes.add(index, aProgressNote);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveProgressNoteAt(MaintenanceNote aProgressNote, int index)
  {
    boolean wasAdded = false;
    if(progressNotes.contains(aProgressNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProgressNotes()) { index = numberOfProgressNotes() - 1; }
      progressNotes.remove(aProgressNote);
      progressNotes.add(index, aProgressNote);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addProgressNoteAt(aProgressNote, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfImages()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ImageLink addImage(String aUrl)
  {
    return new ImageLink(aUrl, this);
  }

  public boolean addImage(ImageLink aImage)
  {
    boolean wasAdded = false;
    if (images.contains(aImage)) { return false; }
    MaintenanceTicket existingTicket = aImage.getTicket();
    boolean isNewTicket = existingTicket != null && !this.equals(existingTicket);
    if (isNewTicket)
    {
      aImage.setTicket(this);
    }
    else
    {
      images.add(aImage);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeImage(ImageLink aImage)
  {
    boolean wasRemoved = false;
    //Unable to remove aImage, as it must always have a ticket
    if (!this.equals(aImage.getTicket()))
    {
      images.remove(aImage);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addImageAt(ImageLink aImage, int index)
  {  
    boolean wasAdded = false;
    if(addImage(aImage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfImages()) { index = numberOfImages() - 1; }
      images.remove(aImage);
      images.add(index, aImage);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveImageAt(ImageLink aImage, int index)
  {
    boolean wasAdded = false;
    if(images.contains(aImage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfImages()) { index = numberOfImages() - 1; }
      images.remove(aImage);
      images.add(index, aImage);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addImageAt(aImage, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssignedStaff()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addAssignedStaff(MaintenanceAccount aAssignedStaff)
  {
    boolean wasAdded = false;
    if (assignedStaff.contains(aAssignedStaff)) { return false; }
    assignedStaff.add(aAssignedStaff);
    if (aAssignedStaff.indexOfAssignedTicket(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAssignedStaff.addAssignedTicket(this);
      if (!wasAdded)
      {
        assignedStaff.remove(aAssignedStaff);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeAssignedStaff(MaintenanceAccount aAssignedStaff)
  {
    boolean wasRemoved = false;
    if (!assignedStaff.contains(aAssignedStaff))
    {
      return wasRemoved;
    }

    int oldIndex = assignedStaff.indexOf(aAssignedStaff);
    assignedStaff.remove(oldIndex);
    if (aAssignedStaff.indexOfAssignedTicket(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAssignedStaff.removeAssignedTicket(this);
      if (!wasRemoved)
      {
        assignedStaff.add(oldIndex,aAssignedStaff);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssignedStaffAt(MaintenanceAccount aAssignedStaff, int index)
  {  
    boolean wasAdded = false;
    if(addAssignedStaff(aAssignedStaff))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedStaff()) { index = numberOfAssignedStaff() - 1; }
      assignedStaff.remove(aAssignedStaff);
      assignedStaff.add(index, aAssignedStaff);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssignedStaffAt(MaintenanceAccount aAssignedStaff, int index)
  {
    boolean wasAdded = false;
    if(assignedStaff.contains(aAssignedStaff))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedStaff()) { index = numberOfAssignedStaff() - 1; }
      assignedStaff.remove(aAssignedStaff);
      assignedStaff.add(index, aAssignedStaff);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssignedStaffAt(aAssignedStaff, index);
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
      existingAssetPlus.removeTicket(this);
    }
    assetPlus.addTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTicketAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TicketAssignment addTicketAssignment(boolean aRequiresManagerApproval, TicketPriority aPriority, TimeEstimate aTimeEstimate, MaintenanceAccount aMaintenanceAccount)
  {
    return new TicketAssignment(aRequiresManagerApproval, aPriority, aTimeEstimate, this, aMaintenanceAccount);
  }

  public boolean addTicketAssignment(TicketAssignment aTicketAssignment)
  {
    boolean wasAdded = false;
    if (ticketAssignments.contains(aTicketAssignment)) { return false; }
    MaintenanceTicket existingMaintenanceTicket = aTicketAssignment.getMaintenanceTicket();
    boolean isNewMaintenanceTicket = existingMaintenanceTicket != null && !this.equals(existingMaintenanceTicket);
    if (isNewMaintenanceTicket)
    {
      aTicketAssignment.setMaintenanceTicket(this);
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
    //Unable to remove aTicketAssignment, as it must always have a maintenanceTicket
    if (!this.equals(aTicketAssignment.getMaintenanceTicket()))
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
    if (asset != null)
    {
      Asset placeholderAsset = asset;
      this.asset = null;
      placeholderAsset.removeTicket(this);
    }
    for(int i=progressNotes.size(); i > 0; i--)
    {
      MaintenanceNote aProgressNote = progressNotes.get(i - 1);
      aProgressNote.delete();
    }
    for(int i=images.size(); i > 0; i--)
    {
      ImageLink aImage = images.get(i - 1);
      aImage.delete();
    }
    ArrayList<MaintenanceAccount> copyOfAssignedStaff = new ArrayList<MaintenanceAccount>(assignedStaff);
    assignedStaff.clear();
    for(MaintenanceAccount aAssignedStaff : copyOfAssignedStaff)
    {
      aAssignedStaff.removeAssignedTicket(this);
    }
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeTicket(this);
    }
    for(int i=ticketAssignments.size(); i > 0; i--)
    {
      TicketAssignment aTicketAssignment = ticketAssignments.get(i - 1);
      aTicketAssignment.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "creationDate" + "=" + (getCreationDate() != null ? !getCreationDate().equals(this)  ? getCreationDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "asset = "+(getAsset()!=null?Integer.toHexString(System.identityHashCode(getAsset())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null");
  }
}