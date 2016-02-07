package com.forerunnergames.tools.net;

public interface InternalAddressResolver
{
  /**
   * @return The internal ip address of the local machine, or an empty string if it cannot be resolved.
   */
  String resolveIp ();
}
