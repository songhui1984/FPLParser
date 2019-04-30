package net.styx.fpl.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.*;

public class AppTest extends TestCase
{
    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp1()
    {
    	System.out.println("Hello test 1");
        assertTrue( true );
    }
    
    public void testCHG()
    {
    	String msg = "(CHG-ESR7202-RJBB0330-RKTU-DOF/180814-7/ESR7202/A3233-10/SWYDFGHIR/SB1-15/N0436F320 XZE G585 SAPRA Y685 BASEM DCT OSPOT-18/PBN/A1B1C1D1O1S1S2 DOF/180814 REG/HL8022 EET/RKRR0038 SEL/BLGSRMK/TCAS II EQUIPPED)";
    	FPLParser parser = new FPLParser();
    	
    	Map<String,String> map = parser.parse(msg);
    	System.out.println("Size of Map : " + map.size());
    	
    	map.forEach((k,v)-> System.out.println(k + " : " + v));
    }
    
    public void testCHG2()
    {
    	String msg = " (CHG-CQH8559-ZSPD0445-RKSI-DOF/180814-10/SWYRIGDE3FHM3/LB1-15/N0460F270 LAMEN A593 NIRAT Y722 OLMEN-18/PBN/A1B2B3B4C1D1O1S2 DOF/180814 REG/B8435 EET/RKRR0025 SEL/QRGLCODE/780D4B RMK/TCAS ADSB AND FOREIGN PILOT ON BOARD)";
    	
    	FPLParser parser = new FPLParser();
    	
    	Map<String,String> map = parser.parse(msg);
    	System.out.println("Size of Map : " + map.size());
    	
    	map.forEach((k,v)-> System.out.println(k + " : " + v));
    }
}
