/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;

// line 81 "../../../../../AssetPlus.ump"
public class MaintenanceNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceNote Attributes
  private String content;
  private Date date;

  //MaintenanceNote Associations
  private MaintenanceTicket maintenanceTicket;
  private UserAccount author;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceNote(String aContent, Date aDate, MaintenanceTicket aMaintenanceTicket, UserAccount aAuthor)
  {
    content = aContent;
    date = aDate;
    boolean didAddMaintenanceTicket = setMaintenanceTicket(aMaintenanceTicket);
    if (!didAddMaintenanceTicket)
    {
      throw new RuntimeException("Unable to create progressNote due to maintenanceTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAuthor = setAuthor(aAuthor);
    if (!didAddAuthor)
    {
      throw new RuntimeException("Unable to create writtenNote due to author. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setContent(String aContent)
  {
    boolean wasSet = false;
    content = aContent;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public String getContent()
  {
    return content;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public MaintenanceTicket getMaintenanceTicket()
  {
    return maintenanceTicket;
  }
  /* Code from template association_GetOne */
  public UserAccount getAuthor()
  {
    return author;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasSet = false;
    if (aMaintenanceTicket == null)
    {
      return wasSet;
    }

    MaintenanceTicket existingMaintenanceTicket = maintenanceTicket;
    maintenanceTicket = aMaintenanceTicket;
    if (existingMaintenanceTicket != null && !existingMaintenanceTicket.equals(aMaintenanceTicket))
    {
      existingMaintenanceTicket.removeProgressNote(this);
    }
    maintenanceTicket.addProgressNote(this);
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
      existingAuthor.removeWrittenNote(this);
    }
    author.addWrittenNote(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MaintenanceTicket placeholderMaintenanceTicket = maintenanceTicket;
    this.maintenanceTicket = null;
    if(placeholderMaintenanceTicket != null)
    {
      placeholderMaintenanceTicket.removeProgressNote(this);
    }
    UserAccount placeholderAuthor = author;
    this.author = null;
    if(placeholderAuthor != null)
    {
      placeholderAuthor.removeWrittenNote(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "content" + ":" + getContent()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "maintenanceTicket = "+(getMaintenanceTicket()!=null?Integer.toHexString(System.identityHashCode(getMaintenanceTicket())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "author = "+(getAuthor()!=null?Integer.toHexString(System.identityHashCode(getAuthor())):"null");
  }
}