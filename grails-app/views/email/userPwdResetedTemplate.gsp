
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
					Estimado usuario de Eguts
					${user.username},
				</p>

				<p
					style="color: #222222; font-family: Arial, Helvetica, sans-serif; font-size: 15px; line-height: 19px; margin-top: 0; margin-bottom: 20px; padding: 0; font-weight: normal;">
					Se solicitó la re-asignación de las credenciales de acceso al portal ${appName}, a continuación le proporcionamos sus nuevas credenciales de acceso:
				<ul>
					<li>Usuario: ${user.username}</li>
					<li>Clave: ${password}</li>
				</ul>
				</p>

				<p style="color: #222222; font-family: Arial, Helvetica, sans-serif; font-size: 15px; line-height: 19px; margin-top: 0; margin-bottom: 20px; padding: 0; font-weight: normal;">
					Si no fue usted quien solicitó el cambio de clave, por favor contactese con un administrador.
				</p>
			</td>
		</tr>
	</table>
	<table bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="0"
		width="100%">
		<tr>
			<td width="25%"></td>
			<td width="50%" bgcolor="#ffffff" style="text-align: center;"><a
				href="${createLink(controller: 'Front', action: 'Index', absolute: true)}">
					<div
						style="display: block; max-width: 100% !important; width: 93% !important; height: auto !important; background-color: #224681; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px; border-radius: 0px; color: #ffffff; font-size: 18px; font-family: Arial, Helvetica, sans-serif;">
						Ir al portal</div>
			</a></td>
			<td width="25%"></td>
		</tr>
	</table>
	<g:render template="/email/emailFooter" />
</body>
</html>

</g:applyLayout>