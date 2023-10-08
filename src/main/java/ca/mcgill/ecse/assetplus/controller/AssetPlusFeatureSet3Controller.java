package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet3Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
      Date purchaseDate, String assetTypeName) {
         
        if (AssetType.hasWithName(assetTypeName)){
          try {
            assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, AssetType.getWithName(assetTypeName));
          } catch (RuntimeException e) {
            return e.getMessage();
          }
          return "";
        }
        return "entered asset type name is incorrect";
  }

  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {
        try {
          SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(assetNumber);
          specificAsset.setFloorNumber(newFloorNumber);
          specificAsset.setRoomNumber(newRoomNumber);
          specificAsset.setPurchaseDate(newPurchaseDate);
          specificAsset.setAssetType(AssetType.getWithName(newAssetTypeName)); //TODO: verify error validation, like above with AssetType.hasWithName(assetTypeName
        } catch (RuntimeException e) {
          return e.getMessage();
        }
        return "";
  }

  public static void deleteSpecificAsset(int assetNumber) {
    SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(assetNumber);
    if (specificAsset != null) {
      specificAsset.delete();
    }
  }

}
