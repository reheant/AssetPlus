/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;

// line 85 "../../../../../AssetPlus.ump"
public abstract class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Associations
  private AssetPlus assetPlus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(AssetPlus aAssetPlus)
  {
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create location due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
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
      existingAssetPlus.removeLocation(this);
    }
    assetPlus.addLocation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeLocation(this);
    }
  }

}