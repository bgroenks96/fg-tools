// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AssetManager <T extends Asset>
{
  public AssetManager()
  {
    super();

    assets = new HashMap <Integer, T>();
  }

  public void deregister (final int assetId)
  {
    Arguments.checkIsNotNull (assetId, "assetId");

    deregister (get (assetId));
  }

  public void deregister (final T asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    if (! assets.containsValue (asset))
    {
      throw new IllegalStateException ("Asset " + asset + "does not exist.");
    }

    assets.remove (asset.getId());
  }

  public Collection <T> get (Collection <Integer> assetIds)
  {
    Arguments.checkIsNotNull         (assetIds, "assetIds");
    Arguments.checkHasNoNullElements (assetIds, "assetIds");

    Collection <T> matchingAssets = new ArrayList <T>();

    for (Integer assetId : assetIds)
    {
      T asset = get (assetId);

      matchingAssets.add (asset);
    }

    return matchingAssets;
  }

  public T get (final int assetId)
  {
    if (! assets.containsKey (assetId))
    {
      throw new IllegalStateException ("Asset with id " + assetId + "does not exist.");
    }

    return assets.get (assetId);
  }

  public Collection <T> getAll()
  {
    return assets.values();
  }

  public Set <Integer> getIds()
  {
    return assets.keySet();
  }

  public String getName (final int assetId)
  {
    return get (assetId).getName();
  }

  public int getNextId()
  {
    int requestedId = 0;

    while (has (requestedId) && requestedId < Integer.MAX_VALUE)
    {
      ++requestedId;
    }

    if (requestedId == Integer.MAX_VALUE)
    {
      throw new IllegalStateException ("There are no more available id's.");
    }

    return requestedId;
  }

  public boolean has (final T asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return has (asset.getId());
  }

  public boolean has (final int assetId)
  {
    return assets.containsKey (assetId);
  }

  public Iterator <T> iterator()
  {
    return getAll().iterator();
  }

  public void register (final T asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    if (assets.size() < Integer.MAX_VALUE)
    {
      assets.put (asset.getId(), asset);
    }
    else
    {
      throw new IllegalStateException ("Max number of assets reached: " + assets.size());
    }
  }

  public int size()
  {
    return assets.size();
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s Registered Assets:\n%2$s", assets.size(), Strings.toString (assets));
  }

  private Map <Integer, T> assets;
}