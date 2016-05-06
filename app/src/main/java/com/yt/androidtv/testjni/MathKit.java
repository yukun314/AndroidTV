package com.yt.androidtv.testjni;

/**
 * Created by zyk on 2016/3/23.
 */
public class MathKit {

    static{
        System.loadLibrary("JniDemo");
    }

    public static native int square(int num);
}
