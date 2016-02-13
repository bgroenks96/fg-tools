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
