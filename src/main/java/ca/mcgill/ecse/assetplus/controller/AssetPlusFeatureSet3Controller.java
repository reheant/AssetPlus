package ca.mcgill.ecse.assetplus.controller;

import java.security.InvalidParameterException;
import java.sql.Date;

import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet3Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
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
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";

  }

  /**
   * @author Rehean Thillainathalingam
   * @param assetNumber The asset number of the asset, must be a greater than or equal to one.
   * @param newfloorNumber The new floor number of the asset, must be greater than or equal to zero
   * @param newroomNumber The new room number of the asset, must be greater than or equal to minus
   *        one
   * @param newpurchaseDate The new purchase date of the asset, must not be empty or null.
   * @param newassetTypeName The new asset type name of the asset, must not be empty or null.
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


  /**
   * @author Rehean Thillainathalingam
   * @param assetNumber The asset number of the asset, must be a greater than or equal to one.
   */
  public static void deleteSpecificAsset(int assetNumber) {

    SpecificAsset specificAsset = SpecificAsset.getWithAssetNumber(assetNumber);
    if (specificAsset != null) {
      specificAsset.delete();
    }
  }

  /**
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
