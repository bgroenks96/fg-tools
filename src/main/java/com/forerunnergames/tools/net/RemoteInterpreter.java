package com.forerunnergames.tools.net;

import com.forerunnergames.tools.Arguments;
import com.forerunnergames.tools.Classes;

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
