import time
import requests
import json
import re
import nacos
from flask import Flask, request, jsonify
import sys
import os
import threading
import queue
import data_al_sfr_version1
import data_al_api_calculate_version2

app = Flask(__name__)

SERVER_ADDRESSES = "localhost:8848"
NAMESPACE = "public"

app.config['JSON_AS_ASCII'] = False

q = queue.Queue()
lock = threading.Lock()

@app.route('/stock/<string:code>', methods=['GET'])
def getprice(code):
    data = {"key": "5cqh0wyOYyFm7HavTAHQ2Z7yAs"}
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0"}
    url = "http://stock.salefx.cn:10000/api/stock/snapshot?code="+str(code)
    html = requests.post(url=url, headers=headers, data=data).json()
    json_data = json.dumps(html, ensure_ascii=False)
    return json_data

def handle_heartbeat():
    while True:
        try:
            requests.get('http://localhost:9110/heartbeat')
            instance_info = q.get()
            client.add_naming_instance(instance_info['serviceName'], instance_info['ip'], instance_info['port'])
        except:
            if(not q.empty()):
                return

@app.route('/heartbeat')
def heartbeat():
    instance_info = {'serviceName': 'stockservice', 'ip': 'localhost', 'port': 9110}
    lock.acquire()
    try:
        q.put(instance_info)
        time.sleep(2)
    finally:
        lock.release()
    return 'OK'

#预测接口，附带预测函数data_al_sfr_version2.py
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

#评分接口，附带评分函数data_al_api_calculate_version2.py
@app.route('/stock/calculate_scores', methods=['POST'])
def calculate_scores():
    if request.method == 'POST':
        # 获取 JSON 数据
        data = request.json
        # 返回统计结果
        return json.dumps(data_al_api_calculate_version2.calculate(data))

if __name__ == '__main__':
    client = nacos.NacosClient(
        SERVER_ADDRESSES, namespace=NAMESPACE, username="nacos", password="nacos")
    client.add_naming_instance(
        service_name="stockservice", ip="localhost", port=9110, healthy=True)
    
    t = threading.Thread(target=handle_heartbeat)
    t.start()
    app.run(debug=True, port=9110)