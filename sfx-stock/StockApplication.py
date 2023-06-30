import time
import requests
import json
import re
import nacos
from flask import Flask
app = Flask(__name__)

SERVER_ADDRESSES = "124.222.191.199:8848"
NAMESPACE = "public"


@app.route('/stock/<string:code>', methods=['GET'])
def getprice(code):
    data = {"key": "5cqh0wyOYyFm7HavTAHQ2Z7yAs"}
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0"}
    url = "http://stock.salefx.cn:10000/api/stock/snapshot?code="+str(code)
    html = requests.post(url=url, headers=headers, data=data).json()
    json_data = json.dumps(html, ensure_ascii=False)
    return json_data


if __name__ == '__main__':
    client = nacos.NacosClient(
        SERVER_ADDRESSES, namespace=NAMESPACE, username="nacos", password="nacos")
    client.add_naming_instance(
        service_name="stockservice", ip="124.222.191.199", port=9110)
    app.run(debug=True, port=9110)
