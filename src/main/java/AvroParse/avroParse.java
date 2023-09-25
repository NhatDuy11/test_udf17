package AvroParse;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
@UdfDescription(name = "demo_avro_parse7", description = "Extract fields from a AVRO STRING")
public class avroParse {
    @Udf(description = "Avro parse extract to field schema")

    public Schema createOptionalSchema(@UdfParameter(value = "inputAvro") String inputAvro) {
        try {
            JSONObject inputJson = new JSONObject(inputAvro);
            String nestedJsonString = inputJson.getJSONObject("VALUE").getString("string");
            JSONObject nestedJson = new JSONObject(nestedJsonString);
            return createSchema(nestedJson, "MyRecord");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Schema createSchema(JSONObject jsonData, String recordName) {
        SchemaBuilder.FieldAssembler<Schema> schemaFields = SchemaBuilder.record(recordName).fields();
        Iterator<String> keys = jsonData.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonData.get(key);
            if (value instanceof JSONObject) {
                createSchemaField(schemaFields, key, (JSONObject) value);
            } else {
                addSchemaField(schemaFields, key, value);
            }
        }
        return schemaFields.endRecord();
    }
    private void createSchemaField(SchemaBuilder.FieldAssembler<Schema> schemaFields, String fieldName, JSONObject fieldData) {
        Iterator<String> keys = fieldData.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = fieldData.get(key);
            addSchemaField(schemaFields, key, value);
        }
    }
    private void addSchemaField(SchemaBuilder.FieldAssembler<Schema> schemaFields, String fieldName, Object value) {
        if (value instanceof Integer) {
            schemaFields.name(fieldName).type().optional().intType();
        } else if (value instanceof String) {
            schemaFields.name(fieldName).type().optional().stringType();
        } else if (value instanceof Boolean) {
            schemaFields.name(fieldName).type().optional().booleanType();
        } else if (value instanceof Double) {
            schemaFields.name(fieldName).type().optional().doubleType();
        } else if (value instanceof Long) {
            schemaFields.name(fieldName).type().optional().longType();
        }
    }
}

