// Copyright © 2011 - 2012 Forerunner Games
package com.forerunnergames.tools.exceptions;

import com.forerunnergames.tools.ClassUtils;

/**
 * 
 * @author Aaron Mahan
 */
@SuppressWarnings("serial")
public class ImageLoadingException extends Exception
{
  // Begin public interface

  public ImageLoadingException (String errorMessage)
  {
    super (errorMessage);
  }

  public ImageLoadingException (String error_message, Throwable cause)
  {
    super (error_message, cause);
  }

  // End public interface

  // Begin private interface

  private ImageLoadingException()
  {
    ClassUtils.defaultConstructorNotSupported();
  }

  // End private interface
}
