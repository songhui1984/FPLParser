package com.kac.fpl.util.doc4444;

import java.util.*;
import org.apache.log4j.Logger;

public class Doc4444FPLParser implements Doc4444Parser
{
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

        map = Doc4444.type7(str[1], map);
        map = Doc4444.type8(str[2], map);
        map = Doc4444.type9(str[3], map);
        map = Doc4444.type10(str[4], map);
        map = Doc4444.type13(str[5], map);
        map = Doc4444.type15(str[6], map);
        map = Doc4444.type16(str[7], map);
        map = Doc4444.type18(str[8], map);

        if(len == 10) map = Doc4444.type19(str[9], map);
        
        return map;
    }
}
