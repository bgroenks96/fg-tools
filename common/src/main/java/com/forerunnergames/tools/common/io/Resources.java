package com.forerunnergames.tools.common.io;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;

import java.io.InputStream;

public final class Resources
{
  public static InputStream toInputStream (final String resource)
  {
    Arguments.checkIsNotNull (resource, "resource");

    InputStream inputStream = Thread.currentThread ().getContextClassLoader ().getResourceAsStream (resource);

    if (inputStream == null)
    {
      throw new IllegalStateException ("Could not get resource as InputStream: " + resource);
    }

    return inputStream;
  }

  private Resources ()
  {
    Classes.instantiationNotAllowed ();
  }
}
