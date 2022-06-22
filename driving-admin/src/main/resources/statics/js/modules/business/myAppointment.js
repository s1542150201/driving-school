$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/appointment/myList',
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
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});