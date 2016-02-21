package models.user

import helpers.Const
import edu.vt.middleware.dictionary.*
import edu.vt.middleware.dictionary.sort.*
import edu.vt.middleware.password.*
import grails.transaction.Transactional

class User {

    transient init = false

    //SPRING SECURITY
    transient springSecurityService
    transient bcryptService
    transient securityMessageResolver
    def grailsApplication

    String email
    Role role
    String username
    String password
    String confirmPassword
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    int logInAttemps = 0
    Date lastPasswordChange

    UserProfile userProfile

    static transients = ['springSecurityService', 'bcryptService', 'confirmPassword', 'securityMessageResolver', 'init']

    static mapping = {
        password column: '`password`'
        userProfile cascade: 'all-delete-orphan'
    }

    static constraints = {

        email(blank: false, unique: true)
        role(nullable: false)
        username blank: false, minSize: 2, maxSize: 50, matches: Const.REGEX_ALFANUMERICO
        lastPasswordChange nullable: true
        password nullable: true, blank: true
        confirmPassword blank: true, nullable: true
        userProfile(nullable: true)
    }

    public static String[] validatePassword(String password, User obj) {

        if (obj.id == null) {
            if (password == null || password.isEmpty() || password.isAllWhitespace()) {
                return ['user.password.blank.message']
                return
            }
        }

        //Password can be null on an edit
        if (password == null) {
            return ["ok"];
        }

        // password must be between 8 and 16 chars long
        LengthRule lengthRule = new LengthRule(10, 20);
        // don't allow whitespace
        WhitespaceRule whitespaceRule = new WhitespaceRule();

        // control allowed characters
        CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
        // require at least 1 digit in passwords
        charRule.getRules().add(new DigitCharacterRule(1));
        // require at least 1 upper case char
        charRule.getRules().add(new UppercaseCharacterRule(1));
        // require at least 1 lower case char
        charRule.getRules().add(new LowercaseCharacterRule(1));

        def isAdmin = false
        if (obj.role != null) {
            isAdmin = obj.role.authority.equals(Const.ROLE_SYSADMIN)
        } else {
            def user = User.findByUsername(obj.username)
            if (user) isAdmin = user.hasRole(Const.ROLE_SYSADMIN)
        }

        if (isAdmin) {
            // require at least 1 non-alphanumeric char
            charRule.getRules().add(new NonAlphanumericCharacterRule(1));
        }

        // require at least 3/4 of the previous rules be met
        charRule.setNumberOfCharacteristics(charRule.getRules().size());

        // don't allow qwerty sequences
        QwertySequenceRule qwertySeqRule = new QwertySequenceRule();

        //don't contain username, match backwards, ignore case
        UsernameRule usernameRule = new UsernameRule(true, true)

        // don't allow 4 repeat characters
        RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4);

        List<Rule> ruleList = new ArrayList<Rule>();
        ruleList.add(lengthRule);
        ruleList.add(whitespaceRule);
        ruleList.add(charRule);
        ruleList.add(qwertySeqRule);
        ruleList.add(repeatRule);
        ruleList.add(usernameRule);

        PasswordValidator validator = new PasswordValidator(obj.securityMessageResolver, ruleList);
        PasswordData passwordData = new PasswordData(new edu.vt.middleware.password.Password(password));
        passwordData.setUsername(obj.getUsername());

        RuleResult result = validator.validate(passwordData);

        if (result.isValid()) {
            //Only perform dictionary analysis - heavy - if no previews error
            def resource = obj.grailsApplication.mainContext.getResource("/data/passwordDictionary.txt")

            if (resource != null) {
                def dicFile = resource.getFile()
                def fileReader = new FileReader[1]
                fileReader[0] = new FileReader(dicFile)

                // create a case sensitive word list and sort it
                ArrayWordList awl = WordLists.createFromReader(fileReader, true, new ArraysSort())

                // create a dictionary for searching
                WordListDictionary dict = new WordListDictionary(awl)

                DictionaryRule dictRule = new DictionaryRule(dict)
                dictRule.setMatchBackwards(false); // match dictionary words backwards

                ruleList.clear();
                ruleList.add(dictRule)

                validator = new PasswordValidator(obj.securityMessageResolver, ruleList)
                result = validator.validate(passwordData)
            }
        }

        if (!result.isValid()) {
            return ['default.unknown.message', validator.getMessages(result)[0]];
        }

        return ["ok"]
    }


    Set<Role> getAuthorities() {
        //UserRole.findAllByUser(this).collect { it.role }
        Set<Role> roles = new HashSet<Role>()
        roles.add(role)
        roles
    }

    def beforeInsert() {
        if (!init) {
            encodePassword()
        }
        //To ensure user changes the password on first logIn
        lastPasswordChange = null
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            if (!init) {
                lastPasswordChange = new Date()
            } else {
                lastPasswordChange = null
            }
            encodePassword()
        }
    }

    public boolean isPasswordValid(String rawPassword, String hashedPassword) {
        return bcryptService.checkPassword(rawPassword, hashedPassword);
    }

    public boolean isAdmin() {
        def isAdmin = this.authorities.any { it.authority == "ROLE_ADMIN" }
        return isAdmin
    }

    @Transactional(readOnly = true)
    protected void encodePassword() {
        password = bcryptService.hashPassword(password)
    }

    def hasRole(String role) {
        getRoles().contains(role)
    }

    Set<String> getRoles() {
        UserRole.findAllByUser(this).collect { it.role.authority } as Set
    }

    def getRolesDescription() {
        def rolesSet = UserRole.findAllByUser(this).collect { it.role.authority } as Set
        rolesSet.join(',')
    }
}
