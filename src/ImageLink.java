/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 87 "model.ump"
// line 173 "model.ump"
public class ImageLink
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ImageLink Attributes
  private String url;

  //ImageLink Associations
  private MaintenanceTicket ticket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ImageLink(String aUrl, MaintenanceTicket aTicket)
  {
    url = aUrl;
    boolean didAddTicket = setTicket(aTicket);
    if (!didAddTicket)
    {
      throw new RuntimeException("Unable to create image due to ticket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public String getUrl()
  {
    return url;
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
      existingTicket.removeImage(this);
    }
    ticket.addImage(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MaintenanceTicket placeholderTicket = ticket;
    this.ticket = null;
    if(placeholderTicket != null)
    {
      placeholderTicket.removeImage(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "url" + ":" + getUrl()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null");
  }
}