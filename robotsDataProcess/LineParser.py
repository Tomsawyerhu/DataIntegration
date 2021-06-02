import re


def parse_line(line: str):
    clean_line = line.strip()
    if parse_login(clean_line):
        return parse_ip(clean_line), parse_session_id(clean_line), parse_time(clean_line), parse_user_id(
            clean_line), parse_pwd(clean_line), parse_authcode(clean_line), parse_login_succ(clean_line)
    else:
        return parse_session_id(clean_line), parse_time(clean_line), parse_action(clean_line), parse_user_id(
            clean_line), parse_item_id(clean_line), parse_category_id(clean_line)


def parse_login(line: str):
    """
    if isLogin
    return  {ip,sessionId,time,userId,password,authcode,success}
    else return
    {sessionId,time,action,userId,itemId,categoryId}
    """
    s = "uri=/user/login"
    return re.search(s, line) is not None


def parse_ip(line: str):
    s = "\\[IPADDR=([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+)\\]"
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def parse_session_id(line: str):
    s = "\\[SESSIONID=([0-9a-z]+)\\]"
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def parse_time(line: str):
    s = "\d{4}-\d{2}-\d{2}\s+\d{2}:\d{2}:\d{2}"
    pattern = re.compile(s)
    return pattern.search(line).group(0)


def parse_user_id(line: str):
    s = "\"userId\" : \"(\d+)\""
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def parse_pwd(line: str):
    s = "\"password\" : \"([0-9a-z]+)\""
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def parse_authcode(line: str):
    s = "\"authCode\" : \"([0-9a-z]+)\""
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def parse_login_succ(line: str):
    s = "\"success\" : \"([01])\""
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def parse_action(line: str):
    s = "uri=/item/([a-zA-Z]+)"
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def parse_item_id(line: str):
    s = "\"itemId\" : \"(\d+)\""
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def parse_category_id(line: str):
    s = "\"categoryId\" : \"([0-9]+)\""
    pattern = re.compile(s)
    return pattern.search(line).group(1)


def test1():
    s1 = "uri=/user/login"
    s2 = "uri=/user/notlogin"
    print(parse_login(s1))


def test2():
    s = "[IPADDR=139.210.43.58]"
    print(parse_ip(s))


def test3():
    s = "[SESSIONID=7ba93001713eb08f7be369203b9d751f]"
    print(parse_session_id(s))


def test4():
    s = "2017-11-27 06:27:40"
    print(parse_time(s))

def test5():
    s = "\"userId\" : \"880864\""
    print(parse_user_id(s))

def test6():
    s = "\"password\" : \"557a64c8f8bef15639178532f33b8a8f\""
    print(parse_pwd(s))

def test7():
    s = "\"authCode\" : \"52eecb7ecfece1d3a9c58755ed4e53fa\""
    print(parse_authcode(s))

def test8():
    s = "\"success\" : \"0\""
    print(parse_login_succ(s))

def test9():
    s = "uri=/item/getDetail"
    print(parse_action(s))

def test10():
    s = "\"itemId\" : \"953851\""
    print(parse_item_id(s))

def test11():
    s = "\"categoryId\" : \"883960\""
    print(parse_category_id(s))

