
<%@ page contentType="text/html"%>
<g:applyLayout name="emailLayout">
	<html>
<head>
</head>
<body bgcolor="#FFFFFF">
	<table bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="25"
		width="100%">
		<tr>
			<td width="100%" bgcolor="#ffffff" style="text-align: left;">
				<p
					style="color: #222222; font-family: Arial, Helvetica, sans-serif; font-size: 15px; line-height: 19px; margin-top: 0; margin-bottom: 20px; padding: 0; font-weight: normal;">
					Estimado usuario de eguts ${user.username},
				</p>

				<p
					style="color: #222222; font-family: Arial, Helvetica, sans-serif; font-size: 15px; line-height: 19px; margin-top: 0; margin-bottom: 20px; padding: 0; font-weight: normal;">
					Le informamos la url ${subscriptionResult.subscription.url} ha recivido las siguintes notificaciones:
				</p>
				<p>
					<table bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="0" width="100%">
						<g:each var="item" in="${subscriptionResult.resultItemDTOs}">
							<tr>
								<td width="10%"></td>
								<g:if test="${item.photo}">
									<td height="60px">
										<img height="60px" src="${item.photo}">
									</td>
								</g:if>
								<td width="75%">
									<g:if test="${item.link}">
										<a href="${item.link}">
									</g:if>

									<g:if test="${item.name}">
										<span>${item.name}</span>
									</g:if>
									<g:if test="${item.price}">
										<b><span>${item.price}</span></b>
									</g:if>

									<g:if test="${item.link}">
										</a>
									</g:if>
								</td>
							</tr>
						</g:each>
					</table>
				</p>
			</td>
		</tr>
	</table>
	<table bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="0"
		width="100%">
		<tr>
			<td width="25%"></td>
			<td width="50%" bgcolor="#ffffff" style="text-align: center;">
				<a href="${createLink(controller: 'Front', action: 'index', absolute: true)}">
					<div
						style="display: block; max-width: 100% !important; width: 93% !important; height: auto !important; background-color: #224681; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px; border-radius: 0px; color: #ffffff; font-size: 18px; font-family: Arial, Helvetica, sans-serif;">
						Modificar subsci&oacute;n</div>
				</a>
			</td>
			<td width="25%"></td>
		</tr>
	</table>
	<g:render template="/email/emailFooter" />
</body>
</html>

</g:applyLayout>