import pandas as pd
import numpy as np
from pandas.tseries.offsets import MonthEnd
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import r2_score

def predict(data):
    ticker = data['data']['ticker']
    category = data['data']['category']
    finance_data = data['data']['finanace_data']

    # 提取日期和值作为特征和目标变量
    dates = [entry['date'] for entry in finance_data]
    values = [float(entry['value']) for entry in finance_data]

    # 将日期转换为pandas的日期时间格式
    df = pd.DataFrame({'date': dates, 'value': values})
    df['date'] = pd.to_datetime(df['date'])

    # 计算日期距离参考日期的天数
    min_date = df['date'].min()
    df['days_since_reference'] = (df['date'] - min_date).dt.days

    # 创建随机森林回归模型
    model = RandomForestRegressor()
    model.fit(df['days_since_reference'].values.reshape(-1, 1), df['value'])

    # 计算R
    r_squared = r2_score(df['value'], model.predict(df['days_since_reference'].values.reshape(-1, 1)))

    # 预测
    future_dates = pd.date_range(start='2024-01-01', end='2024-01-01', freq='MS')
    future_days_since_reference = (future_dates - min_date).days.values
    future_values = model.predict(future_days_since_reference.reshape(-1, 1))

    result = {
        'ticker': ticker,
        'category': category,
        'r_squared': float(r_squared),
        'future_predictions': [
            {'date': date.strftime('%Y-%m-%d'), 'value': str(value)} for date, value in zip(future_dates, future_values)
        ]
    }

    return result