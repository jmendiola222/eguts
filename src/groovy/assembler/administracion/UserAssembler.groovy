package assembler.administracion

import assembler.ConcreteObjectAssembler
import commands.UserCommand
import models.user.User

import javax.annotation.Resource

class UserAssembler extends ConcreteObjectAssembler<User, UserCommand> {
	
	@Resource
	def bcryptService

	@Override
	public UserCommand toBean(User domain) {
		UserCommand bean = super.toBean(domain)

		return bean;
	}

	@Override
	public User fromBean(UserCommand bean) {
		def pass = null
		if (bean.id && bean.id > 0) {
			def userAux = User.get(bean.id)
			if (userAux)
				pass = userAux.password
		}

		User domain = super.fromBean(bean)

		if(bean.password != null && !bean.password.isEmpty()) {
			pass = bean.password
		}

		//TODO sacar el encriptado
		domain.password = bcryptService.hashPassword(pass)

		return domain;
	}

	@Override
	protected User obtenerEntidad(Long id) {
		return ( id == null || id == 0 )? new User() : User.get(id);
	}

	@Override
	protected UserCommand crearBean() {
		return new UserCommand();
	}
}
