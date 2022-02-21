import yfinance as yf
from datetime import datetime
import matplotlib.pyplot as plt
from dateutil.relativedelta import relativedelta
from urllib.request import Request, urlopen
from bs4 import BeautifulSoup
import time
import re
import io
import stocks

stock_names = []
stock_symbols = []

stock_names[:] = stocks.stock_names[:]
stock_symbols[:] = stocks.stock_symbols[:]

matching_stna = []
matching_stsymb = []

matching = []


def search_nm(srcstr):
    i = 0 
    stock_names.clear()
    stock_symbols.clear()
    stock_names[:] = stocks.stock_names[:]
    stock_symbols[:] = stocks.stock_symbols[:]
    print(len(stocks.stock_names))
    matching_stna.clear()
    matching_stsymb.clear()
    while(i < len(stock_names)):
        if(srcstr.lower() in stock_names[i].lower()):
            matching_stna.append(stock_names[i])
            matching_stsymb.append(stock_symbols[i])
        i += 1
    stock_names[:] = matching_stna[:]
    stock_symbols[:] = matching_stsymb[:]

def roundToNearestZero(number):
    n = abs(number)
    if n < 1:
        # Find the first non-zero digit.
        # We want 3 digits, starting at that location.
        s = f'{n:.99f}'
        index = re.search('[1-9]', s).start()
        return s[:index + 3]
    else:
        # We want 2 digits after decimal point.
        return str(round(n, 2))


def peg_rat(symbz):
    dataz = yf.Ticker(symbz)
    return float(roundToNearestZero(dataz.info['pegRatio']))

def sort_l(data_list,data_list2):
    new_list = []
    sym_l = []
    indexf = 0
    while data_list:
        minimum = data_list[0]  # arbitrary number in list 
        min_2 = data_list2[0]
        index_of = 0
        i = 0
        while(i < len(data_list)):
            if data_list[i].lower() < minimum.lower():
                minimum = data_list[i] 
                min_2 = data_list2[i]
            i += 1
        new_list.append(minimum)
        data_list.remove(minimum)   
        data_list2.remove(min_2)
        sym_l.append(min_2)
    return new_list, sym_l

def sort_l_r(data_list,data_list2):
    new_list = []
    sym_l = []
    indexf = 0
    while data_list:
        minimum = data_list[0]  # arbitrary number in list 
        min_2 = data_list2[0]
        index_of = 0
        i = 0
        while(i < len(data_list)):
            if data_list[i].lower() > minimum.lower():
                minimum = data_list[i] 
                min_2 = data_list2[i]
            i += 1
        new_list.append(minimum)
        data_list.remove(minimum)   
        data_list2.remove(min_2)
        sym_l.append(min_2)
    return new_list, sym_l

def get_stock_amount():
    return len(stock_names)

def get_stock_symb(x):
    return stock_symbols[x]

def get_stock_na(x):
    return stock_names[x]

def sort_name():
    data_l_h = []
    data_s_h = []
    data_l_h[:] = stocks.stock_names[:]
    data_s_h[:] = stocks.stock_symbols[:]
    stock_names[:],stock_symbols[:] = sort_l(data_l_h, data_s_h)

def sort_symb():
    data_l_h2 = []
    data_s_h2 = []
    data_l_h2[:] = stocks.stock_names[:]
    data_s_h2[:] = stocks.stock_symbols[:]
    stock_symbols[:], stock_names[:] = sort_l(data_s_h2, data_l_h2)

def sort_name_r():
    data_l_h1 = []
    data_s_h1 = []
    print(len(stocks.stock_names[:]))
    data_l_h1[:] = stocks.stock_names[:]
    data_s_h1[:] = stocks.stock_symbols[:]
    stock_names[:],stock_symbols[:] = sort_l_r(data_l_h1, data_s_h1)

def sort_symb_r():
    data_l_h3 = []
    data_s_h3 = []
    data_l_h3[:] = stocks.stock_names[:]
    data_s_h3[:] = stocks.stock_symbols[:]
    stock_symbols[:], stock_names[:] = sort_l_r(data_s_h3, data_l_h3)

def resetsearch():
    stock_names[:] = stocks.stock_names

def main():
    global symbdatt
    symbdatt = yf.Ticker("AAPL")
    return "6"

def main2():
    global symbdatt
    today = datetime.today().isoformat()
    symbk = symbdatt.history(period='1d',start ='2022-1-1',end=today[:10])
    last_price = symbk['Close'].iloc[-1]
    return last_price     

def main3():
    global symbdatt
    bio = io.BytesIO()
    symb_df = symbdatt.history(period="1wk")
    print('s')
    plt.plot(symb_df['Close'])
    plt.savefig(bio, format="png")
    b = bio.getvalue()
    return b



def price_stock(symbol):
    global symbdat
    symbdat = yf.Ticker(symbol)
    today = datetime.today().isoformat()
    symbk = symbdat.history(period='1d',start ='2022-1-1',end=today[:10])
    last_price = symbk['Close'].iloc[-1]
    return roundToNearestZero(last_price)


def stock_descr(symbol):
    link = "https://finance.yahoo.com/quote/" + symbol + "/profile?p=" + symbol + '"'
    req = Request(link, headers={'User-Agent': 'Mozilla/5.0'})
    webpage = urlopen(req).read()
    soup = BeautifulSoup(webpage, 'html.parser')
    descr = soup.find_all(class_='Mt(15px) Lh(1.6)')[0].get_text()
    return descr




