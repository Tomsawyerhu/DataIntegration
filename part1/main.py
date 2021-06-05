import time
import pymysql
import re
import matplotlib.pyplot as plt


# 绘制折线图
# @param data   传入的数据，为一个二维列表
#               其中一维列表内元素为(y,x)坐标对，二维列表元素为一维列表
#               example: data = [[(y0,x0),(y1,x1)],[(y3,x3),(y4,x4)]]
#               于是可以绘制多条折线
# @param labels 每条折线的标签
def line_chart(data, labels, title):
    font1 = {
        'weight': 'normal',
        'size': 7}
    for i in range(0, len(data)):
        x = []
        y = []
        for j in range(0, len(data[i])):
            x.append(data[i][j][1])
            y.append(data[i][j][0])
        plt.plot(x, y, label=labels[i])
    plt.title(title)
    plt.legend(loc="upper right", prop=font1)
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


def data_transfer(src):
    data = []
    for i in range(1, len(src[0])):
        tmp = []
        for j in range(len(src)):
            if i == 1:
                tmp.append((src[j][i] / 10, src[j][0]))
            else:
                tmp.append((src[j][i], src[j][0]))
        data.append(tmp)
    return data


def daily_op_type_buy_chart():
    # 求日比值时，由于浏览次数过大，导致剩下三条线不明显，因此以下代码将 浏览数/购买的比值缩小十倍
    data = []
    res = [['11-25', 25.217593104889776, 1.0, 1.4465469120775085, 0.8084233951778491],
           ['11-26', 38.111217832234395, 1.0, 2.314305245487427, 1.153003478531748],
           ['11-27', 26.653708142403946, 1.0, 1.5435248501938668, 0.837805780754318],
           ['11-28', 24.73620682034612, 1.0, 1.4207125722758194, 0.7993554945695194],
           ['11-29', 27.85988992692068, 1.0, 1.621949225916216, 0.874127377197072],
           ['11-30', 36.95467250471588, 1.0, 2.303248103322456, 1.1343164387899818],
           ['12-01', 30.639751915135324, 1.0, 1.9138826202428652, 0.9429496921966871],
           ['12-02', 22.153141041310192, 1.0, 1.3143893098671882, 0.6693554958037969],
           ['12-03', 33.180758017492714, 1.0, 2.0466472303206995, 1.1661807580174928]]

    data = data_transfer(res)
    labels = ["detail", "buy", "cart", "favor"]
    title = "op_type/buy daily"
    line_chart(data, labels, title)


def hourly_op_type_buy_chart():
    res = [[0, 2.845331520731632, 1.0, 1.6186893653834271, 0.9258635605264784],
           [1, 2.1347907725321886, 1.0, 1.1949213161659513, 0.7104569027181689],
           [2, 1.532865954729491, 1.0, 0.7916904431944785, 0.04706393847993261],
           [3, 1.151269413719984, 1.0, 0.5896039887672646, 0.3350908361510688],
           [4, 1.084820366404949, 1.0, 0.5539793004996431, 0.2876219367118725],
           [5, 1.280257535512184, 1.0, 0.7150075066404896, 0.3573738306963853],
           [6, 2.1249766791044777, 1.0, 1.2973180970149254, 0.6522621268656716],
           [7, 2.6218715691929813, 1.0, 1.610390577026851, 0.8472116261327478],
           [8, 2.6401963655635052, 1.0, 1.5711197578901859, 0.8762673807232613],
           [9, 2.546659029751548, 1.0, 1.4979953097813752, 0.8473301416791847],
           [10, 2.4836211659448573, 1.0, 1.4591420271257731, 0.8242527936694648],
           [11, 2.502353544297898, 1.0, 1.4914386913361608, 0.8367898587873421],
           [12, 2.620315674618105, 1.0, 1.5097354955226128, 0.8406200617051697],
           [13, 2.752168142397411, 1.0, 1.6073071398701841, 0.87343866475155],
           [14, 2.750052417894516, 1.0, 1.5707069744391284, 0.8610639879534148],
           [15, 2.7263532044671654, 1.0, 1.5792504991552756, 0.8547183886609474],
           [16, 2.7247447468376484, 1.0, 1.6115389529683706, 0.8634932803661243],
           [17, 2.883629326829492, 1.0, 1.7059530761567792, 0.9240836797087012],
           [18, 3.0095765341429113, 1.0, 1.6966314708574235, 0.9034087104047146],
           [19, 3.265884349517778, 1.0, 1.8346061509168425, 0.946096692743905],
           [20, 3.6319800002183382, 1.0, 2.1249003831835895, 1.0284603879870307],
           [21, 3.9303445575567735, 1.0, 2.3564212999216916, 1.1172083007047768],
           [22, 4.006742028691641, 1.0, 2.5684087798448827, 1.2265321640391342],
           [23, 3.7566777696951132, 1.0, 2.596002909759179, 1.2804727082455938]]
    data = data_transfer(res)
    title = "op_type/buy hourly"
    labels = ["detail", "buy", "cart", "favor"]
    line_chart(data, labels, title)


