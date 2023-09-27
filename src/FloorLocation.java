/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.util.*;

// line 94 "model.ump"
// line 183 "model.ump"
public class FloorLocation extends Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FloorLocation Attributes
  private int floorNumber;

  //FloorLocation Associations
  private List<RoomLocation> rooms;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FloorLocation(AssetPlus aAssetPlus, int aFloorNumber)
  {
    super(aAssetPlus);
    floorNumber = aFloorNumber;
    rooms = new ArrayList<RoomLocation>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFloorNumber(int aFloorNumber)
  {
    boolean wasSet = false;
    floorNumber = aFloorNumber;
    wasSet = true;
    return wasSet;
  }

  public int getFloorNumber()
  {
    return floorNumber;
  }
  /* Code from template association_GetMany */
  public RoomLocation getRoom(int index)
  {
    RoomLocation aRoom = rooms.get(index);
    return aRoom;
  }

  public List<RoomLocation> getRooms()
  {
    List<RoomLocation> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(RoomLocation aRoom)
  {
    int index = rooms.indexOf(aRoom);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public RoomLocation addRoom(AssetPlus aAssetPlus, int aRoomNumber)
  {
    return new RoomLocation(aAssetPlus, aRoomNumber, this);
  }

  public boolean addRoom(RoomLocation aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    FloorLocation existingFloor = aRoom.getFloor();
    boolean isNewFloor = existingFloor != null && !this.equals(existingFloor);
    if (isNewFloor)
    {
      aRoom.setFloor(this);
    }
    else
    {
      rooms.add(aRoom);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoom(RoomLocation aRoom)
  {
    boolean wasRemoved = false;
    //Unable to remove aRoom, as it must always have a floor
    if (!this.equals(aRoom.getFloor()))
    {
      rooms.remove(aRoom);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(RoomLocation aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(RoomLocation aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=rooms.size(); i > 0; i--)
    {
      RoomLocation aRoom = rooms.get(i - 1);
      aRoom.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "floorNumber" + ":" + getFloorNumber()+ "]";
  }
}