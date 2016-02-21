<div ng-controller="CarouselDemoCtrl" class="ng-cloak">
	<div class="carousel-container" ng-hide="slides.length === 0">
		<div carousel interval="myInterval" id="carrousel">
			<div slide ng-repeat="slide in slides" active="slide.active"
				ng-repeat="slide in slides" class="item" active="slide.active">
				<a href="#" ng-href="{{slide.url || (slide.file ? '${grailsApplication.config.files.service.toString()}' + slide.file : '#') }}">
					<div class="row">
						<div class="col-md-8">
							<img ng-src="{{slide.imageName}}"
								title="Descripcion imagen noticia 1">
						</div>
						<div class="col-md-4">
							<h2>{{slide.titulo}}</h2>
							<p ng-bind-html="slide.bajada | saltoLinea"></p>
							<p ng-show="slide.url && slide.url.indexOf('mailto') == -1" class="hlk_block">Más info</p>
							<p ng-show="slide.url && slide.url.indexOf('mailto') > -1" class="hlk_block">Email</p>
							<p ng-show="slide.file" class="hlk_block">Descargar</p>
						</div>
					</div>
				</a>
			</div>
		</div>
	</div>
</div>