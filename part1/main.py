import time
import pymysql
import re
import matplotlib.pyplot as plt


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


# 绘制折线图
# @param data   传入的数据，为一个二维列表
#               其中一维列表内元素为(y,x)坐标对，二维列表元素为一维列表
#               example: data = [[(y0,x0),(y1,x1)],[(y3,x3),(y4,x4)]]
#               于是可以绘制多条折线
# @param labels 每条折线的标签
def line_chart(data, labels):
    data = eval(data)
    print(data[0][0][0])
    l = len(data)
    for i in range(0, l):
        x = []
        y = []
        for j in range(0, len(data[i])):
            x.append(data[i][j][1])
            y.append(data[i][j][0])
        plt.plot(x, y, label=labels[i])
    plt.legend()
    plt.show()


# 从数据库获取数据
# @param conn       数据库连接
# @param sql        要执行的sql语句
# @return           返回查询结果
def get_data_from_database(conn, sql):
    cursor = conn.cursor()
    cursor.execute(sql)
    res = cursor.fetchall()

    cursor.close()
    print(res)
    print(res[0][1])
    try:
        print(eval(str(res)))
    except Exception:
        print(" eval error")
    return res


# 获取数据库连接
# @param host       数据库主机名
# @param user       用户名
# @param passwd     密码
# @param db         要连接的数据库
# @return           返回数据库连接
def get_conn(host, user, passwd, db):
    return pymysql.connect(host=host, user=user, passwd=passwd, db=db)


# 将数据库文件中的数据保存到数据库的dbdata表
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


# 将流数据保存到数据库的floatdata表
def add_float_data_to_database(filename, conn):
    cursor = conn.cursor()
    fo = open(filename, "r")
    sql = "insert into floatdata (user_id,item_id,category_id,type,op_time) values(%s,%s,%s,%s,%s)"
    lines = 0
    while True:
        line = fo.readline()
        # 判断是否读取完文件
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
                lines = lines + 1
        except Exception:
            print(line)
    conn.commit()
    cursor.close()
    print("success" + str(lines))


