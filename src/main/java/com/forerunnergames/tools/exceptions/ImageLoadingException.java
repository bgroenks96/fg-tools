// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools.exceptions;

import com.forerunnergames.tools.ClassUtils;

/**
 * 
 * @author Aaron Mahan
 */
@SuppressWarnings("serial")
public class ImageLoadingException extends Exception
{
  public ImageLoadingException (String errorMessage)
  {
    super (errorMessage);
  }

  public ImageLoadingException (String error_message, Throwable cause)
  {
    super (error_message, cause);
  }

  private ImageLoadingException()
  {
    ClassUtils.defaultConstructorNotSupported();
  }
}
