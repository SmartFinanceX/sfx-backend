import json
import pandas as pd
import statistics as stats
import statsmodels.api as sm
import math
import os

def calcullate(data):
        statistics = []

        # 遍历每个条目
        for entry in data:
            ticker = entry['ticker']
            category = entry['category']
            if category == "1":
                finanace_data = json.loads(entry['finanace_data'])

                values = [float(item['value']) for item in finanace_data]

                mean = stats.mean(values)
                median = stats.median(values)
                stdev = stats.stdev(values)
                min_value = min(values)
                max_value = max(values)

                volatility = abs(stdev / median)
                if volatility > 100:
                    volatility = 100

                dates = [pd.to_datetime(item['date']).timestamp() for item in finanace_data]
                values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finanace_data]

                df = pd.DataFrame({'Date': dates, 'Value': values})
                df['Value'] = df['Value'].astype(float)
                df['Intercept'] = 1
                model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
                results = model.fit()
                slope = results.params[1]

                if slope > 0:
                    slope_score = (1 - math.exp(-slope)) * 100
                else:
                    slope_score = 0

                rate_slope = 0.5
                rate_volatility = 1 - rate_slope

                score_total = rate_slope * slope_score + volatility * rate_volatility

                entry_statistics = {
                    'ticker': ticker,
                    'category': category,
                    'score': score_total
                }

                statistics.append(entry_statistics)
        
        return statistics