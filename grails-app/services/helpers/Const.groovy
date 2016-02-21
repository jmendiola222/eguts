package helpers

/**
 * Created by julian on 17/05/15.
 */
class Const {

    //RegEx

    public static final String REGEX_ALFANUMERICO = '^[A-Za-z0-9 ]*$'
    public static final String REGEX_SOLO_NUMEROS = '^(?!0)[0-9]*$'
    public static final String REGEX_SOLO_LETRAS  = '^[A-Za-z ]*$'
    public static final String REGEX_CUIT         = '^[0-9]{2}-[0-9]{8}-[0-9]$'
    public static final String REGEX_TELEFONO     = '^[0-9-]*$'
    public static final String REGEX_EMAIL        = /[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})/

    //Security

    public static final String ROLE_SYSADMIN = "ROLE_SYSADMIN"
    public static final String ROLE_ADMIN = "ROLE_ADMIN"
    public static final String ROLE_USER = "ROLE_USER"
    public static final String ROLE_SUBSCRIBER = "ROLE_SUBSCRIBER"
    public static final String HAS_SYSADMIN_ROLE = "hasAnyRole('ROLE_SYSADMIN')"
    public static final String HAS_ADMIN_ROLE = "hasAnyRole('ROLE_ADMIN')"
}
