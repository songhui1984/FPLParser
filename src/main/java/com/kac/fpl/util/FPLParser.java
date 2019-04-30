package com.kac.fpl.util;

import java.io.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;
import org.apache.log4j.Logger;
import com.kac.fpl.util.doc4444.*;

public class FPLParser
{
    private Logger log = Logger.getLogger(FPLParser.class);
    //private String[] TYPES = {"(ALR", "(RCF", "(FPL", "(CHG", "(CNL", "(DLA", "(DEP", "(ARR", "(CPL", "(EST", "(CDN", "(ACP", "(LAM", "(RQP", "(RQS", "(SPL"};
    //private List<String> TYPES_PREFIX;
    private String regex = "^\\((ALR|RCF|FPL|CHG|CNL|DLA|DEP|ARR|CPL|EST|CDN|ACP|LAM|RQP|RQS|SPL).*\\)$";
    private Pattern pattern = null;

    public FPLParser()
    {
        //TYPES_PREFIX = Arrays.asList(TYPES);
        pattern = Pattern.compile(regex);
    }

    public Map<String, String> parse(String fpl)
    {
        Map<String, String> map = new HashMap<String, String>();

        if(fpl == null || !validateFPL(fpl))
        {
            map.put("ERR", "THIS IS NOT Doc.4444 compliant message, maybe HeartBeat");
            return map;
        }

        fpl = fpl.trim().toUpperCase();
        //log.info(fpl);
        int start = fpl.indexOf("(");
        int end = fpl.indexOf(")");

        if(start == -1 || end == -1)
        {
            map.put("ERR", "THIS IS NOT Doc.4444 compliant message, maybe HeartBeat");
            return map;
        }

        fpl = fpl.substring(start, end+1);

        String prefix = fpl.substring(1, 4);
        //System.out.println("Prefix : " + prefix);
        Doc4444Parser parser = Doc4444.instance(prefix);

        if(parser != null)  map = parser.parse(fpl, prefix);

        return map;
    }

    public String getMsgType(String fpl)
    {
        try
        {
            if(!validateFPL(fpl)) return null;

            int start = fpl.indexOf("(");
            int end = fpl.indexOf(")");

            if(start == -1 || end == -1) return null;
            fpl = fpl.substring(start, end+1);

            return fpl.substring(1, 4);
        }
        catch(Exception e)
        {
        }

        return null;
    }

    public boolean validateFPL(String fpl)
    {
        if(fpl == null) return false;

        fpl = fpl.trim().toUpperCase();

        Matcher matcher = pattern.matcher(fpl);

		if(matcher.find())
		{
		    //System.out.println("Matched .. ");
		    return true;
		}
		else
		{
		    //System.out.println("Not Matched .. ");
		    return false;
		}
    }
}
