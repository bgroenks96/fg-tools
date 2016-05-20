package com.forerunnergames.tools.common.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.forerunnergames.tools.common.concurrent.DelayedCyclicBarrier.DelayMode;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class DelayedCyclicBarrierTest
{
  @Test
  public void testDelayedCyclicBarrierReleasesThreadsFIFO ()
  {
    final int threadCount = 10;
    final int iterationCount = 20;
    final DelayedCyclicBarrier barrier = new DelayedCyclicBarrier (threadCount, 5, DelayMode.LINEAR);
    for (int i = 0; i < iterationCount; i++)
    {
      final ExecutorService threadPool = Executors.newFixedThreadPool (threadCount);

      // set atomic integer to match await index ordering
      final AtomicInteger releaseCounter = new AtomicInteger (threadCount - 1);
      final Queue <Throwable> errorQueue = new ConcurrentLinkedQueue <> ();
      for (int ii = 0; ii < threadCount; ii++)
      {
        threadPool.execute (new Runnable ()
        {
          @Override
          public void run ()
          {
            try
            {
              final int awaitIndex = barrier.await ();
              assertEquals (awaitIndex, releaseCounter.getAndDecrement ());
            }
            catch (final Throwable t)
            {
              errorQueue.offer (t);
            }
          }
        });
      }

      try
      {
        threadPool.shutdown ();
        threadPool.awaitTermination (5, TimeUnit.SECONDS);
        for (final Throwable t : errorQueue)
        {
          t.printStackTrace ();
        }
        assertTrue ("One or more errors occurred on the executor threads.", errorQueue.isEmpty ());
      }
      catch (final InterruptedException e)
      {
        fail (e.toString ());
      }
      barrier.reset ();
    }
  }
}
