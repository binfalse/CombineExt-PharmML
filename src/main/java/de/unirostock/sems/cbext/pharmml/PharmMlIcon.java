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

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import de.unirostock.sems.cbext.FormatRecognizer;
import de.unirostock.sems.cbext.IconCollection;



/**
 * @author Martin Scharm
 * 
 */
public class PharmMlIcon
	implements IconCollection
{
	
	private static final String	ICON_NAME	= "Green-pharmml.png";
	
	/** The Constant ICON_DIR. */
	private static final String	ICON_DIR	= "/icons/";
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unirostock.sems.cbext.IconMapper#getPriority()
	 */
	public int getPriority ()
	{
		return 110;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unirostock.sems.cbext.IconMapper#hasIcon(java.net.URI)
	 */
	public boolean hasIcon (URI format)
	{
		return format.toString ().startsWith (FormatRecognizer.IDENTIFIERS_BASE)
			&& format.toString ().contains ("pharmml");
	}
	
	
	public URL formatToIconUrl (URI format)
	{
		if (hasIcon (format))
			return PharmMlIcon.class.getResource (ICON_DIR + ICON_NAME);
		return null;
	}
	
	
	public InputStream formatToIconStream (URI format)
	{
		if (hasIcon (format))
			return PharmMlIcon.class.getResourceAsStream (ICON_DIR + ICON_NAME);
		return null;
	}
	
	
	public String formatToIconName (URI format)
	{
		if (hasIcon (format))
			return ICON_NAME;
		return null;
	}
}
