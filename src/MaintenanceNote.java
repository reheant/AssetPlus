/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.sql.Date;

// line 82 "model.ump"
// line 168 "model.ump"
public class MaintenanceNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceNote Attributes
  private String content;
  private Date date;

  //MaintenanceNote Associations
  private MaintenanceTicket ticket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceNote(String aContent, Date aDate, MaintenanceTicket aTicket)
  {
    content = aContent;
    date = aDate;
    boolean didAddTicket = setTicket(aTicket);
    if (!didAddTicket)
    {
      throw new RuntimeException("Unable to create progressNote due to ticket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public MaintenanceTicket getTicket()
  {
    return ticket;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTicket(MaintenanceTicket aTicket)
  {
    boolean wasSet = false;
    if (aTicket == null)
    {
      return wasSet;
    }

    MaintenanceTicket existingTicket = ticket;
    ticket = aTicket;
    if (existingTicket != null && !existingTicket.equals(aTicket))
    {
      existingTicket.removeProgressNote(this);
    }
    ticket.addProgressNote(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MaintenanceTicket placeholderTicket = ticket;
    this.ticket = null;
    if(placeholderTicket != null)
    {
      placeholderTicket.removeProgressNote(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "content" + ":" + getContent()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null");
  }
}