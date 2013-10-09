// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools.exceptions;

import com.forerunnergames.tools.Classes;

/**
 * 
 * @author Aaron Mahan <aaron@forerunnergames.com>
 */
@SuppressWarnings("serial")
public class ImageException extends RuntimeException
{
  public ImageException (String errorMessage)
  {
    super (errorMessage);
  }

  public ImageException (String error_message, Throwable cause)
  {
    super (error_message, cause);
  }

  private ImageException()
  {
    Classes.defaultConstructorNotSupported();
  }
}
