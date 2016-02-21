package assembler

abstract class GenericAssembler<Domain, Bean> {	
	
	// este metodo debe redefinirse si se desean setear
	// otros command objects anidados
	
	public abstract Bean toBean(Domain domain)
	
	// este metodo debe redefinirse si se desean setear atributos
	// derivados de otros command objects
	public abstract Domain fromBean(Bean bean)
	
	// metodos para convertir conjuntos de dominios en beans
	public List<Bean> toBeans(List<Domain> lista) {
		return convertirLista(lista, this.&toBean)
	}
	
	// metodos para convertir conjuntos de beans a dominios
	public List<Domain> fromBeans(List<Bean> lista) {
		return convertirLista(lista, this.&fromBean)
	}
	
	// metodo privado de conversion
	private List convertirLista(List original, conversor) {
		if (original == null)
			return null
		def resultado = []
		for (elem in original) {
			resultado.add(conversor(elem))
		}
		return resultado
	}
}
