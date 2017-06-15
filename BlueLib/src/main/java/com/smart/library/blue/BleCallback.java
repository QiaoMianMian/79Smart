package com.smart.library.blue;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;

import com.smart.library.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class BleCallback extends BluetoothGattCallback {

    private Context context;

    public BleCallback(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            BleLogs.i("Discover GattService:STATE_DISCONNECTED");
            BleContants.STATE_BLE_CONNECTED = false;
            Intent intent = new Intent();
            intent.setAction(BleContants.ACTION_BLE_DISCONNECT);
            context.sendBroadcast(intent);
        } else if (newState == BluetoothProfile.STATE_CONNECTING) {
            BleLogs.i("Discover GattService:STATE_CONNECTING");
        } else if (newState == BluetoothProfile.STATE_CONNECTED) {
            BleLogs.i("Discover GattService:STATE_CONNECTED");
            gatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {
            BleLogs.i("Discover GattService:STATE_DISCONNECTING");
            Intent intent = new Intent();
            intent.setAction(BleContants.ACTION_BLE_DISCONNECTING);
            context.sendBroadcast(intent);
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        BleLogs.d("onServicesDiscovered-->status:" + status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            enbleNotification(gatt);
        } else {
            BleLogs.e("onServicesDiscovered: GATT_FAILURE!");
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            byte[] data = characteristic.getValue();
            if (data != null) {
                BleLogs.d(BleString.bytes2HexString(data));
            } else {
                BleLogs.e("onCharacteristicRead: data == null !");
            }
        } else {
            BleLogs.e("onCharacteristicRead: GATT_FAILURE!");
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        byte[] data = characteristic.getValue();
        BleLogs.d("Write:" + BleString.bytes2HexString(data));
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        byte[] bytes = characteristic.getValue();
        BleLogs.d("Reply:" + BleString.bytes2HexString(bytes));
        if (bytes != null) {
            Map map = BleReply.dealReplyData(bytes);
            String action = (String) map.get("action");
            ArrayList<Integer> data = (ArrayList<Integer>) map.get("data");
            Intent intent = new Intent(action);
            intent.putIntegerArrayListExtra("data", data);
            context.sendBroadcast(intent);
        }
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorRead(gatt, descriptor, status);
        BleLogs.d("onDescriptorRead-->status:" + status);
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        BleLogs.d("onDescriptorWrite-->status:" + status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            BleContants.STATE_BLE_CONNECTED = true;

            Intent intent = new Intent();
            intent.setAction(BleContants.ACTION_BLE_CONNECTED);
            context.sendBroadcast(intent);

            BleSend.getInstance().sendSetDeviceTime(context, DateUtils.date2SecondString(new Date()));
        }
    }

    @Override
    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        super.onReliableWriteCompleted(gatt, status);
        BleLogs.d("onReliableWriteCompleted-->status:" + status);
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
        BleLogs.d("onReadRemoteRssi-->status:" + status);
    }

    @Override
    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        super.onMtuChanged(gatt, mtu, status);
        BleLogs.d("onMtuChanged-->status:" + status);
    }

    /**
     * Enble Notification
     */
    public boolean enbleNotification(BluetoothGatt gatt) {
        if (gatt == null) {
            return false;
        }
        BluetoothGattService bleGattService = gatt.getService(BleContants.UUID_NUS_SERVICE);
        if (bleGattService == null) {
            return false;
        }
        BluetoothGattCharacteristic character = bleGattService.getCharacteristic(BleContants.UUID_NUS_NOTIFY);
        if (character == null) {
            return false;
        }
        return setCharacteristicNotification(gatt, character, true);
    }

    /**
     * Write Descriptor
     */
    public boolean setCharacteristicNotification(BluetoothGatt gatt, BluetoothGattCharacteristic character, boolean enabled) {
        if (gatt == null) {
            return false;
        }
        gatt.setCharacteristicNotification(character, enabled);
        if (BleContants.UUID_NUS_NOTIFY.equals(character.getUuid())) {
            BluetoothGattDescriptor descriptor = character.getDescriptor(BleContants.UUID_NUS_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            return gatt.writeDescriptor(descriptor);
        }
        return false;
    }
}
