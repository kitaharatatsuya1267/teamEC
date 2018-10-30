<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	//ブラウザのキャッシュを無効にする。
	// Last-Modified(最終更新日) : 本日
	// Expires(有効期限)         : 過去日(1970/01/01)
	// pragma no-cache           : HTTP1.0仕様に基づく「キャッシュ無効指示」
	// Cache-Control no-cache    : HTTP1.1仕様に基づく「キャッシュ無効指示」
	java.util.Calendar objCal1=java.util.Calendar.getInstance();
	java.util.Calendar objCal2=java.util.Calendar.getInstance();
	objCal2.set(1970,0,1,0,0,0);
	response.setDateHeader("Last-Modified",objCal1.getTime().getTime());
	response.setDateHeader("Expires",objCal2.getTime().getTime());
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="-1">
<link rel="stylesheet" href="./css/header.css">
<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css"
	rel="stylesheet">
<title>ヘッダー</title>
<script>
	function goLoginAction() {
		document.getElementById("form").action = "GoLoginAction";
	}
	function goMyPageAction() {
		document.getElementById("form").action = "MyPageAction";
	}
	function goCartAction() {
		document.getElementById("form").action = "CartAction";
	}
	function goProductListAction() {
		document.getElementById("categoryId").value = 1;
		document.getElementById("form").action = "ProductListAction";
	}
	function goLogoutAction() {
		document.getElementById("form").action = "LogoutAction";
	}
	function goSearchItemAction() {
		document.getElementById("search-form").action = "SearchItemAction";
	}
	function goHomeAction() {
		location.href = "HomeAction";
	}
/* 	 直接リンク禁止　直接入力された場合は、ホームに戻されます　*/
  var refinfo=document.referrer;
  if (!(refinfo)){
	   location.href = "ErrorAction";
  }

</script>
</head>
<body class="header-body">
	<header>
		<div id="header">
			<div id="header-title">
				<a onclick="goHomeAction()" class="hover">i1810a</a>
			</div>
			<div id="header-menu">
				<s:form id="form" name="form" disabled="disabled">
					<div class="search1">
						<label for="menulist" id="menu"><i class="fas fa-bars"
							id="menu-icon"></i></label> <input type="checkbox" id="menulist"
							class="cssacc" />
						<div class="accshow">
							<s:submit type="button" class="submit-btn"
								onclick="goCartAction();">
								<i class="fas fa-shopping-cart hover"> カート</i>
							</s:submit>
							<s:if test="#session.logined==1">
								<s:submit type="button" class="submit-btn"
									onclick="goMyPageAction();">
									<i class="fas fa-user hover"> マイページ</i>
								</s:submit>
							</s:if>
							<s:if test="#session.logined==1">
								<s:submit type="button" class="submit-btn"
									onclick="goLogoutAction();">
									<i class="fas fa-sign-out-alt hover"> ログアウト</i>
								</s:submit>
							</s:if>
							<s:else>
								<s:submit type="button" class="submit-btn"
									onclick="goLoginAction();">
									<i class="fas fa-sign-in-alt hover"> ログイン</i>
								</s:submit>
							</s:else>
							<s:submit type="button" class="submit-btn"
								onclick="goProductListAction();">
								<i class="fas fa-align-justify hover"> 商品一覧</i>
								<s:hidden name="categoryId" value="1"></s:hidden>
								<s:hidden name="keywords" value=""></s:hidden>
							</s:submit>
						</div>
					</div>
				</s:form>

				<s:form id="search-form">
					<div class="search2">
						<div class="search2-category">
							<s:if test='#session.containsKey("mCategoryDtoList")'>
								<s:select name="categoryId" list="#session.mCategoryDtoList"
									listValue="categoryName" listKey="categoryId" class="cs-div"
									id="categoryId" />
							</s:if>
						</div>
						<div class="search2-word">
							<s:textfield name="keywords" class="text-keywords"
								placeholder="検索ワード" />
						</div>
						<div class="search2-search">
							<s:submit type="button" class="submit-btn-search"
								onclick="goSearchItemAction();">
								<i class="fas fa-search"></i>
							</s:submit>
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</header>
</body>
</html>