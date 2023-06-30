import json
import pandas as pd
import numpy as np
from sklearn.svm import SVR
from sklearn.metrics import r2_score

with open('converted_2023年5月10日.json') as file:
    data = json.load(file)

regression_results = []

for item in data:
    ticker = item['ticker']
    category = item['category']
    finance_data = json.loads(item['finanace_data'])

    dates = [entry['date'] for entry in finance_data]
    values = [float(entry['value']) for entry in finance_data]

    # 将日期转换为pandas的日期时间格式
    df = pd.DataFrame({'date': dates, 'value': values})
    df['date'] = pd.to_datetime(df['date'])

    # 创建支持向量回归模型
    model = SVR()
    model.fit(df['date'].values.reshape(-1, 1), df['value'])

    # 计算R
    r_squared = r2_score(df['value'], model.predict(df['date'].values.reshape(-1, 1)))

    future_dates = pd.date_range(start='2024-01-01', end='2025-01-01', freq='M')
    future_values = model.predict(future_dates.values.reshape(-1, 1))

    result = {
        'ticker': ticker,
        'category': category,
        'model_parameters': None,  # 由于SVR没有明确的参数，将其设置为None
        'r_squared': float(r_squared),
        'future_predictions': [
            {'date': date.strftime('%Y-%m-%d'), 'value': str(value)} for date, value in zip(future_dates, future_values)
        ]
    }

    regression_results.append(result)

with open('converted_2023年5月10日_svr.json', 'w') as file:
    json.dump(regression_results, file, indent=4)