if __name__ == '__main__':
    # 获取数据库连接
    conn = get_conn('localhost', "root", "mysql057048", "di")
    # 将流数据添加到数据库
    # i = 1
    # while i <= 10:
    #     fname = "data" + str(i) + ".txt"
    #     print(fname)
    #     add_float_data_to_database(fname, conn)
    #     i = i + 1

    # 平台流量分析
    # 取uv,pv折线图
    # #每天访问量 pv
    # #((4920630, '11-26'), (17041881, '11-27'), (6957562, '11-28'), (10357691, '11-29'), (149145, '11-30'))
    #
    # # 每天访问量 uv
    # # ((486517, '11-26'), (723829, '11-27'), (622483, '11-28'), (724796, '11-29'), (34052, '11-30'))
    #
    # daypvsql = "select count(*),DATE_FORMAT(op_time,'%m-%d') date" \
    #            " from floatdata " \
    #            "group by date "
    # dayuvsql = "SELECT count(*),op_time date " \
    #            "FROM (SELECT DISTINCT user_id, DATE_FORMAT( op_time, '%m-%d') op_time " \
    #            "     FROM floatdata ) AS t " \
    #            "GROUP BY date"
    #
    # # 每个小时访问量 pv
    # # ((885914, '00'), (495254, '01'), (293771, '02'), (224101, '03'), (199556, '04'), (248106, '05'),
    # # (577318, '06'), (1021093, '07'), (1382570, '08'), (1765428, '09'), (2099270, '10'), (2010740, '11'), (2112506,
    # # '12'), (2277894, '13'), (2214822, '14'), (2224014, '15'), (2095530, '16'), (2325879, '17'), (2484685, '18'),
    # # (2852245, '19'), (2436004, '20'), (2657329, '21'), (2609335, '22'), (1933545, '23'))
    #
    # # 每小时访问量 uv
    # # ((128824, '00'), (68522, '01'), (42422, '02'), (33189, '03'), (30885, '04'), (41344, '05'), (88336, '06'),
    # # (147234, '07'), (196365, '08'), (239013, '09'), (272452, '10'), (273036, '11'), (286287, '12'), (290279,'13'),
    # # (277830, '14'), (277905, '15'), (273914, '16'), (321203, '17'), (332797, '18'), (343968, '19'), (334795,'20'),
    # # (353590, '21'), (333103, '22'), (244947, '23'))
    #
    # hourpvsql = "select count(*),DATE_FORMAT(op_time,'%H') date" \
    #             " from floatdata " \
    #             "group by date"
    # houruvsql = "SELECT count(*),op_time date" \
    #             "FROM (SELECT DISTINCT user_id, DATE_FORMAT( op_time, '%H') op_time " \
    #             "     FROM floatdata ) AS t " \
    #             "GROUP BY date"
    #

    # 获取数据并绘制折线图
    # resdayuv = get_data_from_database(conn, dayuvsql)
    # resdaypv = get_data_from_database(conn, daypvsql)
    # data.append(resdayuv)
    # data.append(resdaypv)
    # labels = ["uv_daily", "pv_daily"]
    # line_chart(eval(data), labels)
    #
    # reshouruv = get_data_from_database(conn, houruvsql)
    # reshourpv = get_data_from_database(conn, hourpvsql)
    # data.append(reshouruv)
    # data.append(reshourpv)

    # labels = ["uv_hourly", "pv_hourly"]
    # line_chart(eval(data), labels)

    # # 用户行为分析
    # # 用户日行为分析

    #     (('11-25', 4102045, 162666, 235304, 131503),
    #      ('11-26', 4404361, 115566, 267455, 133248),
    #      ('11-27', 15123314, 567400, 875796, 475371),
    #      ('11-28', 6156174, 248873, 353577, 198938),
    #      ('11-29', 9202846, 330326, 535772, 288747),
    #      ('11-30', 2683907, 72627, 167278, 82382),
    #      ('12-01', 9979214, 325695, 623342, 307114),
    #      ('12-02', 1087542, 49092, 64526, 32860),
    #      ('12-03', 11381, 343, 702, 400))
    #
    # ((0, 1841840, 64732, 104781, 59933),
    #  (1, 955029, 44736, 53456, 31783),
    #  (2, 564113, 36801, 29135, 1732),
    #  (3, 401772, 34898, 20576, 11694),
    #  (4, 364763, 33624, 18627, 9671),
    #  (5, 443430, 34636, 24765, 12378),
    #  (6, 911197, 42880, 55629, 27969),
    #  (7, 1409020, 53741, 86544, 45530),
    #  (8, 1893108, 71703, 112654, 62831),
    #  (9, 2356506, 92533, 138614, 78406),
    #  (10, 2787074, 112218, 163742, 92496),
    #  (11, 2684655, 107285, 160009, 89775),
    #  (12, 2785714, 106312, 160503, 89368),
    #  (13, 3027441, 110002, 176807, 96080),
    #  (14, 2885520, 104926, 164808, 90348),
    #  (15, 2485186, 91154, 143955, 77911),
    #  (16, 2345763, 86091, 138739, 74339),
    #  (17, 2518367, 87333, 148986, 80703),
    #  (18, 2696404, 89594, 152008, 80940),
    #  (19, 3156024, 96636, 177289, 91427),
    #  (20, 3326934, 91601, 194643, 94208),
    #  (21, 4015242, 102160, 240732, 114134),
    #  (22, 3952057, 98635, 253335, 120979),
    #  (23, 2943625, 78357, 203415, 100334))

    # daytypesql = "select DATE_FORMAT(op_time,'%m-%d') as 'day'," \
    #              "sum(case when type='getDetail' then 1 else 0 end) as 'detail_daily'," \
    #              "sum(case when type='buy' then 1 else 0 end) as 'buy_daily'," \
    #              "sum(case when type='cart' then 1 else 0 end) as 'cart_daily'," \
    #              "sum(case when type='favor' then 1 else 0 end) as 'fav_daily'" \
    #              "from floatdata group by day"
    # # # 用户分时行为分析
    #
    # hourtypesql = "select hour(op_time) as 'hour'," \
    #               "sum(case when type='getDetail' then 1 else 0 end) as 'detail_hourly'," \
    #               "sum(case when type='buy' then 1 else 0 end) as 'buy_hourly'," \
    #               "sum(case when type='cart' then 1 else 0 end) as 'cart_hourly'," \
    #               "sum(case when type='favor' then 1 else 0 end) as 'fav_hourly'" \
    #               "from floatdata group by hour"
    # get_data_from_database(conn, daytypesql)
    # get_data_from_database(conn, hourtypesql)
    # 绘制折线图
    # data = []
    # labels = ["buy_daily", "cart_daily", "fav_daily"]
    # res = get_data_from_database(conn, daytypesql)
    # for i in range(1, len(res[0])):
    #     tmp = []
    #     for j in range(len(res)):
    #         tmp.append((res[j][i], res[j][0]))
    #     data.append(tmp)
    # line_chart(data,labels)
    #
    #
    # data = []
    # labels = ["buy_hourly", "cart_hourly", "fav_hourly"]
    # res = get_data_from_database(conn, hourtypesql)
    # for i in range(1, len(res[0])):
    #     tmp = []
    #     for j in range(len(res)):
    #         tmp.append((res[j][i], res[j][0]))
    #     data.append(tmp)
    # line_chart(data,labels)

    itembuyssql = "SELECT item_id, sum( CASE WHEN type = 'buy' THEN 1 ELSE 0 END ) AS 'sumbuy' FROM floatdata  GROUP BY item_id having (sumbuy>0) ORDER BY sumbuy"
    labels = ["buy_of_item"]
    res = get_data_from_database(conn, itembuyssql)
    line_chart(res, labels)
