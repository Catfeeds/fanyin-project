<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图片管理</title>
    <#include "../../../resources.ftl">
    <script type="text/javascript">
        var dataGrid;

        var winWidth = 480;
        var winHeight = 300;

        var addTitle = "添加图片";
        var addUrl = "/public/operation/image/add_image_page";

        var editTitle = "编辑图片";
        var editUrl = "/public/operation/image/edit_image_page";

        var  delMsg = "删除图片可能导致页面展示问题,确定要执行该操作";
        var delUrl = "/operation/image/delete_image";

        $(function() {
            dataGrid = $("#dataGrid").datagrid({
                url : "/operation/image/image_list_page",
                border : false,
                fit : true,
                fitColumns : false,
                idField : 'id',
                nowrap : false,//可以换行显示
                pagination:true,
                pageSize : pageSize,
                pageList : pageList,
                singleSelect : true,
                columns : [ [
                    {
                        field : "action",
                        title : "操作",
                        width : 90,
                        align : "center",
                        formatter : function(value, row) {
                            var str = '';
                            str += '<dl>';
                            str += '<dt><a href="javascript:void(0);">详情<i class="fa fa-angle-down fa-fw"></i></a></dt>';
                            str += '<dd>';
                            str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.editFun('+row.id+',editTitle,winWidth,winHeight,editUrl);" title="编辑图片"> 编辑</a>';
                            str += '<a href="javascript:void(0);" onclick="$.fn.dataGridOptions.confirm('+row.id+',delUrl,delMsg);" title="删除图片"> 删除</a>';
                            str += '</dd>';
                            str += '</dl>';
                            return str;
                        }
                    },
                    {field : "name",title : "名称",width : 150,align : "center",
                        formatter:function(value,rows){
                            return '<a href="javascript:void(0);" onclick="parent.imagePreview(\''+ rows.url +'\');">' + value + '</a>';
                        }
                    },
                    {field : "typeName",title : "分类",width : 150,align : "center"},
                    {field : "url",title : "路径",width : 600,align : "center" },
                    {field : "size",title : "大小",width : 100,align : "center",
                        formatter:function(value){
                            return (parseFloat(value) / (1024 * 1024)).toFixed(2) + "M";
                        }
                    },
                    {field : "addTime",title : "添加时间",width : 150,align : "center",
                        formatter:function(value){
                            return getLocalTime(value,4);
                        }
                    },
                    {field : "updateTime",title : "更新时间",width : 150,align : "center",
                        formatter:function(value){
                            return getLocalTime(value,4);
                        }
                    },
                    {field : "remark",title : "备注",align : "center",width : 300 }
                ] ]
            });
        });

    </script>
</head>
<body class="tabs_body">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" class="condition_bar">
        <div class="layout_norths">
            <div class="left">
                <form id="queryForm">
                    <input name="queryName" placeholder="名称、备注" /><a href="#" onclick="$.fn.dataGridOptions.searchFun('#queryForm');" class="searchBtn"><i class="fa fa-search"></i>查询</a>
                    <a href="#" class="dropBtn">查询条件<i class="fa fa-angle-double-down"></i></a>
                </form>
            </div>
            <div class="right">
                <a href="#" class="searchBtn" onclick="$.fn.dataGridOptions.editFun(0,addTitle,winWidth,winHeight,addUrl);"><i class="fa fa-plus"></i>添加</a>
            </div>
            <form id="showAdw">
                <ul class="showAdw" style="display: none;">
                    <a href="javascript:void(0);" class="close"><i class="fa fa-remove fa-lg"></i></a>
                    <li>
                        <span>下拉列表</span>
                        <@select name="type" total="true"  title="图片分类" nid="image_log_type"/>
                    </li>
                    <li>
                        <div class="submitBtn">
                            <a href="javascript:void(0);" class="searchBtn"
                               onclick="$.fn.dataGridOptions.searchFun('#showAdw');">确定</a>
                            <a href="javascript:void(0);" class="searchBtn"
                               onclick="$.fn.dataGridOptions.cleanFun('#searchForm');">重置</a>
                        </div>
                    </li>
                </ul>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dataGrid" ></table>
    </div>
</div>
</body>
<script type="text/javascript" src="/static/js/search.js?v=${version!}"></script>
</html>