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

import static org.junit.Assert.assertEquals;

import com.forerunnergames.tools.common.pool.SoftObjectPoolTest.TestPoolWithTypeInteger;
import com.forerunnergames.tools.common.pool.SoftObjectPoolTest.TestPoolWithTypeObject;
import com.forerunnergames.tools.common.pool.SoftObjectPoolTest.TestPoolWithTypeString;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith (Suite.class)
@Suite.SuiteClasses ({ TestPoolWithTypeObject.class, TestPoolWithTypeString.class, TestPoolWithTypeInteger.class })
public class SoftObjectPoolTest
{
  public static class TestPoolWithTypeObject extends ObjectPoolTest <Object>
  {
    @Override
    protected ObjectPool <Object> createObjectPool ()
    {
      return Pools.createSoftPoolWithArgs (Object.class);
    }
  }

  public static class TestPoolWithTypeString extends ObjectPoolTest <String>
  {
    private static final String value = "Test";

    @Test
    public void testAcquireReturnsValidValues ()
    {
      final int n = 10;
      try (final ObjectPool <String> pool = createObjectPool ())
      {
        pool.allocate (n);
        for (final String s : pool)
        {
          assertEquals (value, s);
        }
      }
    }    @Override
    protected ObjectPool <String> createObjectPool ()
    {
      return Pools.createSoftPoolWithArgs (String.class, value);
    }


  }

  public static class TestPoolWithTypeInteger extends ObjectPoolTest <Integer>
  {
    private static final int value = 50;

    @Test
    public void testAcquireReturnsValidValues ()
    {
      final int n = 10;
      try (final ObjectPool <Integer> pool = createObjectPool ())
      {
        pool.allocate (n);
        for (final int i : pool)
        {
          assertEquals (value, i);
        }
      }
    }    @Override
    protected ObjectPool <Integer> createObjectPool ()
    {
      return Pools.createSoftPool (Integer.class, new PoolFactory <Integer> ()
      {
        @Override
        public Integer make ()
        {
          return value;
        }
      });
    }


  }
}
