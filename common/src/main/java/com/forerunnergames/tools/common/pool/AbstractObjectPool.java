/*
 * Copyright © 2011 - 2013 Aaron Mahan
 * Copyright © 2013 - 2016 Forerunner Games, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
      factory = new DynamicPoolFactory <> (type, args);
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

  @Override
  public Iterator <T> iterator ()
  {
    return new ObjectPoolIterator ();
  }

  protected Class <T> getType ()
  {
    return type;
  }

  protected PoolFactory <T> getFactory ()
  {
    return factory;
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
