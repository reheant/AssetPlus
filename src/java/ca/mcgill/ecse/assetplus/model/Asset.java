/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;
import java.util.*;

// line 38 "../../../../../AssetPlus.ump"
public class Asset
{

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
  private List<MaintenanceTicket> maintenanceTickets;

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
    maintenanceTickets = new ArrayList<MaintenanceTicket>();
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
  public MaintenanceTicket getMaintenanceTicket(int index)
  {
    MaintenanceTicket aMaintenanceTicket = maintenanceTickets.get(index);
    return aMaintenanceTicket;
  }

  public List<MaintenanceTicket> getMaintenanceTickets()
  {
    List<MaintenanceTicket> newMaintenanceTickets = Collections.unmodifiableList(maintenanceTickets);
    return newMaintenanceTickets;
  }

  public int numberOfMaintenanceTickets()
  {
    int number = maintenanceTickets.size();
    return number;
  }

  public boolean hasMaintenanceTickets()
  {
    boolean has = maintenanceTickets.size() > 0;
    return has;
  }

  public int indexOfMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    int index = maintenanceTickets.indexOf(aMaintenanceTicket);
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
  public static int minimumNumberOfMaintenanceTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasAdded = false;
    if (maintenanceTickets.contains(aMaintenanceTicket)) { return false; }
    Asset existingAsset = aMaintenanceTicket.getAsset();
    if (existingAsset == null)
    {
      aMaintenanceTicket.setAsset(this);
    }
    else if (!this.equals(existingAsset))
    {
      existingAsset.removeMaintenanceTicket(aMaintenanceTicket);
      addMaintenanceTicket(aMaintenanceTicket);
    }
    else
    {
      maintenanceTickets.add(aMaintenanceTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasRemoved = false;
    if (maintenanceTickets.contains(aMaintenanceTicket))
    {
      maintenanceTickets.remove(aMaintenanceTicket);
      aMaintenanceTicket.setAsset(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMaintenanceTicketAt(MaintenanceTicket aMaintenanceTicket, int index)
  {  
    boolean wasAdded = false;
    if(addMaintenanceTicket(aMaintenanceTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceTickets()) { index = numberOfMaintenanceTickets() - 1; }
      maintenanceTickets.remove(aMaintenanceTicket);
      maintenanceTickets.add(index, aMaintenanceTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMaintenanceTicketAt(MaintenanceTicket aMaintenanceTicket, int index)
  {
    boolean wasAdded = false;
    if(maintenanceTickets.contains(aMaintenanceTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceTickets()) { index = numberOfMaintenanceTickets() - 1; }
      maintenanceTickets.remove(aMaintenanceTicket);
      maintenanceTickets.add(index, aMaintenanceTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMaintenanceTicketAt(aMaintenanceTicket, index);
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
    while( !maintenanceTickets.isEmpty() )
    {
      maintenanceTickets.get(0).setAsset(null);
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