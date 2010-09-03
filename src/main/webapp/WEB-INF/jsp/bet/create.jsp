<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<div>
	<h2>New Request for Resources</h2>
    <form:form action="/rest/bet" method="POST" modelAttribute="bet">
        <div id="bet_betOfferId">
            <label for="_betOfferId">Bet offer id:</label>
            <form:input cssStyle="width:300px" id="_betOfferId" path="betOfferId"/>
            <br/>
            <form:errors cssClass="errors" id="_message" path="betOfferId"/>
        </div>
        <br/>
        <div id="bet_customerId">
            <label for="_customerId">Customer id:</label>
            <form:input cssStyle="width:300px" id="_customerId" path="customerId"/>
            <br/>
            <form:errors cssClass="errors" id="_message" path="customerId"/>
        </div>
        <br/>
        <div id="bet_amount">
            <label for="_amount">Amount:</label>
            <form:input cssStyle="width:300px" id="_amount" path="amount"/>
            <br/>
            <form:errors cssClass="errors" id="_amount" path="amount"/>
        </div>
        <br/>
        <div class="submit" id="bet_submit">
            <input id="proceed" type="submit" value="Place Bet"/>
        </div>
    </form:form>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
