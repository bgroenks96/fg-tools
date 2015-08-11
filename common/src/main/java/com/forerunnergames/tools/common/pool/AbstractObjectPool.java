package com.forerunnergames.tools.common.pool;

import com.forerunnergames.tools.common.Arguments;

import com.google.common.base.Optional;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractObjectPool <T> implements ObjectPool <T>
{
  private static final Logger log = LoggerFactory.getLogger (AbstractObjectPool.class);
  private final PoolFactory <T> factory;
  private final Class <T> type;

  protected AbstractObjectPool (final Class <T> type, final Object... args)
  {
    Arguments.checkIsNotNull (type, "type");
    Arguments.checkIsNotNull (args, "args");

    this.type = type;

    try
    {
      factory = new DynamicPoolFactory <T> (type, args);
    }
    catch (final NoSuchMethodException | SecurityException e)
    {
      log.error ("Unable to resolve constructor for class [{}] with args [{}]: {}", type, Arrays.toString (args), e);
      throw new RuntimeException (e);
    }
  }

  protected AbstractObjectPool (final PoolFactory <T> factory, final Class <T> type)
  {
    Arguments.checkIsNotNull (factory, "factory");
    Arguments.checkIsNotNull (type, "type");

    this.type = type;
    this.factory = factory;
  }

  protected Class <T> getType ()
  {
    return type;
  }

  protected PoolFactory <T> getFactory ()
  {
    return factory;
  }

  @Override
  public Iterator <T> iterator ()
  {
    return new ObjectPoolIterator ();
  }

  private class ObjectPoolIterator implements Iterator <T>
  {
    private Optional <T> current;

    @Override
    public boolean hasNext ()
    {
      return canAcquire ();
    }

    @Override
    public T next ()
    {
      current = acquire ();
      if (!current.isPresent ()) throw new NoSuchElementException ();
      return current.get ();
    }

    @Override
    public void remove ()
    {
      throw new UnsupportedOperationException ();
    }
  }
}
