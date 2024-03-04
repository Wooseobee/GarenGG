import pickle

import pandas as pd
import numpy as np

from .train import recommend_champs, load_data

score_data, champ_data = load_data()
with open('models/df_svd_preds.pkl', 'rb') as f:
    df_svd_preds = pickle.load(f)

def get_recommendations(user_id):
    already_rated, predictions = recommend_champs(df_svd_preds, user_id, champ_data, score_data, 3)
    return predictions
