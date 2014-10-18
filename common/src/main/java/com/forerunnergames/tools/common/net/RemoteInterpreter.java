package com.forerunnergames.tools.common.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;

public class RemoteInterpreter
{
  public static String addressOf (final Remote remote)
  {
    Arguments.checkIsNotNull (remote, "remote");

    return remote.getAddress();
  }


  private RemoteInterpreter()
  {
    Classes.instantiationNotAllowed();
  }
}
