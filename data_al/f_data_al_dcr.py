import json
import statistics as stats
import sys

if len(sys.argv) != 3:
    print("Usage: python statistics.py input_file output_file")
    sys.exit(1)

input_file = sys.argv[1]
output_file = sys.argv[2]

with open(input_file, 'r') as file:
    data = json.load(file)

statistics = []

for entry in data:
    ticker = entry['ticker']
    category = entry['category']
    finance_data = json.loads(entry['finance_data'])

    values = [float(item['value']) for item in finance_data]

    mean = stats.mean(values)
    median = stats.median(values)
    stdev = stats.stdev(values)
    min_value = min(values)
    max_value = max(values)

    entry_statistics = {
        'ticker': ticker,
        'category': category,
        'mean': mean,
        'median': median,
        'stdev': stdev,
        'min': min_value,
        'max': max_value
    }

    statistics.append(entry_statistics)

with open(output_file, 'w') as file:
    json.dump(statistics, file, indent=4)