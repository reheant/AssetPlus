/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;

// line 100 "../../../../../AssetPlus.ump"
public class Room extends Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private int roomNumber;

  //Room Associations
  private Floor floor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(AssetPlus aAssetPlus, int aRoomNumber, Floor aFloor)
  {
    super(aAssetPlus);
    roomNumber = aRoomNumber;
    boolean didAddFloor = setFloor(aFloor);
    if (!didAddFloor)
    {
      throw new RuntimeException("Unable to create room due to floor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomNumber(int aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
    wasSet = true;
    return wasSet;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }
  /* Code from template association_GetOne */
  public Floor getFloor()
  {
    return floor;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFloor(Floor aFloor)
  {
    boolean wasSet = false;
    if (aFloor == null)
    {
      return wasSet;
    }

    Floor existingFloor = floor;
    floor = aFloor;
    if (existingFloor != null && !existingFloor.equals(aFloor))
    {
      existingFloor.removeRoom(this);
    }
    floor.addRoom(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Floor placeholderFloor = floor;
    this.floor = null;
    if(placeholderFloor != null)
    {
      placeholderFloor.removeRoom(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "roomNumber" + ":" + getRoomNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "floor = "+(getFloor()!=null?Integer.toHexString(System.identityHashCode(getFloor())):"null");
  }
}