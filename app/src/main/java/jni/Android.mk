LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
#bzlib模块
bzlib_files := \
	main.c

LOCAL_MODULE := libbz
LOCAL_SRC_FILES := $(bzlib_files)
include $(BUILD_STATIC_LIBRARY)

#bspath模块
include $(CLEAR_VARS)
LOCAL_MODULE    := main
LOCAL_SRC_FILES := main.c
LOCAL_STATIC_LIBRARIES := libbz #引入libbz库

include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := JniTest
LOCAL_SRC_FILES := main.c
LOCAL_STATIC_LIBRARIES := main
LOCAL_LDLIBS := -llog#加入log

include $(BUILD_SHARED_LIBRARY)