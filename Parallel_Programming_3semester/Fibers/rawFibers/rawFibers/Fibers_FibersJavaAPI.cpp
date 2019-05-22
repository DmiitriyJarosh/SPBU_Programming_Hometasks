#include "stdafx.h"
#include <iostream>
#include <jni.h>

#include <windows.h>
#include "Fibers_FibersJavaAPI.h"


JNIEXPORT jint JNICALL Java_Fibers_FibersJavaAPI_convertThreadToFiber
        (JNIEnv *, jclass, jint lpParam) {
    LPVOID tmp = ConvertThreadToFiber(reinterpret_cast<LPVOID>(lpParam));
	return (jint)(tmp);
}

JNIEXPORT void JNICALL Java_Fibers_FibersJavaAPI_switchToFiber
        (JNIEnv *, jclass, jint lpParam) {
    SwitchToFiber(reinterpret_cast<LPVOID>(lpParam));
}

JNIEXPORT void JNICALL Java_Fibers_FibersJavaAPI_deleteFiber
        (JNIEnv *, jclass, jint lpParam) {
//    std::cout << "DeleteFiber works!" << std::endl;

    DeleteFiber(reinterpret_cast<LPVOID>(lpParam));
}

JavaVM *jvm;

VOID WINAPI callback(LPVOID lpParam) {
//    std::cout << "Callback works!" << std::endl;

    jobject delegate = static_cast<jobject>(GetFiberData());

    JNIEnv *env;
    JavaVMAttachArgs args;
    args.version = JNI_VERSION_1_8;
    args.name = NULL;
    args.group = NULL;
    if (jvm->AttachCurrentThread((void **) &env, &args) != 0) {
        std::cerr << "!!! FIBERS DLL ERROR: Failed to attach to the thread !!!" << std::endl;
	return;
    }

    jclass delegateClass = env->GetObjectClass(delegate);
    jmethodID methodId = env->GetMethodID(delegateClass, "start", "(I)I");

    env->CallIntMethod(delegate, methodId);

    env->DeleteGlobalRef(delegate);
}

JNIEXPORT jint JNICALL Java_Fibers_FibersJavaAPI_createFiber
        (JNIEnv *env, jclass, jint stackSize, jobject delegate, jint lpParam) {
    env->GetJavaVM(&jvm);
    jobject globalDelegate = env->NewGlobalRef(delegate);
    LPVOID tmp = CreateFiber(static_cast<SIZE_T>(stackSize), callback, reinterpret_cast<LPVOID>(globalDelegate));
	return (jint)(tmp);
}
