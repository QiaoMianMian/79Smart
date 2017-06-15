package com.smartjewelry.blue105;

public class DataSet {

    //*****************************Sleep****************************************
    public static byte[] setData_26(int index) {
        byte[] bytes = new byte[]{
                (byte) 0x5b, (byte) 0x26, (byte) 0x00, (byte) index, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        return bytes;
    }

    public static byte[] setData_27_0() {
        byte[] bytes = new byte[]{
                (byte) 0x5b, (byte) 0x27, (byte) 0x00, (byte) 0x00, (byte) 0x10,
                (byte) 0x0b, (byte) 0x08, (byte) 0x14, (byte) 0x1e, (byte) 0x10,
                (byte) 0x0b, (byte) 0x09, (byte) 0x08, (byte) 0x0a, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        return bytes;
    }

    public static byte[] setData_27_1() {
        byte[] bytes = new byte[]{
                (byte) 0x5b, (byte) 0x27, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                (byte) 0x2c, (byte) 0x00, (byte) 0x64, (byte) 0x00, (byte) 0x96,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        return bytes;
    }

    //****************************Step*****************************************

    public static byte[] setData_08(int index) {
        byte[] bytes = new byte[]{
                (byte) 0x5b, (byte) 0x09, (byte) 0x00, (byte) index, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        return bytes;
    }

    public static byte[] setData_09_0() {
        byte[] bytes = new byte[]{
                (byte) 0x5b, (byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x10,
                (byte) 0x0b, (byte) 0x08, (byte) 0x14, (byte) 0x00, (byte) 0x00,
                (byte) 0x0a, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x0F, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        return bytes;
    }

    public static byte[] setData_09_1() {
        byte[] bytes = new byte[]{
                (byte) 0x5b, (byte) 0x0a, (byte) 0x01, (byte) 0x01, (byte) 0x00,
                (byte) 0x11, (byte) 0x00, (byte) 0x22, (byte) 0x00, (byte) 0x33,
                (byte) 0x00, (byte) 0x44, (byte) 0x00, (byte) 0x55, (byte) 0x00,
                (byte) 0x66, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        return bytes;
    }

    public static byte[] setData_09_2() {
        byte[] bytes = new byte[]{
                (byte) 0x5b, (byte) 0x0a, (byte) 0x02, (byte) 0x02, (byte) 0x00,
                (byte) 0x11, (byte) 0x00, (byte) 0x22, (byte) 0x00, (byte) 0x33,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        return bytes;
    }

}
