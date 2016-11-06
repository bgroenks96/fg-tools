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

package com.forerunnergames.tools.common.assets;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.id.Id;

public abstract class AbstractAsset implements Asset
{
  private final String name;
  private final Id id;

  public AbstractAsset (final Id id)
  {
    this ("", id);
  }

  public AbstractAsset (final String name, final Id id)
  {
    Arguments.checkIsNotNull (name, "name");
    Arguments.checkIsNotNull (id, "id");

    this.name = name;
    this.id = id;
  }

  protected AbstractAsset ()
  {
    name = null;
    id = null;
  }

  @Override
  public String getName ()
  {
    return name;
  }

  @Override
  public Id getId ()
  {
    return id;
  }

  @Override
  public boolean doesNotHave (final Id id)
  {
    return !has (id);
  }

  @Override
  public boolean has (final String name)
  {
    Arguments.checkIsNotNull (name, "name");

    return getName ().equals (name);
  }

  @Override
  public boolean hasName (final String name)
  {
    return has (name);
  }

  @Override
  public boolean has (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return this.id.equals (id);
  }

  @Override
  public boolean is (final Asset asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return this.equals (asset);
  }

  @Override
  public boolean isNot (final Asset asset)
  {
    return !is (asset);
  }

  @Override
  public int compareTo (final Asset asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return getId ().compareTo (asset.getId ());
  }

  @Override
  public int hashCode ()
  {
    return id.hashCode ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o) return true;
    if (!(o instanceof AbstractAsset)) return false;

    final AbstractAsset that = (AbstractAsset) o;

    return id.equals (that.id);
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Name: %2$s | Id: %3$s", getClass ().getSimpleName (), name, id);
  }
}
