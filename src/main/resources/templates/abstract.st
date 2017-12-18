package $package$;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;

import java.util.Set;

public abstract class AbstractValidator {

    public boolean isValid(JsonNode node) {
        return validate(node).isEmpty();
    }

    public Set<ValidationMessage> validate(JsonNode node) {
        return getJsonSchema().validate(node);
    }

    protected abstract JsonSchema getJsonSchema();
}
