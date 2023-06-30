import json
import pandas as pd
import statsmodels.api as sm

with open('converted_2023年5月10日.json', 'r') as file:
    data = json.load(file)

# 存储回归结果和预测值的列表
regression_results = []

for entry in data:
    ticker = entry['ticker']
    category = entry['category']
    finance_data = json.loads(entry['finanace_data'])

    # 提取日期和值的列表
    dates = [pd.to_datetime(item['date']).timestamp() for item in finance_data]
    values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finance_data]

    df = pd.DataFrame({'Date': dates, 'Value': values})

    df['Value'] = df['Value'].astype(float)

    # 添加常数列作为截距
    df['Intercept'] = 1

    # 执行线性回归
    model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
    results = model.fit()

    slope = results.params[1]
    intercept = results.params[0]
    r_squared = results.rsquared

    # 进行未来日期的预测
    future_dates = pd.date_range(start='2023-01-01', periods=5, freq='Q')

    future_dates = [date.timestamp() for date in future_dates]

    future_df = pd.DataFrame({'Date': future_dates, 'Intercept': 1})

    # 进行预测
    predicted_values = results.predict(future_df[['Intercept', 'Date']].values)

    regression_result = {
        'ticker': ticker,
        'category': category,
        'slope': slope,
        'intercept': intercept,
        'r_squared': r_squared,
        'predicted_values': {str(pd.to_datetime(date, unit='s').date()): value for date, value in zip(future_dates, predicted_values)}
    }
    regression_results.append(regression_result)


with open('converted_2023年5月10日_线性回归分析.json', 'w') as file:
    json.dump(regression_results, file, indent=4)
