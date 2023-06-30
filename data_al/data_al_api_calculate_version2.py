import pandas as pd
import statistics as stats
import statsmodels.api as sm
import math

def calculate(data):
    statistics = []

    code = data['code']
    ticker = data['data']['ticker']
    category = data['data']['category']
    finance_data = data['data']['finanace_data']

    if code == 200:
        if category == 1:
            values = [float(item['value']) for item in finance_data]

            mean = stats.mean(values)
            median = stats.median(values)
            stdev = stats.stdev(values)
            min_value = min(values)
            max_value = max(values)

            volatility = abs(stdev / median)
            if volatility > 100:
                volatility = 100
                msg = '股票净利润呈现波动性较大，风险较大；'
            elif volatility < 10:
                msg = '股票净利润呈现波动性较小，风险较小；'
            else:
                msg = '股票净利润波动性一般，风险适中；'

            dates = [pd.to_datetime(item['date']).timestamp() for item in finance_data]
            values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finance_data]

            df = pd.DataFrame({'Date': dates, 'Value': values})
            df['Value'] = df['Value'].astype(float)
            df['Intercept'] = 1
            model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
            results = model.fit()
            slope = results.params[1]

            if slope > 0:
                slope_score = (1 - math.exp(-slope)) * 100
                msg = msg + '股票总体呈现增长趋势。'
            else:
                slope_score = 0
                msg = msg + '股票总体呈现下降趋势。'

            rate_slope = 0.5
            rate_volatility = 1 - rate_slope

            score_total = rate_slope * slope_score + volatility * rate_volatility

            entry_statistics = {
                'ticker': ticker,
                'category': category,
                'msg': msg,
                'mean': mean,
                'min_value': min_value,
                'max_value': max_value,
                'score': score_total
            }

            statistics.append(entry_statistics)
        
        elif category == 3:
            values = [float(item['value']) for item in finance_data]

            mean = stats.mean(values)
            median = stats.median(values)
            stdev = stats.stdev(values)
            min_value = min(values)
            max_value = max(values)

            volatility = abs(stdev / median)
            if volatility > 100:
                volatility = 100
                msg = '股票营业总成本呈现波动性较大，企业活动每年情况不一；'
            elif volatility < 10:
                msg = '股票营业总成本呈现波动性较小，企业活动每年比价固定；'
            else:
                msg = '股票营业总成本波动性一般；'

            dates = [pd.to_datetime(item['date']).timestamp() for item in finance_data]
            values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finance_data]

            df = pd.DataFrame({'Date': dates, 'Value': values})
            df['Value'] = df['Value'].astype(float)
            df['Intercept'] = 1
            model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
            results = model.fit()
            slope = results.params[1]

            if slope > 0:
                slope_score = (1 - math.exp(-slope)) * 100
                msg = msg + '股票营业总成本呈现增长趋势。'
            else:
                slope_score = 0
                msg = msg + '股票营业总成本呈现下降趋势。'

            rate_slope = 0.5
            rate_volatility = 1 - rate_slope

            score_total = rate_slope * slope_score + volatility * rate_volatility

            entry_statistics = {
                'ticker': ticker,
                'category': category,
                'msg': msg,
                'mean': mean,
                'min_value': min_value,
                'max_value': max_value,
                'score': score_total
            }

            statistics.append(entry_statistics)
        
        elif category == 2:
            values = [float(item['value']) for item in finance_data]

            mean = stats.mean(values)
            median = stats.median(values)
            stdev = stats.stdev(values)
            min_value = min(values)
            max_value = max(values)

            volatility = abs(stdev / median)
            if volatility > 100:
                volatility = 100
                msg = '股票营业总收入呈现波动性较大;'
            elif volatility < 10:
                msg = '股票营业总收入呈现波动性较小;'
            else:
                msg = '股票营业总收入波动性一般;'

            dates = [pd.to_datetime(item['date']).timestamp() for item in finance_data]
            values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finance_data]

            df = pd.DataFrame({'Date': dates, 'Value': values})
            df['Value'] = df['Value'].astype(float)
            df['Intercept'] = 1
            model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
            results = model.fit()
            slope = results.params[1]

            if slope > 0:
                slope_score = (1 - math.exp(-slope)) * 100
                msg = msg + '股票营业总收入呈现增长趋势'
            else:
                slope_score = 0
                msg = msg + '股票营业总收入呈现下降趋势'

            rate_slope = 0.5
            rate_volatility = 1 - rate_slope

            score_total = rate_slope * slope_score + volatility * rate_volatility

            entry_statistics = {
                'ticker': ticker,
                'category': category,
                'msg': msg,
                'mean': mean,
                'min_value': min_value,
                'max_value': max_value,
                'score': score_total
            }

            statistics.append(entry_statistics)

        elif category == 4:
            values = [float(item['value']) for item in finance_data]

            mean = stats.mean(values)
            median = stats.median(values)
            stdev = stats.stdev(values)
            min_value = min(values)
            max_value = max(values)

            volatility = abs(stdev / median)
            if volatility > 100:
                volatility = 100
                msg = '股票归属于母公司所有者的净利润呈现波动性较大;'
            elif volatility < 10:
                msg = '股票归属于母公司所有者的净利润呈现波动性较小;'
            else:
                msg = '股票归属于母公司所有者的净利润波动性一般;'

            dates = [pd.to_datetime(item['date']).timestamp() for item in finance_data]
            values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finance_data]

            df = pd.DataFrame({'Date': dates, 'Value': values})
            df['Value'] = df['Value'].astype(float)
            df['Intercept'] = 1
            model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
            results = model.fit()
            slope = results.params[1]

            if slope > 0:
                slope_score = (1 - math.exp(-slope)) * 100
                msg = msg + '股票归属于母公司所有者的净利润呈现增长趋势'
            else:
                slope_score = 0
                msg = msg + '股票归属于母公司所有者的净利润呈现下降趋势'

            rate_slope = 0.5
            rate_volatility = 1 - rate_slope

            score_total = rate_slope * slope_score + volatility * rate_volatility

            entry_statistics = {
                'ticker': ticker,
                'category': category,
                'msg': msg,
                'mean': mean,
                'min_value': min_value,
                'max_value': max_value,
                'score': score_total
            }

            statistics.append(entry_statistics)

        elif category == 5:
            values = [float(item['value']) for item in finance_data]

            mean = stats.mean(values)
            median = stats.median(values)
            stdev = stats.stdev(values)
            min_value = min(values)
            max_value = max(values)

            volatility = abs(stdev / median)
            if volatility > 100:
                volatility = 100
                msg = '股票扣除非经常性损益后的净利润呈现波动性较大;'
            elif volatility < 10:
                msg = '股票扣除非经常性损益后的净利润呈现波动性较小;'
            else:
                msg = '股票扣除非经常性损益后的净利润波动性一般;'

            dates = [pd.to_datetime(item['date']).timestamp() for item in finance_data]
            values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finance_data]

            df = pd.DataFrame({'Date': dates, 'Value': values})
            df['Value'] = df['Value'].astype(float)
            df['Intercept'] = 1
            model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
            results = model.fit()
            slope = results.params[1]

            if slope > 0:
                slope_score = (1 - math.exp(-slope)) * 100
                msg = msg + '股票扣除非经常性损益后的净利润呈现增长趋势'
            else:
                slope_score = 0
                msg = msg + '股票扣除非经常性损益后的净利润呈现下降趋势'

            rate_slope = 0.5
            rate_volatility = 1 - rate_slope

            score_total = rate_slope * slope_score + volatility * rate_volatility

            entry_statistics = {
                'ticker': ticker,
                'category': category,
                'msg': msg,
                'mean': mean,
                'min_value': min_value,
                'max_value': max_value,
                'score': score_total
            }

            statistics.append(entry_statistics)
    else:
        entry_statistics = {
                'statement':"accept gets wrong."
            }
        
        statistics.append(entry_statistics)
    
    return statistics

