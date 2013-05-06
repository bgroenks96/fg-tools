// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools.exceptions;

import com.forerunnergames.tools.ClassUtils;

/**
 * 
 * @author Aaron Mahan
 */
@SuppressWarnings("serial")
public class StreamParserException extends Exception
{
  public StreamParserException (String errorMessage)
  {
    super (errorMessage);
  }

  public StreamParserException (String error_message, Throwable cause)
  {
    super (error_message, cause);
  }

  private StreamParserException()
  {
    ClassUtils.defaultConstructorNotSupported();
  }
}
