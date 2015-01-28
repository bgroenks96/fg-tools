package com.forerunnergames.tools.common.io;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;
import com.forerunnergames.tools.common.Strings;
import com.forerunnergames.tools.common.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public final class StreamParser
{
  private static final int QUOTE_CHARACTER = (int) '\"';
  private static final int FORWARD_SLASH_CHARACTER = (int) '/';
  private static final int STAR_CHARACTER = (int) '*';
  private boolean areSlashSlashCommentsEnabled = false;
  private boolean areSlashStarCommentsEnabled = false;
  private Reader reader;
  private StreamTokenizer s;

  public enum CommentType
  {
    SLASH_SLASH,
    SLASH_STAR;

    public boolean is (final CommentType type)
    {
      Arguments.checkIsNotNull (type, "type");

      return equals (type);
    }
  }

  public enum CommentStatus
  {
    ENABLED(true),
    DISABLED(false);

    private boolean isEnabled;

    public boolean isEnabled ()
    {
      return isEnabled;
    }

    private CommentStatus (final boolean isEnabled)
    {
      this.isEnabled = isEnabled;
    }
  }

  private enum TokenType
  {
    DOUBLE,
    END_OF_FILE,
    END_OF_LINE,
    INTEGER,
    QUOTED_STRING,
    SINGLE_CHARACTER,
    UNQUOTED_STRING
  }

  /*
   * Constructs a new StreamParser with the specified file.
   *
   * @param file The file to parse, must not be null.
   *
   * @throws StreamParserException If the file cannot be open or found.
   */
  public StreamParser (final File file) throws StreamParserException
  {
    Arguments.checkIsNotNull (file, "file");

    initialize (file);
  }

  /*
   * Constructs a new StreamParser with the specified filename.
   *
   * @param fileName The path and name of the file to parse, must not be null, must not be empty, must not be blank
   * (whitespace only).
   *
   * @throws StreamParserException If filename cannot be open or found.
   */
  public StreamParser (final String fileName) throws StreamParserException
  {
    Arguments.checkIsNotNullOrEmptyOrBlank (fileName, "fileName");

    initialize (fileName);
  }

  /*
   * Constructs a new StreamParser with the specified inputStream.
   *
   * @param inputStream The input stream to parse, must not be null.
   */
  public StreamParser (final InputStream inputStream)
  {
    Arguments.checkIsNotNull (inputStream, "inputStream");

    initialize (inputStream);
  }

  /*
   * Constructs a new StreamParser with the specified reader.
   *
   * @param reader The reader object providing the input stream to parse, must not be null.
   */
  public StreamParser (final Reader reader)
  {
    Arguments.checkIsNotNull (reader, "reader");

    initialize (reader);
  }

  /**
   * Default constructor not supported.
   *
   * @throws UnsupportedOperationException
   *           When called.
   */
  protected StreamParser ()
  {
    Classes.defaultConstructorNotSupported ();
  }

  /**
   * Configures the StreamParser for CSV (comma separated values) files.
   *
   * @return The StreamParser instance, configured for parsing CSV files.
   */
  public StreamParser withCSVSyntax ()
  {
    assert s != null;

    s.resetSyntax ();
    s.parseNumbers ();
    s.lowerCaseMode (false);
    s.eolIsSignificant (false);
    s.wordChars (0, 9);
    s.whitespaceChars (10, 10);
    s.wordChars (11, 12);
    s.whitespaceChars (13, 13);
    s.wordChars (14, 31);
    s.whitespaceChars (32, 32);
    s.wordChars (33, 33);
    s.quoteChar (34);
    s.wordChars (35, 41);
    s.wordChars (43, 43);
    s.whitespaceChars (44, 44);
    s.wordChars (45, 46);
    s.wordChars (58, 255);

    configureComments ();

    return this;
  }

  /**
   * Enable / disable slash-slash comments or slash-star (/*) comments.
   * <p/>
   * When slash-slash comments are enabled, lines beginning with two forward slashes are ignored. When slash-star
   * comments are enabled, all lines between a forward slash + star & a star + forward slash are ignored (including the
   * forward slash + star & star + forward slash characters themselves). When either comment type is disabled, the
   * comment characters and comments are parsed ordinarily.
   * <p/>
   * Note: All comment types are disabled by default.
   *
   * @param commentType
   * @param commentStatus
   *
   * @return The StreamParser instance, configured with the specified comment settings.
   */
  public StreamParser withComments (final CommentType commentType, final CommentStatus commentStatus)
  {
    switch (commentType)
    {
      case SLASH_SLASH:
      {
        areSlashSlashCommentsEnabled = commentStatus.isEnabled ();
      }
      case SLASH_STAR:
      {
        areSlashStarCommentsEnabled = commentStatus.isEnabled ();
      }
    }

    configureComments ();

    return this;
  }

  /**
   * Close the StreamParser and any associated open IO resources.
   *
   * @throws StreamParserException
   *           If the open IO resources cannot be closed.
   */
  public void close () throws StreamParserException
  {
    if (reader != null)
    {
      try
      {
        reader.close ();
      }
      catch (final IOException e)
      {
        throw new StreamParserException ("Unable to close resources.", e);
      }
    }
  }

  /**
   * Attempt to discard a single character from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a single character (excluding EOF).
   */
  public void discardNextCharacter () throws StreamParserException
  {
    parseNextToken ();

    checkTokenTypeEquals (TokenType.SINGLE_CHARACTER);
  }

  /**
   * Attempt to discard the specified single character from the stream.
   *
   * @param c
   *          The character to discard.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a single character (excluding EOF), <br/>
   *           If the content of unquotedString does not equal the content of the parsed token (excluding EOF).
   */
  public void discardNextCharacter (final char c) throws StreamParserException
  {
    discardNextCharacter ();

    checkTokenContentEquals (String.valueOf (c));
  }

  /**
   * Attempt to discard multiple single characters from the stream.
   *
   * @param count
   *          The number of multiple single characters to discard, must be > 0.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not single characters (excluding EOF).
   */
  public void discardNextCharacters (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      discardNextCharacter ();
    }
  }

  /**
   * Attempt to discard multiple specified single characters from the stream.
   *
   * @param characters
   *          The collection of single characters to discard, must not be null, must not be empty, must not contain any
   *          null elements.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next characters.size() tokens from the stream, <br/>
   *           If the next characters.size() tokens are not single characters (excluding EOF) <br/>
   *           If the content of characters does not equal the content of the parsed tokens (excluding EOF).
   */
  public void discardNextCharacters (final Collection <Character> characters) throws StreamParserException
  {
    Arguments.checkIsNotNullOrEmpty (characters, "characters");
    Arguments.checkHasNoNullElements (characters, "characters");

    for (final Character c : characters)
    {
      discardNextCharacter (c.charValue ());

      if (isEOF ())
      {
        break;
      }
    }
  }

  /**
   * Attempt to discard a double from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a double (excluding EOF).
   */
  public void discardNextDouble () throws StreamParserException
  {
    parseNextToken ();

    checkTokenTypeEquals (TokenType.DOUBLE);
  }

  /**
   * Attempt to discard the specified double from the stream.
   *
   * @param n
   *          The double to discard.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a double (excluding EOF), <br/>
   *           If the content of n does not equal the content of the parsed token (excluding EOF).
   */
  public void discardNextDouble (final double n) throws StreamParserException
  {
    discardNextDouble ();

    checkTokenContentEquals (String.valueOf (n));
  }

  /**
   * Attempt to discard count doubles from the stream.
   *
   * @param count
   *          The number of doubles to discard, must be > 0.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not doubles (excluding EOF).
   */
  public void discardNextDoubles (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      discardNextDouble ();
    }
  }

  /**
   * Attempt to discard multiple specified doubles from the stream.
   *
   * @param doubles
   *          The collection of doubles to discard, must not be null, must not be empty, must not contain any null
   *          elements.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next doubles.size() tokens from the stream, <br/>
   *           If the next doubles.size() tokens are not doubles (excluding EOF) <br/>
   *           If the content of doubles does not equal the content of the parsed tokens (excluding EOF).
   */
  public void discardNextDoubles (final Collection <Double> doubles) throws StreamParserException
  {
    Arguments.checkIsNotNullOrEmpty (doubles, "doubles");
    Arguments.checkHasNoNullElements (doubles, "doubles");

    for (final Double d : doubles)
    {
      discardNextDouble (d.doubleValue ());

      if (isEOF ())
      {
        break;
      }
    }
  }

  /**
   * Attempt to discard an integer from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not an integer (excluding EOF).
   */
  public void discardNextInteger () throws StreamParserException
  {
    parseNextToken ();

    checkTokenTypeEquals (TokenType.INTEGER);
  }

  /**
   * Attempt to discard the specified integer from the stream.
   *
   * @param n
   *          The integer to discard.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not an integer (excluding EOF), <br/>
   *           If the content of n does not equal the content of the parsed token (excluding EOF).
   */
  public void discardNextInteger (final int n) throws StreamParserException
  {
    discardNextInteger ();

    checkTokenContentEquals (String.valueOf (n));
  }

  /**
   * Attempt to discard multiple integers from the stream.
   *
   * @param count
   *          The number of integers to discard, must be > 0.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not integers (excluding EOF).
   */
  public void discardNextIntegers (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      discardNextInteger ();
    }
  }

  /**
   * Attempt to discard multiple specified integers from the stream.
   *
   * @param integers
   *          The collection of integers to discard, must not be null, must not be empty, must not contain any null
   *          elements.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next integers.size() tokens from the stream, <br/>
   *           If the next integers.size() tokens are not integers (excluding EOF) <br/>
   *           If the content of integers does not equal the content of the parsed tokens (excluding EOF).
   */
  public void discardNextIntegers (final Collection <Integer> integers) throws StreamParserException
  {
    Arguments.checkIsNotNullOrEmpty (integers, "integers");
    Arguments.checkHasNoNullElements (integers, "integers");

    for (final Integer i : integers)
    {
      discardNextInteger (i.intValue ());

      if (isEOF ())
      {
        break;
      }
    }
  }

  /**
   * Attempt to discard a line from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next line from the stream.
   */
  public void discardNextLine () throws StreamParserException
  {
    enableEndOfLineTokens ();

    do
    {
      parseNextToken ();
    }
    while (!isEOL () && !isEOF ());

    disableEndOfLineTokens ();
  }

  /**
   * Attempt to discard the specified number of lines from the stream.
   *
   * @param count
   *          The number of lines to discard, must be > 0.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count lines from the stream.
   */
  public void discardNextLines (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    enableEndOfLineTokens ();

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      do
      {
        parseNextToken ();
      }
      while (!isEOL () && !isEOF ());
    }

    disableEndOfLineTokens ();
  }

  /**
   * Attempt to discard a quoted string from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a quoted String (excluding EOF).
   */
  public void discardNextQuotedString () throws StreamParserException
  {
    parseNextToken ();

    checkTokenTypeEquals (TokenType.QUOTED_STRING);
  }

  /**
   * Attempt to discard the specified quoted string from the stream.
   *
   * @param quotedString
   *          The quoted string to discard, must not be null, must not be empty, must not be blank (whitespace only).
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a quoted String (excluding EOF), <br/>
   *           If the content of quotedString does not equal the content of the parsed token (excluding EOF).
   */
  public void discardNextQuotedString (final String quotedString) throws StreamParserException
  {
    Arguments.checkIsNotNullOrEmptyOrBlank (quotedString, "quotedString");

    discardNextQuotedString ();

    checkTokenContentEquals (quotedString);
  }

  /**
   * Attempt to discard the specified number of quoted strings from the stream.
   *
   * @param count
   *          The number of quoted strings to discard, must be > 0.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not quoted strings (excluding EOF).
   */
  public void discardNextQuotedStrings (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      discardNextQuotedString ();
    }
  }

  /**
   * Attempt to discard multiple specified quoted strings from the stream.
   *
   * @param quotedStrings
   *          The collection of quoted strings to discard, must not be null, must not be empty, must not contain any
   *          null elements.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next quotedStrings.size() tokens from the stream, <br/>
   *           If the next quotedStrings.size() tokens are not quoted Strings (excluding EOF), <br/>
   *           If the content of quotedStrings does not equal the content of the parsed tokens (excluding EOF).
   */
  public void discardNextQuotedStrings (final Collection <String> quotedStrings) throws StreamParserException
  {
    Arguments.checkIsNotNullOrEmpty (quotedStrings, "quotedStrings");
    Arguments.checkHasNoNullElements (quotedStrings, "quotedStrings");

    for (final String quotedString : quotedStrings)
    {
      discardNextQuotedString (quotedString);

      if (isEOF ())
      {
        break;
      }
    }
  }

  /**
   * Attempt to discard a token from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream.
   */
  public void discardNextToken () throws StreamParserException
  {
    parseNextToken ();
  }

  /**
   * Attempt to discard the specified number of tokens from the stream.
   *
   * @param count
   *          The number of tokens to discard, must be > 0.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream.
   */
  public void discardNextTokens (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      discardNextToken ();
    }
  }

  /**
   * Attempt to discard an unquoted string from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not an unquoted string (excluding EOF).
   */
  public void discardNextUnquotedString () throws StreamParserException
  {
    parseNextToken ();

    checkTokenTypeEquals (TokenType.UNQUOTED_STRING);
  }

  /**
   * Attempt to discard the specified unquoted string from the stream.
   *
   * @param unquotedString
   *          The unquoted string to discard.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not an unquoted string (excluding EOF), <br/>
   *           If the content of unquotedString does not equal the content of the parsed token (excluding EOF).
   */
  public void discardNextUnquotedString (final String unquotedString) throws StreamParserException
  {
    Arguments.checkIsNotNullOrEmptyOrBlank (unquotedString, "unquotedString");

    discardNextUnquotedString ();

    checkTokenContentEquals (unquotedString);
  }

  /**
   * Attempt to discard the specified number of unquoted strings from the stream.
   *
   * @param count
   *          The number of unquoted strings to discard, must be > 0.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not unquoted Strings (excluding EOF).
   */
  public void discardNextUnquotedStrings (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      discardNextUnquotedString ();
    }
  }

  /**
   * Attempt to discard multiple specified unquoted strings from the stream.
   *
   * @param unquotedStrings
   *          The collection of unquoted strings to discard, must not be null, must not be empty, must not contain any
   *          null elements.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next unquotedStrings.size() tokens from the stream, <br/>
   *           If the next unquotedStrings.size() tokens are not unquoted Strings (excluding EOF), <br/>
   *           If the content of unquotedStrings does not equal the content of the parsed tokens (excluding EOF).
   */
  public void discardNextUnquotedStrings (final Collection <String> unquotedStrings) throws StreamParserException
  {
    Arguments.checkIsNotNullOrEmpty (unquotedStrings, "unquotedStrings");
    Arguments.checkHasNoNullElements (unquotedStrings, "unquotedStrings");

    for (final String unquotedString : unquotedStrings)
    {
      discardNextUnquotedString (unquotedString);

      if (isEOF ())
      {
        break;
      }
    }
  }

  /**
   * Attempt to get the next single character from the stream. <br/>
   * <br/>
   * Note: This method will return -1 if EOF is encountered.
   *
   * @return The next single character code in the stream, or -1 on EOF.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a single character (excluding EOF).
   */
  public int getNextCharacter () throws StreamParserException
  {
    assert s != null;

    discardNextCharacter ();

    return isEOF () ? -1 : s.ttype;
  }

  /**
   * Attempt to get a collection of count single characters from the stream. <br/>
   * <br/>
   * Note: If EOF is encountered before the last character to get, then the size of the collection will be smaller than
   * count, and the last character in the smaller collection will have a value of -1, indicating EOF.
   *
   * @param count
   *          The number of single characters to get from the stream, must be > 0.
   *
   * @return A collection of single character codes from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not single characters (excluding EOF).
   */
  public Collection <Integer> getNextCharacters (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    final Collection <Integer> characterCodes = new ArrayList <> (count);

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      characterCodes.add (getNextCharacter ());
    }

    return characterCodes;
  }

  /**
   * Attempt to get the next double from the stream. <br/>
   * <br/>
   * Note: This method will return Double.MIN_VALUE if EOF is encountered.
   *
   * @return The next double in the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a double (excluding EOF).
   */
  public double getNextDouble () throws StreamParserException
  {
    assert s != null;

    parseNextToken ();

    checkTokenTypeEquals (TokenType.DOUBLE);

    return isEOF () ? Double.MIN_VALUE : s.nval;
  }

  /**
   * Attempt to get a collection of count doubles from the stream. <br/>
   * <br/>
   * Note: If EOF is encountered before the last double to get, then the size of the collection will be smaller than
   * count, and the last double in the collection will have a value of Double.MIN_VALUE, indicating EOF.
   *
   * @param count
   *          The number of doubles to get from the stream, must be > 0.
   *
   * @return A collection of doubles from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not doubles (excluding EOF).
   */
  public Collection <Double> getNextDoubles (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    final Collection <Double> doubles = new ArrayList <> (count);

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      doubles.add (getNextDouble ());
    }

    return doubles;
  }

  /**
   * Attempt to get the next integer from the stream. <br/>
   * <br/>
   * Note: This method will return Integer.MIN_VALUE if EOF is encountered.
   *
   * @return The next integer in the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not an integer (excluding EOF).
   */
  public int getNextInteger () throws StreamParserException
  {
    assert s != null;

    parseNextToken ();

    checkTokenTypeEquals (TokenType.INTEGER);

    return isEOF () ? Integer.MIN_VALUE : (int) s.nval;
  }

  /**
   * Attempt to get a collection of count integers from the stream. <br/>
   * <br/>
   * Note: If EOF is encountered before the last integer to get, then the size of the collection will be smaller than
   * count, and the last integer in the collection will have a value of Integer.MIN_VALUE, indicating EOF.
   *
   * @param count
   *          The number of integers to get from the stream, must be > 0.
   *
   * @return A collection of integers from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not integers (excluding EOF).
   */
  public Collection <Integer> getNextIntegers (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    final Collection <Integer> integers = new ArrayList <> (count);

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      integers.add (getNextInteger ());
    }

    return integers;
  }

  /**
   * Attempt to get the next line from the stream. <br/>
   * <br/>
   * Note: Each token in the line will be delimited by a space. This method will return an empty string if EOF is
   * encountered.
   *
   * @return The next line in the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next line from the stream (excluding EOF)
   */
  public String getNextLine () throws StreamParserException
  {
    final StringBuilder stringBuilder = new StringBuilder ();

    enableEndOfLineTokens ();

    do
    {
      parseNextToken ();

      if (!isEOL () && !isEOF ())
      {
        stringBuilder.append (getCurrentTokenContent ()).append (" ");
      }
    }
    while (!isEOL () && !isEOF ());

    disableEndOfLineTokens ();

    return stringBuilder.toString ().trim ();
  }

  /**
   * Attempt to get a collection of count lines from the stream. <br/>
   * <br/>
   * Note: Each token in each line will be delimited by a space. If EOF is encountered before the last line to get, then
   * the size of the collection will be smaller than count, and the last line in the collection will be empty, to
   * indicate EOF.
   *
   * @param count
   *          The number of lines to get from the stream, must be > 0.
   *
   * @return A collection of lines from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream (excluding EOF)
   */
  public Collection <String> getNextLines (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    final Collection <String> lines = new ArrayList <> (count);

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      lines.add (getNextLine ());
    }

    return lines;
  }

  /**
   * Attempt to get the next quoted string from the stream. <br/>
   * <br/>
   * Note: This method will return an empty string if EOF is encountered.
   *
   * @return The next quoted string in the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not a quoted string (excluding EOF).
   */
  public String getNextQuotedString () throws StreamParserException
  {
    assert s != null;

    parseNextToken ();

    checkTokenTypeEquals (TokenType.QUOTED_STRING);

    return isEOF () ? "" : s.sval;
  }

  /**
   * Attempt to get a collection of count quoted strings from the stream. <br/>
   * <br/>
   * Note: If EOF is encountered before the last quoted string to get, then the size of the collection will be smaller
   * than count, and the last quoted string in the collection will be empty, indicating EOF.
   *
   * @param count
   *          The number of quoted strings to get from the stream, must be > 0.
   *
   * @return An collection of quoted strings from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not quoted strings (excluding EOF).
   */
  public Collection <String> getNextQuotedStrings (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    final Collection <String> quotedStrings = new ArrayList <> (count);

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      quotedStrings.add (getNextQuotedString ());
    }

    return quotedStrings;
  }

  /**
   * Attempt to get the next token from the stream. <br/>
   * <br/>
   * Note: This method will return an empty string if EOF is encountered.
   *
   * @return The next token in the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream.
   */
  public String getNextToken () throws StreamParserException
  {
    parseNextToken ();

    return isEOF () ? "" : getCurrentTokenContent ();
  }

  /**
   * Attempt to get a collection of count tokens from the stream. <br/>
   * <br/>
   * Note: If EOF is encountered before the last token to get, then the size of the collection will be smaller than
   * count, and the last token in the collection will be an empty string, indicating EOF.
   *
   * @param count
   *          The number of tokens to get from the stream, must be > 0.
   *
   * @return A collection of tokens from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream.
   */
  public Collection <String> getNextTokens (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    final Collection <String> tokens = new ArrayList <> (count);

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      tokens.add (getNextToken ());
    }

    return tokens;
  }

  /**
   * Attempt to get the next unquoted string from the stream. <br/>
   * <br/>
   * Note: This method will return an empty string if EOF is encountered.
   *
   * @return The next unquoted string in the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not an unquoted string (excluding EOF).
   */
  public String getNextUnquotedString () throws StreamParserException
  {
    assert s != null;

    parseNextToken ();

    checkTokenTypeEquals (TokenType.UNQUOTED_STRING);

    return isEOF () ? "" : s.sval;
  }

  /**
   * Attempt to get a collection of count unquoted strings from the stream. <br/>
   * <br/>
   * Note: If EOF is encountered before the last unquoted string to get, then the size of the collection will be smaller
   * than count, and the last unquoted string in the collection will be empty, indicating EOF.
   *
   * @param count
   *          The number of unquoted strings to get from the stream, must be > 0.
   *
   * @return An collection of unquoted strings from the stream.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next count tokens from the stream, <br/>
   *           If the next count tokens are not unquoted strings (excluding EOF).
   */
  public Collection <String> getNextUnquotedStrings (final int count) throws StreamParserException
  {
    Arguments.checkLowerExclusiveBound (count, 0, "count");

    final Collection <String> unquotedStrings = new ArrayList <> (count);

    for (int i = 0; i < count && !isEOF (); ++i)
    {
      unquotedStrings.add (getNextUnquotedString ());
    }

    return unquotedStrings;
  }

  /**
   * Gets whether the StreamParser has parsed the EOF token.
   *
   * @return True if the EOF token has been parsed, false otherwise.
   */
  public boolean isEndOfFile ()
  {
    return isEOF ();
  }

  /**
   * Parse the next token and verify that it is EOF.
   *
   * @throws StreamParserException
   * <br/>
   *           If the StreamParser could not read the next token from the stream, <br/>
   *           If the next token is not the EOF token.
   */
  public void verifyEndOfFile () throws StreamParserException
  {
    parseNextToken ();

    checkTokenTypeEquals (TokenType.END_OF_FILE);
  }

  private void configureComments ()
  {
    if (areSlashStarCommentsEnabled || areSlashSlashCommentsEnabled)
    {
      s.ordinaryChar (FORWARD_SLASH_CHARACTER);
    }
    else
    {
      s.wordChars (FORWARD_SLASH_CHARACTER, FORWARD_SLASH_CHARACTER);
    }

    if (areSlashStarCommentsEnabled)
    {
      s.ordinaryChar (STAR_CHARACTER);
    }
    else
    {
      s.wordChars (STAR_CHARACTER, STAR_CHARACTER);
    }

    s.slashSlashComments (areSlashSlashCommentsEnabled);
    s.slashStarComments (areSlashStarCommentsEnabled);
  }

  private void checkTokenContentEquals (final String expectedTokenContent) throws StreamParserException
  {
    assert expectedTokenContent != null;

    if (!isEOF ())
    {
      final String actualTokenContent = getCurrentTokenContent ();

      if (!actualTokenContent.equals (expectedTokenContent))
      {
        throw new StreamParserException ("Token[" + expectedTokenContent + "] expected, but found Token["
                        + actualTokenContent + "]\n\n" + "Last token successfully parsed: " + getCurrentTokenInfo ());
      }
    }
  }

  private void checkTokenTypeEquals (final TokenType expectedTokenType) throws StreamParserException
  {
    assert expectedTokenType != null;

    final TokenType actualTokenType = getCurrentTokenType ();

    if (!isEOF () && actualTokenType != expectedTokenType)
    {
      throw new StreamParserException ("Token type: " + expectedTokenType + " expected, but found token type: "
                      + actualTokenType + "\n\nLast token successfully parsed: " + getCurrentTokenInfo ());
    }
  }

  private void disableEndOfLineTokens ()
  {
    assert s != null;

    s.eolIsSignificant (false);
  }

  private void enableEndOfLineTokens ()
  {
    assert s != null;

    s.eolIsSignificant (true);
  }

  private String getCurrentTokenContent () throws StreamParserException
  {
    assert s != null;

    String currentTokenContent = "";

    switch (s.ttype)
    {
      case StreamTokenizer.TT_NUMBER:
      {
        if (Utils.isInteger (s.nval))
        {
          currentTokenContent = String.valueOf ((int) (s.nval));
        }
        else
        {
          currentTokenContent = String.valueOf (s.nval);
        }

        break;
      }

      case StreamTokenizer.TT_WORD:
      {
        currentTokenContent = s.sval;

        break;
      }

      case StreamTokenizer.TT_EOF:
      {
        throw new StreamParserException ("Cannot get token content for " + "current token type: "
                        + TokenType.END_OF_FILE + "\n\n" + "Last token successfully parsed: " + getCurrentTokenInfo ());
      }

      case StreamTokenizer.TT_EOL:
      {
        throw new StreamParserException ("Cannot get token content for " + "current token type: "
                        + TokenType.END_OF_LINE + "\n\n" + "Last token successfully parsed: " + getCurrentTokenInfo ());
      }

      case QUOTE_CHARACTER:
      {
        currentTokenContent = s.sval;

        break;
      }

      default:
      {
        assert s.ttype >= 0 && s.ttype <= Character.MAX_VALUE;

        currentTokenContent = String.valueOf ((char) s.ttype);

        break;
      }
    }

    assert !currentTokenContent.isEmpty ();

    return currentTokenContent;
  }

  private String getCurrentTokenInfo ()
  {
    assert s != null;

    return s.toString ();
  }

  private TokenType getCurrentTokenType () throws StreamParserException
  {
    assert s != null;

    TokenType currentTokenType = null;

    switch (s.ttype)
    {
      case StreamTokenizer.TT_NUMBER:
      {
        if (Utils.isInteger (s.nval))
        {
          currentTokenType = TokenType.INTEGER;
        }
        else
        {
          currentTokenType = TokenType.DOUBLE;
        }

        break;
      }

      case StreamTokenizer.TT_WORD:
      {
        currentTokenType = TokenType.UNQUOTED_STRING;

        break;
      }

      case StreamTokenizer.TT_EOF:
      {
        currentTokenType = TokenType.END_OF_FILE;

        break;
      }

      case StreamTokenizer.TT_EOL:
      {
        currentTokenType = TokenType.END_OF_LINE;

        break;
      }

      case StreamParser.QUOTE_CHARACTER:
      {
        currentTokenType = TokenType.QUOTED_STRING;

        break;
      }

      default:
      {
        assert s.ttype >= 0 && s.ttype <= Character.MAX_VALUE;

        currentTokenType = TokenType.SINGLE_CHARACTER;

        break;
      }
    }

    assert currentTokenType != null;

    return currentTokenType;
  }

  private void initialize (final String filePath) throws StreamParserException
  {
    assert !com.google.common.base.Strings.isNullOrEmpty (filePath);
    assert !Strings.isWhitespace (filePath);

    initialize (new File (filePath));
  }

  private void initialize (final File file) throws StreamParserException
  {
    assert file != null;

    try
    {
      initialize (new FileReader (file));
    }
    catch (final FileNotFoundException e)
    {
      throw new StreamParserException ("Could not load file: " + file, e);
    }
  }

  private void initialize (final InputStream inputStream)
  {
    assert inputStream != null;

    initialize (new BufferedReader (new InputStreamReader (inputStream)));
  }

  private void initialize (final Reader reader)
  {
    assert reader != null;

    this.reader = reader;

    s = new StreamTokenizer (reader);

    setupSyntaxTables ();
  }

  private boolean isEOF ()
  {
    assert s != null;

    return s.ttype == StreamTokenizer.TT_EOF;
  }

  private boolean isEOL ()
  {
    assert s != null;

    return s.ttype == StreamTokenizer.TT_EOL;
  }

  private void parseNextToken () throws StreamParserException
  {
    assert s != null;

    try
    {
      s.nextToken ();
    }
    catch (final IOException e)
    {
      throw new StreamParserException ("Could not read next token.\n\n" + "Last token successfully parsed: "
                      + getCurrentTokenInfo (), e);
    }
  }

  private void setupSyntaxTables ()
  {
    assert s != null;

    s.resetSyntax ();
    s.parseNumbers ();
    s.lowerCaseMode (false);
    s.eolIsSignificant (false);
    s.whitespaceChars (0, 32);
    s.wordChars (33, 33);
    s.quoteChar (34);
    s.wordChars (35, 41);
    s.wordChars (43, 46);
    s.wordChars (58, 126);
    s.whitespaceChars (127, 159);
    s.wordChars (160, 255);

    configureComments ();
  }
}
