package helpers

class SecurityConstants {

    public static final String ROLE_SYSADMIN = "ROLE_SYSADMIN"
    public static final String ROLE_ADMIN = "ROLE_ADMIN"
    public static final String ROLE_USER = "ROLE_USER"

    public static final String PERMIT_ALL = "permitAll"

    public static final String HAS_APP_ROLE = "hasAnyRole('ROLE_ADMIN', 'ROLE_SYSADMIN', 'ROLE_USER')"
    public static final String HAS_ADMIN_ROLE = "hasRole('ROLE_ADMIN')"
    public static final String HAS_SYS_ADMIN_ROLE = "hasRole('ROLE_SYSADMIN')"
    public static final String HAS_BACK_ABM_ROLE = "hasRole('ROLE_ADMIN')"



}
