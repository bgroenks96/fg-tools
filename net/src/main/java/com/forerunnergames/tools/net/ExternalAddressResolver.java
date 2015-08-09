package com.forerunnergames.tools.net;

public interface ExternalAddressResolver
{
  /**
   * @return The external ip address of the local machine, or an empty string if it cannot be resolved.
   */
  String resolveIp ();

  /**
   * @return The external host name of the local machine, or an empty string if it cannot be resolved.
   */
  String resolveHostName ();
}
