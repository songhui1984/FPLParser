package net.styx.fpl.util.common;

import java.io.*;
import java.awt.font.*;
import java.util.*;
import java.text.*;
import java.beans.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class Utils
{
    public static String makeDate(String format)
    {
    	SimpleDateFormat sf = new SimpleDateFormat();
    	try
    	{
    		sf.applyPattern(format);
    	}
    	catch(Exception e)
    	{
    		return "";
    	}

    	return sf.format(new Date());
    }

    public static double[] convert2double(String s)
	{
		// 3626N12722E011 or 364028N1261023E

		if(s == null) return null;
		String str = s.trim();
		int len = str.length();
		int indexN = str.indexOf('N');
		int indexE = str.indexOf('E');

		if(indexN == -1 || indexE == -1) return null;

		String north = str.substring(0, indexN);
		String east = str.substring(indexN+1, indexE);
		double diameter;

		if(indexE == len - 1) diameter = 0;
		else                  diameter = Double.parseDouble(str.substring(indexE+1));

		//System.out.println(north + ": " + east + ": " + diameter);
		double lat, lon;

		if(north.length() == 4)      lat = convert(north.trim().substring(0, 2), north.trim().substring(2), "00");
		else if(north.length() == 6) lat = convert(north.trim().substring(0, 2), north.trim().substring(2, 4), north.trim().substring(4));
		else return null;

		if(east.length() == 5)      lon = convert(east.trim().substring(0, 3), east.trim().substring(3), "00");
		else if(east.length() == 7) lon = convert(east.trim().substring(0, 3), east.trim().substring(3, 5), east.trim().substring(5));
		else return null;

        double[] d = new double[3];
        d[0] = lat;
        d[1] = lon;
        d[2] = diameter;

        return d;
        //return new Coord(lat, lon, diameter);

	}

	public static double convert(String str)
	{
		// 372745N or 1262621E or 0931758.55W

		if(str == null) return -1;
		str = str.trim();

		if(str.endsWith("N"))
		{
		    int idx = str.indexOf('N');
		    String coord = str.substring(0, idx);

    		if(coord.length() == 4)      return convert(coord.trim().substring(0, 2), coord.trim().substring(2), "00");
    		else if(coord.length() >= 6) return convert(coord.trim().substring(0, 2), coord.trim().substring(2, 4), coord.trim().substring(4, 6));
    		else return -1;
        }
		else if(str.endsWith("E"))
		{
		    int idx = str.indexOf('E');
		    String coord = str.substring(0, idx);

    		if(coord.length() == 5)      return convert(coord.trim().substring(0, 3), coord.trim().substring(3), "00");
    		else if(coord.length() >= 7)  return convert(coord.trim().substring(0, 3), coord.trim().substring(3, 5), coord.trim().substring(5, 7));
    		else return -1;

		}
		else if(str.endsWith("W"))
		{
		    int idx = str.indexOf('W');
		    String coord = str.substring(0, idx);
    		double d;

    		if(coord.length() == 5)      d = convert(coord.trim().substring(0, 3), coord.trim().substring(3), "00");
    		else if(coord.length() >= 7)  d = convert(coord.trim().substring(0, 3), coord.trim().substring(3, 5), coord.trim().substring(5, 7));
    		else return -1;

		    d = 180 - d + 180;

		    return d;
		}

		return -1;

	}

	public static double convert(String s1, String s2, String s3)
	{
	    if(s1 == null || s2 == null || s2 == null) return -999.0;

        try
        {
    		return Double.parseDouble(s1) + Double.parseDouble(s2) / 60.0 + Double.parseDouble(s3)/3600.0;
    	}
    	catch(Exception e)
    	{
    	    e.printStackTrace();
    	    return -999.0;
    	}
	}

	public static String padLeft(String s, int n)
	{
	    if(n == 0) return s;

	    StringBuffer sb = new StringBuffer();
	    for(int i=0; i<n; i++) sb.append(" ");

	    sb.append(s);
	    return sb.toString();
    }

    public static String padZero(String s)
	{
	    if(s == null) return "0000";

	    s = s.trim();
	    int len = s.length();

	    if(len == 4) return s;

	    int howmuch = 4 - len;

	    StringBuffer sb = new StringBuffer();
	    for(int i=0; i<howmuch; i++) sb.append("0");

	    sb.append(s);
	    return sb.toString();
    }

    public static int findMax(int x, int y, int z, int a)
    {
        int[] arr = new int[4];

        arr[0] = x;
        arr[1] = y;
        arr[2] = z;
        arr[3] = a;

        Arrays.sort(arr);

        return arr[arr.length -1 ];
    }

    public static String byteArrayToString(byte[] b)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for(int i=0; i<b.length; i++)
        {
            sb.append(String.format("%02x", b[i]&0xff));
        }

        return sb.toString();
    }

    public static int unsignedToInt(byte[] data)
    {
        int val = 0;
        int len = data.length;

        if(len > 4) return -1;

        for(int i=0; i<len; i++)
        {
            val = val << 8;
            val = val | (data[i] & 0xff);
        }

        return val;
    }

    public static int signedToInt(byte[] data)
    {
        int len = data.length;
        int val = 0;

        for(int i=0; i<len; i++)
        {
            int shift = (len - 1 -i) * 8;

            if(data[i] < 0 && i == 0)
            {
                val += (data[i] | 0xffffff00) << shift;
            }
            else
                val += (data[i] & 0x000000ff) << shift;
        }
        return val;
    }

    public static byte[] int2ByteArray(int value)
    {
		byte[] byteArray = new byte[4];

		byteArray[0] = (byte)(value >> 24);
		byteArray[1] = (byte)(value >> 16);
		byteArray[2] = (byte)(value >> 8);
		byteArray[3] = (byte)(value);

		return byteArray;
	}

	public static String isNull(String s)
    {
        if(s == null) return "";
        else return s.trim();
    }
}
