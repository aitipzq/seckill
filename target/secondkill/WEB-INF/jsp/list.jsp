
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <%@include file="common/head.jsp"%>
    <title>秒杀列表页</title>
</head>
<body>
   <div class="container">
       <div class="panel panel-default text-center">
           <div class="panel-heading">秒杀列表页</div>
           <div class="panel-body">
               <table class="table table-hover">
                   <thead>
                    <tr>
                        <th>商品名称</th>
                        <th>库存</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>创建时间</th>
                        <th>详情页</th>
                    </tr>
                   </thead>
                   <tbody>
                       <c:forEach items="${list}" var="sk">
                           <tr>
                               <td>${sk.name}</td>
                               <td>${sk.number}</td>
                               <td>
                                   <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                               </td>
                               <td>
                                   <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                               </td>
                               <td>
                                   <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                               </td>
                               <td><a class="btn btn-default" href="${ctx}/seckill/${sk.seckillId}/detail" role="button" target="_blank">Link</a></td>
                           </tr>
                       </c:forEach>
                   </tbody>
               </table>
           </div>
       </div>
   </div>


</body>
</html>
