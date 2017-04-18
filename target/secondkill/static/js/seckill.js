var seckill = {
    url : {
         now:function () {
             return '/seckill/now/time';
         },
         exporseUrl:function (seckillId) {
             return '/seckill/' + seckillId + '/expose';
         },
         killUrl:function (seckillId,md5) {
             return'/seckill/'+ seckillId + "/" + md5 + "/excution";
         }
    },
    validatePhone : function (phone) {
        if(phone&&phone.length==11&&!isNaN(phone)){
            return true;
        }
        return false;
    },
    countDown:function (seckillId,now,startTime,endTime) {
        var seckillBox = $('#seckillBox');
        if(now > endTime){
            seckillBox.html("秒杀已结束");
        }else if(startTime > now){
            var killTime = new Date(startTime);
            seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('秒杀倒计时 %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function () {
                seckill.handleSeckill(seckillId,seckillBox);
            })
        }else {
            seckill.handleSeckill(seckillId,seckillBox);
        }
    },
    handleSeckill:function (seckillId,node) {
        node.hide()
            .html('<button class="btn btn-default" id="startKill">开始秒杀</button>');
        $.post(seckill.url.exporseUrl(seckillId),{},function (result) {
            if(result && result.success){
                var data = result.data;
                var md5 = data.md5;
                $('#startKill').one('click',function () {
                    $.post(seckill.url.killUrl(seckillId,md5),{},function (result) {
                        if(result && result.success){
                            var info = result.data;
                            node.html('<label class="label label-success">' + info.stateInfo + '</label>')
                        }else {
                           var now = data.now;
                           var startTime = data.start;
                           var end = data.end;
                           seckill.countDown(seckillId,now,startTime,end);
                        }
                    })
                })
            }else{
                alert("系统错误");
            }
            node.show();
        });
    },
    detail:{
        init:function (params) {
            var killPhone = $.cookie('killPhone');
            if(!seckill.validatePhone(killPhone)){
                var phoneModal = $('#phoneModal');
                phoneModal.modal({
                    show:true,
                    keyboard:false,
                    backdrop:'static'
                })
            }
            $('#login').click(function () {
                var phone = $('#killPhone').val();
                if(seckill.validatePhone(phone)){
                    $.cookie('killPhone',phone,{expires:7,path:"/seckill"});
                    location.reload();
                }else {
                    $('#message').hide().html('<laber class="label label-danger">手机号错误</laber>').show(300);
                }
            });
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime =  params['endTime'];
            $.get(seckill.url.now(),{},function (result) {
                if(result.success){
                    var nowTime = result.data
                   seckill.countDown(seckillId,nowTime,startTime,endTime);
                }else {
                    alert("未知错误")
                }
            });

        }
    }

}