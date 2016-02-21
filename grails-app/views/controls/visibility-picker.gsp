
<select class="form-control" multiple ng-multiple="true"
	ng-model="visibility" name="selectVisibility"
	ng-options="userCategory for userCategory in userCategories"  ng-required="{{isRequired}}">
</select>

