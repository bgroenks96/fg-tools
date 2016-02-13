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

/**
 * Specification for an ObjectPool that allows for recycling of object instances. Once the user has finished processing
 * an acquired object instance, it can be returned to the pool for further reuse via the {@link #release(Object)}
 * method.
 */
public interface RecyclableObjectPool <T> extends ObjectPool <T>
{
  void release (final T t);

  void addRecycleListener (final RecycleListener <T> t);

  void removeRecycleListener (final RecycleListener <T> t);

  /**
   * Listener type for when objects are released back into the object pool. These should be used to perform any
   * necessary cleanup operations to prepare the object for recycled use.
   */
  interface RecycleListener <T>
  {
    /**
     * Called when a given object 't' is recycled via RecyclableObjectPool.release. Note that this method will be called
     * <b>before</b> the object is processed and returned to the pool; so implementations should not assume that the
     * object has already become available in the pool again.
     */
    void onRelease (final T t);
  }
}
