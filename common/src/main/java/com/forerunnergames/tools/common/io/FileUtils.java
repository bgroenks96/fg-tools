package com.forerunnergames.tools.common.io;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileUtils
{
  public static String loadFile (final String path, final Charset encoding)
  {
    Arguments.checkIsNotNull (path, "path");
    Arguments.checkIsNotNull (encoding, "encoding");

    try
    {
      return new String (Files.readAllBytes (Paths.get (path)), encoding);
    }
    catch (final IOException e)
    {
      throw new RuntimeException ("Error loading file [" + path + "] with encoding [" + encoding + "]", e);
    }
  }

  private FileUtils ()
  {
    Classes.instantiationNotAllowed ();
  }
}
