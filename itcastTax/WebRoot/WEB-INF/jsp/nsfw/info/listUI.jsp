<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>
    <script type="text/javascript">
  	//全选、全反选
	function doSelectAll(){
		$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
	}
  	//新增
  	function doAdd(){
  		document.forms[0].action = "${basePath}nsfw/info_addUI.action";
  		document.forms[0].submit();
  	}
  	//编辑
  	function doEdit(id){
  		document.forms[0].action = "${basePath}nsfw/info_editUI.action?info.infoId=" + id;
  		document.forms[0].submit();
  	}
  	//删除
  	function doDelete(id){
  		document.forms[0].action = "${basePath}nsfw/info_delete.action?info.infoId=" + id;
  		document.forms[0].submit();
  	}
  	//批量删除
  	function doDeleteAll(){
  		document.forms[0].action = "${basePath}nsfw/info_deleteSelected.action";
  		document.forms[0].submit();
  	}
  	//异步信息发布
  	function doPublicInfo(infoId,state){
  	$.ajax({
  		url:"${basePath}nsfw/info_publicInfo.action",
  		type:"get",
  		data:{"info.infoId":infoId,"info.state":state},
  		success:function(msg){
  			if("更新状态成功"==msg){
  				if(state==1){//现在信息的状态为 发布，操作栏显示 停用
  					//1、更新状态栏的显示值
  					$("#state_"+infoId).text("发布");
  			    	//2、更新操作栏的操作值
  			    	$("#oper_"+infoId).html("<a href=\"javascript:doPublicInfo('"+infoId+"',0)\">停用</a>");
  					
  				}else{//现在信息的状态为 停用，操作栏显示 发布
  					//1、更新状态栏的显示值
  					$("#state_"+infoId).text("停用");
  					$("#oper_"+infoId).html("<a href=\"javascript:doPublicInfo('"+infoId+"',1)\">发布</a>");
  				}
  			}else{
  				alert("更新信息状态失败");
  			}
  		},
  		error:function(){alert("更新信息状态失败");}	
  	});
  		
  	}
  	
  	var list_url="${basePath}nsfw/info_listUI.action";
  	//搜索
  	function doSearch(){
  		//搜索条件时回到第一页
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();
  	}
  	

    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong></div> </div>
                <div class="search_art">
                    <li>
                        信息标题：<s:textfield name="info.title" cssClass="s_text" id="infoTitle"  cssStyle="width:160px;"/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                    </li>
                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td align="center">信息标题</td>
                            <td width="120" align="center">信息分类</td>
                            <td width="120" align="center">创建人</td>
                            <td width="140" align="center">创建时间</td>
                            <td width="80" align="center">状态</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                        <s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='infoId'/>"/></td>
                                <td align="center"><s:property value="title"/></td>
                                <td align="center">
                                	<s:property value="#infoTypeMap[type]"/>	
                                </td>
                                <td align="center"><s:property value="creator"/></td>
                                <td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm"/></td>
                                <td align="center" id="state_<s:property value='infoId'/>"><s:property value="state==1?'发布':'停用'"/></td>
                                <td align="center">
                                	<span id="oper_<s:property value='infoId'/>" >
                                		<s:if test="state==1">
                                			<a href="javascript:doPublicInfo('<s:property value='infoId'/>', 0)">停用</a>
                                		</s:if><s:else>
                                			<a href="javascript:doPublicInfo('<s:property value='infoId'/>', 1)">发布</a>
                                		</s:else>
                                	</span>
                                    <a href="javascript:doEdit('<s:property value='infoId'/>')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='infoId'/>')">删除</a>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </div>
                 
                 <jsp:include page="/common/pageNavigator.jsp"></jsp:include>
    </div>
</form>

</body>
</html>