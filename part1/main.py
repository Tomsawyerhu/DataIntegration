import time
import pymysql
import re


def test(name):
    fo = open(name, "r")
    while True:
        line = fo.readline()
        if line == "":
            break
        strs = line.split()
        timestamp = int(strs[5])
        realtime = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(timestamp))
        print(strs[0], strs[1], strs[2], strs[3], realtime)


def get_data_from_database(cursor, sql):
    cursor.execute(sql)
    res = cursor.fetchall()
    return res


def get_conn(host, user, passwd, db):
    return pymysql.connect(host=host, user=user, passwd=passwd, db=db)


# 将文件中的数据保存到数据库
def add_data_to_database(filename, conn):
    cursor = conn.cursor()
    fo = open(filename, "r")
    sql = "insert into dbdata values(%s,%s,%s,%s,%s)"
    linesum = 0
    while True:
        try:
            line = fo.readline()
            linesum = linesum + 1
            if line == "":
                break
            strs = line.split()
            timestamp = int(strs[5])
            realtime = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(timestamp))
            # 只保留orederid，userid，itemid，categoryid，buytime（将时间戳转换为标准时间格式）
            cursor.execute(sql, (strs[0], strs[1], strs[2], strs[3], realtime))
        except Exception:
            # 遇到数据错误的行，打印行号
            print("error in line " + str(linesum))
    conn.commit()
    cursor.close()
    print("success")


def add_float_data_to_database(filename, conn):
    cursor = conn.cursor()
    fo = open(filename, "r")
    sql = "insert into floatdata (user_id,item_id,category_id,type,op_time) values(%s,%s,%s,%s,%s)"
    lines = 0
    while True:
        line = fo.readline()
        if line == "":
            break
        strs = re.split(" \| requestBody=|uri=| \| requestBody = ", line)
        try:
            # 不记录登陆行为
            if strs[1][0:5] != "/user":
                gettime = strs[0].split()
                t = gettime[1] + " " + gettime[2]
                optype = strs[1].split("/")[2]
                bodyjson = eval(strs[2])
                cursor.execute(sql, (bodyjson["userId"], bodyjson["itemId"], bodyjson["categoryId"], optype, t))
                # print(bodyjson["userId"])
                # print(bodyjson["itemId"])
                # print(bodyjson["categoryId"])
                # print(type)
                # print(t)
                # print()
                lines = lines + 1
        except Exception:
            print(line)
    conn.commit()
    cursor.close()
    print("success" + str(lines))


if __name__ == '__main__':
    conn = get_conn('localhost', "root", "mysql057048", "di")
    # # add_data_to_database("buy_data.txt",conn)
    # # 按照时间段查询
    # sql = "select count(*),DATE_FORMAT(buy_time,'%H') time" \
    #       " from dbdata " \
    #       "group by time "
    # data = get_data_from_database(cursor, sql)
    # for i in data:
    #     print(i)
    i = 1
    while i <= 10:
        fname = "data" + str(i) + ".txt"
        print(fname)
        add_float_data_to_database(fname, conn)
        i = i + 1
    # string = eval('{"userId" : "825352", "itemId" : "3584710", "categoryId" : "3607361"}')
    # print(string['userId'])
