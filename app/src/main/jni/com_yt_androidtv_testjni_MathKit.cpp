//
// Created by zyk on 2016/3/23.
//
#include <com_yt_androidtv_testjni_MathKit.h>

JNIEXPORT jint JNICALL Java_com_yt_androidtv_testjni_MathKit_square
        (JNIEnv *env, jclass cls, jint num)
{
    return num*num;
}