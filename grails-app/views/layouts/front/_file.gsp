<li>
	<article>
	
		<a href="${grailsApplication.config.files.service.toString() + aFile.file}">
			<div>
				<g:if test="${aFile.file.toLowerCase().contains('pdf')}"><asset:image src="file-pdf.png" alt="PDF"/></g:if>
				<g:if test="${aFile.file.toLowerCase().contains('doc') || aFile.file.toLowerCase().contains('docx')}"><asset:image src="file-doc.png" alt="DOC"/></g:if>
				<g:if test="${aFile.file.toLowerCase().contains('xls') || aFile.file.toLowerCase().contains('xlsx')}"><asset:image src="file-xls.png" alt="XLS"/></g:if>
				<g:if test="${aFile.file.toLowerCase().contains('ppt') || aFile.file.toLowerCase().contains('pptx')}"><asset:image src="file-ppt.png" alt="PPT"/></g:if>
			</div>
			<h2>${aFile.titulo}</h2>
		</a>
	</article>
</li>