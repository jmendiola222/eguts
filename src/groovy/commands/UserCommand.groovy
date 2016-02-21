package commands

import edu.vt.middleware.dictionary.ArrayWordList
import edu.vt.middleware.dictionary.WordListDictionary
import edu.vt.middleware.dictionary.WordLists
import edu.vt.middleware.dictionary.sort.ArraysSort
import edu.vt.middleware.password.*
import helpers.SecurityMessageResolver
import models.user.Role
import models.user.User

@grails.validation.Validateable
class UserCommand extends ConcreteCommandObject {

    transient SecurityMessageResolver securityMessageResolver

    String email
    Role role
    String username
    String password
    String confirmPassword
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {

        email (blank:false)
        role (nullable:false)
        username	blank: false, unique: true, minSize: 2, maxSize: 50, matches: Constants.REGEX_ALFANUMERICO

        password nullable:true, blank: true, validator: {password, obj ->
            if (obj.id == null) {
                if (password == null || password.isEmpty() || password.isAllWhitespace()) {
                    return ['user.password.blank.message']
                }
            }

            //Password can be null on an edit
            if(password == null){
                return true;
            }

            // password must be between 8 and 16 chars long
            LengthRule lengthRule = new LengthRule(10,20);
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
            if(obj.role != null) {
                isAdmin = obj.role.authority.equals(SecurityConstants.ROLE_SYSADMIN)
            }else{
                def user = User.findByUsername(obj.username)
                if(user) isAdmin = user.hasRole(SecurityConstants.ROLE_SYSADMIN)
            }

            if(isAdmin){
                // require at least 1 non-alphanumeric char
                charRule.getRules().add(new NonAlphanumericCharacterRule(1));
            }

            // require at least 3/4 of the previous rules be met
            charRule.setNumberOfCharacteristics(charRule.getRules().size());

            // don't allow qwerty sequences
            QwertySequenceRule qwertySeqRule = new QwertySequenceRule();

            //don't contain username, match backwards, ignore case
            UsernameRule usernameRule = new UsernameRule(true,true)

            // don't allow 4 repeat characters
            RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4);

            List<Rule> ruleList = new ArrayList<Rule>();
            ruleList.add(lengthRule);
            ruleList.add(whitespaceRule);
            ruleList.add(charRule);
            ruleList.add(qwertySeqRule);
            ruleList.add(repeatRule);
            ruleList.add(usernameRule);

            PasswordValidator validator = new PasswordValidator(ruleList);
            PasswordData passwordData = new PasswordData(new edu.vt.middleware.password.Password(password));
            passwordData.setUsername(obj.getUsername());

            RuleResult result = validator.validate(passwordData);

            if(result.isValid() && obj.getId() != null && obj.getId() > 0) {
                User user = User.findById(obj.getId())
                boolean wasAlreadyUsed  = false;
                user.getPasswordHistory().find() { pwd ->
                    wasAlreadyUsed = user.isPasswordValid(password, pwd.getValue());
                    return wasAlreadyUsed;
                };
                if(wasAlreadyUsed)
                    return ['user.password.alreadyUsed.invalid']
            }

            if(result.isValid()){
                //Only perform dictionary analysis - heavy - if no previews error

                def resource = getClass().getResource("/data/passwordDictionary.txt");
                def dicFile = resource.getFile();
                def fileReader = new FileReader[1];
                fileReader[0] = new FileReader(dicFile);
                // create a case sensitive word list and sort it
                ArrayWordList awl = WordLists.createFromReader(
                        fileReader, true, new ArraysSort());

                // create a dictionary for searching
                WordListDictionary dict = new WordListDictionary(awl);

                DictionaryRule dictRule = new DictionaryRule(dict);
                dictRule.setMatchBackwards(false); // match dictionary words backwards

                ruleList.clear();
                ruleList.add(dictRule);

                validator = new PasswordValidator(ruleList);
                result = validator.validate(passwordData);
            }

            if (!result.isValid()) {
                return ['default.unknown.message', validator.getMessages(result)[0]]
            }

            return true
        }

        confirmPassword blank: true, nullable: true
    }
}
