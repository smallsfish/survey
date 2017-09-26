<%
	String baseUrl = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<base href="<%=baseUrl%>">
<%--引入js--%>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/system.js"></script>
<script src="layui/layui.js"></script>