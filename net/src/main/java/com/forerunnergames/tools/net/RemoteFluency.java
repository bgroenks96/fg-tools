package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;

public class RemoteFluency
{
  public static String addressOf (final Remote remote)
  {
    Arguments.checkIsNotNull (remote, "remote");

    return remote.getAddress ();
  }

  private RemoteFluency ()
  {
    Classes.instantiationNotAllowed ();
  }
}
