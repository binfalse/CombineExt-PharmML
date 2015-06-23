package de.unirostock.sems.cbext.pharmml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;

import de.binfalse.bflog.LOGGER;
import de.unirostock.sems.cbext.FormatRecognizer;
import de.unirostock.sems.cbext.Formatizer;
import de.unirostock.sems.cbext.Iconizer;

/**
 * Unit test for the pharmml extensions.
 * 
 * @author Martin Scharm
 */
public class PharmMlTest
{
	
	/**
	 * 
	 */
	@Before
	public void initStuff ()
	{
		Formatizer.removeRecognizers ();
		Formatizer.addDefaultRecognizers ();
		
		Iconizer.removeCollections ();
		Iconizer.addDefaultCollection ();
	}
    /**
     * Test recognizer.
     *
     */
	@Test
    public void testRecognizer ()
    {
			PharmMlRecognizer pmr = new PharmMlRecognizer ();
			
			File validFile = new File ("test/vancauter_v1.xml");
			File invalidFile = new File ("test/invalid-vancauter_v1.xml");
			try
			{
				String validMime = Files.probeContentType (validFile.toPath ());
				assertNotNull ("expected pharmml format for pharmml file", pmr.getFormatByParsing (validFile, validMime));
				assertNull ("expected null format for wrong mime", pmr.getFormatByParsing (validFile, "something"));
				
				String invalidMime = Files.probeContentType (invalidFile.toPath ());
				assertNull ("did not expect a format for an invalid file", pmr.getFormatByParsing (invalidFile, invalidMime));

				assertNull ("did not expect a format for just a mime type", pmr.getFormatFromMime ("application/xml"));
				assertNull ("did not expect a format for null mime", pmr.getFormatFromMime (null));


				assertNull ("did not expect a format for invalid ext", pmr.getFormatFromExtension ("something"));
				assertEquals ("did expect pharmml format for pharmml ext", "http://identifiers.org/combine.specifications/pharmml",
					pmr.getFormatFromExtension ("pharmml").toString ());
				assertNull ("did not expect a format for null ext", pmr.getFormatFromExtension (null));
				
			}
			catch (IOException e)
			{
				LOGGER.error (e, "io error recognizing pharmml file");
				fail ("io error");
			}
    }
	
	/**
	 * Test icon collection.
	 */
	@Test
	public void testIconCollection ()
	{
		PharmMlIcon pmi = new PharmMlIcon ();

		try
		{
			URI format = new URI (FormatRecognizer.IDENTIFIERS_BASE + "pharmml");
			assertTrue ("did not find icon for format: " + format, pmi.hasIcon (format));
			assertNotNull ("cannot find icon name for format: " + format, pmi.formatToIconName (format));
			assertNotNull ("cannot find icon url for format: " + format, pmi.formatToIconUrl (format));
			assertNotNull ("cannot open icon stream for format: " + format, pmi.formatToIconStream (format));
			
			format = new URI (FormatRecognizer.IDENTIFIERS_BASE + "pharmml.level-1.version-1");
			assertTrue ("did not find icon for format: " + format, pmi.hasIcon (format));
			assertNotNull ("cannot find icon name for format: " + format, pmi.formatToIconName (format));
			assertNotNull ("cannot find icon url for format: " + format, pmi.formatToIconUrl (format));
			assertNotNull ("cannot open icon stream for format: " + format, pmi.formatToIconStream (format));
			
			format = new URI ("http://binfalse.de");
			assertFalse ("did not expect to find icon for format: " + format, pmi.hasIcon (format));
			assertNull ("did not expect to find icon name for format: " + format, pmi.formatToIconName (format));
			assertNull ("did not expect to find icon url for format: " + format, pmi.formatToIconUrl (format));
			assertNull ("did not expect to open icon stream for format: " + format, pmi.formatToIconStream (format));		
	
			format = new URI (FormatRecognizer.IDENTIFIERS_BASE + "sbml.level-1.version-1");
			assertFalse ("did not expect to find icon for format: " + format, pmi.hasIcon (format));
			assertNull ("did not expect to find icon name for format: " + format, pmi.formatToIconName (format));
			assertNull ("did not expect to find icon url for format: " + format, pmi.formatToIconUrl (format));
			assertNull ("did not expect to open icon stream for format: " + format, pmi.formatToIconStream (format));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
			fail ("could not create uri " + e.getMessage ());
		}
	}
	