def daily_uv_pv_chart():
    # 获取数据的sql
    # daypvsql = "select count(*),DATE_FORMAT(op_time,'%m-%d') date" \
    #            " from floatdata " \
    #            "group by date "
    # dayuvsql = "SELECT count(*),op_time date " \
    #            "FROM (SELECT DISTINCT user_id, DATE_FORMAT( op_time, '%m-%d') op_time " \
    #            "     FROM floatdata ) AS t " \
    #            "GROUP BY date"
    #
    # # 每个小时访问量 pv
    # # 每小时访问量 uv
    #
    data = [((496688, '11-25'), (486517, '11-26'), (723829, '11-27'), (622483, '11-28'), (724796, '11-29'),
             (354450, '11-30'), (752448, '12-01'), (161558, '12-02'), (9207, '12-03')),
            ((4631518, '11-25'), (4920630, '11-26'), (17041881, '11-27'), (6957562, '11-28'), (10357691, '11-29'),
             (3006194, '11-30'), (11235365, '12-01'), (1234020, '12-02'), (12826, '12-03'))]
    # 由于日uv和pv差别太大，为了使得对比更清晰，增加一条10*uv线
    tmp = []
    for i in range(0, len(data[0])):
        tmp.append((data[0][i][0] * 10, data[1][i][1]))
    data.append(tmp)

    # 获取数据并绘制折线图
    # data = []
    # conn = get_conn('localhost', "root", "mysql057048", "di")
    # resdayuv = get_data_from_database(conn, dayuvsql)
    # resdaypv = get_data_from_database(conn, daypvsql)
    # data.append(resdayuv)
    # data.append(resdaypv)

    labels = ["uv", "pv", "10*uv"]
    title = "daily"
    line_chart(data, labels, title)


def hourly_uv_pv_chart():
    # hourpvsql = "select count(*),DATE_FORMAT(op_time,'%H') date" \
    #             " from floatdata " \
    #             "group by date"
    #
    # houruvsql = "SELECT count(*),t.op_time FROM (SELECT DISTINCT user_id, DATE_FORMAT(op_time, '%H') op_time from floatdata) t group by t.op_time"

    data = [((128824, '00'), (68522, '01'), (42422, '02'), (33189, '03'), (30885, '04'), (41344, '05'), (88336, '06'),
             (147234, '07'), (196365, '08'), (239013, '09'), (272452, '10'), (273036, '11'), (286287, '12'),
             (290279, '13'), (277830, '14'), (277905, '15'), (273914, '16'), (321203, '17'), (332797, '18'),
             (343968, '19'), (334795, '20'), (353590, '21'), (333103, '22'), (244947, '23')), (
                (241351, '00'), (132101, '01'), (82827, '02'), (64423, '03'), (59759, '04'), (76154, '05'),
                (145454, '06'),
                (222806, '07'), (293861, '08'), (348979, '09'), (393337, '10'), (401306, '11'), (409345, '12'),
                (415965, '13'), (395420, '14'), (345570, '15'), (341815, '16'), (381599, '17'), (392842, '18'),
                (410817, '19'), (451924, '20'), (492464, '21'), (467954, '22'), (357301, '23'))
            ]

    # data = []
    # conn = get_conn('localhost', "root", "mysql057048", "di")
    # reshouruv = get_data_from_database(conn, houruvsql)
    # reshourpv = get_data_from_database(conn, hourpvsql)
    # data.append(reshouruv)
    # data.append(reshourpv)

    title = "hourly"
    labels = ["uv_hourly", "pv_hourly"]
    line_chart(data, labels, title)


