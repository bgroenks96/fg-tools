package com.forerunnergames.tools;

import com.forerunnergames.tools.net.MessagePacket;

public abstract class Message implements MessagePacket
{
  private final String text;

  public Message (final String text)
  {
    this.text = text;
  }

  @Override
  public String getText()
  {
    return this.text;
  }

  // Required for network serialization
  protected Message()
  {
    text = null;
  }
}
