package com.smart.library.blue;

import java.util.ArrayList;


public class BleString {

    /**
     * bytes to String
     */
    public static String bytes2String(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length);
        String temp;
        for (byte element : bytes) {
            temp = String.valueOf(0xFF & element);
            sb.append("[");
            if (temp.length() < 2) {
                sb.append(0);
            }
            sb.append(temp);
            sb.append("] ");
        }
        return sb.toString();
    }

    /**
     * Bytes Check
     */
    public static int bytesCheckAnd(byte[] bytes) {
        int count = 0;
        for (byte element : bytes) {
            count += Integer.parseInt(String.valueOf(0xFF & element));
        }
        return count;
    }

    /**
     * Check
     */
    public static boolean isCheckAnd(ArrayList<Integer> data) {
        if (data != null && data.size() > 19) {
            int checkCount = shift(data.get(18), data.get(19));
            int checkAnd = BleString.intCheckAnd(data);
            BleLogs.i("checkCount:" + checkCount + ",checkAnd" + checkAnd);
            return (checkCount == checkAnd);
        }
        return false;
    }

    /**
     * Int Check
     */
    public static int intCheckAnd(ArrayList<Integer> datas) {
        int count = 0;
        for (int i = 0; i < 18; i++) {
            count += datas.get(i);
        }
        return count;
    }

    /**
     * Bytes to HexString
     */
    public static final String bytes2HexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(0xFF & bytes[i]);
            sb.append("[");
            if (temp.length() < 2) {
                sb.append(0);
            }
            sb.append(temp.toUpperCase());
            sb.append("] ");
        }
        return sb.toString();
    }

    /**
     * int to Ascii
     */
    public static char Integer2Ascii(int a) {
        return (a >= 0 && a <= 255) ? (char) a : '\0';
    }

    /**
     * Shift Operation
     */
    public static int shift(int h, int l) {
        if (h >= 255) h = 0;
        return h * 256 + l;
    }

}
