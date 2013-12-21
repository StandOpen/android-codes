#include <string.h>
#include <jni.h>
#include "com_standopen_socket_Main.h"
#include <stdio.h>
#include <android/log.h>


#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netinet/in.h>
#define BUFFSIZE 32
#define FP fprintf




#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

	void Die(char *mess)
	{
		LOGI("s%",*mess);
	  //perror(mess);
	  //exit(1);
	}


	jstring JNICALL Java_com_standopen_socket_Main_SendMsg
	  (JNIEnv *env, jobject obj)
	{





		 int sock;
		    struct sockaddr_in echoserver;
		    char buffer[BUFFSIZE];
		    unsigned int echolen;
		    int received = 0;
		    if ((sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) {
		        Die("Failed to create socket");
		    }
		    memset(&echoserver, 0, sizeof(echoserver));
		    echoserver.sin_family = AF_INET;
		    echoserver.sin_addr.s_addr = inet_addr("127.0.0.1");
		    echoserver.sin_port = htons(atoi("8899"));
		    if (connect(sock, (struct sockaddr *) &echoserver, sizeof(echoserver)) < 0) {
		        Die("Failed to connect with server");
		    }
		    echolen = strlen("hello");
		    if (send(sock, "hello", echolen, 0) != echolen) {
		        Die("Mismatch in number of sent bytes");
		    }
		    FP(stdout, "Received: ");
		    while (received < echolen) {
		        int bytes = 0;
		        if ((bytes = recv(sock, buffer, BUFFSIZE-1, 0)) < 1) {
		            Die("Failed to receive bytes from server");
		        }
		        received += bytes;
		        buffer[bytes] = '/0';
		        FP(stdout, buffer);
		    }

		    FP(stdout, "/n");
		    close(sock);
		    exit(0);







	}