	/**
	 * Test iconizer.
	 */
	@Test
	public void testIconizer ()
	{

		URI pharmml = FormatRecognizer.buildUri (FormatRecognizer.IDENTIFIERS_BASE, "pharmml");
		
		// test empty icon
		InputStream fin;
    byte[] bytes;
    int noOfBytes = 0, b = 0;
    
    
		// test w/o extension
		fin = Iconizer.formatToIconStream (pharmml);
    bytes = new byte[1024];
    noOfBytes = 0; b = 0;

    try
		{
			while( (b = fin.read(bytes)) != -1 )
			{
				noOfBytes += b;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail ("failed to read generic icon");
		}
    assertEquals ("generic icon has unexpected size", 1487, noOfBytes);
    
		assertEquals ("expected to get the generic icon", "Blue-unknown.png", Iconizer.formatToIcon (pharmml));
		assertTrue ("expected to get the generic icon", Iconizer.formatToIconUrl (pharmml).toString ().endsWith ("Blue-unknown.png"));
    
    
    
		Iconizer.addIconCollection (new PharmMlIcon ());
    
    
    
		// test icon w/ extension
		fin = Iconizer.formatToIconStream (pharmml);
    bytes = new byte[1024];
    noOfBytes = 0; b = 0;

    try
		{
			while( (b = fin.read(bytes)) != -1 )
			{
				noOfBytes += b;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail ("failed to read pharmml icon");
		}
    assertEquals ("pharmml icon has unexpected size", 1150, noOfBytes);
    
		assertEquals ("expected to get the pharmml icon", "Green-pharmml.png", Iconizer.formatToIcon (pharmml));
		assertTrue ("expected to get the pharmml icon", Iconizer.formatToIconUrl (pharmml).toString ().endsWith ("Green-pharmml.png"));
		
	}
	
	/**
	 * Test demo.
	 * @throws IOException 
	 */
	@Test
	public void testDemo () throws IOException
	{
		new Demo ().demo ();
	}
	
	/**
	 * Test the formatizer.
	 */
	@Test
	public void testFormatizer ()
	{
		// default
		checkFormat (
			new File ("test/vancauter_v1.xml"),
			"http://purl.org/NET/mediatypes/application/xml",
			"http://purl.org/NET/mediatypes/application/xml",
			"http://purl.org/NET/mediatypes/application/xml");
		checkFormat (
			new File ("test/invalid-vancauter_v1.xml"),
			"http://purl.org/NET/mediatypes/application/xml",
			"http://purl.org/NET/mediatypes/application/xml",
			"http://purl.org/NET/mediatypes/application/xml");
		
		// add pharmml
		Formatizer.addFormatRecognizer (new PharmMlRecognizer ());
		
		checkFormat (
			new File ("test/vancauter_v1.xml"),
			"http://identifiers.org/combine.specifications/pharmml",
			"http://purl.org/NET/mediatypes/application/xml",
			"http://purl.org/NET/mediatypes/application/xml");
		checkFormat (
			new File ("test/invalid-vancauter_v1.xml"),
			"http://purl.org/NET/mediatypes/application/xml",
			"http://purl.org/NET/mediatypes/application/xml",
			"http://purl.org/NET/mediatypes/application/xml");
	}

	
	/**
	 * Check format.
	 * 
	 * Taken from CombineExt project.
	 *
	 * @param f the file
	 * @param expectedGuess the expected format by guess
	 * @param expectedExt the expected format from the extension
	 * @param expectedMime the expected format from the mime
	 */
	public static void checkFormat (File f, String expectedGuess, String expectedExt, String expectedMime)
	{
		try
		{
			URI format = Formatizer.guessFormat (f);
			assertEquals ("got wrong format for guessing " + f.getAbsolutePath (), expectedGuess, format.toString ());
			
			format = Formatizer.getFormatFromMime (Files.probeContentType (f.toPath ()));
			assertEquals ("got wrong format for mime of " + f.getAbsolutePath (), expectedMime, format.toString ());
			
			format = Formatizer.getFormatFromExtension (f.getName ().substring (f.getName ().lastIndexOf (".") + 1));
			assertEquals ("got wrong format for ext of " + f.getAbsolutePath (), expectedExt, format.toString ());
		}
		catch (IOException e)
		{
			e.printStackTrace ();
			fail ("couldn't test format for " + f.getAbsolutePath ());
		}
	}
}
