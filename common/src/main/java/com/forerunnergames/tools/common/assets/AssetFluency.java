package com.forerunnergames.tools.common.assets;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;
import com.forerunnergames.tools.common.id.Id;

public final class AssetFluency
{
  public static String nameOf (final Asset asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return asset.getName ();
  }

  public static Id withIdOf (final Asset asset)
  {
    return idOf (asset);
  }

  public static Id idOf (final Asset asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return asset.getId ();
  }

  public static int idValueOf (final Asset asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return asset.getId ().value ();
  }

  private AssetFluency ()
  {
    Classes.instantiationNotAllowed ();
  }
}
