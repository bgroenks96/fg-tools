package ch.fourmilab.randomx;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Implementation of a <b>randomX</b>-compliant class which obtains genuine random data from <a
 * href="http://www.fourmilab.ch/">John Walker</a>'s <a href="http://www.fourmilab.ch/hotbits/">HotBits</a> radioactive
 * decay random sequence generator.
 * <p/>
 * <p/>
 * Designed and implemented in July 1996 by <a href="http://www.fourmilab.ch/">John Walker</a>.
 */

public class RandomHotBits extends RandomX
{
  private int buflen = 0;
  private int nuflen = 256;
  private byte[] buffer;
  private int bufptr = -1;

  // Constructors

  /**
   * Creates a new random sequence generator.
   */

  public RandomHotBits ()
  {
    buffer = new byte [nuflen];
  }

  /* Private method to fill buffer from HotBits server. */

  /**
   * Get next byte from generator.
   *
   * @return the next byte from the generator.
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
    catch (IOException e)
    {
      throw new RuntimeException ("Cannot obtain HotBits", e);
    }
  }

  private void fillBuffer () throws java.io.IOException
  {
    URL u = new URL ("http://www.fourmilab.ch/cgi-bin/uncgi/Hotbits?nbytes=128&fmt=bin");
    InputStream s = u.openStream ();
    int l;

    buflen = 0;
    while ((l = s.read ()) != -1)
    {
      buffer [buflen++] = (byte) l;
    }
    s.close ();
    bufptr = 0;
  }
};
