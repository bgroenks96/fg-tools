package ch.fourmilab.randomx;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Implementation of a <b>randomX</b>-compliant class which obtains genuine random data from
 * <a href="http://www.fourmilab.ch/">John Walker</a>'s <a href="http://www.fourmilab.ch/hotbits/">HotBits</a>
 * radioactive decay random sequence generator.
 * <p/>
 * <p/>
 * Designed and implemented in July 1996 by <a href="http://www.fourmilab.ch/">John Walker</a>.
 */
public class RandomHotBits extends RandomX
{
  private final byte[] buffer;
  private int buflen = 0;
  private int bufptr = -1;

  /**
   * Creates a new random sequence generator.
   */
  public RandomHotBits ()
  {
    buffer = new byte [256];
  }

  /**
   * Gets the next byte from the random number sequence generator.
   *
   * @throws RuntimeException
   *           If the next byte could not be obtained for any reason from the HotBits server.
   */
  @Override
  public byte nextByte ()
  {
    try
    {
      synchronized (buffer)
      {
        if (bufptr < 0 || bufptr >= buflen)
        {
          fillBuffer ();
        }
        return buffer [bufptr++];
      }
    }
    catch (final IOException e)
    {
      throw new RuntimeException ("Cannot obtain HotBits.", e);
    }
  }

  /* Private method to fill buffer from HotBits server. */
  private void fillBuffer () throws IOException
  {
    final URL u = new URL ("http://www.fourmilab.ch/cgi-bin/uncgi/Hotbits?nbytes=128&fmt=bin");
    final InputStream s = u.openStream ();

    buflen = 0;
    int l;
    while ((l = s.read ()) != -1)
    {
      buffer [buflen++] = (byte) l;
    }
    s.close ();
    bufptr = 0;
  }
}
