<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="../inc/admin-header.jsp"%>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<style>
.activePage {
	border: 4px solid  rgba(171, 147, 201, 0.1);
	background-color:  rgba(171, 147, 201, 0.1);
	border-radius: 4px;
}
</style>


<script type="text/javascript">
$(document).ready(function(){   
	/************* 페이징 ***************/
	var moveForm =$("#moveForm");
	$(".pageInfo_btn a").on("click", function(e){
		e.preventDefault();
		
		moveForm.find("input[name='pageNum']").val($(this).attr("href"));
		moveForm.submit();
	});


	 /************* 검색 ****************/
       var sf = $("#searchForm");
       $("#btn-search").on("click", function(e){
          if(!sf.find("input[name='keyWord']").val()){
             alert("키워드를 입력하세요!!");
             $("#keyWord").focus();
             return false;
          }
          sf.submit();
       });

    });

</script>     

<!-- main 부분 여기여기 폼 부분 -->
<main class="board container w-100 p-5">
   <form action='category-list' method="get" id="moveForm" name="moveForm">
		<input type="hidden" name="pageNum" value="${pageMake.cri.pageNum}">
		<input type="hidden" name="amount" value="${pageMake.cri.amount}">
		<input type="hidden" name="keyWord" value="${pageMake.cri.keyWord}">
		<input type="hidden" name="searchType" value="${pageMake.cri.searchType}">
   </form>
   <h4>카테고리 목록</h4>
   <!-- 검색창  -->
      <form id="searchForm" method="post" action='category-list'>
         <div class="search">
            <select name="searchType">
				<option
					<c:out value="${pageMake.cri.searchType == null ? 'selected':''}"/>>선택</option>
				<option value="N"
					<c:out value="${pageMake.cri.searchType == 'N' ? 'selected':''}"/>>카테고리명</option>
				<option value="C"
					<c:out value="${pageMake.cri.searchType == 'C' ? 'selected':''}"/>>카테고리 코드</option>
            </select>
            <input class="board-search" type="text" name="keyWord" id="keyWord"
               placeholder="검색어를 입력하세요" value="${cri.keyWord}"/> 
            <button id="btn-search" class="button board-search-button">검색</button>
         </div>
      </form>   
   <table class="table">
      <thead>
         <tr class="text-center">
            <th class="w60">NO</th>
            <th class="expand">카테고리 이름</th>
            <th class="w100">카테고리 코드</th>
            <th class="w60"><input type="checkbox" id="allCheck"
             name="allCheck" style="margin-left: 38px; margin-right: 10px;"/><label for="allCheck">삭제</label>
             <script>
     		$(function(){
    			var chkObj = document.getElementsByName("RowCheck");
    			var rowCnt = chkObj.length;
    			
    			$("input[name='allCheck']").click(function(){
    				var chk_listArr = $("input[name='RowCheck']");
    				for (var i=0; i<chk_listArr.length; i++){
    					chk_listArr[i].checked = this.checked;
    				}
    			});
    			$("input[name='RowCheck']").click(function(){
    				if($("input[name='RowCheck']:checked").length == rowCnt){
    					$("input[name='allCheck']")[0].checked = true;
    				}
    				else{
    					$("input[name='allCheck']")[0].checked = false;
    				}
    			});
    		});
             </script>
             </th>
         </tr>
      </thead>
      <tbody>
         <c:set var="cno" value="${pageMake.startPage}"/>
         <c:forEach var="cateList" items="${cateList}">
         <tr class="text-center">
            <td><a class="pro-hre" href="<c:url value='category-update?cateNum=${cateList.cateNum}'/>"onClick="alert('수정하시겠습니까?')" ><c:out value="${cateList.cateNum}"/></a></td>
            <td><a class="pro-hre" href="<c:url value='category-update?cateNum=${cateList.cateNum}'/>"onClick="alert('수정하시겠습니까?')" >${cateList.cateName}</a></td>
            <td>${cateList.cateCode}</td>
            <td class="checkbox"><input type="checkbox" name="RowCheck" value="${cateList.cateNum}"/></td>
         </tr>
         <c:set var="cno" value="${cno-1}"/>
         </c:forEach>
      </tbody>
   </table>
	<div class="notice-footer w-100">
		<div class="indexer align-right">
		<span>${pageMake.cri.pageNum}/${pageMake.realEnd} pages</span>
			<ul id="pageInfo" class="notice-page pager">
				<!-- 각 번호 페이지 버튼 -->
				<c:forEach var="num" begin="${pageMake.startPage }" end="${pageMake.endPage }">
				<li class="pageInfo_btn ${pageMake.pageNum eq num ? 'activePage' : ''} ">
				<a class="page-link" href="${num}" style="border:none">${num}</a>
				</li>
				</c:forEach>
            </ul>
	         <input class="bloc" type="button" value="삭제" onclick="deleteValue();">
	         <script>
	         function deleteValue(){
	 			var url = "/category-delete";    // Controller로 보내고자 하는 URL
	 			var valueArr = new Array();
	 		    var list = $("input[name='RowCheck']");
	 		    for(var i = 0; i < list.length; i++){
	 		        if(list[i].checked){ //선택되어 있으면 배열에 값을 저장
	 		            valueArr.push(list[i].value);
	 		        }
	 		    }
	 		    if (valueArr.length == 0){
	 		    	alert("선택된 글이 없습니다.");
	 		    }
	 		    else{
	 				var chk = confirm("선택한 항목을 삭제하시겠습니까?");				 
	 				$.ajax({
	 				    url : url,                    // 전송 URL
	 				    type : 'POST',                // GET or POST 방식
	 				    traditional : true,
	 				    data : {
	 				    	valueArr : valueArr        // 보내고자 하는 data 변수 설정
	 				    },
	 	                success: function(jdata){
	 	                    if(jdata = 1) {
	 	                        alert("삭제 되었습니다.");
	 	                        location.replace("/category-list")
	 	                    }
	 	                    else{
	 	                        alert("다시 시도해주세요.");
	 	                    }
	 	                }
	 				});
	 			}
	 		}
	         </script>
         <button class="write">
            <a href="<c:url value='category-input'/>">등록</a>
         </button>
      </div>
   </div>
</main>



<!------------------------------->
<%@ include file="../inc/footer.jsp"%>