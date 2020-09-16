package com.edawtech.jiayou.functions;

/**
 * Created by Jiangxuewu on 2015/2/3.
 */
public class HttpManager extends BaseHttp {

    private static final String TAG = HttpManager.class.getSimpleName();

    private static HttpManager mInstance = null;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        synchronized (TAG) {
            if (mInstance == null) {
                mInstance = new HttpManager();
            }
            return mInstance;
        }
    }

}
