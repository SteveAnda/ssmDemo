<%--
  Created by IntelliJ IDEA.
  User: wanganda
  Date: 18-4-17
  Time: 上午10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

var TT = TAOTAO = {
checkLogin : function(){
var _ticket = $.cookie("TOKEN");
if(!_ticket){
return ;
}
$.ajax({
url : "http://localhost:8084/user/token/" + _ticket,
dataType : "jsonp",
type : "GET",
success : function(data){
if(data.status == 200){
var username = data.data.username;
var html = username + "，欢迎来到！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
$("#loginbar").html(html);
}
}
});
}
}

$(function(){
// 查看是否已经登录，如果已经登录查询登录信息
TT.checkLogin();
});
<script type="text/javascript" ></script>
</body>
</html>
