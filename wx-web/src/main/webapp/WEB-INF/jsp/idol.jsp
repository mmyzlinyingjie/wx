<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>1931</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" type="text/css" href="../../wx/static/css/idol.css">
</head>
<body>
	<div>
		<div class="my-idol" id="idols" data-fans_id="${fans_id}">
			<h2>我关注的偶像：</h2>
			<ul>
				<c:forEach items="${idol_id}" var="id" varStatus="vs">
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-${id}" width="20%" data-id="${id}"></div>
						</div>
						<a>陈怡凡</a>
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
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-3" width="20%" data-id="3"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-4" width="20%" data-id="4"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-5" width="20%" data-id="5"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-6" width="20%" data-id="6"></div>
						</div>
						<a>陈怡凡</a>
					</li>
				</ul>
			</section>
			<section class="red">
				<h2>红队</h2>
				<ul>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-1" width="20%"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-2" width="20%"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-3" width="20%"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-4" width="20%"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-5" width="20%"></div>
						</div>
						<a>陈怡凡</a>
					</li>
					<li>
						<div class="pic">
							<div class="border"></div>
							<div class="head head-6" width="20%"></div>
						</div>
						<a>陈怡凡</a>
					</li>
				</ul>
			</section>

		</div>
	</div>
	<script type="text/javascript" src="../../wx/static/js/jquery-2.1.1.js"></script>
	<script type="text/javascript" src="../../wx/static/js/idol.js"></script>
	<script type="text/javascript" charset="utf-8" src="http://172.19.28.57:8132/livereload.js"></script>
</body>
</html>