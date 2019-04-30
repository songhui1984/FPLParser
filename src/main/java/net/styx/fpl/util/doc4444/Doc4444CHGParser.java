package net.styx.fpl.util.doc4444;

import java.util.*;
import org.apache.log4j.Logger;

public class Doc4444CHGParser implements Doc4444Parser
{
    /*
    (CHG-FIXM01-LEMD0745-LEMG-DOF/160130
    -15/N0440F310 VTB UN10 CRISA UN864 VULPE
    -16/LEMG0050 LEZL LEJR
    -18/PBN/B3B4B5D3D4 DOF/160130 REG/ABC123 SEL/LREK OPR/ABC
    ORGN/ABC123 RVR/075 SUR/TCAS EQUIPPED RMK/RADIO CALLSIGN
    ABCABCABC RMK/PERMIT NUMBER)
 */
    public Map<String, String> parse(String msg, String type)
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        if(type != null) map.put("MSGTYPE", type);

        if(msg == null) return map;

        msg = msg.trim();
        msg = msg.substring(4);
        int len = msg.length();

        msg = msg.substring(0, len-1);
        String[] str = msg.trim().split("-");
        len = str.length;
        StringBuilder sb = new StringBuilder();

        map = Doc4444.type7(str[1], map);
        map = Doc4444.type13(str[2], map);
        map = Doc4444.type16(str[3], map);
		map = Doc4444.dof(str[4], map);
		
        int idx;

        for(int i=5; i<str.length; i++)
        {
            String item = str[i];
            String itemNo;
            
            idx = item.indexOf("/");
            
            if(idx != -1) 
            {
            	itemNo = item.substring(0, idx);
            	item = item.substring(idx+1);
            }
            else continue;
                    
            if(itemNo.equalsIgnoreCase("15")) map = Doc4444.type15(item, map);
            else if(itemNo.equalsIgnoreCase("16")) map = Doc4444.type16(item, map);
            else if(itemNo.equalsIgnoreCase("18")) map = Doc4444.type18(item, map);
            else if(itemNo.equalsIgnoreCase("7")) map = Doc4444.type7(item, map);
            else if(itemNo.equalsIgnoreCase("10")) map = Doc4444.type10(item, map);
            
        }
        return map;
    }
}
