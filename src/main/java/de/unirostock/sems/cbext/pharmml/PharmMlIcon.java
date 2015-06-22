/**
 * 
 */
package de.unirostock.sems.cbext.pharmml;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import de.unirostock.sems.cbext.FormatRecognizer;
import de.unirostock.sems.cbext.IconCollection;


/**
 * @author Martin Scharm
 *
 */
public class PharmMlIcon implements IconCollection
{
	private static final String ICON_NAME = "Green-pharmml.png";
	
	/** The Constant ICON_DIR. */
	private static final String ICON_DIR				= "/icons/";


	/* (non-Javadoc)
	 * @see de.unirostock.sems.cbext.IconMapper#getPriority()
	 */
	public int getPriority() {
		return 110;
	}

	/* (non-Javadoc)
	 * @see de.unirostock.sems.cbext.IconMapper#hasIcon(java.net.URI)
	 */
	public boolean hasIcon(URI format)
	{
		return format.toString ().startsWith (FormatRecognizer.IDENTIFIERS_BASE) &&  format.toString ().contains ("pharmml");
	}

	public URL formatToIconUrl (URI format)
	{
		if (hasIcon (format))
			return PharmMlIcon.class.getResource( ICON_DIR + ICON_NAME );
		return null;
	}

	public InputStream formatToIconStream (URI format)
	{
		if (hasIcon (format))
			return PharmMlIcon.class.getResourceAsStream( ICON_DIR + ICON_NAME );
		return null;
	}

	public String formatToIconName (URI format)
	{
		if (hasIcon (format))
			return ICON_NAME;
		return null;
	}
}
