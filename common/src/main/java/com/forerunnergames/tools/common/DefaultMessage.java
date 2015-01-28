package com.forerunnergames.tools.common;

public final class DefaultMessage implements Message
{
  private final String text;

  public DefaultMessage (final String text)
  {
    Arguments.checkIsNotNull (text, "text");

    this.text = text;
  }

  // Required for network serialization
  protected DefaultMessage ()
  {
    text = null;
  }

  @Override
  public String getText ()
  {
    return text;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Text: %2$s", getClass ().getSimpleName (), text);
  }
}
