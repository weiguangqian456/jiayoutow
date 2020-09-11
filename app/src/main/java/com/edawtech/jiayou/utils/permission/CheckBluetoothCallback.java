package com.edawtech.jiayou.utils.permission;

public interface CheckBluetoothCallback {

    /**
     * 权限开启失败。
     */
    void openAccessFailure(boolean permission);

    /**
     * 权限开启成功。
     */
    void openAccessSuccessful(boolean permission);

}
