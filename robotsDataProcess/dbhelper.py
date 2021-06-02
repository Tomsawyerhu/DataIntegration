import pymysql

conn = pymysql.connect(
    host='localhost',
    user='root',
    password='root',
    db='test',
    charset='utf8'
)
cursor = conn.cursor()


def execute_sql_file_without_return(file_path):
    # 以分号切割
    sql_list = None
    with open(file=file_path, encoding='utf8', mode='r+') as f:
        sql_list = f.read().split(';')
        sql_list = [x.replace('\n', '') for x in sql_list]
    for sql in sql_list:
        try:
            cursor.execute(sql)
        except Exception as e:
            continue


def insert_with_commit(table_name, record, attributes=None):
    r = insert_without_commit(table_name, record, attributes)
    if r >0:
        conn.commit()


def insert_without_commit(table_name, record, attributes=None):
    sql_template1 = "INSERT INTO %s VALUES(%s);"
    sql_template2 = "INSERT INTO %s(%s) VALUES(%s);"
    sql = ""
    if attributes is None:
        sql = sql_template1 % (table_name, ','.join(["\'" + str(x) + "\'" for x in record]))
    else:
        sql = sql_template2 % (table_name, ','.join(attributes), ','.join(["\'" + str(x) + "\'" for x in record]))
    print(sql)
    try:
        cursor.execute(sql)
        return 1
    except:
        return -1


def commit():
    conn.commit()


def close_cursor():
    cursor.close()


def close_conn():
    conn.close()
