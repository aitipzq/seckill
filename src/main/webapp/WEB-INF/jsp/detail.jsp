<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <%@include file="common/head.jsp"%>
    <title>秒杀详情页</title>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">秒杀详情页</div>
            <div class="panel-body">
                <h1 class="center">${seckill.name}</h1>
                <h2 class="text-danger">
                    <span class="glyphicon glyphicon-time"></span>
                    <span class="glyphicon" id="seckillBox"></span>
                </h2>
            </div>
        </div>

        <div id="phoneModal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">请输入手机号</h4>
                    </div>
                    <div class="modal-body">
                        <input type="text" id="killPhone" class="form-control" placeholder="手机号" aria-describedby="basic-addon2">
                    </div>
                    <div class="modal-footer">
                        <span id="message" class="glyphicon"></span>
                        <button type="button" id="login" class="btn btn-primary">login</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </div>

</body>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="http://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
<script src="/static/js/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
   $(function(){
       seckill.detail.init({
           seckillId:${seckill.seckillId},
           startTime:${seckill.startTime.time},
           endTime:${seckill.endTime.time}
       });
   });
</script>
</html>
