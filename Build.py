#! /usr/bin/python

from Utils import *

class Build:
    def build():
        gradle build
        targetApkPath = "app/build/outputs/"
        oldname = targetApkPath + "app-debug.apk"
        time = Utils.getCurrentTime()
        newname = targetApkPath + "app_" + time + "_debug.apk"
        os.renames(oldname, newname)
        
