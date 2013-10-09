// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import java.io.InputStream;

public class Resources
{
  public static InputStream toInputStream (String resource)
  {
    InputStream inputStream = Thread.currentThread()
                                    .getContextClassLoader()
                                    .getResourceAsStream (resource);

    if (inputStream == null)
    {
      throw new IllegalStateException ("Could not get resource as InputStream: " + resource);
    }

    return inputStream;
  }

  private Resources()
  {
    Classes.instantiationNotAllowed();
  }
}
