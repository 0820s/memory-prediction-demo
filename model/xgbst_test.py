import numpy as np
import pandas as pd
import sys
#import xgbst

"""
XGBoost prediction model
"""

#import numpy as np
import xgboost as xgb
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
#from sklearn.preprocessing import normalize

"""
load the data from the disk
split them into training and testing set
"""
def loadData():
  # load data
  samples = np.load("samples.npy")
  labels = np.load("labels.npy")
  # split
  x_train, x_test, y_train, y_test = train_test_split(samples, labels)
  return x_train, x_test, y_train, y_test

"""
trian a prediction model and save the model
input:
  x_test = (# of samples, # of features, # of time segments) = (7500, 10, 7)
  y_test = (# of samples) = (7500,)
"""
def train(x_train, y_train):
  # TODO: normalization
  # flatten for DT-based models
  x_train = np.reshape(x_train, (x_train.shape[0], -1))
  dTrain = xgb.DMatrix(x_train, label = y_train)
  # build and train the model
  param = {'max_depth':2, 'eta':0.1, 'objective':'binary:logistic'}
  print("Training...")
  model = xgb.train(param, dTrain, 100)
  # training accuracy
  probs = model.predict(dTrain)
  y_pred = [round(i) for i in probs]
  train_accuracy = accuracy_score(y_train, y_pred)
  print "training accuracy:", train_accuracy
  # save the model
  model.save_model("DFP.model")

"""
test the model
input:
  x_test = (# of samples, # of features, # of time segments) = (2500, 10, 7)
  y_test = (# of samples) = (2500,)
"""
def test(x_test, y_test):
  # TODO: normalization
  # flatten for DT-based models
  x_test = np.reshape(x_test, (x_test.shape[0], -1))
  dTest = xgb.DMatrix(x_test, label = y_test)
  # load the model
  model = xgb.Booster(model_file = "DFP.model")
  # testing accuracy
  probs = model.predict(dTest)
  y_pred = [round(i) for i in probs]
  test_accuracy = accuracy_score(y_test, y_pred)
  print "testing accuracy:", test_accuracy

"""
interface of inference
input: (# of features, # of time segments) = (10, 7)
output: the probability of DRAM failures, which is one floating number
"""
def infer(one_sample):
  # TODO: normalization
  # flatten for DT-based models
  one_sample = one_sample.flatten()
  dInfer = xgb.DMatrix([one_sample])
  # load the model
  #model = xgb.Booster(model_file = "DFP.model")
  probs = model.predict(dInfer)
  return probs[0]

model = xgb.Booster(model_file = "DFP.model")
data=",0,0,0,0,0,2,0,2,2,0,0,0,0,0,0,0,0,0,0,0,12000.0,12900.0,12900.0,12900.0,12900.0,12900.0,12900.0,12900.0,12900.0,12900.0,6400.0,5674.0,5674.0,5674.0,5674.0,5674.0,5674.0,5674.0,5674.0,5674.0,50.0,56.0,56.0,56.0,56.0,56.0,56.0,56.0,56.0,56.0"
data=data.split(',')[1:]
'''
data='Dell Inc.,Intel(R) Xeon(R) Silver 4108 CPU @ 1.80GHz,00AD063200AD,2666 MT/s,HMA82GR7JJR8N-VK'
for i in range(10):
    data+=',0,0'
    data+=',0,0,0'
'''

sample = [[] for i in range(10)]
for i in range(10):
  sample[0].append(int(data[i]))
  sample[1].append(int(data[10+i]))
  sample[2].append(0)
  sample[3].append(1)
  sample[4].append(1)
  sample[5].append(0)
  sample[6].append(1)
  sample[7].append(float(data[20+i]))
  sample[8].append(float(data[30+i]))
  sample[9].append(float(data[40+i]))

one_sample = np.asarray(sample)
print sample
#prob = xgbst.infer(one_sample)
prob = infer(one_sample)
print prob
