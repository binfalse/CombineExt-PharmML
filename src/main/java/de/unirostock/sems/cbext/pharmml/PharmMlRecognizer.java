/**
 * Copyright © 2015 Martin Scharm <martin@binfalse.de>
 * 
 * This file is part of the CombineExt library.
 * 
 * CombineExt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * CombineExt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CombineExt. If not, see <http://www.gnu.org/licenses/>.
 */
package de.unirostock.sems.cbext.pharmml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import de.binfalse.bflog.LOGGER;
import de.unirostock.sems.cbext.FormatRecognizer;
import de.unirostock.sems.cbext.Formatizer;
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
public class PharmMlRecognizer
	extends FormatRecognizer
{
	
	/**
	 * As we are mainly working with PharmML files in this project this recognizer
	 * will have a higher priority.
	 */
	public static int	priority	= 120;
	
	/**
	 * Sets the priority of this format recognizer and triggers a resort of all
	 * format recognizers.
	 * 
	 * The higher the priority, the earlier this recognizer gets called.
	 * The first recognizer, which is able to identify a file, determines it's
	 * format.
	 * Setting a negative priority will be ignored.
	 * Default recognizers have a priority of 100.
	 * 
	 * @param newPriority
	 */
	public static void setPriority (int newPriority) {
		
		// no negative priorities!
		if( priority < 0 )
			return;
		
		priority = newPriority;
		Formatizer.resortRecognizers();
	}

	/* (non-Javadoc)
	 * @see de.unirostock.sems.cbext.FormatRecognizer#getPriority()
	 */
	@Override
	public int getPriority ()
	{
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
			ILibPharmML libPharmML = PharmMlFactory.getInstance ()
				.createLibPharmML ();
			InputStream in = new FileInputStream (file);
			IPharmMLResource resource = libPharmML.createDomFromResource (in);
			IPharmMLValidator validator = libPharmML.getValidator ();
			IValidationReport rpt = validator.createValidationReport (resource);
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
