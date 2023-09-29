/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;
import java.util.*;

// line 41 "../../../../../AssetPlus.ump"
public class MaintenanceTicket
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TimeEstimate { LESS_THAN_ONE_DAY, BETWEEN_ONE_TO_THREE_DAYS, BETWEEN_THREE_AND_SEVEN_DAYS, BETWEEN_ONE_AND_THREE_WEEKS, MORE_THAN_ONE_WEEK }
  public enum TicketStatus { OPEN, RESOLVED, CLOSED }
  public enum TicketPriority { LOW, NORMAL, URGENT }

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
  private boolean requiresManagerApproval;
  private TicketPriority priority;
  private TimeEstimate timeEstimate;

  //Autounique Attributes
  private int id;

  //MaintenanceTicket Associations
  private Asset asset;
  private List<ImageLink> images;
  private List<MaintenanceNote> progressNotes;
  private AssetPlus assetPlus;
  private UserAccount author;
  private List<TicketAssignment> ticketAssignments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceTicket(String aDescription, TicketStatus aStatus, Date aCreationDate, boolean aRequiresManagerApproval, TicketPriority aPriority, TimeEstimate aTimeEstimate, AssetPlus aAssetPlus, UserAccount aAuthor)
  {
    description = aDescription;
    status = aStatus;
    creationDate = aCreationDate;
    requiresManagerApproval = aRequiresManagerApproval;
    priority = aPriority;
    timeEstimate = aTimeEstimate;
    id = nextId++;
    images = new ArrayList<ImageLink>();
    progressNotes = new ArrayList<MaintenanceNote>();
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create maintenanceTicket due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAuthor = setAuthor(aAuthor);
    if (!didAddAuthor)
    {
      throw new RuntimeException("Unable to create writtenTicket due to author. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetOne */
  public UserAccount getAuthor()
  {
    return author;
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
      existingAsset.removeMaintenanceTicket(this);
    }
    if (aAsset != null)
    {
      aAsset.addMaintenanceTicket(this);
    }
    wasSet = true;
    return wasSet;
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
    MaintenanceTicket existingMaintenanceTicket = aImage.getMaintenanceTicket();
    boolean isNewMaintenanceTicket = existingMaintenanceTicket != null && !this.equals(existingMaintenanceTicket);
    if (isNewMaintenanceTicket)
    {
      aImage.setMaintenanceTicket(this);
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
    //Unable to remove aImage, as it must always have a maintenanceTicket
    if (!this.equals(aImage.getMaintenanceTicket()))
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
  public static int minimumNumberOfProgressNotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceNote addProgressNote(String aContent, Date aDate, UserAccount aAuthor)
  {
    return new MaintenanceNote(aContent, aDate, this, aAuthor);
  }

  public boolean addProgressNote(MaintenanceNote aProgressNote)
  {
    boolean wasAdded = false;
    if (progressNotes.contains(aProgressNote)) { return false; }
    MaintenanceTicket existingMaintenanceTicket = aProgressNote.getMaintenanceTicket();
    boolean isNewMaintenanceTicket = existingMaintenanceTicket != null && !this.equals(existingMaintenanceTicket);
    if (isNewMaintenanceTicket)
    {
      aProgressNote.setMaintenanceTicket(this);
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
    //Unable to remove aProgressNote, as it must always have a maintenanceTicket
    if (!this.equals(aProgressNote.getMaintenanceTicket()))
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
  public boolean setAuthor(UserAccount aAuthor)
  {
    boolean wasSet = false;
    if (aAuthor == null)
    {
      return wasSet;
    }

    UserAccount existingAuthor = author;
    author = aAuthor;
    if (existingAuthor != null && !existingAuthor.equals(aAuthor))
    {
      existingAuthor.removeWrittenTicket(this);
    }
    author.addWrittenTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTicketAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TicketAssignment addTicketAssignment(MaintenanceAccount aMaintenanceAccount)
  {
    return new TicketAssignment(this, aMaintenanceAccount);
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
      placeholderAsset.removeMaintenanceTicket(this);
    }
    while (images.size() > 0)
    {
      ImageLink aImage = images.get(images.size() - 1);
      aImage.delete();
      images.remove(aImage);
    }
    
    while (progressNotes.size() > 0)
    {
      MaintenanceNote aProgressNote = progressNotes.get(progressNotes.size() - 1);
      aProgressNote.delete();
      progressNotes.remove(aProgressNote);
    }
    
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeMaintenanceTicket(this);
    }
    UserAccount placeholderAuthor = author;
    this.author = null;
    if(placeholderAuthor != null)
    {
      placeholderAuthor.removeWrittenTicket(this);
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
            "description" + ":" + getDescription()+ "," +
            "requiresManagerApproval" + ":" + getRequiresManagerApproval()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "creationDate" + "=" + (getCreationDate() != null ? !getCreationDate().equals(this)  ? getCreationDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "priority" + "=" + (getPriority() != null ? !getPriority().equals(this)  ? getPriority().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeEstimate" + "=" + (getTimeEstimate() != null ? !getTimeEstimate().equals(this)  ? getTimeEstimate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "asset = "+(getAsset()!=null?Integer.toHexString(System.identityHashCode(getAsset())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "author = "+(getAuthor()!=null?Integer.toHexString(System.identityHashCode(getAuthor())):"null");
  }
}