package com.forerunnergames.tools.common.pool;

import com.forerunnergames.tools.common.pool.SoftDisposableObjectPoolTest.TestPoolWithTypeInputStream;
import com.forerunnergames.tools.common.pool.SoftDisposableObjectPoolTest.TestPoolWithTypeMyDisposable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith (Suite.class)
@Suite.SuiteClasses ({ TestPoolWithTypeInputStream.class, TestPoolWithTypeMyDisposable.class })
public class SoftDisposableObjectPoolTest
{
  public static class TestPoolWithTypeInputStream extends DisposableObjectPoolTest <AutoDisposable <InputStream>>
  {
    @Override
    protected DisposableObjectPool <AutoDisposable <InputStream>> createObjectPool ()
    {
      return Pools.createSoftAutoDisposablePool (InputStream.class,
                                                 AutoDisposable.forCloseable (new InputStreamFactory ()));
    }

    private class InputStreamFactory implements PoolFactory <InputStream>
    {
      @Override
      public InputStream make ()
      {
        return new ByteArrayInputStream (new byte [0]);
      }
    }
  }

  public static class TestPoolWithTypeMyDisposable extends DisposableObjectPoolTest <MyDisposable>
  {
    @Override
    protected DisposableObjectPool <MyDisposable> createObjectPool ()
    {
      return Pools.createSoftDisposablePoolWithArgs (MyDisposable.class);
    }
  }

  private static class MyDisposable implements Disposable
  {
    @Override
    public void dispose () throws Exception
    {
    }
  }
}
