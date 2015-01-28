package com.forerunnergames.tools.common.assets;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Strings;
import com.forerunnergames.tools.common.id.Id;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import java.util.Map;

public final class AssetManager <T extends Asset>
{
  private final Map <Id, T> assets = Maps.newHashMap ();

  public void remove (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    remove (assetWith (id));
  }

  public void remove (final T asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    if (!assets.containsValue (asset))
    {
      throw new IllegalStateException ("Asset " + asset + "does not exist.");
    }

    assets.remove (asset.getId ());
  }

  public ImmutableCollection <T> get (final ImmutableCollection <Id> ids)
  {
    Arguments.checkIsNotNull (ids, "ids");
    Arguments.checkHasNoNullElements (ids, "ids");

    ImmutableCollection.Builder <T> matchingAssetsBuilder = new ImmutableList.Builder <> ();

    for (final Id assetId : ids)
    {
      matchingAssetsBuilder.add (assetWith (assetId));
    }

    return matchingAssetsBuilder.build ();
  }

  public T assetWith (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    if (!assets.containsKey (id)) throw new IllegalStateException ("Cannot find asset with id [" + id + "].");

    return assets.get (id);
  }

  public ImmutableCollection <T> allAssets ()
  {
    return ImmutableList.copyOf (assets.values ());
  }

  public ImmutableSet <Id> allIds ()
  {
    return ImmutableSet.copyOf (assets.keySet ());
  }

  public String nameOf (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return assetWith (id).getName ();
  }

  public Id nextAvailableId ()
  {
    int requestedIdValue = 0;

    while (existsAssetWith (requestedIdValue))
    {
      ++requestedIdValue;
    }

    return new Id (requestedIdValue);
  }

  public boolean existsAssetWith (final int idValue)
  {
    for (final T asset : allAssets ())
    {
      if (asset.getId ().value () == idValue) return true;
    }

    return false;
  }

  public boolean has (final T asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return has (asset.getId ());
  }

  public boolean has (final Id id)
  {
    return assets.containsKey (id);
  }

  public void add (final T asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    assets.put (asset.getId (), asset);
  }

  public int size ()
  {
    return assets.size ();
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Asset Count: %2$s | Assets: %3$s", getClass ().getSimpleName (), assets.size (),
                    Strings.toString (assets));
  }
}
