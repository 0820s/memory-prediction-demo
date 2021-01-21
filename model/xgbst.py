"""
XGBoost prediction model
"""

import numpy as np
import xgboost as xgb
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import normalize

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
  model = xgb.Booster(model_file = "DFP.model")
  probs = model.predict(dInfer)
  return probs[0]

# ----- load data -----
# x_train, x_test, y_train, y_test = loadData()
# print(x_train.shape)
# print(y_train.shape)
# print(x_test.shape)
# print(y_test.shape)
# ----- train the model -----
# train(x_train, y_train)
# ----- test the model -----
# test(x_test, y_test)
# ----- inference for one sample -----
# prob = infer(x_test[0])
# print(prob)
