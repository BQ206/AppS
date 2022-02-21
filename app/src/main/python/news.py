from GoogleNews import GoogleNews
from urllib.request import Request, urlopen
from bs4 import BeautifulSoup
import datetime
import threading


title_n = []
link_n = []
media_n = []
date_n = []

prev = []
prev_myst = []


def callnews():
    link = "https://www.google.com/search?q=stock+news&rlz=1C5GCEM_enIE960IE960&sxsrf=APq-WBvJPuo_Df7blIjWnJport1S4dlBRw:1643384362029&source=lnms&tbm=nws&sa=X&ved=2ahUKEwj96YLp49T1AhVwQEEAHSj3BC4Q_AUoAnoECAEQBA&biw=1440&bih=730&dpr=2"
    if(link not in prev):
        prev.append(link)
    req = Request(link, headers={'User-Agent': 'Mozilla/5.0'})
    webpage = urlopen(req).read()
    global soup
    soup = BeautifulSoup(webpage, 'html.parser')

def nxt_pge():
    ogz = "https://www.google.com/"
    global soup
    next = soup.find('a', attrs={'aria-label':'Next page'})
    next = (next['href'])
    link = ogz + next
    prev.append(link)
    req = Request(link, headers={'User-Agent': 'Mozilla/5.0'})
    webpage = urlopen(req).read()
    soup = BeautifulSoup(webpage, 'html.parser')

def pre_pge(index):
    link = prev[index]
    req = Request(link, headers={'User-Agent': 'Mozilla/5.0'})
    webpage = urlopen(req).read()
    global soup
    soup = BeautifulSoup(webpage, 'html.parser')

def news_r_title(index ):
    global soup
    title = soup.find_all(class_='BNeawe vvjwJb AP7Wnd')[index].get_text()
    title = str(title)
    return title



def news_r_date(index ):
    global soup
    if(index % 2 != 0):
        index += 1
    datz = soup.find_all(class_='xUrNXd UMOHqf')[index].next_element # go up by 2 every time
    return datz



def news_r_media(index ):
    global soup
    media = soup.find_all(class_='BNeawe UPmit AP7Wnd')[index].get_text()
    return media

def news_r_descr(index ):
    global soup
    media = soup.find_all(class_='BNeawe UPmit AP7Wnd')[index].get_text()
    return media

linkz = []
def news_r_link(index ):
    global soup
    islinkin = False
    while(islinkin == False):
        try:
            link = soup.find_all(class_='egMi0 kCrYT')[index]
            link = str(link)
            linkz1 = link.split("url?q=",1)[1]
            linkz2 = linkz1.split(">",1)[0]
            linkz3 = linkz2.split("&amp",1)[0]
            if(linkz3 not in linkz):
                islinkin = True
                linkz.append(linkz3)
            index += 1
        except:
            islinkin = True
    link = str(link)
    linkz1 = link.split("url?q=",1)[1]
    linkz2 = linkz1.split(">",1)[0]
    linkz3 = linkz2.split("&amp",1)[0]
    return linkz3

#/url?q=https://finance.yahoo.com/news/stock-market-news-live-updates-january-28-2022-231329863.html&amp;sa=U&amp;ved=2ahUKEwiJ_9GEpdX1AhVLQkEAHRKFAOIQxfQBegQIABAB&amp;usg=AOvVaw1UzCpjogdWLHhTAq52Q2yC"><h3 class="zBAuLc l97dzf"><div class="BNeawe vvjwJb AP7Wnd">Stock market news live updates: Stocks rebound as inflation rises by most since 1982, Apple gains after earnings</div></h3><div class="BNeawe UPmit AP7Wnd">Yahoo Finance</div></a></div>
#<div class="kCrYT"><a href="/url?q=https://www.nasdaq.com/articles/stock-market-news-for-jan-28-2022&amp;sa=U&amp;ved=2ahUKEwjCxeiupdX1AhXVEcAKHXsmDr8QxfQBegQICRAB&amp;usg=AOvVaw2uRbLnlMp9Y5vwUnW1Ln3x"><h3 class="zBAuLc l97dzf"><div class="BNeawe vvjwJb AP7Wnd">Stock Market News for Jan 28, 2022</div></h3><div class="BNeawe UPmit AP7Wnd">Nasdaq</div></a></div>


myst = ["Ford Motor Company " ,"Apple Inc. " ]
prev_myst = [[]] * len(myst)

def get_s_sto():
    return myst

titles = []
media = []
date = []
linkx = []
descr = []


