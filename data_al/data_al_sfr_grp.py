import json
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import r2_score

with open('converted_2023年5月10日.json') as file:
    data = json.load(file)

for item in data:
    ticker = item['ticker']
    category = item['category']
    finance_data = json.loads(item['finanace_data'])

    # 提取日期和值作为特征和目标变量
    dates = [entry['date'] for entry in finance_data]
    values = [float(entry['value']) for entry in finance_data]

    df = pd.DataFrame({'date': dates, 'value': values})
    df['date'] = pd.to_datetime(df['date'])

    # 创建随机森林回归模型
    model = RandomForestRegressor()
    model.fit(df['date'].values.reshape(-1, 1), df['value'])

    # 预测结果
    predictions = model.predict(df['date'].values.reshape(-1, 1))

    # 计算R系数
    r_squared = r2_score(df['value'], predictions)

    plt.figure(figsize=(10, 6))
    plt.scatter(df['date'], df['value'], color='blue', label='Actual')
    plt.plot(df['date'], predictions, color='red', label='Predicted')
    plt.xlabel('Date')
    plt.ylabel('Value')
    plt.title(f'Ticker: {ticker} - Category: {category}\nR-squared: {r_squared:.2f}')
    plt.legend()
    plt.grid(True)
    
    plt.savefig(f'results_{ticker}_{category}.png', dpi=300)
    plt.close()
