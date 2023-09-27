/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.util.*;

// line 22 "model.ump"
// line 136 "model.ump"
public class ManagerAccount extends MaintenanceAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ManagerAccount Attributes
  private String email;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ManagerAccount(String aEmail, String aPassword, AssetPlus aAssetPlus)
  {
    super(aEmail, aPassword, aAssetPlus);
    email = "manager@ap.com";
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "]";
  }
}