package de.unirostock.sems.cbext.pharmml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import de.binfalse.bflog.LOGGER;
import de.unirostock.sems.cbext.FormatRecognizer;
import eu.ddmore.libpharmml.ILibPharmML;
import eu.ddmore.libpharmml.IPharmMLResource;
import eu.ddmore.libpharmml.IPharmMLValidator;
import eu.ddmore.libpharmml.IValidationReport;
import eu.ddmore.libpharmml.PharmMlFactory;

/**
 * The Class PharmMlFormatizer to recognize PharmML files.
 * 
 * @author Martin Scharm
 */
public class PharmMlRecognizer extends FormatRecognizer {
	
	/**
	 * As we are mainly working with PharmML files in this project this recognizer will have a higher priority.
	 */
	public static int priority = 120;
	
	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public URI getFormatByParsing (File file, String mimeType)
	{
		// mime type check
		if (mimeType == null || mimeType.equals ("application/xml") == false)
			return null;
		
		try
		{
			ILibPharmML libPharmML = PharmMlFactory.getInstance().createLibPharmML();
			InputStream in = new FileInputStream(file);
			IPharmMLResource resource = libPharmML.createDomFromResource(in);
			IPharmMLValidator validator = libPharmML.getValidator();
			IValidationReport rpt = validator.createValidationReport(resource);
			if (rpt.isValid ())
				return buildUri (IDENTIFIERS_BASE, "pharmml");
		}
		catch (IOException e)
		{
			LOGGER.info (e, "file ", file, " seems to be no PhramML file..");
		}
		catch (Exception e)
		{
			LOGGER.warn (e, "pharmml could not parse file ", file);
		}
		
		return null;
	}

	@Override
	public URI getFormatFromMime (String mime)
	{
		// we are not able to decide for a format by just kowing the mime
		return null;
	}

	@Override
	public URI getFormatFromExtension (String extension)
	{
		// every *.pharmml file is supposed to be pharmml
		if (extension != null && extension != null && extension.equals ("pharmml"))
			return buildUri (IDENTIFIERS_BASE, "pharmml");
		return null;
	}

}