from time import time

from LineParser import parse_line
from dbhelper import *


def read_file_and_savedb(file_path: str):
    with open(file=file_path, encoding='utf8', mode='r') as f:
        while True:
            line = f.readline()
            if not line:
                commit()
                break
            if len(line.strip()) > 0:
                result = parse_line(line)
                print(result)
                if len(result) == 6:
                    # 用户行为
                    insert_without_commit(table_name="actions",
                                          attributes=['sessionId', 'actiontime', 'actiontype', 'userid', 'itemid',
                                                      'categoryid'], record=list(result))
                else:
                    # 登录
                    insert_without_commit(table_name="logins",
                                          attributes=['ip', 'sessionid', 'logintime', 'userid', 'passwd', 'authcode',
                                                      'success'], record=list(result))



if __name__ == '__main__':
    current1=time()
    fp1 = "C:\\Users\\Administrator\\Desktop\\data1.txt"
    fp2="E:\\myuniversity\\我的项目\\python\\robots\\init.sql"
    #建表
    execute_sql_file_without_return(fp2)
    #插入数据
    read_file_and_savedb(fp1)
    current2 = time()
    print("共用%s秒"%str(current2-current1))

