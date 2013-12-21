#include <string.h>
#include <jni.h>
#include "com_standopen_hello_MainActivity.h"
#include <stdio.h>
#include <android/log.h>

#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

/*
 * 测试jni
 * */
jstring JNICALL Java_com_standopen_hello_MainActivity_hello(JNIEnv* env,jobject thiz )
{

	LOGD("%s","hello,i am from c");
	LOGI("%s","hello,i am from c");
	return (*env)->NewStringUTF(env, "hello jni!");

}

/*
 * jni传值练习*/
jint JNICALL Java_com_standopen_hello_MainActivity_add
 (JNIEnv * env, jobject thiz, jint x, jint y)
{

	LOGI("x=%d",x);
	LOGI("y=%d",y);
	return x+y;
}


/*
 * 将java中的String类型的数据转换成c语言中的char数组
 **/

	char * JstringToCStr(JNIEnv* env,jstring jstr)
	{

		char * rtn = NULL;
		jclass clsstring = (*env)->FindClass(env,"java/lang/String");
		jstring strencode = (*env)->NewStringUTF(env,"GB2312");
		jmethodID mid = (*env)->GetMethodID(env,clsstring,"getBytes","(Ljava/lang/String;)[B");
		jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env,jstr,mid,strencode);
		jsize alen = (*env)->GetArrayLength(env,barr);
		jbyte * ba = (*env)->GetByteArrayElements(env,barr,JNI_FALSE);
		if(alen>0)
		{
			rtn = (char*)malloc(alen+1);
			memcpy(rtn,ba,alen);
			rtn[alen]=0;
		}
		(*env)->ReleaseByteArrayElements(env,barr,ba,0);
		return rtn;






	}

	/**
	 *
	 * 传字符串练习
	 *
	 * */

	jstring JNICALL Java_com_standopen_hello_MainActivity_getstr
	  (JNIEnv * env, jobject obj, jstring str)
	{
		char * p = JstringToCStr(env,str);
		LOGI("in c code=*s",p);
		char * newstr = "nihao";

		return (*env)->NewStringUTF(env,strcat(p,newstr));

	}

/*
 *
 * 传递数组练习
 *
 *
 * */
	jintArray JNICALL Java_com_standopen_hello_MainActivity_intMethod
	  (JNIEnv *env, jobject obj, jintArray arr)
	{

		int len = (*env)->GetArrayLength(env,arr);
		LOGI("len = %d",len);
		LOGI("address = %#x",&arr);
		jint * arr_int = (*env)->GetIntArrayElements(env,arr,0);
		int i = 0;
		for(;i<len;i++)
		{
			LOGI("arr[%d] = %d",i,*(arr_int+i));
			*(arr_int + i) += 10;
		}
		return arr_int;
	}

