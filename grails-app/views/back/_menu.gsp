
<!-- NAVIGATION : This navigation is also responsive

			To make this navigation dynamic please make sure to link the node
			(the reference to the nav > ul) after page load. Or the navigation
			will not initialize.
			-->
<nav>
	<!-- NOTE: Notice the gaps after each icon usage <i></i>..
				Please note that these links work a bit different than
				traditional hre="" links. See documentation for details.
				-->

	<ul>
		<sec:ifAnyGranted roles="ROLE_SYSADMIN">
			<li>
				<g:link controller="UserView" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-group"></i>
					<span class="menu-item-parent">Usuarios</span>
				</g:link>
			</li>
			<li>
				<g:link controller="UserView" action="profile" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-user"></i>
					<span class="menu-item-parent">Perfil</span>
				</g:link>
			</li>
		</sec:ifAnyGranted>
		<sec:ifAnyGranted roles="ROLE_ADMIN">
			<li>
				<g:link controller="UserCategoryView" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-tags "></i>
					<span class="menu-item-parent">Categor&iacute;as de Usuarios</span>
				</g:link>
			</li>
			<li>
				<g:link controller="PilarView" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-tags "></i>
					<span class="menu-item-parent">Pilares</span>
				</g:link>
			</li>
			<li>
				<g:link controller="TopicView" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-exchange "></i>
					<span class="menu-item-parent">T&oacute;picos</span>
				</g:link>
			</li>
			<li>
				<g:link controller="QuestionView" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-question"></i>
					<span class="menu-item-parent">Preguntas</span>
				</g:link>
			</li>
			<li>
				<g:link controller="EvaluationView" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-list"></i>
					<span class="menu-item-parent">Evaluaciones</span>
				</g:link>
			</li>
			<li>
				<g:link controller="NewsView" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-info"></i>
					<span class="menu-item-parent">Noticias</span>
				</g:link>
			</li>
		</sec:ifAnyGranted>
	</ul>
</nav>