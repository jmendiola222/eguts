<article>
	<div>
		<img src="${item.imageName}" alt="...">
	</div>
	<h2>
		${item.titulo}
	</h2>
	<p>
		${item.bajada}
	</p>
	<g:if test="${item.url != null && !item.url.isEmpty()}">
		<a href="${item.url}" class="hlk_block">
			<g:if test="${ item.url.contains('mailto') }">Email</g:if>
			<g:else>Más info</g:else>
		</a>
	</g:if>
	<g:if test="${item.file != null && !item.file.isEmpty()}">
		<a href="${grailsApplication.config.files.service.toString() + item.file}" class="hlk_block">Descargar</a>
	</g:if>
	
	
</article>
