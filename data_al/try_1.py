import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import r2_score

def generate_prediction_plot(data):
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

    # 计算R²
    r_squared = r2_score(df['value'], model.predict(df['days_since_reference'].values.reshape(-1, 1)))

    # 预测
    future_dates = pd.date_range(start='2024-01-01', end='2024-03-01', freq='MS')
    future_days_since_reference = (future_dates - min_date).days.values
    future_values = model.predict(future_days_since_reference.reshape(-1, 1))

    # 绘制历史数据和预测数据曲线
    plt.plot(df['date'], df['value'], label='Historical Data')
    plt.plot(future_dates, future_values, label='Predicted Data')

    # 设置图形标题和标签
    plt.title('Predicted vs. Historical Data')
    plt.xlabel('Date')
    plt.ylabel('Value')

    # 添加图例
    plt.legend()

    # 保存图像为PNG文件
    plt.savefig('prediction_plot.png')

    # 清除图形
    plt.clf()
