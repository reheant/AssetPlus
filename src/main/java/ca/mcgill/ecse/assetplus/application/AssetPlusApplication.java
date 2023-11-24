package ca.mcgill.ecse.assetplus.application;

import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import ca.mcgill.ecse.assetplus.view.Home;

public class AssetPlusApplication {

  private static AssetPlus assetPlus;

  public static void main(String[] args) {
    Home.launch(Home.class, args);
  }

  public static AssetPlus getAssetPlus() {
    if (assetPlus == null) {
      assetPlus = AssetPlusPersistence.load();
    }
    return assetPlus;
  }

}
