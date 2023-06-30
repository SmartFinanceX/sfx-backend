from flask import Flask, render_template, request, jsonify
import json
import data_al_sfr_version1
import requests

app = Flask(__name__)

@app.route('/stock/predict', methods=['POST'])
def calculate_scores():
    if request.method == 'POST':
        # 获取 JSON 数据
        data = request.json

        # 用于存储股票信息
        ticker = data['ticker']
        category = data['category']
        url = 'http://124.222.191.199:9000/inc/_fnc/'

        url = url + ticker + '/' + str(category)

        # 发送GET请求
        response = requests.get(url)

        # 检查响应状态码
        if response.status_code == 200:
            # 获取响应内容
            data = response.json()  # 假设服务器返回JSON格式的数据
        
            # 返回统计结果
            return json.dumps(data_al_sfr_version1.predict(data))
        else:
            attention = str(response.status_code) + 'request data gets wrong.'
            return attention

if __name__ == '__main__':
    app.run(debug=True)

#暂存一些预测等股票分析方法
#https://kns.cnki.net/kcms2/article/abstract?v=3uoqIhG8C44YLTlOAiTRKibYlV5Vjs7iJTKGjg9uTdeTsOI_ra5_XQ9F_O4JltsrwDwcso1B9ZC_BVkTKxLgM0zaWYo2fK_z&uniplatform=NZKPT