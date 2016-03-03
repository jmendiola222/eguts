
<!--HEADER-->
<header ng-controller="NavbarCtrl">
	<div class="cnt_session">

			<div class="container">
				<ul>
				<g:if test="${isLoggedIn}">
					<g:if test="${isAdmin}">
						<li>
							<span>
								<g:link controller="admin">
									<g:message code="default.admin.label"/>&nbsp;
								</g:link>
							</span>
							<span>
								&nbsp|&nbsp&nbsp;
							</span>
						</li>
					</g:if>
					<g:else>
					<li>
						<span>
							<g:link action="userProfile"><g:message code="front.profile" /></g:link>
						</span>
						<span>
							&nbsp|&nbsp&nbsp;
						</span>
					</li>
					</g:else>
					<li>
						¡
						<g:message code="front.wellcome" />
						<span>
							${userName}
						</span>
						!
					</li>
					<li>
						<g:link controller='logout'>
							<i class="fa fa-power-off"></i>
							<g:message code="front.logout" />
						</g:link>
					</li>
					</g:if>
					<g:else>
						<li>
							<span>
								<g:link action="logIn"><g:message code="front.login" /></g:link>
							</span>
						</li>
					</g:else>
				</ul>
			</div>

	</div>
	<div class="cnt_nav">
		<div class="container">

			<div class="row">
				<div class="col-md-2">
					<g:link controller="Front" action="Index" class="logo_eguts">
						<asset:image src="logo-eguts.png" alt="eguts" />
					</g:link>
				</div>
				<nav class="col-md-10 mobile_hide">
					<ul>
						<li>
						</li>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</header>
<!--end HEADER-->

<!-- MODALS  -->

<script type="text/ng-template" id="contact-template">
<div class="mdl_contact">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" ng-click="cancel()">
                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
            </button>
            <h4 class="modal-title">Contacto</h4>
        </div>

        <div class="modal-body">
            <p>Por dudas o sugerencias podés contactarte con nosotros a través
            de los siguientes medios:</p>
            <h6>Teléfono</h6>
            <strong>Yes, sure</strong>
            <h6>E-mail</h6>
            <a href="mailto:info@eguts.com.ar?subject=Contacto+web+Eguts"><strong>info@eguts.com.ar</strong>
            </a>


            <p>¡Muchas gracias!</p>
        </div>
    </div>
</div>
</script>
<!-- /MODALS  -->

