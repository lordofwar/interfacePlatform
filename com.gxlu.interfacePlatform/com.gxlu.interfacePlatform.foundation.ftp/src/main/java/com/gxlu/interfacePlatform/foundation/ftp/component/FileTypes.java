/**************************************************************************
 *
 * $RCSfile: FileTypes.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:51 $
 *
 * $Log: FileTypes.java,v $
 * Revision 1.1  2007/11/22 15:05:51  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *  Attempts to classify files as ASCII or binary via their filename 
 *  extension. Email support at enterprisedt dot com if you feel we
 *  have missed out important file types. Of course, extensions can
 *  be registered and unregistered at runtime to customize file types for
 *  different applications
 *
 */
public class FileTypes {
        
    /**
     * Holds map of ASCII extensions
     */
    private Hashtable fileTypes = new Hashtable();
    
    public static FileTypes ASCII = new FileTypes();
    
    public static FileTypes BINARY = new FileTypes();
    
    static {
        // ASCII default extensions
        ASCII.registerExtension("ASC");
        ASCII.registerExtension("C");
        ASCII.registerExtension("CPP");
        ASCII.registerExtension("CS");
        ASCII.registerExtension("CSV");
        ASCII.registerExtension("H");
        ASCII.registerExtension("HTM");
        ASCII.registerExtension("HTML");
        ASCII.registerExtension("INF");
        ASCII.registerExtension("INI");
        ASCII.registerExtension("JAVA");
        ASCII.registerExtension("KSH");
        ASCII.registerExtension("LOG");
        ASCII.registerExtension("PS");
        ASCII.registerExtension("SH");
        ASCII.registerExtension("SHTML");
        ASCII.registerExtension("TXT");
        ASCII.registerExtension("UU");
        ASCII.registerExtension("UUE");
        ASCII.registerExtension("XML");
        ASCII.registerExtension("XSL");
        
        // binary default extensions
        BINARY.registerExtension("EXE");
        BINARY.registerExtension("PDF");
        BINARY.registerExtension("XLS");
        BINARY.registerExtension("DOC");
        BINARY.registerExtension("CHM");
        BINARY.registerExtension("PPT");
        BINARY.registerExtension("DOT");
        BINARY.registerExtension("DLL");
        BINARY.registerExtension("GIF");
        BINARY.registerExtension("JPG");
        BINARY.registerExtension("JPEG");
        BINARY.registerExtension("BMP");
        BINARY.registerExtension("TIF");
        BINARY.registerExtension("TIFF");
        BINARY.registerExtension("CLASS");
        BINARY.registerExtension("JAR");
        BINARY.registerExtension("SO");
        BINARY.registerExtension("AVI");
        BINARY.registerExtension("MP3");
        BINARY.registerExtension("MPG");
        BINARY.registerExtension("MPEG");
        BINARY.registerExtension("MSI");
        BINARY.registerExtension("OCX");
        BINARY.registerExtension("ZIP");
        BINARY.registerExtension("GZ");
        BINARY.registerExtension("RAM");
        BINARY.registerExtension("WAV");
        BINARY.registerExtension("WMA");
        BINARY.registerExtension("XLA");
        BINARY.registerExtension("XLL");
        BINARY.registerExtension("MDB");
        BINARY.registerExtension("MOV");
        BINARY.registerExtension("OBJ");
        BINARY.registerExtension("PUB");
        BINARY.registerExtension("PCX");
        BINARY.registerExtension("MID");
        BINARY.registerExtension("BIN");
        BINARY.registerExtension("WKS");
        BINARY.registerExtension("PNG");
        BINARY.registerExtension("WPS");
        BINARY.registerExtension("AAC");
        BINARY.registerExtension("AIFF");
        BINARY.registerExtension("PSP");
    }
    
    
    /**
     * Private so others can't create instances
     */
    private FileTypes() {}
    
    /**
     * Get the list of registered file extensions
     * 
     * @return String[] of file extensions
     */
    public String[] extensions() {
        String[] ext = new String[fileTypes.size()];
        Enumeration e = fileTypes.elements();
        int i = 0;
        while (e.hasMoreElements()) {
            ext[i++] = (String)e.nextElement();
        }
        return ext;
    }
    
    /**
     * Register a new file extension
     *  
     * @param ext   filename extension (excluding ".") to register
     */
    public void registerExtension(String ext) {
        ext = ext.toUpperCase();
        fileTypes.put(ext, ext);
    }
    
    /**
     * Unregister a file extension
     *  
     * @param ext   filename extension (excluding ".") to unregister
     */
    public void unregisterExtension(String ext) {
        ext = ext.toUpperCase();
        fileTypes.remove(ext);
    }
    
    /**
     * Determines if a file matches this extension type
     * 
     * @param file  handle to file
     * @return  true if matches, false otherwise
     */
    public boolean matches(File file) {
        return matches(file.getName());
    } 
    
    /**
     * Determines if a file matches this extension type
     * 
     * @param name  file's name
     * @return  true if matches, false otherwise
     */
    public boolean matches(String name) {
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            String ext = name.substring(pos+1).toUpperCase();
            if (fileTypes.get(ext) != null)
                return true;
        }
        return false;
    } 


}
