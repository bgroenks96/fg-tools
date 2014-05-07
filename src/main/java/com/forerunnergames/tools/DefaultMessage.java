package com.forerunnergames.tools;

public class DefaultMessage implements Message
{
  private final String text;

  public DefaultMessage (final String text)
  {
    Arguments.checkIsNotNull (text, "text");

    this.text = text;
  }

  @Override
  public String getText()
  {
    return text;
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s", text);
  }

  // Required for network serialization
  protected DefaultMessage()
  {
    text = null;
  }
}
