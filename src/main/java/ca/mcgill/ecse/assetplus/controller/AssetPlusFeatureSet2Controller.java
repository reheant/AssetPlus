package ca.mcgill.ecse.assetplus.controller;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet2Controller {
	
    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    
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

  public static void deleteAssetType(String name) {
      AssetType assetType = AssetType.getWithName(name);
      if (assetType != null) {
          assetType.delete();
      }

  }
  
  private static String nameValidation(String name) {
	  if (name == null || name.isEmpty()) {
          return "A valid name must be inputted. ";
      }
	  else {
		  return "";
	  }
  }

    private static String lifeSpanValidation(int expectedLifeSpan) {
        if (expectedLifeSpan <= 0) {
            return "Not a valid life span input. ";
        }
        else{
            return "";
        }
    }

}