import pandas as pd
import numpy as np
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import r2_score, mean_squared_error
from sklearn.model_selection import train_test_split

def predict(data):
    ticker = data['data']['ticker']
    category = data['data']['category']
    finance_data = data['data']['finanace_data']

    # 提取日期和数值作为特征和目标变量
    dates = [entry['date'] for entry in finance_data]
    values = [float(entry['value']) for entry in finance_data]

    # 将日期转换为Pandas的日期时间格式
    df = pd.DataFrame({'date': dates, 'value': values})
    df['date'] = pd.to_datetime(df['date'])

    # 将数据拆分为训练集和测试集
    X_train, X_test, y_train, y_test = train_test_split(df['date'], df['value'], test_size=0.2, random_state=42)

    # 创建和训练随机森林回归模型
    model = RandomForestRegressor()
    model.fit(X_train.values.reshape(-1, 1), y_train)

    # 对训练集和测试集进行预测
    y_train_pred = model.predict(X_train.values.reshape(-1, 1))
    y_test_pred = model.predict(X_test.values.reshape(-1, 1))

    # 计算评估指标
    r2_train = r2_score(y_train, y_train_pred)
    r2_test = r2_score(y_test, y_test_pred)
    mse_train = mean_squared_error(y_train, y_train_pred)
    mse_test = mean_squared_error(y_test, y_test_pred)

    # 预测2023-1-1到2023-3-1的数据
    future_dates = pd.date_range(start='2023-01-01', end='2023-03-01', freq='M')
    future_values = model.predict(future_dates.values.reshape(-1, 1))

    result = {
        'ticker': ticker,
        'category': category,
        'r2_train': float(r2_train),
        'r2_test': float(r2_test),
        'mse_train': float(mse_train),
        'mse_test': float(mse_test),
        'future_predictions': [
            {'date': date.strftime('%Y-%m-%d'), 'value': str(value)} for date, value in zip(future_dates, future_values)
        ]
    }

    return result
