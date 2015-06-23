package de.unirostock.sems.cbext.pharmml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import de.unirostock.sems.cbext.Formatizer;
import de.unirostock.sems.cbext.Iconizer;

/**
 * Demonstrate the usage of this extension.
 * 
 * @author Martin Scharm
 */
public class Demo 
{
    
    /**
     * Demonstrate how to use the extension.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void demo () throws IOException
    {
    	// demonstrate how to use the extension
    	
    	
    	
    	
    	// first: detecting the format of a file
    	
    	File pharmmlFile = new File ("test/vancauter_v1.xml");
    	// add the pharmml recognizer to the formatizer
    	Formatizer.addFormatRecognizer (new PharmMlRecognizer ());
    	// guess the format
    	URI format = Formatizer.guessFormat (pharmmlFile);
    	System.out.println ("the format of file " + pharmmlFile + " is recognized as " + format);
    	
    	
    	
    	
    	
    	// second: get an icon for pharmml files
    	
    	// add the pharmml icon to the iconizer
    	Iconizer.addIconCollection (new PharmMlIcon ());
    	// lets write the icon for the pharmml format to a temporary file
    	File tmpIcon = File.createTempFile ("pharmml", ".png");

			InputStream fin = Iconizer.formatToIconStream (format);
			FileOutputStream fout = new FileOutputStream (tmpIcon);
			
			byte[] b = new byte[1024];
			int noOfBytes = 0;
			
			while ( (noOfBytes = fin.read (b)) != -1)
			{
				fout.write (b, 0, noOfBytes);
			}
			
			System.out.println ("extracted icon to " + tmpIcon);
			fin.close ();
			fout.close ();
    		
    }
}
