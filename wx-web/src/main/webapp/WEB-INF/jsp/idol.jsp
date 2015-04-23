<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设置我的偶像</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" type="text/css" href="../../wx/static/css/idol.css">
<script>
var idols =[]
<c:forEach items="${idol}" var="info" varStatus="vs">
 idols.push(${info.idol_id});

	</c:forEach>
	//var members = ["陈怡凡","代雨丹","宫冬雪","黄艺林","苏 鑫","覃婵婵","","","","","","","","","","","","","","",]
</script>
</head>
<body>

	<div>
		<div class="my-idol">
			<h2>我关注的偶像：</h2>
			<ul id="idols" _fans_id="${fans_id}">
			    <c:forEach items="${idol}" var="info" varStatus="vs">
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-${info.idol_id}" width="20%" data-id="${info.idol_id}"></div>
						</div>
						<a>${info.idol_name}</a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="all" id="list">
			<section class="white">
				<h2>白队</h2>
				<ul>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-1" width="20%" data-id="1"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-2" width="20%" data-id="2"></div>
						</div>
						<a>代雨丹</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-3" width="20%" data-id="3"></div>
						</div>
						<a>宫冬雪</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-4" width="20%" data-id="4"></div>
						</div>
						<a>黄艺林</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-5" width="20%" data-id="5"></div>
						</div>
						<a>苏 鑫</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-6" width="20%" data-id="6"></div>
						</div>
						<a>覃婵婵</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-7" width="20%" data-id="7"></div>
						</div>
						<a>魏雨欣</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-8" width="20%" data-id="8"></div>
						</div>
						<a>闫 莉</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-9" width="20%" data-id="9"></div>
						</div>
						<a>赵亚男</a>
					</li>
					
				</ul>
			</section>
			<section class="red">
				<h2>红队</h2>
				<ul>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-10" width="20%" data-id="10"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-11" width="20%" data-id="11"></div>
						</div>
						<a>范 薇</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-12" width="20%" data-id="12"></div>
						</div>
						<a>韩净丹</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-13" width="20%" data-id="13"></div>
						</div>
						<a>姜京京</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-14" width="20%" data-id="14"></div>
						</div>
						<a>李 玲</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-15" width="20%" data-id="15"></div>
						</div>
						<a>马剑越</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-16" width="20%" data-id="16"></div>
						</div>
						<a>吴 茜</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-17" width="20%" data-id="17"></div>
						</div>
						<a>严文竹</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-18" width="20%" data-id="18"></div>
						</div>
						<a>张 扬</a>
					</li>
				</ul>
			</section>

		</div>
	</div>
	<script type="text/javascript" src="../../wx/static/js/jquery-2.1.1.js"></script>
	<script type="text/javascript" src="../../wx/static/js/idol.js"></script>
	<script type="text/javascript" charset="utf-8" src="http://172.19.28.57:8132/livereload.js"></script>
<script type="text/javascript" charset="utf-8" src="http://172.19.28.57:8132/livereload.js"></script></body>
</html>