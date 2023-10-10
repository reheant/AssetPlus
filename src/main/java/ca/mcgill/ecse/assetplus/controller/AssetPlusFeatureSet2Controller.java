package ca.mcgill.ecse.assetplus.controller;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet2Controller {
    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    public static String addAssetType(String name, int expectedLifeSpanInDays) {
        //input validation
        var error = "";
        if (name == null) {
            error = "A valid name must be inputted to add asset type";
        }
        if (expectedLifeSpanInDays <= 0) {
            error += "Not a valid life span input"
        }
        if (!error.isEmpty()) {
            return error.trim();
        }

        //call model
        try {
            assetPlus.addAssetType(name, expectedLifeSpanInDays);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

  public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {
        //input validation
        var error = "";
        AssetType assetType = AssetType.getWithName(oldName)
        if (assetType == null) {
            error = "A valid old asset type name must be inputted";
        }
        if (expectedLifeSpanInDays <= 0){
            error+= "Not a valid life span input"
        }
        if (!error.isEmpty()) {
            return error.trim();
        }

        //call model
        try {
            assetType.setName(newName);
            assetType.setExpectedLifeSpan(newExpectedLifeSpanInDays);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
  }

  public static void deleteAssetType(String name) {
      //call model
      AssetType assetType = AssetType.getWithName(name)
      if (assetType != null) {
          assetType.delete();
      }

  }

}