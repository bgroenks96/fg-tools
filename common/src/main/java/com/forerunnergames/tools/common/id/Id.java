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

package com.forerunnergames.tools.common.id;

import com.forerunnergames.tools.common.Arguments;

import java.util.UUID;

public final class Id implements Comparable <Id>
{
  private final UUID value;

  public Id (final UUID value)
  {
    Arguments.checkIsNotNull (value, "value");

    this.value = value;
  }

  @Override
  public int compareTo (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return this.value.compareTo (id.value);
  }

  @Override
  public int hashCode ()
  {
    return value.hashCode ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o) return true;

    if (o == null || getClass () != o.getClass ()) return false;

    final Id that = (Id) o;

    return value.equals (that.value);
  }

  @Override
  public String toString ()
  {
    return String.valueOf (value);
  }

  public UUID value ()
  {
    return value;
  }

  public boolean is (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return equals (id);
  }

  public boolean isNot (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return !is (id);
  }

  public boolean hasValue (final UUID value)
  {
    Arguments.checkIsNotNull (value, "value");

    return this.value.equals (value);
  }
}