def daily_optype_chart():
    # 获取数据sql
    # 用户日行为分析
    # daytypesql = "select DATE_FORMAT(op_time,'%m-%d') as 'day'," \
    #              "sum(case when type='getDetail' then 1 else 0 end) as 'detail_daily'," \
    #              "sum(case when type='buy' then 1 else 0 end) as 'buy_daily'," \
    #              "sum(case when type='cart' then 1 else 0 end) as 'cart_daily'," \
    #              "sum(case when type='favor' then 1 else 0 end) as 'fav_daily'" \
    #              "from floatdata group by day"
    #
    # 由于浏览次数和其他数据差距过大，于是直接将浏览次数减少十倍
    res = (('11-25', 410204, 162666, 235304, 131503),
           ('11-26', 440436, 115566, 267455, 133248),
           ('11-27', 1512331, 567400, 875796, 475371),
           ('11-28', 615617, 248873, 353577, 198938),
           ('11-29', 920284, 330326, 535772, 288747),
           ('11-30', 268390, 72627, 167278, 82382),
           ('12-01', 997921, 325695, 623342, 307114),
           ('12-02', 108754, 49092, 64526, 32860),
           ('12-03', 1138, 343, 702, 400))
    data = []
    # res = get_data_from_database(conn, daytypesql)
    for i in range(1, len(res[0])):
        tmp = []
        for j in range(len(res)):
            tmp.append((res[j][i], res[j][0]))
        data.append(tmp)
    labels = ["detail", "buy", "cart", "fav"]
    title = "sum_optype_daily"
    line_chart(data, labels, title)


def hourly_optype_chart():
    # 获取数据sql
    # hourtypesql = "select hour(op_time) as 'hour'," \
    #               "sum(case when type='getDetail' then 1 else 0 end) as 'detail_hourly'," \
    #               "sum(case when type='buy' then 1 else 0 end) as 'buy_hourly'," \
    #               "sum(case when type='cart' then 1 else 0 end) as 'cart_hourly'," \
    #               "sum(case when type='favor' then 1 else 0 end) as 'fav_hourly'" \
    #               "from floatdata group by hour"
    # 由于浏览次数和其他数据差距过大，于是直接将浏览次数减少十倍
    res = ((0, 184184, 64732, 104781, 59933),
           (1, 95502, 44736, 53456, 31783),
           (2, 56411, 36801, 29135, 1732),
           (3, 40177, 34898, 20576, 11694),
           (4, 36476, 33624, 18627, 9671),
           (5, 44343, 34636, 24765, 12378),
           (6, 91119, 42880, 55629, 27969),
           (7, 140902, 53741, 86544, 45530),
           (8, 189310, 71703, 112654, 62831),
           (9, 235650, 92533, 138614, 78406),
           (10, 278707, 112218, 163742, 92496),
           (11, 268465, 107285, 160009, 89775),
           (12, 278571, 106312, 160503, 89368),
           (13, 302744, 110002, 176807, 96080),
           (14, 288552, 104926, 164808, 90348),
           (15, 248518, 91154, 143955, 77911),
           (16, 234576, 86091, 138739, 74339),
           (17, 251836, 87333, 148986, 80703),
           (18, 269640, 89594, 152008, 80940),
           (19, 315602, 96636, 177289, 91427),
           (20, 332693, 91601, 194643, 94208),
           (21, 401524, 102160, 240732, 114134),
           (22, 395205, 98635, 253335, 120979),
           (23, 294362, 78357, 203415, 100334))
    data = []
    # res = get_data_from_database(conn, daytypesql)
    for i in range(1, len(res[0])):
        tmp = []
        for j in range(len(res)):
            tmp.append((res[j][i], res[j][0]))
        data.append(tmp)
    labels = ["detail", "buy", "cart", "fav"]
    title = "sum_optype_hourly"
    line_chart(data, labels, title)


# 将不同类型的操作总数去除以购买行为总数，得到 optype/buy的比值
def util(src):
    data = []
    for i in range(len(src)):
        tmp = [i]
        for j in range(1, 5):
            tmp.append(src[i][j] / src[i][2])
        data.append(tmp)
    return data


if __name__ == '__main__':
    # 获取数据库连接
    # conn = get_conn('localhost', "root", "mysql057048", "di")
    # 将流数据添加到数据库
    # i = 1
    # while i <= 10:
    #     fname = "data" + str(i) + ".txt"
    #     print(fname)
    #     add_float_data_to_database(fname, conn)
    #     i = i + 1

    # 平台流量分析
    # 取uv,pv折线图
    # # 每天访问量 uv pv
    # daily_uv_pv_chart()
    #
    # # 每个小时访问量 uv pv
    # hourly_uv_pv_chart()

    # 用户行为分析
    # 用户日行为分析
    # daily_optype_chart()

    # # 用户分时行为分析
    # hourly_optype_chart()

    # 将上述数据全部除以购买数，得到 浏览数/购买、加购物车/购买、收藏/购买 的比值，即多少次相应的操作才促成一次实际购买    # 小时比值
    # hourly_op_type_buy_chart()

    # 日比值
    # 求日比值时，由于浏览次数过大，导致剩下三条线不明显，因此以下代码将 浏览数/购买的比值缩小十倍
    # daily_op_type_buy_chart()

    print("sucess ^_^")
