package com.yt.androidtv.testjni;

/**
 * Created by zyk on 2016/3/23.
 */
public class StringKit {

    static {
        System.loadLibrary("JniDemo");
    }

    public static native void setNull(String str);

}