def reset_titles():
    titles.clear()



def myst_title(stknam,index,index1):
    i = index1
    titles.clear()
    ix = 0
    x = []
    ii = index
    while(ix < 10):
        if(i > len(myst) - 1):
            i = 0
            ii += 1
        x.append(threading.Thread(target=myst_title_t, args=(myst[i],ii)))
        x[ix].start()
        i += 1
        ix += 1
    for t in x:
        t.join()
    titles.append(str(ii))
    titles.append(str(i))
    return titles

def stock_go_up():
    global soup
    next = soup.find('a', attrs={'aria-label':'Next page'})
    next = (next['href'])
    link = ogz + next
    prev_myst.append(link)
    req = Request(link, headers={'User-Agent': 'Mozilla/5.0'})
    webpage = urlopen(req).read()
    soup = BeautifulSoup(webpage, 'html.parser')

isres = 0

def ch_isr():
    global isres
    isres = 1

def ch_isr_f():
    global isres
    isres = 0
linkz = []
def myst_title_t(stknam,index,index2):
    mystlink = stknam.replace(" ", "+")
    mystlink = mystlink + "stock"
    link = "https://www.google.com/search?q=" + mystlink + "&rlz=1C5GCEM_enIE960IE960&sxsrf=APq-WBvJPuo_Df7blIjWnJport1S4dlBRw:1643384362029&source=lnms&tbm=nws&sa=X&ved=2ahUKEwj96YLp49T1AhVwQEEAHSj3BC4Q_AUoAnoECAEQBA&biw=1440&bih=730&dpr=2"
    req = Request(link, headers={'User-Agent': 'Mozilla/5.0'})
    webpage = urlopen(req).read()
    soup = BeautifulSoup(webpage, 'html.parser')
    ogz = "https://www.google.com/"
    global isres
    if(index > 9):
        next = soup.find('a', attrs={'aria-label':'Next page'})
        next = (next['href'])
        link = ogz + next
        req = Request(link, headers={'User-Agent': 'Mozilla/5.0'})
        webpage = urlopen(req).read()
        soup = BeautifulSoup(webpage, 'html.parser')
        prz = [link]
        if(len(prev_myst[index2]) == 0):
            prev_myst[index2] = prz
        else:
            prev_myst[index2] = prev_myst[index2] + prz
        index = 0
    else:
        prz = [link]
        if(len(prev_myst[index2]) == 0):
            prev_myst[index2] = prz
        else:
            prev_myst[index2] = prev_myst[index2] + prz
    if(isres == 1):
        link = prev_myst[index2][0]
        req = Request(link, headers={'User-Agent': 'Mozilla/5.0'})
        webpage = urlopen(req).read()
        soup = BeautifulSoup(webpage, 'html.parser')
    islinkin = False
    indexz = index
    while(islinkin == False):
        try:
            link = soup.find_all(class_='kCrYT')[indexz]
            link = str(link)
            linkz1 = link.split("url?q=",1)[1]
            linkz2 = linkz1.split(">",1)[0]
            linkz3 = linkz2.split("&amp",1)[0]
            if(linkz3 not in linkz):
                islinkin = True
                linkz.append(linkz3)
            indexz += 1
        except: 
            islinkin = True
    link = str(link)
    linkz1 = link.split("url?q=",1)[1]
    linkz2 = linkz1.split(">",1)[0]
    linkz3 = linkz2.split("&amp",1)[0]
    title = soup.find_all(class_='BNeawe vvjwJb AP7Wnd')[index].get_text()
    title = str(title)
    mediaf = soup.find_all(class_='BNeawe UPmit AP7Wnd')[index].get_text()

    if(index % 2 != 0):
        index += 1
    datz = soup.find_all(class_='xUrNXd UMOHqf')[index].next_element # go up by 2 every time
    xz = []
    
    myst_date(datz)
    myst_media(mediaf)
    myst_link(linkz3)
    myst_desc(linkz3)
    titles.append(title)
    return title

def myst_date(date_apnd):
    global gnn
    date.append(date_apnd)

def myst_media(media_apnd):
    global gnn
    media.append(media_apnd)

def myst_link(link_apnd):
    global gnn
    linkx.append(link_apnd)

def myst_desc(desc_apnd):
    global gnn
    descr.append(desc_apnd)

def ret_link(index):
    return linkx[index]

def ret_descr(index):
    return descr[index]

def ret_date(index):
    return date[index]

def ret_media(index):
    return media[index]



begin_time = datetime.datetime.now()





