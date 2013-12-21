
#include <string.h>
#include <jni.h>
#include "com_standopen_test_Data.h"
#include <stdio.h>
#include <android/log.h>

#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)


	void JNICALL Java_com_standopen_test_Data_callhellofromjava
	  (JNIEnv *env, jobject obj)
		{

		char * classname = "com/standopen/test/Data";
		jclass clazz;
		clazz = (*env)->FindClass(env,classname);
		if(clazz == 0)
		{
			LOGI("can not find clazz");
		}
		else
		{
			LOGI("find clazz");
		}

		jmethodID java_method = (*env)->GetMethodID(env,clazz,"hellofromc","()V");
		if(java_method == 0)
		{
			LOGI("can not find method");


		}
		else
		{
			LOGI("find method");
		}

		(*env)->CallVoidMethod(env,obj,java_method);

		}


	void JNICALL Java_com_standopen_test_Data_calladd
	  (JNIEnv *env, jobject obj)
		{

		char * classname = "com/standopen/test/Data";
			jclass clazz;
			clazz = (*env)->FindClass(env,classname);
			if(clazz == 0)
			{
				LOGI("can not find clazz");
			}
			else
			{
				LOGI("find clazz");
			}

			jmethodID java_method = (*env)->GetMethodID(env,clazz,"add","(II)I");
			if(java_method == 0)
			{
				LOGI("can not find method");


			}
			else
			{
				LOGI("find method");
			}

			(*env)->CallIntMethod(env,obj,java_method,2,3);
		}


	void JNICALL Java_com_standopen_test_Data_callprintstring
	  (JNIEnv *env, jobject obj)
	{

		char * classname = "com/standopen/test/Data";
			jclass clazz;
			clazz = (*env)->FindClass(env,classname);
			if(clazz == 0)
			{
				LOGI("can not find clazz");
			}
			else
			{
				LOGI("find clazz");
			}

			jmethodID java_method = (*env)->GetMethodID(env,clazz,"printstring","(Ljava/lang/String;)V");
			if(java_method == 0)
			{
				LOGI("can not find method");


			}
			else
			{
				LOGI("find method");
			}

			(*env)->CallVoidMethod(env,obj,java_method,(*env)->NewStringUTF(env,"hello from c"));
	}

