package com.smart.library.blue;


public interface BleImp {
    void onSuccess(BleCode code, Object result);

    void onFail(String error);
}
