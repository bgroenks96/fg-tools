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
