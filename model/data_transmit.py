# -*- coding: utf-8 -*-
"""
Created on Mon Oct 26 15:29:59 2020

@author: 86131
"""

import socket
import os
import sys
import time

"""
get hardware config
"""
def getHardwareConfig():
  # server brand
  server_brand_file = os.popen("dmidecode -t system | grep 'Manufacturer' | head -n 1")
  server_brand = server_brand_file.readline().split(": ")[1]
  # processor version, used for CPU platform
  proc_ver_file = os.popen("dmidecode -t processor | grep 'Version' | head -n 1")
  proc_ver = proc_ver_file.readline().split(": ")[1]
  # memory manufacturer, now it is an encoding
  mem_manu_file = os.popen("dmidecode -t memory | grep 'Manufacturer' | head -n 1")
  mem_manu = mem_manu_file.readline().split(": ")[1]
  # memory speed
  mem_speed_file = os.popen("dmidecode -t memory | grep 'Speed' | head -n 1")
  mem_speed = mem_speed_file.readline().split(": ")[1]
  # memory part number, used for process
  mem_part_file = os.popen("dmidecode -t memory | grep 'Part Number' | head -n 1")
  mem_part = mem_part_file.readline().split(": ")[1]
  return server_brand.strip('\n'), proc_ver.strip('\n'), mem_manu.strip('\n'), mem_speed.strip('\n'), mem_part.strip('\n')


"""
get mcelog: ecc error and bank id
"""
class MceInfo:
  def __init__(self):
    self.info_ = {}
    self.info_["MCE"] = ""
    self.info_["CPU"] = ""
    self.info_["BANK"] = ""
    self.info_["ADDR"] = ""
    self.info_["STATUS"] = ""
    self.info_["MCGSTATUS"] = ""
    self.info_["MCGCAP"] = ""
    self.info_["APICID"] = ""
    self.info_["SOCKETID"] = ""
    self.info_["Vendor"] = ""  # CPUID Vendor, type: string
    self.info_["Family"] = ""  # CPUID Family
    self.info_["Model"] = ""  # CPUID Model

  @staticmethod
  def isDefaultVal(val):
    return val == ""

  def isDefault(self):
    for k, v in self.info_.items():
      if v != "":
        return False
    return True

def getInfo(val, key, mce_info_curr, mce_info_list):
  if not MceInfo.isDefaultVal(mce_info_curr.info_[key]):  # a new mcelog detected
    mce_info_list.append(mce_info_curr)
    mce_info_curr = MceInfo()
    mce_info_curr.info_[key] = val
  else:
    mce_info_curr.info_[key] = val
  return mce_info_list, mce_info_curr

def parse(file_mcelog):
  mce_info_list = []
  mce_info_curr = MceInfo()

  while True:  # go through lines
    line = file_mcelog.readline()
    if not line:
      break
    line_arr = line.strip().split(" ")
    for i in range(len(line_arr) - 1):  # go through items in one line, at least one item after
      for key in mce_info_curr.info_.keys():  # match with items in MceInfo
        if line_arr[i] == key:
          mce_info_list, mce_info_curr = getInfo(line_arr[i + 1], key,
                                                 mce_info_curr, mce_info_list)

  if not mce_info_curr.isDefault():
    mce_info_list.append(mce_info_curr)
  return mce_info_list

def getMostBank(mce_info_list):
  bank_count = {}
  for mce_info in mce_info_list:
    bank = mce_info.info_["BANK"]
    if bank in bank_count:
      bank_count[bank] += 1
    else:
      bank_count[bank] = 1
  most_bank = "0"
  max_count = 0
  for bank, count in bank_count.items():
    if count > max_count:
      most_bank = bank
      max_count = count
  return most_bank

"""
the interface of mcelog parser
parse the /var/log/mcelog, empty it, and returns two features
returns:
  ce_count: int, the count of CEs
  bank_id: str, the most frequent BANK ID in mcelog
"""
def getMcelogFeature():
  file_mcelog = open("/var/log/mcelog", "r+")
  # file_mcelog = open("/home/sxy/workspace_j/mcelog", "r+")
  mce_info_list = parse(file_mcelog)
  ce_count = len(mce_info_list)
  bank_id = getMostBank(mce_info_list)
  file_mcelog.truncate(0)
  return str(ce_count), str(bank_id)


"""
get memory workloads: memory read bandwidth, write bandwidth, read latency
"""
def getMean(arr):
  if len(arr) == 0:
    return -1
  mean = sum(arr) / len(arr)
  return mean

def getPcmMetrics():
  # os.system("./pcm_daemon.sh 1")  # flush and restart daemon
  rd_bw_arr = []
  wt_bw_arr = []
  file_bw = open("./pcm_log/bandwidth.csv", "r+")
  while True:
    line = file_bw.readline()
    if not line:
      break
    line_arr = line.split(",")
    if len(line_arr) < 12 or line_arr[0] == "" or line_arr[0] == "Date":
      continue
    rd_bw_arr.append(float(line_arr[-3]))
    wt_bw_arr.append(float(line_arr[-2]))
  file_bw.truncate(0)
  file_bw.close()
  rd_bw_mean = getMean(rd_bw_arr)
  wt_bw_mean = getMean(wt_bw_arr)

  lt_arr = []
  file_lt = open("./pcm_log/latency.log", "r+")
  while True:
    line = file_lt.readline()
    if not line:
      break
    if len(line) > 10 and line[:3] == "DDR":
      line_arr = line.split("\t")
      if len(line_arr) <= 1:
        continue
      lt_tmp = 0
      for i in range(1, len(line_arr)):
        lt_tmp += float(line_arr[i])
      lt_arr.append(lt_tmp / (len(line_arr) - 1))
  file_lt.truncate(0)
  file_lt.close()
  lt_mean = getMean(lt_arr)

  return rd_bw_mean, wt_bw_mean, lt_mean



"""
send message
"""

def send_message():
    while True:
        last_send_time=time.time()
    
        #read_bandwidth,write_bandwidth,read_latency=10000.0,20000.0,30.0
        #ce_count, bank_id="3,7".split(',')
        
        ce_count, bank_id = getMcelogFeature()
        read_bandwidth,write_bandwidth,read_latency=getPcmMetrics()

        client = socket.socket()
        client.connect(('8.134.39.104',7484))
        #timestamp=time.strftime('%Y-%m-%d',time.localtime(time.time()))
        client.send(("append,"+str(round(read_bandwidth,2))+","+str(round(write_bandwidth,2))+","+str(round(read_latency,2))+","+ce_count+","+bank_id+"\n").encode('utf-8'))
        client.send("close\n".encode())
        client.close()
        
        while True:
            time.sleep(rate/4)
            if time.time()-last_send_time>=rate:
                break

if __name__=="__main__":   
    rate=int(sys.argv[1])
    client = socket.socket()
    client.connect(('8.134.39.104',7484))

    server_brand, proc_ver, mem_manu, mem_speed, mem_part = getHardwareConfig()
    #server_brand, proc_ver, mem_manu, mem_speed, mem_part="unknown","unknown","unknown","unknown","unknown"
    client.send(("hardware,"+server_brand+","+proc_ver+","+mem_manu+","+mem_speed+","+mem_part+"\n").encode('utf-8'))
    client.send("close\n".encode('utf-8'))
    client.close()
    send_message()

    
    
