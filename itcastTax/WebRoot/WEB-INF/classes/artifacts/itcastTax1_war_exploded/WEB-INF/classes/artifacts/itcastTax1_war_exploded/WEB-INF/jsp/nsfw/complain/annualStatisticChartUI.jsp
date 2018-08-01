<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Calendar cal = Calendar.getInstance();
	int curYear = cal.get(Calendar.YEAR);
	//设置当前年度
	request.setAttribute("curYear", curYear);
	
	List yearList = new ArrayList();
	for(int i = curYear; i > curYear-5; i--){
		yearList.add(i);
	}
	//设置最近5年年度列表
	request.setAttribute("yearList", yearList);
	
%>

<!DOCTYPE HTML>
<html>
  <head>
    <%@include file="/common/header.jsp"%>
    <title>年度投诉统计图</title>
    <script type="text/javascript" src="${basePath}js/fusioncharts/fusioncharts.js"></script>
    <script type="text/javascript" src="${basePath}js/fusioncharts/themes/fusioncharts.theme.fint.js"></script>
    <script type="text/javascript">
      $(document).ready(doAnnualStatistic());
      
      //根据年度统计该年度的投诉数
      function doAnnualStatistic(){
      	//1、获取年度
      	var year = $("#year").val();
      	if(year == "" || year == undefined){
      		year = "${curYear}";
      	}
        //2、根据年度获取对应的投诉数并渲染统计图表
		$.ajax({
			url:"${basePath}nsfw/complain_getAnnualStatisticData.action",
			data:{"year": year},
			type:"get",
			dataType:"json",
			success: function(data){
				//将返回的投诉数渲染到统计图表中
				if(data != undefined){
					var revenueChart = new FusionCharts({
			              "type": "line",
			              "renderAt": "chartContainer",
			              "width": "600",
			              "height": "400",
			              "dataFormat": "json",
			              "dataSource":  {
			                "chart": {
			                  "caption": year +" 年年度投诉数统计图",
			                  "xAxisName": "月  份",
			                  "yAxisName": "投  诉  数",
			                  "theme": "fint"
			               },
			               "data": data.chartData
			            }
			        });
			      revenueChart.render();
				}
			},
			error: function(){alert("获取年度投诉数失败！");}
		});
      }
    </script>
  </head>
  
  <body>
  	<br>
  	<div style="width:100%;text-align:center;">
    <s:select id="year" list="#request.yearList" onchange="doAnnualStatistic()"></s:select>
    </div>
    <br>
    <div  style="width:100%;text-align:center;" id="chartContainer"></div>
  </body>
</html>
