/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 11 "../../../../../AssetPlus.ump"
public class UserAccount
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, UserAccount> useraccountsByEmail = new HashMap<String, UserAccount>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserAccount Attributes
  private String email;
  private String password;
  private String name;
  private String phoneNumber;

  //UserAccount Associations
  private AssetPlus assetPlus;
  private List<MaintenanceTicket> writtenTickets;
  private List<MaintenanceNote> writtenNotes;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(String aEmail, String aPassword, AssetPlus aAssetPlus)
  {
    password = aPassword;
    name = null;
    phoneNumber = null;
    if (!setEmail(aEmail))
    {
      throw new RuntimeException("Cannot create due to duplicate email. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create userAccount due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    writtenTickets = new ArrayList<MaintenanceTicket>();
    writtenNotes = new ArrayList<MaintenanceNote>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    // line 18 "../../../../../AssetPlus.ump"
    if (aEmail != null){
          return false;
        }
    // END OF UMPLE BEFORE INJECTION
    String anOldEmail = getEmail();
    if (anOldEmail != null && anOldEmail.equals(aEmail)) {
      return true;
    }
    if (hasWithEmail(aEmail)) {
      return wasSet;
    }
    email = aEmail;
    wasSet = true;
    if (anOldEmail != null) {
      useraccountsByEmail.remove(anOldEmail);
    }
    useraccountsByEmail.put(aEmail, this);
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template attribute_GetUnique */
  public static UserAccount getWithEmail(String aEmail)
  {
    return useraccountsByEmail.get(aEmail);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEmail(String aEmail)
  {
    return getWithEmail(aEmail) != null;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getWrittenTicket(int index)
  {
    MaintenanceTicket aWrittenTicket = writtenTickets.get(index);
    return aWrittenTicket;
  }

  public List<MaintenanceTicket> getWrittenTickets()
  {
    List<MaintenanceTicket> newWrittenTickets = Collections.unmodifiableList(writtenTickets);
    return newWrittenTickets;
  }

  public int numberOfWrittenTickets()
  {
    int number = writtenTickets.size();
    return number;
  }

  public boolean hasWrittenTickets()
  {
    boolean has = writtenTickets.size() > 0;
    return has;
  }

  public int indexOfWrittenTicket(MaintenanceTicket aWrittenTicket)
  {
    int index = writtenTickets.indexOf(aWrittenTicket);
    return index;
  }
  /* Code from template association_GetMany */
  public MaintenanceNote getWrittenNote(int index)
  {
    MaintenanceNote aWrittenNote = writtenNotes.get(index);
    return aWrittenNote;
  }

  public List<MaintenanceNote> getWrittenNotes()
  {
    List<MaintenanceNote> newWrittenNotes = Collections.unmodifiableList(writtenNotes);
    return newWrittenNotes;
  }

  public int numberOfWrittenNotes()
  {
    int number = writtenNotes.size();
    return number;
  }

  public boolean hasWrittenNotes()
  {
    boolean has = writtenNotes.size() > 0;
    return has;
  }

  public int indexOfWrittenNote(MaintenanceNote aWrittenNote)
  {
    int index = writtenNotes.indexOf(aWrittenNote);
    return index;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setAssetPlus(AssetPlus aAssetPlus)
  {
    boolean wasSet = false;
    //Must provide assetPlus to userAccount
    if (aAssetPlus == null)
    {
      return wasSet;
    }

    if (assetPlus != null && assetPlus.numberOfUserAccounts() <= AssetPlus.minimumNumberOfUserAccounts())
    {
      return wasSet;
    }

    AssetPlus existingAssetPlus = assetPlus;
    assetPlus = aAssetPlus;
    if (existingAssetPlus != null && !existingAssetPlus.equals(aAssetPlus))
    {
      boolean didRemove = existingAssetPlus.removeUserAccount(this);
      if (!didRemove)
      {
        assetPlus = existingAssetPlus;
        return wasSet;
      }
    }
    assetPlus.addUserAccount(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWrittenTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceTicket addWrittenTicket(String aDescription, MaintenanceTicket.TicketStatus aStatus, Date aCreationDate, boolean aRequiresManagerApproval, MaintenanceTicket.TicketPriority aPriority, MaintenanceTicket.TimeEstimate aTimeEstimate, AssetPlus aAssetPlus)
  {
    return new MaintenanceTicket(aDescription, aStatus, aCreationDate, aRequiresManagerApproval, aPriority, aTimeEstimate, aAssetPlus, this);
  }

  public boolean addWrittenTicket(MaintenanceTicket aWrittenTicket)
  {
    boolean wasAdded = false;
    if (writtenTickets.contains(aWrittenTicket)) { return false; }
    UserAccount existingAuthor = aWrittenTicket.getAuthor();
    boolean isNewAuthor = existingAuthor != null && !this.equals(existingAuthor);
    if (isNewAuthor)
    {
      aWrittenTicket.setAuthor(this);
    }
    else
    {
      writtenTickets.add(aWrittenTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWrittenTicket(MaintenanceTicket aWrittenTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aWrittenTicket, as it must always have a author
    if (!this.equals(aWrittenTicket.getAuthor()))
    {
      writtenTickets.remove(aWrittenTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWrittenTicketAt(MaintenanceTicket aWrittenTicket, int index)
  {  
    boolean wasAdded = false;
    if(addWrittenTicket(aWrittenTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWrittenTickets()) { index = numberOfWrittenTickets() - 1; }
      writtenTickets.remove(aWrittenTicket);
      writtenTickets.add(index, aWrittenTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWrittenTicketAt(MaintenanceTicket aWrittenTicket, int index)
  {
    boolean wasAdded = false;
    if(writtenTickets.contains(aWrittenTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWrittenTickets()) { index = numberOfWrittenTickets() - 1; }
      writtenTickets.remove(aWrittenTicket);
      writtenTickets.add(index, aWrittenTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWrittenTicketAt(aWrittenTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWrittenNotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceNote addWrittenNote(String aContent, Date aDate, MaintenanceTicket aMaintenanceTicket)
  {
    return new MaintenanceNote(aContent, aDate, aMaintenanceTicket, this);
  }

  public boolean addWrittenNote(MaintenanceNote aWrittenNote)
  {
    boolean wasAdded = false;
    if (writtenNotes.contains(aWrittenNote)) { return false; }
    UserAccount existingAuthor = aWrittenNote.getAuthor();
    boolean isNewAuthor = existingAuthor != null && !this.equals(existingAuthor);
    if (isNewAuthor)
    {
      aWrittenNote.setAuthor(this);
    }
    else
    {
      writtenNotes.add(aWrittenNote);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWrittenNote(MaintenanceNote aWrittenNote)
  {
    boolean wasRemoved = false;
    //Unable to remove aWrittenNote, as it must always have a author
    if (!this.equals(aWrittenNote.getAuthor()))
    {
      writtenNotes.remove(aWrittenNote);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWrittenNoteAt(MaintenanceNote aWrittenNote, int index)
  {  
    boolean wasAdded = false;
    if(addWrittenNote(aWrittenNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWrittenNotes()) { index = numberOfWrittenNotes() - 1; }
      writtenNotes.remove(aWrittenNote);
      writtenNotes.add(index, aWrittenNote);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWrittenNoteAt(MaintenanceNote aWrittenNote, int index)
  {
    boolean wasAdded = false;
    if(writtenNotes.contains(aWrittenNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWrittenNotes()) { index = numberOfWrittenNotes() - 1; }
      writtenNotes.remove(aWrittenNote);
      writtenNotes.add(index, aWrittenNote);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWrittenNoteAt(aWrittenNote, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    useraccountsByEmail.remove(getEmail());
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeUserAccount(this);
    }
    for(int i=writtenTickets.size(); i > 0; i--)
    {
      MaintenanceTicket aWrittenTicket = writtenTickets.get(i - 1);
      aWrittenTicket.delete();
    }
    for(int i=writtenNotes.size(); i > 0; i--)
    {
      MaintenanceNote aWrittenNote = writtenNotes.get(i - 1);
      aWrittenNote.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null");
  }
}