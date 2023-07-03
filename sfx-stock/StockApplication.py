import json
import logging
import nacos
import queue
import requests
import threading
import time
from flask import Flask
from flask_cors import cross_origin

import data_al_api_calculate_version2
import data_al_sfr_version1

app = Flask(__name__)

SERVER_ADDRESSES = "localhost:8848"
NAMESPACE = "public"

app.config['JSON_AS_ASCII'] = False

q = queue.Queue()
lock = threading.Lock()
port = 9110


@app.route('/stock/live/<string:code>', methods=['GET'])
def getprice(code):
    data = {"key": "5cqh0wyOYyFm7HavTAHQ2Z7yAs"}
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0"}
    url = "http://stock.salefx.cn:10000/api/stock/snapshot?code=" + str(code)
    html = requests.post(url=url, headers=headers, data=data).json()
    json_data = json.dumps(html, ensure_ascii=False)
    return json_data


@app.route('/stock/k/<string:ticker>/<string:ktype>', methods=['GET'])
def getkline(ticker, ktype):
    gp = ticker
    gp_type = 'sh'
    if gp.find('60', 0, 3) == 0:
        gp_type = 'sh'
    elif gp.find('688', 0, 4) == 0:
        gp_type = 'sh'
    elif gp.find('900', 0, 4) == 0:
        gp_type = 'sh'
    elif gp.find('00', 0, 3) == 0:
        gp_type = 'sz'
    elif gp.find('300', 0, 4) == 0:
        gp_type = 'sz'
    elif gp.find('200', 0, 4) == 0:
        gp_type = 'sz'
    # headers = {
    #     "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0"}
    url = "https://quotes.sina.cn/cn/api/json_v2.php/CN_MarketDataService.getKLineData?symbol=" + gp_type + str(
        ticker) + "&scale=" + str(ktype) + "&ma=no&datalen=1023"
    response = requests.get(url=url).json()
    print(response)
    return response


def handle_heartbeat():
    log = logging.getLogger('werkzeug')
    while True:
        try:
            log.setLevel(logging.ERROR)
            requests.get('http://localhost:' + str(port) + '/heartbeat')
            instance_info = q.get()
            client.add_naming_instance(instance_info['serviceName'], instance_info['ip'], instance_info['port'])
            log.setLevel(logging.INFO)
        except:
            if (not q.empty()):
                return


@app.route('/heartbeat')
def heartbeat():
    instance_info = {'serviceName': 'stockservice', 'ip': 'localhost', 'port': port}
    lock.acquire()
    try:
        q.put(instance_info)
        time.sleep(2)
    finally:
        lock.release()
    return 'OK'


@app.route('/stock/predict/<string:ticker>/<string:category>', methods=['GET'])
@cross_origin()
def predict(ticker, category):
    url = 'http://localhost:9000/inc/_fnc/'
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


@app.route('/stock/calculate_scores/<string:ticker>/<string:category>', methods=['GET'])
@cross_origin()
def calculate_scores(ticker, category):
    url = 'http://localhost:9000/inc/_fnc/'
    url = url + ticker + '/' + str(category)
    # 发送GET请求
    response = requests.get(url)

    # 检查响应状态码
    if response.status_code == 200:
        # 获取响应内容
        data = response.json()  # 假设服务器返回JSON格式的数据
        # 返回统计结果
        return json.dumps(data_al_api_calculate_version2.calculate(data))


if __name__ == '__main__':
    client = nacos.NacosClient(
        SERVER_ADDRESSES, namespace=NAMESPACE, username="nacos", password="nacos")
    client.add_naming_instance(
        service_name="stockservice", ip="localhost", port=port, healthy=True)
    t = threading.Thread(target=handle_heartbeat)
    t.start()
    app.run(debug=True, port=port)
