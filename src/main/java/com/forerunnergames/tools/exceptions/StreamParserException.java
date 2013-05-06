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
  // Begin public interface

  public StreamParserException (String errorMessage)
  {
    super (errorMessage);
  }

  public StreamParserException (String error_message, Throwable cause)
  {
    super (error_message, cause);
  }

  // End public interface

  // Begin private interface

  private StreamParserException()
  {
    ClassUtils.defaultConstructorNotSupported();
  }

  // End private interface
}
