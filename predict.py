import pandas as pd
from pandas import datetime
import matplotlib.pyplot as plt
import pickle
from statsmodels.tsa.arima_model import ARIMA
from sklearn.metrics import mean_absolute_error
import warnings
warnings.filterwarnings("ignore")
import sys

args = sys.argv
item = args[1]
city = args[2]

if args[3] == "3-Months":
	months = 3
elif args[3] == "6-Months":
	months = 6
elif args[3] == "9-Months":
	months = 9
elif args[3] == "1-Year":
	months = 12
elif args[3] == "2-Years":
	months = 24
elif args[3] == "3-Years":
	months = 36				

filename = "Models/" + item + "_" + city + "_model" + ".pickle"

model = pickle.load(open(filename, "rb"))

predictions = []
predictions = model.forecast(steps=months)[0]

for i in range(predictions.size):
	predictions[i] = round(predictions[i], 2)

file = "Predictions/" + item + "_" + city + "_pred" + ".txt"
with open(file, "w") as f:
    for i in predictions:
        f.write("%s\n" % i)

fn = "Data/data.txt"
data = []
data.append(item)
data.append(city)
print(data)
with open(fn, "w") as f:
    for i in data:
        f.write("%s\n" % i)
