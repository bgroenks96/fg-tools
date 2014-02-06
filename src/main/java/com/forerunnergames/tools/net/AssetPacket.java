// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools.net;

public interface AssetPacket extends Packet
{
  public String getName();
  public int getId();
  public boolean equals (final Object object);
  public int hashCode();
  public String toString();
}
