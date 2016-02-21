<section class="${ sectionClass }">
	<div class="banner">
		<div class="container">
			<div class="col-md-4">
				<i></i>
			</div>
			<div class="col-md-8">
				<h1>${ title }</h1>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<g:each in="${boxNews}" var="item">
				<article>
					<div class="row">
						<div class="col-md-4">
							<img src="${item.imageName}" alt="..." />
						</div>
						<div class="col-md-8">
							<h2>
								${item.titulo}
							</h2>
							<p>
								${item.bajada}
							</p>
							<g:if test="${item.url != null && !item.url.isEmpty()}">
								<a href="${item.url ? item.url : '#'}" class="hlk_block">
									<g:if test="${ item.url.contains('mailto') }">Email</g:if>
									<g:else>Más info</g:else>
								</a>
							</g:if>
							<g:if test="${item.file != null && !item.file.isEmpty()}">
								<a href="${grailsApplication.config.files.service.toString() + item.file}" class="hlk_block">Descargar</a>
							</g:if>
	
						</div>
					</div>
				</article>
			</g:each>

		</div>
	</div>
</section>