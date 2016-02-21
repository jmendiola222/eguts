package assembler

abstract class ConcreteObjectAssembler<Domain, Bean> extends GenericAssembler<Domain, Bean> {
	
	protected abstract obtenerEntidad(Long id)
	
	protected abstract crearBean()
	
	// este metodo debe redefinirse si se desean setear
	// otros command objects anidados
	public Bean toBean(Domain domain) {
		Bean bean = crearBean()
		for (attr in bean.properties.keySet()) {
			if (domain.properties.containsKey(attr) && !(attr in ['class', 'metaClass'])) {
				bean[attr] = domain[attr]
			}
		}
		bean.id = domain.id
		bean.versionValue = domain.version
		return bean
	}
	
	// este metodo debe redefinirse si se desean setear atributos
	// derivados de otros command objects
	public Domain fromBean(Bean bean) {
		Domain domain = obtenerEntidad(bean.id)
		def domainProperties = domain.properties.keySet() 
		for (attr in domainProperties) {
			if (bean.properties.containsKey(attr) && !(attr in ['class', 'metaClass','dateCreated'])) {
				domain[attr] = bean[attr]
			}
		}
		domain.id = bean.id
		domain.version = bean.versionValue
		return domain
	}
}
