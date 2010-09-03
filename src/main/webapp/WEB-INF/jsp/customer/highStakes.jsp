<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<div>
    <c:if test="${not empty highStakes}">
    <table>
    	<thead>
    		<th>Customer ID</th>
    		<th>Customer Name</th>
    		<th>Total Amount</th>
    	</thead>
    	<c:forEach items="${highStakes}" var="each">
    		<tr>
    			<td>
    				${each.customerId}
    			</td>
    			<td>
    				${each.customerName}
    			</td>
    			<td>
    				${each.totalAmount}
    			</td>
    		</tr>
    	</c:forEach>
    </table>
    </c:if>
    <c:if test="${empty highStakes}">There are no high stakes.</c:if>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
