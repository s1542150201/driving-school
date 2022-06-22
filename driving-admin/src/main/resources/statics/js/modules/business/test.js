$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/test/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '学员姓名', name: 'studentName', index: 'student_id', width: 80 },
			{ label: '考试科目', name: 'subject', index: 'subject', width: 80 }, 			
			{ label: '分数', name: 'score', index: 'score', width: 80 }, 			
			{ label: '考试时间', name: 'createTime', index: 'create_time', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    $("#createTime").datetimepicker({
        format: "yyyy-mm-dd",
        minView: "month",
        autoclose: true,
        startDate: new Date()
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		test: {},
        studentList: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.test = {};
            this.getStudentList();
            $("#km").empty();
            $("#ts").val("");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.test.id == null ? "business/test/save" : "business/test/update";
                if($("#createTime").val() == ""){
                    alert("考试时间不能为空");
                    $('#btnSaveOrUpdate').button('reset');
                    $('#btnSaveOrUpdate').dequeue();
                    return;
                }
                vm.test.createTime = $("#createTime").val();
                console.log(vm.test.subject);
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.test),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "business/test/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "business/test/info/"+id, function(r){
                vm.test = r.test;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        getStudentList: function(){
            $.get(baseURL + "sys/user/studentSelect", function(r){
                vm.studentList = r.list;
            });
        }
	}
});

function selectChange(values) {
    $("#km").empty();
    $.get(baseURL + "business/test/getByStudentId/"+values, function(r){
        if(r.code == 0){
            if(r.test == ""){
                $("#ts").val("该学员未参加过考试，仅能创建科目一！");
                var html = '';
                html += '<label class="radio-inline">';
                html += '<input type="radio" name="subject" value="科目一" v-model="test.subject" checked="checked"/> 科目一';
                html += '</label>';
                $("#km").append(html);
                vm.test.subject = "科目一";
            }else{
                var test = r.test[0];//因为是按照id大小倒叙排序，所以第一个肯定是最新的一次考试
                console.log(test);
                if(test.subject == "科目一"){
                    if(test.score<80){
                        $("#ts").val("该学员科目一少于80分，未通过，仅能创建科目一！");
                        var html = '';
                        html += '<label class="radio-inline">';
                        html += '<input type="radio" name="subject" value="科目一" v-model="test.subject" checked="checked"/> 科目一';
                        html += '</label>';
                        $("#km").append(html);
                        vm.test.subject = "科目一";
                    }else{
                        $("#ts").val("该学员科目一已通过，仅能创建科目二！");
                        var html = '';
                        html += '<label class="radio-inline">';
                        html += '<input type="radio" name="subject" value="科目一" v-model="test.subject" checked="checked"/> 科目二';
                        html += '</label>';
                        $("#km").append(html);
                        vm.test.subject = "科目二";
                    }
                }else if(test.subject == "科目二"){
                    if(test.score<90){
                        $("#ts").val("该学员科目二少于90分，未通过，仅能创建科目二！");
                        var html = '';
                        html += '<label class="radio-inline">';
                        html += '<input type="radio" name="subject" value="科目二" v-model="test.subject" checked="checked"/> 科目二';
                        html += '</label>';
                        $("#km").append(html);
                        vm.test.subject = "科目二";
                    }else{
                        $("#ts").val("该学员科目二已通过，仅能创建科目三！");
                        var html = '';
                        html += '<label class="radio-inline">';
                        html += '<input type="radio" name="subject" value="科目三" v-model="test.subject" checked="checked"/> 科目三';
                        html += '</label>';
                        $("#km").append(html);
                        vm.test.subject = "科目三";
                    }
                }else if(test.subject == "科目三"){
                    if(test.score<90){
                        $("#ts").val("该学员科目三少于90分，未通过，仅能创建科目三！");
                        var html = '';
                        html += '<label class="radio-inline">';
                        html += '<input type="radio" name="subject" value="科目三" v-model="test.subject" checked="checked"/> 科目三';
                        html += '</label>';
                        $("#km").append(html);
                        vm.test.subject = "科目三";
                    }else{
                        $("#ts").val("该学员科目三已通过，仅能创建科目四！");
                        var html = '';
                        html += '<label class="radio-inline">';
                        html += '<input type="radio" name="subject" value="科目四" v-model="test.subject" checked="checked"/> 科目四';
                        html += '</label>';
                        $("#km").append(html);
                        vm.test.subject = "科目四";
                    }
                }else if(test.subject == "科目四"){
                    if(test.score<90){
                        $("#ts").val("该学员科目四少于90分，未通过，仅能创建科目四！");
                        var html = '';
                        html += '<label class="radio-inline">';
                        html += '<input type="radio" name="subject" value="科目四" v-model="test.subject" checked="checked"/> 科目四';
                        html += '</label>';
                        $("#km").append(html);
                        vm.test.subject = "科目四";
                    }else{
                        $("#ts").val("该学员已完成全部科目考试！");
                    }
                }
            }
        }
    });
}