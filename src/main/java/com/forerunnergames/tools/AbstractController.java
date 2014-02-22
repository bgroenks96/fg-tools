package com.forerunnergames.tools;

public abstract class AbstractController <T> implements Controller
{
  private final T controllee;
  private boolean isInitialized = false;

  protected AbstractController (final T controllee)
  {
    Arguments.checkIsNotNull (controllee, "controllee");

    this.controllee = controllee;
  }

  public void checkIsInitialized()
  {
    if (! isInitialized)
    {
      throw new IllegalStateException (getClass().getSimpleName() + " is not initialized.");
    }
  }

  public void checkIsNotInitialized()
  {
    if (isInitialized)
    {
      throw new IllegalStateException (getClass().getSimpleName() + " is already initialized.");
    }
  }

  @Override
  public boolean isInitialized()
  {
    return isInitialized;
  }

  protected T getControllee()
  {
    return controllee;
  }

  protected void setInitialized (final boolean isInitialized)
  {
    this.isInitialized = isInitialized;
  }

  // Required for network serialization
  private AbstractController()
  {
    controllee = null;
    isInitialized = false;

    Classes.defaultConstructorNotSupported();
  }
}
