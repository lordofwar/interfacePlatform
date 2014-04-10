/**************************************************************************
 *
 * $RCSfile: FTPFileParser.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:53 $
 *
 * $Log: FTPFileParser.java,v $
 * Revision 1.1  2007/11/22 15:05:53  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/

package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.text.ParseException;
import java.util.Locale;

/**
 *  Root class of all file parsers
 *
 */
abstract public class FTPFileParser {
    
    /**
     * Maximum number of fields in raw string
     */
    private final static int MAX_FIELDS = 100;
    
    /**
     * Parse server supplied string
     * 
     * @param raw   raw string to parse
     */
    abstract public FTPFile parse(String raw) throws ParseException;
    
    /**
     * Set the locale for date parsing of listings
     * 
     * @param locale    locale to set
     */
    abstract public void setLocale(Locale locale);
      
    /**
     * Splits string consisting of fields separated by
     * whitespace into an array of strings. Yes, we could
     * use String.split() but this would restrict us to 1.4+
     * 
     * @param str   string to split
     * @return array of fields
     */
    protected String[] split(String str) {
        return split(str, new WhitespaceSplitter());
    }
    
    /**
     * Splits string consisting of fields separated by
     * whitespace into an array of strings. Yes, we could
     * use String.split() but this would restrict us to 1.4+
     * 
     * @param str   string to split
     * @return array of fields
     */
    protected String[] split(String str, char token) {
        return split(str, new CharSplitter(token));
    }
    
    /**
     * Splits string consisting of fields separated by
     * whitespace into an array of strings. Yes, we could
     * use String.split() but this would restrict us to 1.4+
     * 
     * @param str   string to split
     * @return array of fields
     */
    protected String[] split(String str, Splitter splitter) {
        String[] fields = new String[MAX_FIELDS];
        int pos = 0;
        StringBuffer field = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!splitter.isSeparator(ch))
                field.append(ch);
            else {
                if (field.length()> 0) {
                    fields[pos++] = field.toString();
                    field.setLength(0);
                }
            }
        }
        // pick up last field
        if (field.length() > 0) {
            fields[pos++] = field.toString();
        }
        String[] result = new String[pos];
        System.arraycopy(fields, 0, result, 0, pos);
        return result;
    }
    
    
    interface Splitter {
        boolean isSeparator(char ch); 
    }
    
    class CharSplitter implements Splitter {
        private char token;

        CharSplitter(char token) {
            this.token = token;
        }

        public boolean isSeparator(char ch) {
            if (ch == token)
                return true;
            return false;
        }
        
    }
    
    class WhitespaceSplitter implements Splitter {
        
        public boolean isSeparator(char ch) {
            if (Character.isWhitespace(ch))
                return true;
            return false;
        }
    }
}
