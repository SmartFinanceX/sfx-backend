import json
import pandas as pd
import statistics as stats
import statsmodels.api as sm
import math

with open('converted_2023年5月10日.json', 'r') as file:
    data = json.load(file)

statistics = []

# 遍历每个条目
for entry in data:
    ticker = entry['ticker']
    category = entry['category']
    if category=="1" :
        finance_data = json.loads(entry['finanace_data'])

        values = [float(item['value']) for item in finance_data]

        mean = stats.mean(values)
        median = stats.median(values)
        stdev = stats.stdev(values)
        min_value = min(values)
        max_value = max(values)

        # 计算其波动性评分
        volatility = abs(stdev / median)
        if volatility > 100:
            volatility = 100


        # 提取日期和值的列表
        dates = [pd.to_datetime(item['date']).timestamp() for item in finance_data]
        values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finance_data]
        # 执行线性回归
        df = pd.DataFrame({'Date': dates, 'Value': values})
        df['Value'] = df['Value'].astype(float)
        df['Intercept'] = 1
        # 执行线性回归
        model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
        results = model.fit()
        # 获得其斜率作为评估指标之一
        slope = results.params[1]

        # 评判其增长率以及增长可能
        if slope > 0:
            slope_socre = (1 - math.exp(-slope))*100
        else:
            slope_socre = 0

        rate_slope = 0.5
        rate_volatility= 1 - rate_slope

        socre_total = rate_slope * slope_socre + volatility * rate_volatility
        # 创建统计结果的字典，并添加ticker、category以及统计量
        entry_statistics = {
            'ticker': ticker,
            'category': category,
            'socre':socre_total
        }

        # 将统计结果添加到列表中
        statistics.append(entry_statistics)

with open('converted_2023年5月10日_socre.json', 'w') as file:
    json.dump(statistics, file, indent=4)
