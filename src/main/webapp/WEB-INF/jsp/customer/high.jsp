<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<div>
    <c:if test="${not empty stats}">
    <table>
    	<thead>
    		<th>Customer ID</th>
    		<th>Customer Name</th>
    		<th>Average Amount</th>
    		<th>Number of Bets</th>
    	</thead>
    	<c:forEach items="${stats}" var="each">
    		<tr>
    			<td>
    				${each.customerId}
    			</td>
    			<td>
    				${each.customerName}
    			</td>
    			<td>
    				${each.averageAmount}
    			</td>
    			<td>
    				${each.numberOfBets}
    			</td>
    		</tr>
    	</c:forEach>
    </table>
    </c:if>
    <c:if test="${empty stats}">There are no customers above the limit.</c:if>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
