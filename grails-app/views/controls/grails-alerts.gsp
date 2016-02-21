<div ng-show="show">
	<div
		ng-class="{'alert-success' : errors == null , 'alert-danger': errors && errors !== null}"
		class="alert alert-dismissible" ng-class="alertType" role="alert">
		<button type="button" class="close" ng-click="show = false">
			<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
		</button>
		<div ng-show="errors && errors !== null">
			<span>{{errorTitle}}</span>
			<ul>
				<li ng-repeat="error in errors">{{error.message}}</li>
			</ul>
		</div>
		<div ng-show="errors === null">
			<span>{{successTitle}}</span>
		</div>
	</div>
</div>