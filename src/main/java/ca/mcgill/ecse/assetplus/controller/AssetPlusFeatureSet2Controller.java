package ca.mcgill.ecse.assetplus.controller;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet2Controller {
	
    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    
    /**
     * @author Tiffany Miller
     * @param name The name of the asset type to create. Must not be empty or null.
     * @param expectedLifeSpanInDays The expected life span in days of the asset type. Must be > 0.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String addAssetType(String name, int expectedLifeSpanInDays) {
        var error = "";
        error += nameValidation(name);
        error += lifeSpanValidation(expectedLifeSpanInDays);
        
        if (!error.isEmpty()) {
            return error.trim();
        }

        try {
            assetPlus.addAssetType(name, expectedLifeSpanInDays);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }
    
    /**
     * @author Tiffany Miller
     * @param oldName The old name of the asset type to modify. Must not be empty or null. The old name must belong to an existing asset type.
     * @param newName The new name of the asset type to modify. Must not be empty or null.
     * @param newExpectedLifeSpanInDays The new expected life span in days of the asset type of which to modify. Must be > 0.
     * @return An empty string indicating success. An error message if failure.
     */
  public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {
        var error = "";
        error += nameValidation(newName);
        error += nameValidation(oldName);
        error += lifeSpanValidation(newExpectedLifeSpanInDays);
        
        AssetType assetType = AssetType.getWithName(oldName);
        if (assetType == null) {
            error = "Old asset name is not valid. ";
        }
        if (!error.isEmpty()) {
            return error.trim();
        }

        try {
            assetType.setName(newName);
            assetType.setExpectedLifeSpan(newExpectedLifeSpanInDays);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
  }
  
  /**
   * @author Tiffany Miller
   * @param name The name of the asset type to delete. Must not be empty or null.
   */
  public static void deleteAssetType(String name) {
      AssetType assetType = AssetType.getWithName(name);
      if (assetType != null) {
          assetType.delete();
      }
      else{
    	  throw new NullPointerException("No such asset type");
      }

  }
  
  /**
   * @author Tiffany Miller
   * @param name The name of the asset type to validate. Must not be empty or null.
   * @return An empty string indicating success. An error message if failure.
   */
  private static String nameValidation(String name) {
	  if (name == null || name.isEmpty()) {
          return "A valid name must be inputted. ";
      }
	  else {
		  return "";
	  }
  }
  	
  /**
   * @author Tiffany Miller
   * @param expectedLifeSpan The expected life span in days of the asset type. Must be > 0.
   * @return An empty string indicating success. An error message if failure.
   */
    private static String lifeSpanValidation(int expectedLifeSpan) {
        if (expectedLifeSpan <= 0) {
            return "Not a valid life span input. ";
        }
        else{
            return "";
        }
    }

}