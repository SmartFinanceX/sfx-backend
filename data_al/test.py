import requests
import data_al_sfr_version1
import try_1

url = 'http://124.222.191.199:9000/inc/_fnc/'  # 替换为服务器的URL

ticker = '300456'
category = 1

url = url + ticker + '/' + str(category)
# 发送GET请求
response = requests.get(url)

# 检查响应状态码
if response.status_code == 200:
    # 获取响应内容
    data = response.json()  # 假设服务器返回JSON格式的数据

    try_1.generate_prediction_plot(data)
    
else:
    print('Request failed with status code:', response.status_code)
