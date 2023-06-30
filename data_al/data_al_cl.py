import json
import pandas as pd

with open('converted_2023年5月10日.json', 'r') as file:
    data = json.load(file)

cleaned_data = []
for item in data:
    ticker = item['ticker']
    category = item['category']
    
    finance_data = json.loads(item['finance_data'])
    for entry in finance_data:
        date = entry['date']
        value = entry['value']
        
        
        # 将清洗后的数据存储到列表中
        cleaned_data.append({
            'ticker': ticker,
            'category': category,
            'date': date,
            'value': value
        })

df = pd.DataFrame(cleaned_data)

print(df.head())
