(function ($, layui) {
    var questionnaire = {};
    var life = -1;
    var types = 0;
    var typesHave = false;
    questionnaireNameChange = function () {
        questionnaire.name = $(":input[name='qname']").val();
        $(".questionnaireadd-main-title").text(questionnaire.name);
    }
    questionnaireCompChange = function () {
        questionnaire.comp = $(":input[name='comp']").val();
        $(".questionnaireadd-main-comp").text(questionnaire.comp);
    }
    questionnaireWhereChange = function () {
        questionnaire.where = $(":input[name='qwhere']").val();
        $(".questionnaireadd-main-from").text(questionnaire.where);
    }
    questionnaireDateRangeChange = function () {
        questionnaire.daterange = $(":input[name='daterange']").val();
    }
    questionnaireExplainChange = function () {
        questionnaire.explain = $(":input[name='explain']").val();
        $(".questionnaireadd-main-explain").text(questionnaire.explain);
    }
    questionnaireSubmit = function () {
        layui.use('layer', function () {
            var timeRange=$(":input[name='daterange']").val();
            if(timeRange !== ""){
                questionnaire.daterange = timeRange;
            }
            var questionnaireResult=JSON.stringify(questionnaire);
            if(questionnaireResult.indexOf("说明性问题")!=-1 || questionnaireResult.indexOf("初始选项")!=-1 || questionnaireResult.indexOf("新增选项")!=-1 ){
                layer.msg("系统检测到您有没有修改的初始问题或者选项，请仔细检查后再提交！",{icon:5,time:5000});
                return;
            }
            if (questionnaireResult.indexOf("name")==-1){
                layer.msg("系统检测到您有没有输入问卷名称，请填写完整！",{icon:5,time:5000});
                return;
            }
            if (questionnaireResult.indexOf("questions")==-1){
                layer.msg("系统检测到您有没有输入问卷名称，请填写完整！",{icon:5,time:5000});
                return;
            }
            for(var i=0; i<questionnaire.types.length;i++){
                if(questionnaire.types[i]!=null){
                    if(questionnaire.types[i].questions.length<=0){
                        layer.msg("系统检测到您有没有填写任何的问题或者类别下没有填写问题，请填写完整！",{icon:5,time:5000});
                        return;
                    }else{
                        var qs=questionnaire.types[i].questions.length,count=0;
                            for(var j=0; j< questionnaire.types[i].questions.length;j++){
                                if(questionnaire.types[i].questions[j]==null){
                                    count++;
                                }
                            }
                        if(count==qs){
                            layer.msg("系统检测到您有没有填写任何的问题或者类别下没有填写问题，请填写完整！",{icon:5,time:5000});
                            return;
                        }
                    }

                }
            }
            var loadIndex=layer.load(0);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: 'system/questionnaireAdd',
                data: {'json':questionnaireResult},
                success: function (result) {
                    layer.close(loadIndex);
                    layer.msg(result.msg);
                },
                error: function(data) {
                    layer.close(loadIndex);
                    alert("error:"+data);
                }
            });
        });
    }
    questionnaireAddTypes = function () {
        layui.use('layer', function () {
            layer.open({
                type: 1,
                title: '添加类别',
                content: '<input style="width: 300px;height: 35px;border-radius: 5px;margin: 15px;" placeholder="请输入类别名称" name="typename">',
                skin: 'layui-layer-molv',
                btn: ['确定', '取消'],
                yes: function (index) {
                    var tempname = $(":input[name='typename']").val();
                    if (tempname === "") {
                        layer.msg("请输入类别名称", {icon: 2});
                        return;
                    }
                    if (!typesHave) {
                        questionnaire.types = [];
                        questionnaire.types[types] = {};
                        questionnaire.types[types].title = tempname;
                        questionnaire.types[types].sort = types;
                        questionnaire.types[types].questions = [];
                        typesHave = true;
                    } else {
                        questionnaire.types[types] = {};
                        questionnaire.types[types].title = tempname;
                        questionnaire.types[types].sort = types;
                        questionnaire.types[types].questions = [];
                    }
                    types++;
                    $(".questionnaireadd-main").append("<div class=\"questionnaireadd-main-type\">\n" +
                        "                <div class=\"questionnaireadd-main-type-title\" onclick=\"questionnaireAddMainTypeLife(this,event)\">" + tempname + "<img src=\"img/icon/icon-delete.png\" onclick=\"deleteItemTypes(this,event)\" title=\"删除\"></div>\n" +
                        " <div class=\"questionnaireadd-main-type-content\">\n" +
                        "                    <ul>\n" +
                        "                    </ul>\n" +
                        "                </div>\n" +
                        "            </div>");
                    $(".questionnaireadd-main-type-title:eq(" + (types - 1) + ")").click();
                    layer.close(index);
                }
            });
        });
    }
    questionnaireAddSingleSelection = function () {

        layui.use('layer', function () {
            if (life == -1) {
                layer.msg("请激活一个类别或者添加一个类别！", {icon: 5});
            } else {

                var lis = $(".questionnaireadd-main-type-content:eq(" + life + ") ul li").length;
                $(".questionnaireadd-main-type-content:eq(" + life + ") ul").append("<li>\n" +
                    "                            <label onblur='changeItemTitle(this)' contenteditable=\"true\"><span>*</span>Q" + (lis + 1) + ":说明性问题</label>\n" +
                    "                            <div class=\"questionnaireadd-main-type-content-option\">\n" +
                    "                                <input type=\"radio\"> <span onblur='changeItemOption(this)' contenteditable=\"true\">初始选项</span><br>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"questionnaireadd-main-type-item-tool\" data-type=\"radio\">\n" +
                    "                                <img src=\"img/icon/icon-delete.png\" onclick=\"deleteItemOption(this)\" title=\"删除\">\n" +
                    "                                <img src=\"img/icon/icon-more2.png\" onclick=\"addItemOptions(this)\" title=\"添加选项\">\n" +
                    "                            </div>\n" +
                    "                        </li>");
                questionnaire.types[life].questions[lis] = {};
                questionnaire.types[life].questions[lis].type = 'radio';
                questionnaire.types[life].questions[lis].sort = lis;
                questionnaire.types[life].questions[lis].main = "Q" + (lis + 1) + "说明性问题";
                questionnaire.types[life].questions[lis].options = [];
                //questionnaire.types[life].questions[lis].options[0]={};
                questionnaire.types[life].questions[lis].options[0] = "初始选项";
            }
        });
    }
    questionnaireAddMultiple = function () {

        layui.use('layer', function () {
            if (life == -1) {
                layer.msg("请激活一个类别或者添加一个类别！", {icon: 5});
            } else {
                var lis = $(".questionnaireadd-main-type-content:eq(" + life + ") ul li").length;
                $(".questionnaireadd-main-type-content:eq(" + life + ") ul").append("<li>\n" +
                    "                            <label onblur='changeItemTitle(this)' contenteditable=\"true\"><span>*</span>Q" + (lis + 1) + ":说明性问题</label>\n" +
                    "                            <div class=\"questionnaireadd-main-type-content-option\">\n" +
                    "                                <input type=\"checkbox\"> <span onblur='changeItemOption(this)' contenteditable=\"true\">初始选项</span><br>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"questionnaireadd-main-type-item-tool\" data-type=\"checkbox\">\n" +
                    "                                <img src=\"img/icon/icon-delete.png\" onclick=\"deleteItemOption(this)\" title=\"删除\">\n" +
                    "                                <img src=\"img/icon/icon-more2.png\" onclick=\"addItemOptions(this)\" title=\"添加选项\">\n" +
                    "                            </div>\n" +
                    "                        </li>");
                questionnaire.types[life].questions[lis] = {};
                questionnaire.types[life].questions[lis].type = 'checkbox';
                questionnaire.types[life].questions[lis].sort = lis;
                questionnaire.types[life].questions[lis].main = "Q" + (lis + 1) + "说明性问题";
                questionnaire.types[life].questions[lis].options = [];
                //questionnaire.types[life].questions[lis].options[0]={};
                questionnaire.types[life].questions[lis].options[0] = "初始选项";
            }
        });
    }
    addItemOptions = function (ele) {
        var cur = $('.questionnaireadd-main-type-item-tool').index($(ele).parent('div'));
        var curtypes = $(".questionnaireadd-main-type-content ul").index($($($(ele).parent('div')).parent('li')).parent('ul'));
        var curquedtion = $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li").index($($($(ele).parent('div')).parent('li')));
        var type = $('.questionnaireadd-main-type-item-tool:eq(' + cur + ')').attr('data-type');
        var curspans = $(".questionnaireadd-main-type-content-option:eq(" + cur + ") span").length;
        questionnaire.types[curtypes].questions[curquedtion].options[curspans] = "新增选项";
        $(".questionnaireadd-main-type-content-option:eq(" + cur + ")").append(
            "<input type=\"" + type + "\"> <span onblur='changeItemOption(this)' contenteditable=\"true\">新增选项</span><br>"
        );
    }
    deleteItemOption = function (ele) {
        var cur = $('.questionnaireadd-main-type-item-tool').index($(ele).parent('div'));
        var curtypes = $(".questionnaireadd-main-type-content ul").index($($($(ele).parent('div')).parent('li')).parent('ul'));
        var curquedtion = $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li").index($($($(ele).parent('div')).parent('li')));
        var lis = $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li").length;
        for (var j = curquedtion; j <= lis; j++) {
            if (j == lis) {
                delete questionnaire.types[curtypes].questions[j];
            } else {
                questionnaire.types[curtypes].questions[j] = questionnaire.types[curtypes].questions[j+1];
            }
        }
        $(".questionnaireadd-main-type-content ul li:eq(" + cur + ")").remove();
        lis = $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li").length;
        var tempt="";
        for(var x=0;x<lis;x++){
            tempt=$(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li:eq("+x+") label").text();
            $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li:eq("+x+") label").html("<span>*</span>Q"+(x+1)+tempt.substring(3,tempt.length));
            questionnaire.types[curtypes].questions[x].main="Q"+(x+1)+tempt.substring(3,tempt.length);
            questionnaire.types[curtypes].questions[x].sort=x;
        }
    }
    questionnaireAddMainTypeLife = function (ele,ev) {
        var cur = $(".questionnaireadd-main-type-title").index($(ele));
        $(".questionnaireadd-main-type-life").remove();
        if (life == cur) {
            life = -1;
        } else {
            $(".questionnaireadd-main-type:eq(" + cur + ")").append("<img class=\"questionnaireadd-main-type-life\" src=\"img/icon-white/icon-life.png\">");
            life = cur;
        }
        ev.stopPropagation();
    }
    changeItemOption = function (ele) {
        var curtypes = $(".questionnaireadd-main-type-content ul").index($($($(ele).parent('div')).parent('li')).parent('ul'));
        var curquedtion = $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li").index($($($(ele).parent('div')).parent('li')));
        var curspans = $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li:eq(" + curquedtion + ") span").index($(ele));
        var spans = $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li:eq(" + curquedtion + ") span").length;
        var text = $(ele).text();
        var begin = curspans - 1, end = spans - 2;
        if (text === "") {
            $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li:eq(" + curquedtion + ") input:eq(" + (curspans - 1) + ")").remove();
            $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li:eq(" + curquedtion + ") br:eq(" + (curspans - 1) + ")").remove();
            $(ele).remove();
            for (var i = begin; i <= end; i++) {
                if (i == end) {
                    delete questionnaire.types[curtypes].questions[curquedtion].options[end];
                } else {
                    questionnaire.types[curtypes].questions[curquedtion].options[i] = questionnaire.types[curtypes].questions[curquedtion].options[i + 1];
                }
            }
        } else {
            questionnaire.types[curtypes].questions[curquedtion].options[curspans - 1] = text;
        }
    }
    changeItemTitle = function (ele) {
        var curtypes = $(".questionnaireadd-main-type-content ul").index($($(ele).parent('li')).parent('ul'));
        var curquedtion = $(".questionnaireadd-main-type-content:eq(" + curtypes + ") ul li").index($(ele).parent('li'));
        var text = ($(ele).text()).replace("*", "");
        questionnaire.types[curtypes].questions[curquedtion].main = text;
    }
    deleteItemTypes = function (ele,event) {
        var curtypes=$(".questionnaireadd-main-type-title").index($(ele).parent("div"));
        var typess=$(".questionnaireadd-main-type-title").length;
        for (var i = curtypes; i <= typess; i++) {
            if (i == typess) {
                delete questionnaire.types[i];
            } else {
                questionnaire.types[i]= questionnaire.types[i+1];
            }
        }
        $(".questionnaireadd-main-type:eq("+curtypes+")").remove();
        types--;
        life=-1;
        $(".questionnaireadd-main-type-life").remove();
        typess=$(".questionnaireadd-main-type-title").length;
        for (var j=0;j<typess;j++){
            questionnaire.types[j].sort=j;
        }
        event.stopPropagation();
    }
})(jQuery, layui);