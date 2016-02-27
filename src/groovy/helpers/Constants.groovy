package helpers

class Constants {

    public static final int PWD_MIN_LENGTH = 10;
    public static final int PWD_MAX_LENGTH = 20;
    public static final int PWD_EXPIRE_MAX_DAYS = 90;

    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 50;

    public static final int EMAIL_MAX_LENGTH = 50;
    public static final int NAME_MAX_LENGTH = 50;

    public static final String DATE_FORMAT = 'dd/MM/yy'
    public static final String DATETIME_FORMAT = 'dd/MM/yy HH:mm'

    public static final String REGEX_ALFANUMERICO = '^[A-Za-z0-9 ]*$'
    public static final String REGEX_SOLO_NUMEROS = '^(?!0)[0-9]*$'
    public static final String REGEX_SOLO_LETRAS  = '^[A-Za-z ]*$'
    public static final String REGEX_CUIT         = '^[0-9]{2}-[0-9]{8}-[0-9]$'
    public static final String REGEX_TELEFONO     = '^[0-9-]*$'
    public static final String REGEX_EMAIL        = /[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})/
}