def stock_rating(symbol):
    y_date = datetime.today()
    begin = datetime.now()
    r_date = y_date - relativedelta(months=3)
    current_date = y_date.strftime("%Y/%m/%d")
    past_date = r_date.strftime("%Y/%m/%d")
    current_date = current_date.replace("/", "-")
    past_date = past_date.replace("/", "-")
    x = 1
    overall_rat = 0
    symbdate = yf.Ticker(symbol)
    try:
        recc2 = ['symbdate.recommendations[To Grade][symbdate.recommendations.index > past_date]']
        recz2 = ['symbdate.recommendations[Action][symbdate.recommendations.index > past_date]']
        i = 0
        overall_rat = 0
        average_rat = 0
        am_sto = 0
        while(i < len(recc2)):
            print(recz2[i])
            if(recc2[i] == "Overweight"):
                average_rat += 4
            if(recc2[i] == "Underweight"):
                average_rat += 2
            if(recc2[i] == "Buy"):
                average_rat += 4
            if(recc2[i] == "Sell"):
                average_rat += 1
            if(recc2[i] == "Strong Buy"):
                average_rat += 5
            if(recc2[i] == "Outperform"):
                average_rat += 3.5
            if(recc2[i] == "Underperform"):
                average_rat += 2.5
            if(recc2[i] == "Peer perform"):
                average_rat += 3
            if(recc2[i] == "Neutral"):
                average_rat += 3
            if(recc2[i] == "Market Perform"):
                average_rat += 3
            if(recz2[i] == "down"):
                average_rat -= 1
            if(recz2[i] == "up"): 
                average_rat += 1
            i += 1
        if(i > 0):
            overall_rat = average_rat / i
        else:
            overall_rat = 3
        print(overall_rat)
    except:
        overall_rat = 0.5
    print(datetime.now() - begin)
    return overall_rat

    

def pr_stck_1wk(symbol):
    symbdatt = yf.Ticker(symbol)
    symb_df = symbdatt.history(period="1wk")
    print(symb_df)
    bio = io.BytesIO()
    plt.style.use('dark_background')
    plt.figure(facecolor='yellow')
    plt.plot(symb_df['Close'])
    plt.gca().margins(x=0)
    N = 9
    ax = plt.axes()
    plt.gcf().canvas.draw()
    tl = plt.gca().get_xticklabels()
    maxsize = max([t.get_window_extent().width for t in tl])
    m = 0.2 # inch margin
    s = maxsize/plt.gcf().dpi*N+2*m
    margin = m/plt.gcf().get_size_inches()[0]
    plt.gcf().subplots_adjust(left=1.-margin, right=1.-margin)
    plt.gcf().set_size_inches(s, plt.gcf().get_size_inches()[1])
    plt.savefig(bio, format="png")
    b = bio.getvalue()
    return b
    plt.show()
def pr_stck_3m(symbol):
    y_date = datetime.today()
    begin = datetime.now()
    r_date = y_date - relativedelta(months=3)
    current_date = y_date.strftime("%Y/%m/%d")
    past_date = r_date.strftime("%Y/%m/%d")
    current_date = current_date.replace("/", "-")
    past_date = past_date.replace("/", "-")
    symbdatt = yf.Ticker(symbol)
    symb_df = symbdatt.history(start = past_date,  end = current_date)
    print(symb_df)
    bio = io.BytesIO()
    plt.style.use('dark_background')
    plt.plot(symb_df['Close'])
    plt.gca().margins(x=0)
    N = 9
    plt.gcf().canvas.draw()
    tl = plt.gca().get_xticklabels()
    maxsize = max([t.get_window_extent().width for t in tl])
    m = 0.4 # inch margin
    s = maxsize/plt.gcf().dpi*N+2*m
    margin = m/plt.gcf().get_size_inches()[0]

    plt.gcf().subplots_adjust(left=margin, right=1.-margin)
    plt.gcf().set_size_inches(s, plt.gcf().get_size_inches()[1])
    plt.savefig(bio, format="png")
    b = bio.getvalue()
    return b
def pr_stck_6m(symbol):
    y_date = datetime.today()
    begin = datetime.now()
    bio = io.BytesIO()
    r_date = y_date - relativedelta(months=6)
    current_date = y_date.strftime("%Y/%m/%d")
    past_date = r_date.strftime("%Y/%m/%d")
    current_date = current_date.replace("/", "-")
    past_date = past_date.replace("/", "-")
    symbdatt = yf.Ticker(symbol)
    symb_df = symbdatt.history(start = past_date,  end = current_date)
    print(symb_df)
    plt.plot(symb_df['Close'])
    plt.savefig(bio, format="png")
    b = bio.getvalue()
    return b
def pr_stck_1y(symbol):
    symbdatt = yf.Ticker(symbol)
    symb_df = symbdatt.history(period="1y")
    print('s')
    bio = io.BytesIO()
    plt.plot(symb_df['Close'])
    plt.savefig(bio, format="png")
    b = bio.getvalue()
    return b
def pr_stck_5y(symbol):
    symbdatt = yf.Ticker(symbol)
    bio = io.BytesIO()
    symb_df = symbdatt.history(period="5y")
    print('s')
    plt.plot(symb_df['Close'])
    plt.savefig(bio, format="png")
    b = bio.getvalue()
    return b



