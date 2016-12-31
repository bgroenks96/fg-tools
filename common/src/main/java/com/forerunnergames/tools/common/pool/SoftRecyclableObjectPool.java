/*
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
import com.forerunnergames.tools.common.Exceptions;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Set;

// @formatter:off
public class SoftRecyclableObjectPool <T> extends SoftObjectPool <T> implements RecyclableObjectPool <T>
// @formatter:on
{
  private final Set <RecycleListener <T>> listeners = Sets.newConcurrentHashSet ();

  protected SoftRecyclableObjectPool (final PoolFactory <T> factory, final Class <T> type)
  {
    super (factory, type);
  }

  protected SoftRecyclableObjectPool (final Class <T> type, final Object... args)
  {
    super (type, args);
  }

  @Override
  public synchronized void release (final T t)
  {
    Arguments.checkIsNotNull (t, "t");

    // find the object in the checkout set and return it to the pool
    final Iterator <SoftReference <T>> checkoutItr = checkout.iterator ();
    boolean foundMatch = false;
    while (checkoutItr.hasNext ())
    {
      final SoftReference <T> nextRef = checkoutItr.next ();
      final Optional <T> obj = Optional.fromNullable (nextRef.get ());
      // remove cleared references from checkout set + the reference to this object, if it hasn't been cleared
      final boolean isMatch = obj.get ().equals (t);
      if (!obj.isPresent () || isMatch)
      {
        checkoutItr.remove ();
        if (!foundMatch) foundMatch = true;
      }
    }

    if (!foundMatch) Exceptions.throwIllegalState ("Object [{}] is not a member of this pool.", t);

    notifyListeners (t);

    pool.offer (t);
  }

  @Override
  public void addRecycleListener (final RecycleListener <T> listener)
  {
    Arguments.checkIsNotNull (listener, "listener");

    listeners.add (listener);
  }

  @Override
  public void removeRecycleListener (final RecycleListener <T> listener)
  {
    Arguments.checkIsNotNull (listener, "listener");

    listeners.remove (listener);
  }

  private void notifyListeners (final T recycledObj)
  {
    for (final RecycleListener <T> listener : listeners)
    {
      listener.onRelease (recycledObj);
    }
  }
}
