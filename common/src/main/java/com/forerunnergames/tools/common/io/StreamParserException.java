package com.forerunnergames.tools.common.io;

@SuppressWarnings ("serial")
public final class StreamParserException extends RuntimeException
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
