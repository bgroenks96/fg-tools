package com.forerunnergames.tools.common.exceptions;

@SuppressWarnings("serial")
public class StreamParserException extends RuntimeException
{
  public StreamParserException (final String errorMessage)
  {
    super (errorMessage);
  }

  public StreamParserException (final String errorMessage, final Throwable cause)
  {
    super (errorMessage, cause);
  }
}