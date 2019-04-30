package com.kac.fpl.util.doc4444;

import java.io.*;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;

public class Doc4444
{
    //private static Doc4444Parser alrParser = new Doc4444ALRParser();
    //private static Doc4444Parser rcfParser = new Doc4444RCFParser();
    private static Doc4444Parser fplParser = new Doc4444FPLParser();
    private static Doc4444Parser chgParser = new Doc4444CHGParser();
    private static Doc4444Parser dlaParser = new Doc4444DLAParser();
    private static Doc4444Parser depParser = new Doc4444DEPParser();
    private static Doc4444Parser cnlParser = new Doc4444CNLParser();
    private static Doc4444Parser arrParser = new Doc4444ARRParser();
    //private static Doc4444Parser cplParser = new Doc4444CPLParser();
    private static Doc4444Parser estParser = new Doc4444ESTParser();
    //private static Doc4444Parser cdnParser = new Doc4444CDNParser();
    //private static Doc4444Parser acpParser = new Doc4444ACPParser();
    //private static Doc4444Parser lamParser = new Doc4444LAMParser();
    //private static Doc4444Parser rqpParser = new Doc4444RQPParser();
    //private static Doc4444Parser rqsParser = new Doc4444RQSParser();
    //private static Doc4444Parser splParser = new Doc4444SPLParser();

    private Doc4444()
    {}

    public static Doc4444Parser instance(String prefix)
    {
        if(prefix == null) return null;
        else if(prefix.trim().equalsIgnoreCase("FPL")) return fplParser;
        else if(prefix.trim().equalsIgnoreCase("DEP")) return depParser;
        else if(prefix.trim().equalsIgnoreCase("ARR")) return arrParser;
        else if(prefix.trim().equalsIgnoreCase("CNL")) return cnlParser;
        else if(prefix.trim().equalsIgnoreCase("EST")) return estParser;
        else if(prefix.trim().equalsIgnoreCase("CHG")) return chgParser;
        else if(prefix.trim().equalsIgnoreCase("DLA")) return dlaParser;
        else return null;
    }

    // Aircraft Identification and Mode A Code

    public static Map<String,String> type7(String msg, Map<String, String> map)
    {
        //System.out.println("TYPE 7: " + msg);

        if(msg == null || msg.length() == 0) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);

        String[] s = msg.split("/");
        if(s.length > 0)
        {
            map.put("ACID", s[0]);

            if(s.length == 2)
            {
                if(s[1].length() > 1)
                {
                    map.put("SSR MODE", s[1].substring(0, 1));
                    map.put("SSR CODE", s[1].substring(1));
                }
            }
        }

        return map;
    }

    // Flight Rules and Type of Flight

    public static Map<String,String> type8(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);

        if(msg.length() == 1) map.put("RULES", msg);
        else if(msg.length() == 2)
        {
            map.put("RULES", msg.substring(0,1));
            map.put("TYPEF", msg.substring(1));
        }

        return map;
    }

	// DOF
	// DOF/180514
	public static Map<String,String> dof(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();

        String[] str = msg.split("/");
		map.put("DOF", str[1]);
		
        return map;
    }
    
    // Number and Type of Aircraft and Wake Turbulance Category

    public static Map<String,String> type9(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);

        String[] str = msg.split("/");
        String s = str[0];

        int idx = 0;

        for(idx=0; idx<s.length(); idx++)
        {
            char c = s.charAt(idx);
            if(c < 48 || c > 57) break;
        }

        if(idx > 0)
        {
            map.put("NUM", s.substring(0, idx));
            map.put("TYPE", s.substring(idx));
        }
        else map.put("TYPE", s);

        if(str.length == 2) map.put("WTC", str[1]);

        return map;
    }

    // Equipment

    public static Map<String,String> type10(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);

        String[] str = msg.split("/");

        if(str.length > 0) map.put("EQUIP", str[0]);

        if(str.length == 2) map.put("SUR", str[1]);

        return map;
    }

    // Departure Aerodrome and Time

    public static Map<String,String> type13(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);

        map.put("DEP", msg.substring(0, 4));
        map.put("EOBT", msg.substring(4));

        return map;
    }

    // Estimate data

    public static Map<String, String> type14(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        String[] str = msg.split("/");

        if(str == null) return map;

        if(str.length > 0)
        {
            map.put("BND", str[0]);

            if(str.length == 2)
            {
                map.put("BNDTIME", str[1].substring(0, 4));
                if(str[1].length() == 4) return map;

                String s = str[1].substring(4);

                int idx = 0;

                if(s.charAt(0) == 'F' || s.charAt(0) == 'A') idx = 3;
                else if(s.charAt(0) == 'S' || s.charAt(0) == 'M') idx = 4;

                map.put("CLRLVL", s.substring(1, idx));

                s = s.substring(idx);

                if(s.charAt(s.length()-1) == 'A' || s.charAt(s.length()-1) == 'B')
                {
                    map.put("SUPDATA", s.substring(idx, s.length()-1));
                    map.put("CRSCOND", String.valueOf(s.charAt(s.length()-1)));
                }
                else map.put("SUPDATA", s);
            }
        }

        return map;
    }

    // Route

    public static Map<String,String> type15(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);

        map.put("ROUTE", msg);

        return map;
    }

    // Destination Aerodrome and Total Estimated Elapsed Time, Alternate Aerodrome(s)

    public static Map<String,String> type16(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);

        int idx = msg.indexOf(' ');

        if(idx == -1)
        {
            map.put("ARR", msg.substring(0, 4));
            map.put("ARRET", msg.substring(4));
        }
        else
        {
            String s = msg.substring(0, idx);
            map.put("ARR", s.substring(0, 4));
            map.put("ARRET", s.substring(4));
            map.put("ALT", msg.substring(idx));
        }

        return map;
    }

    // Arrival aerodrome and time
    public static Map<String,String> type17(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();

        map.put("ARR", msg.substring(0, 4));
        map.put("ARRTM", msg.substring(4, 8));

        int idx = msg.indexOf(' ');
        if(idx != -1) map.put("ARRAD", msg.substring(idx+1));

        return map;
    }

    // Other Information
    public static Map<String,String> type18(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);

        map.put("OTHER", msg);

        return map;
    }

    // Supplementary Information
    public static Map<String,String> type19(String msg, Map<String, String> map)
    {
        if(msg == null) return map;

        msg = msg.trim().toUpperCase();
        //msg = msg.substring(1);
        String other = map.get("OTHER");

        if(other != null) other = other + " " + msg;

        map.put("OTHER", other);

        return map;
    }
}
