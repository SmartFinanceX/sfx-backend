from flask import Flask, render_template, request, jsonify
import json
import data_al_api_calculate_version2

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('calculate_scores.html')

@app.route('/calculate_scores', methods=['POST'])
def calculate_scores():
    if request.method == 'POST':
        # 获取 JSON 数据
        data = request.json
        # 返回统计结果
        return json.dumps(data_al_api_calculate_version2.calculate(data))

if __name__ == '__main__':
    app.run(debug=True)

#暂存一些预测等股票分析方法
#https://kns.cnki.net/kcms2/article/abstract?v=3uoqIhG8C44YLTlOAiTRKibYlV5Vjs7iJTKGjg9uTdeTsOI_ra5_XQ9F_O4JltsrwDwcso1B9ZC_BVkTKxLgM0zaWYo2fK_z&uniplatform=NZKPT