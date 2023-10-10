package ca.mcgill.ecse.assetplus.controller;

import java.security.InvalidParameterException;
import java.sql.Date;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet3Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus(); 

  public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
      Date purchaseDate, String assetTypeName) {     

        if (AssetType.hasWithName(assetTypeName) && assetNumber >= 1 && floorNumber >= 0 && roomNumber >= -1){
          try {
            assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, AssetType.getWithName(assetTypeName));
          } catch (RuntimeException e) {
            return e.getMessage();
          }
          return "";
        }
        return "Entered asset information is incorrect";
  }

  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {

        if (AssetType.hasWithName(newAssetTypeName) && assetNumber >= 1 && newFloorNumber >= 0 && newRoomNumber >= -1){
          try {
            SpecificAsset specificAsset = assetPlus.getSpecificAsset(assetNumber);
            specificAsset.setFloorNumber(newFloorNumber);
            specificAsset.setRoomNumber(newRoomNumber);
            specificAsset.setPurchaseDate(newPurchaseDate);
            specificAsset.setAssetType(AssetType.getWithName(newAssetTypeName)); 
          } catch (RuntimeException e) {
            return e.getMessage();
          }
          return "";
        }
        return "Entered asset information is incorrect";
  }

  public static void deleteSpecificAsset(int assetNumber) {

    try {
      SpecificAsset specificAsset = assetPlus.getSpecificAsset(assetNumber);
      if (specificAsset != null) {
        specificAsset.delete();
      }
    } catch (RuntimeException e) {
      throw new InvalidParameterException("asset number is invalid");
    }
  }

}
