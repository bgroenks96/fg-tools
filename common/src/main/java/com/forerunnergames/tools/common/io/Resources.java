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
package com.forerunnergames.tools.common.io;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;
import com.forerunnergames.tools.common.Strings;

import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.EnumSet;

public final class Resources
{
  public static InputStream toInputStream (final String resource)
  {
    Arguments.checkIsNotNull (resource, "resource");

    InputStream inputStream = Thread.currentThread ().getContextClassLoader ().getResourceAsStream (resource);

    if (inputStream == null)
    {
      throw new IllegalStateException ("Could not get resource as InputStream: " + resource);
    }

    return inputStream;
  }

  /**
   * Obtain a list of sub-directory names one level below the specified directory, from a jar that is on the classpath.
   * The correct jar is specified by jarClass, any class on the classpath existing within the correct jar.
   *
   * @param jarClass
   *          A class within the jar containing the specified directory. This is necessary because there is a chance
   *          that a different jar on the classpath could contain the same directory structure, causing an ambiguous
   *          result.
   *
   * @param parentDirectoryPath
   *          The jar directory path, specified from the root of the jar, from which to obtain the child directory
   *          names, must not be null, may or may not begin with a /, may or may not end with a / (ending / doesn't
   *          affect the result).
   *
   * @return A list of sub-directory names, may be empty, will never be null, will never contain null elements.
   */
  public static ImmutableList <String> getJarChildDirectoryNames (final Class <?> jarClass,
                                                                  final String parentDirectoryPath)
  {
    Arguments.checkIsNotNull (jarClass, "jarClass");
    Arguments.checkIsNotNull (parentDirectoryPath, "parentDirectoryPath");

    final URL resourceUrl = jarClass.getResource (parentDirectoryPath);

    if (resourceUrl == null)
    {
      throw new IllegalStateException (Strings.format ("Cannot find jar resource [{}] in jar containing class [{}]",
                                                       parentDirectoryPath, jarClass));
    }

    FileSystem fileSystem = null;

    try
    {
      final URI uri = resourceUrl.toURI ();
      final Path myPath;

      if (uri.getScheme ().equals ("jar"))
      {
        fileSystem = FileSystems.newFileSystem (uri, Collections.<String, Object> emptyMap ());
        myPath = fileSystem.getPath (parentDirectoryPath);
      }
      else
      {
        myPath = Paths.get (uri);
      }

      final ImmutableList.Builder <String> directoryNames = ImmutableList.builder ();

      // @formatter:off
      Files.walkFileTree (myPath, EnumSet.noneOf (FileVisitOption.class), 2, new FileVisitor <Path> ()
      {
        private boolean isFirstVisit = true;

        @Override
        public FileVisitResult preVisitDirectory (final Path dir, final BasicFileAttributes attrs) throws IOException
        {
          Arguments.checkIsNotNull (dir, "dir");
          Arguments.checkIsNotNull (attrs, "attrs");

          // The parent directory itself is the first visited, so ignore it.
          // We only want the children of the parent directory.
          if (isFirstVisit)
          {
            isFirstVisit = false;
            return FileVisitResult.CONTINUE;
          }

          String directoryName = dir.getFileName ().toString ();

          if (directoryName.endsWith ("/")) directoryName = directoryName.substring (0, directoryName.length () - 1);

          directoryNames.add (directoryName);

          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile (final Path file, final BasicFileAttributes attrs) throws IOException
        {
          Arguments.checkIsNotNull (file, "file");
          Arguments.checkIsNotNull (attrs, "attrs");

          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed (final Path file, final IOException exc) throws IOException
        {
          Arguments.checkIsNotNull (file, "file");

          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory (final Path dir, final IOException exc) throws IOException
        {
          Arguments.checkIsNotNull (dir, "dir");

          return FileVisitResult.CONTINUE;
        }
      });
      // @formatter:on

      return directoryNames.build ();
    }
    catch (final IOException | URISyntaxException e)
    {
      throw new IllegalStateException (Strings.format ("Failed to process jar resource: {}", parentDirectoryPath), e);
    }
    finally
    {
      if (fileSystem != null)
      {
        try
        {
          fileSystem.close ();
        }
        catch (final IOException ignored)
        {
        }
      }
    }
  }

  private Resources ()
  {
    Classes.instantiationNotAllowed ();
  }
}
