import json
import nacos
import requests
import threading
import time
from flask import Flask

app = Flask(__name__)

SERVER_ADDRESSES = "localhost:8848"
NAMESPACE = "public"


@app.route('/stock/<string:code>', methods=['GET'])
def getprice(code):
    data = {"key": "5cqh0wyOYyFm7HavTAHQ2Z7yAs"}
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0"}
    url = "http://stock.salefx.cn:10000/api/stock/snapshot?code=" + str(code)
    html = requests.post(url=url, headers=headers, data=data).json()
    json_data = json.dumps(html, ensure_ascii=False)
    return json_data


@app.route('/stock/heartbeat')
def heartbeat():
    instance_info = {'serviceName': 'flaskService',
                     'ip': '127.0.0.1', 'port': 8100}
    lock.acquire()
    try:
        q.put(instance_info)
        time.sleep(2)
    finally:
        lock.release()
    return 'OK'


if __name__ == '__main__':
    client = nacos.NacosClient(
        SERVER_ADDRESSES, namespace=NAMESPACE, username="nacos", password="nacos")
    client.add_naming_instance(
        service_name="stockservice", ip="localhost", port=9110)
    t = threading.Thread(target=handle_heartbeat)
    app.run(debug=True, port=9110)
