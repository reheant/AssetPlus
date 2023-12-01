package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.*;
import java.util.List;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class AssetPlusFeatureSet2Controller {

    private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

    /**
     * Adds an asset type to the system with the given details
     * 
     * @author Tiffany Miller
     * @param name The name of the asset type to create. Must not be empty or null.
     * @param expectedLifeSpanInDays The expected life span in days of the asset type. Must be > 0.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String addAssetType(String name, int expectedLifeSpanInDays) {
        var error = "";
        error += assertNameValid(name);
        error += assertLifeSpanValid(expectedLifeSpanInDays);

        if (!error.isEmpty()) {
            return error.trim();
        }

        try {
            AssetType existingAsset = AssetType.getWithName(name);
            if (existingAsset != null) {
                if (existingAsset.getName().equals(name)) {
                    return "The asset type already exists";
                }
            } else {
                assetPlus.addAssetType(name, expectedLifeSpanInDays);
            }
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * Updates the details of an existing asset type in the system
     * 
     * @author Tiffany Miller
     * @param oldName The old name of the asset type to modify. Must not be empty or null. The old
     *        name must belong to an existing asset type.
     * @param newName The new name of the asset type to modify. Must not be empty or null.
     * @param newExpectedLifeSpanInDays The new expected life span in days of the asset type of
     *        which to modify. Must be > 0.
     * @return An empty string indicating success. An error message if failure.
     */
    public static String updateAssetType(String oldName, String newName,
            int newExpectedLifeSpanInDays) {
        var error = "";
        error += assertNameValid(newName);
        error += assertNameValid(oldName);
        error += assertLifeSpanValid(newExpectedLifeSpanInDays);

        AssetType oldAssetType = AssetType.getWithName(oldName);
        if (oldAssetType == null) {
            error += "The asset type does not exist ";
        }

        AssetType newAssetType = AssetType.getWithName(newName);
        if (newAssetType != null && !newName.equals(oldName)) {
            error += "The asset type already exists ";
        }

        if (!error.isEmpty()) {
            return error.trim();
        }

        try {
            if (oldName.equals(newName)) {
                oldAssetType.setExpectedLifeSpan(newExpectedLifeSpanInDays);
            } else {
                oldAssetType.setName(newName);
                oldAssetType.setExpectedLifeSpan(newExpectedLifeSpanInDays);
            }
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * Deletes an existing asset type in the system
     * 
     * @author Tiffany Miller
     * @param name The name of the asset type to delete. Must not be empty or null.
     */
    public static void deleteAssetType(String name) {
        AssetType assetType = AssetType.getWithName(name);
        if (assetType != null) {
            List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();
            for (MaintenanceTicket ticket : maintenanceTickets) {
                if (ticket != null && ticket.getAsset() != null && ticket.getAsset().getAssetType().equals(assetType)){
                    AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(ticket.getId());
                }
            }
            assetType.delete();
            AssetPlusPersistence.save();
        }
    }
    
    /**
     * Gets asset type names from asset plus application 
     * 
     * @author Tiffany Miller
     */
    public static String[] getAssetTypes() {
    	List<AssetType> assetTypes = assetPlus.getAssetTypes();
    	String[] typeNames = new String[assetTypes.size()];
    	
    	for (int i=0; i< assetTypes.size(); i++) {
    		typeNames[i] = assetTypes.get(i).getName();
    	}
    	
    	return typeNames;
    }
    
    /**
     * Gets asset type lifespan by name from asset plus application 
     * 
     * @author Tiffany Miller
     */
    public static int getLifespanByName(String name) {
    	try {
    	      AssetType assetType = AssetType.getWithName(name);

    	      if (assetType == null) {
    	        return 0;
    	      }
    	      
    	      int specificLifespan;
    	      specificLifespan = assetType.getExpectedLifeSpan();
    	      return specificLifespan;
    } catch (Exception e) {
        return 0;
      }
    }

    /**
     * Validates a name according to basic constraints
     * 
     * @author Tiffany Miller
     * @param name The name of the asset type to validate. Must not be empty or null.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertNameValid(String name) {
        if (name == null || name.isEmpty()) {
            return "The name must not be empty ";
        } else {
            return "";
        }
    }

    /**
     * Validates a life span according to basic constraints
     * 
     * @author Tiffany Miller
     * @param expectedLifeSpan The expected life span in days of the asset type. Must be > 0.
     * @return An empty string indicating success. An error message if failure.
     */
    private static String assertLifeSpanValid(int expectedLifeSpan) {
        if (expectedLifeSpan <= 0) {
            return "The expected life span must be greater than 0 days ";
        } else {
            return "";
        }
    }
}
