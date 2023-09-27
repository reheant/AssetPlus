/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.sql.Date;
import java.util.*;

// line 63 "model.ump"
// line 158 "model.ump"
public class Asset
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TicketStatus { Open, Resolved, Closed }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextUniqueNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Asset Attributes
  private Date purchaseDate;
  private int expectedLifespanDays;

  //Autounique Attributes
  private int uniqueNumber;

  //Asset Associations
  private AssetType type;
  private AssetPlus assetPlus;
  private List<MaintenanceTicket> tickets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Asset(Date aPurchaseDate, int aExpectedLifespanDays, AssetType aType, AssetPlus aAssetPlus)
  {
    purchaseDate = aPurchaseDate;
    expectedLifespanDays = aExpectedLifespanDays;
    uniqueNumber = nextUniqueNumber++;
    boolean didAddType = setType(aType);
    if (!didAddType)
    {
      throw new RuntimeException("Unable to create asset due to type. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create asset due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    tickets = new ArrayList<MaintenanceTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPurchaseDate(Date aPurchaseDate)
  {
    boolean wasSet = false;
    purchaseDate = aPurchaseDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpectedLifespanDays(int aExpectedLifespanDays)
  {
    boolean wasSet = false;
    expectedLifespanDays = aExpectedLifespanDays;
    wasSet = true;
    return wasSet;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }

  public int getExpectedLifespanDays()
  {
    return expectedLifespanDays;
  }

  public int getUniqueNumber()
  {
    return uniqueNumber;
  }
  /* Code from template association_GetOne */
  public AssetType getType()
  {
    return type;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getTicket(int index)
  {
    MaintenanceTicket aTicket = tickets.get(index);
    return aTicket;
  }

  public List<MaintenanceTicket> getTickets()
  {
    List<MaintenanceTicket> newTickets = Collections.unmodifiableList(tickets);
    return newTickets;
  }

  public int numberOfTickets()
  {
    int number = tickets.size();
    return number;
  }

  public boolean hasTickets()
  {
    boolean has = tickets.size() > 0;
    return has;
  }

  public int indexOfTicket(MaintenanceTicket aTicket)
  {
    int index = tickets.indexOf(aTicket);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setType(AssetType aType)
  {
    boolean wasSet = false;
    if (aType == null)
    {
      return wasSet;
    }

    AssetType existingType = type;
    type = aType;
    if (existingType != null && !existingType.equals(aType))
    {
      existingType.removeAsset(this);
    }
    type.addAsset(this);
    wasSet = true;
    return wasSet;
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
      existingAssetPlus.removeAsset(this);
    }
    assetPlus.addAsset(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addTicket(MaintenanceTicket aTicket)
  {
    boolean wasAdded = false;
    if (tickets.contains(aTicket)) { return false; }
    Asset existingAsset = aTicket.getAsset();
    if (existingAsset == null)
    {
      aTicket.setAsset(this);
    }
    else if (!this.equals(existingAsset))
    {
      existingAsset.removeTicket(aTicket);
      addTicket(aTicket);
    }
    else
    {
      tickets.add(aTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicket(MaintenanceTicket aTicket)
  {
    boolean wasRemoved = false;
    if (tickets.contains(aTicket))
    {
      tickets.remove(aTicket);
      aTicket.setAsset(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketAt(MaintenanceTicket aTicket, int index)
  {  
    boolean wasAdded = false;
    if(addTicket(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketAt(MaintenanceTicket aTicket, int index)
  {
    boolean wasAdded = false;
    if(tickets.contains(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketAt(aTicket, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    AssetType placeholderType = type;
    this.type = null;
    if(placeholderType != null)
    {
      placeholderType.removeAsset(this);
    }
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeAsset(this);
    }
    while( !tickets.isEmpty() )
    {
      tickets.get(0).setAsset(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "uniqueNumber" + ":" + getUniqueNumber()+ "," +
            "expectedLifespanDays" + ":" + getExpectedLifespanDays()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "type = "+(getType()!=null?Integer.toHexString(System.identityHashCode(getType())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null");
  }
}