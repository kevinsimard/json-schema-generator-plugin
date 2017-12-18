package $package$.$directory$;

import $package$.AbstractValidator;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;

public class $className$ extends AbstractValidator {

    private static JsonSchema schema;

    @Override
    protected JsonSchema getJsonSchema() {
        if (schema == null) {
            schema = JsonSchemaFactory.getInstance().getSchema(this.getClass()
                .getResourceAsStream("$schemaPath$"));
        }

        return schema;
    }
}
