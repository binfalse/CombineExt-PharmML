package de.unirostock.sems.cbext.pharmml;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import de.unirostock.sems.cbext.FormatRecognizer;

/**
 * The Class PharmMlFormatizer to recognize PharmML files.
 */
public class PhramMlFormatizer extends FormatRecognizer {
	
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
		if (extension != null && extension.equals ("pharmml"))
			return buildUri (IDENTIFIERS_BASE, "pharmml");
		return null;
	}

}
