
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
		<sec:ifAnyGranted roles="ROLE_ADMIN">
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
			<li>
				<g:link controller="EndPointsView" mapping="backendMapping">
					<i class="fa fa-lg fa-fw fa-exchange "></i>
					<span class="menu-item-parent">EndPoints</span>
				</g:link>
			</li>

		</sec:ifAnyGranted>
	</ul>
</nav>