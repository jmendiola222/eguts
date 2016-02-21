package helpers

import edu.vt.middleware.password.MessageResolver
import edu.vt.middleware.password.RuleResultDetail

class SecurityMessageResolver extends MessageResolver {

    def i18nService;

    public SecurityMessageResolver() {
        super();
    }

    @Override
    public String resolve(RuleResultDetail detail) {
        String key = detail.getErrorCode();

        if(detail.parameters.containsKey("characterType")) {
            detail.parameters["characterType"] = i18nService.message(detail.parameters["characterType"]);
        }

        return i18nService.message(key, new ArrayList(detail.parameters.values()));

    }
}
