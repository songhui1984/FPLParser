package net.styx.fpl.util.doc4444;

import java.util.*;
import org.apache.log4j.Logger;

public class Doc4444CNLParser implements Doc4444Parser
{
	// (CNL-FIXM03-KEWR0145-VIDP-DOF/160130)
	
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

        map = Doc4444.type7(str[1], map); // ACID, SSR Mode, SSR Code
        map = Doc4444.type13(str[2], map); // Departure
        map = Doc4444.type16(str[3], map); // Destination
		map = Doc4444.dof(str[4], map);
		
        return map;
    }
}
