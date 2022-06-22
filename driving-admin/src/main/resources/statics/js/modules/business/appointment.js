$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/appointment/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '教练', name: 'coachName', width: 80 },
			{ label: '车辆', name: 'carNumber', width: 80 },
            { label: '时间段', name: 'period', width: 60, formatter: function(value, options, row){
                    return value === 0 ?
                        '上午':
                        '下午';
                }},
			{ label: '日期', name: 'day', index: 'day', width: 80 },
            { label: '学员姓名', name: 'studentName', width: 80 },
            { label: '确认', name: 'confirm', width: 60, formatter: function(value, options, row){
                    return value === 0 ?
                        '学员未来':
                        '学员应邀';
                }}
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
    $("#day").datetimepicker({
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
		appointment: {},
        carList: {},
        coachList: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.appointment = {};
            this.getCarList();
            this.getCoachList();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
            this.getCarList();
            this.getCoachList();
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.appointment.id == null ? "business/appointment/save" : "business/appointment/update";
                if($("#day").val() == ""){
                    alert("日期不能为空");
                    $('#btnSaveOrUpdate').button('reset');
                    $('#btnSaveOrUpdate').dequeue();
                    return;
                }
                if(vm.appointment.period == undefined){
                    alert("时间段不能为空！");
                    return;
                }
                vm.appointment.day = $("#day").val();
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.appointment),
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
                        url: baseURL + "business/appointment/delete",
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
			$.get(baseURL + "business/appointment/info/"+id, function(r){
                vm.appointment = r.appointment;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        getCarList: function(){
            $.get(baseURL + "business/car/select", function(r){
                vm.carList = r.list;
            });
        },
        getCoachList: function(){
            $.get(baseURL + "sys/user/select", function(r){
                vm.coachList = r.list;
            });
        },
        book: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            var rowData = $("#jqGrid").jqGrid('getRowData',id);
            if(rowData.studentName != ""){
                alert("该记录已被其他学员预约");
                return;
            }
            $.get(baseURL + "business/appointment/book/"+id, function(r){
                alert("预约成功！");
                vm.reload();
            });
        },
        confirm: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            $.get(baseURL + "business/appointment/confirm/"+id, function(r){
                alert("确认成功！");
                vm.reload();
            });
        }
	}
});