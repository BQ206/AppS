from flask import Flask
import time

app = Flask(__name__)

@app.route("/")
def home():
    print( "Hello, Flask!" )
    return "Hello Fkl"

def run():
    app.run(host = '0.0.0.0')