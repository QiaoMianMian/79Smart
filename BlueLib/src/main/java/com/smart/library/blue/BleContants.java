package com.smart.library.blue;

import java.util.UUID;

public class BleContants {

    public static boolean STATE_BLE_CONNECTED = false;  //Connected State

    public static String BLE_CONNECTED_ADDRESS;//Device Address

    public static String BLE_CONNECTED_NAME;//Device Name

    /**
     * NUS
     */
    public final static UUID UUID_NUS_SERVICE = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public final static UUID UUID_NUS_CHARACTER = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    public final static UUID UUID_NUS_NOTIFY = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    public final static UUID UUID_NUS_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    /**
     * CONNECTED
     */
    public static final String ACTION_BLE_CONNECTED = "action.ble.connected";

    /**
     * DISCONNECTING
     */
    public static final String ACTION_BLE_DISCONNECTING = "action.ble.disconnecting";

    /**
     * DISCONNECT
     */
    public static final String ACTION_BLE_DISCONNECT = "action.ble.disconnect";

    /**
     * DEVICE TYPE
     */
    public static final String ACTION_BLE_DEVICE_TYPE = "ACTION2";

    /**
     * DEVICE CODE
     */
    public static final String ACTION_BLE_DEVICE_CODE = "ACTION3";

    /**
     * CURRENT STEPS
     */
    public static final String ACTION_BLE_CURRENT_STEPS = "ACTION6";

    /**
     * HISTORY STEPS INDEX
     */
    public static final String ACTION_BLE_HISTORY_STEPS_INDEX = "ACTION9";

    /**
     * HISTORY STEPS DATA
     */
    public static final String ACTION_BLE_HISTORY_STEPS_DATA = "ACTIONa";

    /**
     * HISTORY SLEEP INDEX
     */
    public static final String ACTION_BLE_HISTORY_SLEEPSUM_INDEX = "ACTION26";

    /**
     * HISTORY SLEEP DATA
     */
    public static final String ACTION_BLE_HISTORY_SLEEPSUM_DATA = "ACTION27";

    /**
     * Current Sleep
     */
    public static final String ACTION_BLE_CURRENT_SLEEP = "ACTION28";

    /**
     * DEVICE VERSION
     */
    public static final String ACTION_BLE_VERSION = "ACTION14";

    /**
     * DEVICE BATTERY
     */
    public static final String ACTION_BLE_BATTERY = "ACTION15";

    /**
     * FLASH CLEAR
     */
    public static final String ACTION_BLE_FLASH_CLEAR = "ACTION1b";

}
