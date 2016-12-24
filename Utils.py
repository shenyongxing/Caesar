#! /usr/bin/python
# 工具类

import os
import time 
import smtplib
import ssl 
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

class Utils:

    @staticmethod
    def exist(path):
        return os.path.exists(path)

    @staticmethod
    def callCmd(linuxCmd):
        os.system(linuxCmd)

    @staticmethod
    def callCmdEx(dir, linuxCmd):
        if dir != None:
            os.chdir(dir)
        return os.system(linuxCmd)

    @staticmethod
    def readFile(path):
        if os.path.exists(path):
            f = open(path, "r")
            content = f.readlines()
            f.close()
            return content
        else:
            print(path + " is not exist")

    @staticmethod
    def wirteFile(path, content):
        Utils.existDirAndMake(path)
        file = open(path, "w")
        file.write(content)
        file.close()

    @staticmethod
    def existDirAndMake(path):
        if not os.path.exists(os.path.dirname(path)):
            os.makedirs(os.path.dirname(path))

    @staticmethod 
    def sendEmail(fromAddr, toAddrs, subject, msg):
        # 创建一个带附件的实例
        att = MIMEMultipart()
        # 发送内容
        content = MIMEText(msg, "html", "utf-8")
        att.attach(content)

        att['to'] = toAddrs
        att['from'] = fromAddr
        # 设置主题
        att['subject'] = subject  
        
        server = smtplib.SMTP('smtp.gmail.com', 587) # google email service port is 587
        server.ehlo()
        server.set_debuglevel(1)
        server.starttls()
        server.login("shenxing583@gmail.com", "some reason lead to your username and password was not accecpted")
        server.sendmail(fromAddr, toAddrs, msg)
        server.quit()
        pass

    @staticmethod
    def getCurrentTime():
        return time.strftime("%Y-%m-%d %H:%M:%S", time.localtime)

