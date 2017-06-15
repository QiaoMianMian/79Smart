package com.smart.library.blue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BleReply {

    public static Map dealReplyData(byte[] bytes) {
        Map map = new HashMap();
        String action = "";
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (bytes != null && bytes.length > 0) {
            for (int i = 0; i < bytes.length; i++) {
                byte data = bytes[i];
                String str16 = Integer.toHexString(data);
                if (str16.startsWith("ffffff")) {
                    str16 = str16.substring(6);
                }
                int value = Integer.parseInt(str16, 16);
                if (i == 1) {
                    action = "ACTION" + str16;
                }
                list.add(value);
            }
        }
        map.put("data", list);
        map.put("action", action);
        return map;
    }
}
