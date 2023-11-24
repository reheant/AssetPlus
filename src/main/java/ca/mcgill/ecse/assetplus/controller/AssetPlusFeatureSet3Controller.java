package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import java.util.List;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet3Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  public static String[] getSpecificAssetData(){
    List<SpecificAsset> specificAssets = assetPlus.getSpecificAssets();
    String[] specificAssetData = new String[specificAssets.size()];
    int currentAssetNumber;
    int currentRoomNumber;
    int currentFloorNumber;
    Date currentDate;
    AssetType currentAssetType;
    String currentAssetTypeString;

    for (int i = 0; i<specificAssets.size(); i++) {
       currentAssetNumber = specificAssets.get(i).getAssetNumber();
       currentAssetType = specificAssets.get(i).getAssetType();
       currentAssetTypeString = currentAssetType.getName();
       currentFloorNumber = specificAssets.get(i).getFloorNumber();
       currentRoomNumber = specificAssets.get(i).getRoomNumber();
       currentDate = specificAssets.get(i).getPurchaseDate();

       specificAssetData[i] = currentAssetTypeString + " #" + Integer.toString(currentAssetNumber) + " " + currentFloorNumber + " " + currentRoomNumber + " "+ currentDate ;
    }

    return specificAssetData;
  }
  /**
   * Adds a specific asset to the system with the given details.
   * 
   * @author Rehean Thillainathalingam
   * @param assetNumber The asset number of the asset, must be a greater than or equal to one.
   * @param floorNumber The floor number of the asset, must be greater than or equal to zero
   * @param roomNumber The room number of the asset, must be greater than or equal to minus one
   * @param purchaseDate The purchase date of the asset, must not be empty or null.
   * @param assetTypeName The asset type name of the asset, must not be empty or null.
   * @return An empty string indicating success. An error message if failure.
   */
  public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
      Date purchaseDate, String assetTypeName) {

    var error = "";
    error += assertValidAssetNumber(assetNumber);
    error += assertValidFloorNumber(floorNumber);
    error += assertValidRoomNumber(roomNumber);
    error += assertValidDate(purchaseDate);
    error += assertValidTypeName(assetTypeName);

    if (!error.isEmpty()) {
      return error.trim();
    }

    try {
      assetPlus.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate,
          AssetType.getWithName(assetTypeName));
      AssetPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";

  }

  /**
   * Updates the details of an existing specific asset in the system.
   * 
   * @author Rehean Thillainathalingam
   * @param assetNumber The asset number of the asset, must be a greater than or equal to one.
   * @param newFloorNumber The new floor number of the asset, must be greater than or equal to zero
   * @param newRoomNumber The new room number of the asset, must be greater than or equal to minus
   *        one
   * @param newPurchaseDate The new purchase date of the asset, must not be empty or null.
   * @param newAssetTypeName The new asset type name of the asset, must not be empty or null.
   * @return An empty string indicating success. An error message if failure.
   */
  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {

    var error = "";
    error += assertValidAssetNumber(assetNumber);
    error += assertValidFloorNumber(newFloorNumber);
    error += assertValidRoomNumber(newRoomNumber);
    error += assertValidDate(newPurchaseDate);
    error += assertValidTypeName(newAssetTypeName);

    if (!error.isEmpty()) {
      return error.trim();
    }

    try {
      SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(assetNumber);
      specificAsset.setFloorNumber(newFloorNumber);
      specificAsset.setRoomNumber(newRoomNumber);
      specificAsset.setPurchaseDate(newPurchaseDate);
      specificAsset.setAssetType(AssetType.getWithName(newAssetTypeName));
      AssetPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }


  /**
   * Deletes a specific asset in the system.
   * 
   * @author Rehean Thillainathalingam
   * @param assetNumber The asset number of the asset, must be a greater than or equal to one.
   */
  public static void deleteSpecificAsset(int assetNumber) {

    SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(assetNumber);
    if (specificAsset != null) {
      specificAsset.delete();
      AssetPlusPersistence.save();
    }
  }

  /**
   * Validates asset number according to specified constraints.
   * 
   * @author Rehean Thillainathalingam
   * @param assetNumber The asset number of the asset, must be a greater than or equal to one.
   * @return An empty string indicating success. An error message if failure.
   */
  private static String assertValidAssetNumber(int assetNumber) {
    if (assetNumber < 1) {
      return "The asset number shall not be less than 1";
    }
    return "";
  }

  /**
   * Validates floor number according to specified constraints.
   * 
   * @author Rehean Thillainathalingam
   * @param floorNumber The floor number of the asset, must be greater than or equal to zero
   * @return An empty string indicating success. An error message if failure.
   */
  private static String assertValidFloorNumber(int floorNumber) {
    if (floorNumber < 0) {
      return "The floor number shall not be less than 0";
    }
    return "";
  }

  /**
   * Validates room number according to specified constraints.
   * 
   * @author Rehean Thillainathalingam
   * @param roomNumber The room number of the asset, must be greater than or equal to minus one.
   * @return An empty string indicating success. An error message if failure.
   */
  private static String assertValidRoomNumber(int roomNumber) {
    if (roomNumber < -1) {
      return "The room number shall not be less than -1";
    }
    return "";
  }

  /**
   * Validates date according to specified constraints.
   * 
   * @author Rehean Thillainathalingam
   * @param date The purchase date of the asset, must not be null or empty.
   * @return An empty string indicating success. An error message if failure.
   */
  private static String assertValidDate(Date date) {
    if (date == null) {
      return "Error: Date is null.";
    }
    return "";
  }

  /**
   * Validates asset type name according to specified constraints.
   * 
   * @author Rehean Thillainathalingam
   * @param name The name of the asset type, must not be null, empty and must exist in the created
   *        asset types.
   * @return An empty string indicating success. An error message if failure.
   */
  private static String assertValidTypeName(String name) {
    for (AssetType assetType : assetPlus.getAssetTypes()) {
      if (assetType.getName().equals(name)) {
        return "";
      }
    }
    return "The asset type does not exist";
  }
}
