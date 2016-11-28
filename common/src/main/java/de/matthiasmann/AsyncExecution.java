/*
 * Copyright (c) 2008-2013, Matthias Mann
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Matthias Mann nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.matthiasmann;

import com.forerunnergames.tools.common.Arguments;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows executing code on a different thread and retrieving the result synchronously by calling
 * {@link #executeQueuedJobs()}.
 *
 */
public class AsyncExecution
{
  private final Logger log = LoggerFactory.getLogger (AsyncExecution.class);
  private final LinkedBlockingQueue <Runnable> completionQueue;

  public AsyncExecution ()
  {
    completionQueue = new LinkedBlockingQueue<> ();
  }

  /**
   * Queues a job for execution by {@link #executeQueuedJobs() }
   *
   * @param job
   *          The job to execute, must not be null.
   */
  public void invokeLater (final Runnable job)
  {
    Arguments.checkIsNotNull (job, "job");

    completionQueue.add (job);
  }

  /**
   * Executes all queued jobs.
   *
   * @see #invokeLater(Runnable)
   */
  public void executeQueuedJobs ()
  {
    Runnable job;
    while ((job = completionQueue.poll ()) != null)
    {
      try
      {
        job.run ();
      }
      catch (final Exception ex)
      {
        log.error ("Exception while executing queued job.", ex);
      }
    }
  }

  /**
   * Invokes a {@link Callable} job on the specified executor and invokes the {@code listener} via
   * {@link #invokeLater(Runnable) } once the job has completed normally or with an exception.
   *
   * @param <V>
   *          The return type of the callable.
   * @param executor
   *          The executor for the asynchronous execution of the job, must not be null.
   * @param asyncJob
   *          The job to execute, must not be null.
   * @param listener
   *          The listener to invoke once the job has completed, must not be null.
   */
  public <V> void invokeAsync (final ExecutorService executor,
                               final Callable <V> asyncJob,
                               final AsyncCompletionListener <V> listener)
  {
    Arguments.checkIsNotNull (executor, "executor");
    Arguments.checkIsNotNull (asyncJob, "asyncJob");
    Arguments.checkIsNotNull (listener, "listener");

    executor.submit ((Callable <V>) new AC<> (asyncJob, null, listener));
  }

  /**
   * Invokes a {@link Runnable} job on the specified executor and invokes the {@code listener} via
   * {@link #invokeLater(Runnable) } once the job has completed normally or with an exception.
   *
   * @param executor
   *          The executor for the asynchronous execution of the job, must not be null.
   * @param asyncJob
   *          The job to execute, must not be null.
   * @param listener
   *          The listener to invoke once the job has completed, must not be null.
   */
  public void invokeAsync (final ExecutorService executor,
                           final Runnable asyncJob,
                           final AsyncCompletionListener <Void> listener)
  {
    Arguments.checkIsNotNull (executor, "executor");
    Arguments.checkIsNotNull (asyncJob, "asyncJob");
    Arguments.checkIsNotNull (listener, "listener");

    executor.submit ((Callable <Void>) new AC<> (null, asyncJob, listener));
  }

  class AC <V> implements Callable <V>, Runnable
  {
    private final Callable <V> jobC;
    private final Runnable jobR;
    private final AsyncCompletionListener <V> listener;
    private V result;
    private Exception exception;

    AC (final Callable <V> jobC, final Runnable jobR, final AsyncCompletionListener <V> listener)
    {
      Arguments.checkIsNotNull (jobC, "jobC");
      Arguments.checkIsNotNull (jobR, "jobR");
      Arguments.checkIsNotNull (listener, "listener");

      this.jobC = jobC;
      this.jobR = jobR;
      this.listener = listener;
    }

    @Override
    public V call () throws Exception
    {
      try
      {
        if (jobC != null)
        {
          result = jobC.call ();
        }
        else
        {
          jobR.run ();
        }
        invokeLater (this);
        return result;
      }
      catch (final Exception ex)
      {
        exception = ex;
        invokeLater (this);
        throw ex;
      }
    }

    @Override
    public void run ()
    {
      if (exception != null)
      {
        listener.failed (exception);
      }
      else
      {
        listener.completed (result);
      }
    }
  }
}
