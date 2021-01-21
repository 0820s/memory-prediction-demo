# -*- coding: utf-8 -*-
"""
Created on Thu Jan 21 16:21:32 2021

@author: 86131
"""
import socket
import os

def login():
    print "Please input your ID (if no, please register in the website): "
    name=raw_input()
    if name=='exit':
        return False
    print "Please input your password: "
    password=raw_input()
    print ""

    client.send(("login,"+name+","+password+"\n").encode())
    szBuf = client.recv(1024)
    byt = szBuf.decode('utf-8').strip()
    if byt=="refused":
        print byt
        print "Wrong ID or password, please input again. Input 'exit' to exit."
        ret=login()
    else:
        ret=True
        
    print "Please set data transfer rate, press enter to use the default rate: (minute)"
    rate=raw_input()
    if rate==None or rate=='0' or rate.isdigit()==False:
        rate=24*60*60 #second
    else:
        rate=int(rate)*60
        
    return ret,rate


if __name__=="__main__":        
    client = socket.socket()
    client.connect(('8.134.39.104',7484))
    log_in,rate=login()

    client.send("close\n".encode('utf-8'))
    client.close()
    
    if log_in:
        os.system("nohup python data_transmit.py "+str(rate)+" 2>&1 >transmit.log &")
