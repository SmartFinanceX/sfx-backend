import sys
import json
import pandas as pd
import statsmodels.api as sm

if len(sys.argv) != 3:
    print("Usage: python linear_regression.py input_file output_file")
    sys.exit(1)

input_file = sys.argv[1]
output_file = sys.argv[2]

with open(input_file, 'r') as file:
    data = json.load(file)

regression_results = []

for entry in data:
    ticker = entry['ticker']
    category = entry['category']
    finance_data = json.loads(entry['finanace_data'])

    dates = [pd.to_datetime(item['date']).timestamp() for item in finance_data]
    values = [float(item['value']) if '.' in item['value'] else int(item['value']) for item in finance_data]

    df = pd.DataFrame({'Date': dates, 'Value': values})

    df['Value'] = df['Value'].astype(float)

    df['Intercept'] = 1

    model = sm.OLS(df['Value'].values, df[['Intercept', 'Date']].values)
    results = model.fit()

    slope = results.params[1]
    intercept = results.params[0]
    r_squared = results.rsquared

    future_dates = pd.date_range(start='2023-01-01', periods=5, freq='Q')

    future_dates = [date.timestamp() for date in future_dates]

    future_df = pd.DataFrame({'Date': future_dates, 'Intercept': 1})

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


with open(output_file, 'w') as file:
    json.dump(regression_results, file, indent=4)