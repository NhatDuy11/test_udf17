        package ExtractFields;
        import com.fasterxml.jackson.databind.JsonNode;
        import io.confluent.ksql.function.udf.Udf;
        import io.confluent.ksql.function.udf.UdfDescription;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import io.confluent.ksql.function.udf.UdfParameter;
        import org.apache.kafka.connect.data.Field;
        import org.apache.kafka.connect.data.Schema;
        import org.apache.kafka.connect.data.SchemaBuilder;
        import org.apache.kafka.connect.data.Struct;

        import java.util.*;

        @UdfDescription(name = "demo_extract_all_fields7", description =   "Extract fields from a JSON string")
        public class JsonFieldExtractorUDF {
                        private static final ObjectMapper mapper = new ObjectMapper();

                        @Udf(description = "Extract specific fields from a JSON string")
                        public Struct extractAllFieldsFromJson(
                                @UdfParameter(value = "json", description = "The JSON object containing the JSON strings to extract the fields from") final String json) {
                            try {
                                JsonNode rootNode = mapper.readTree(json);
                                JsonNode rowNode = rootNode.get("row");
                                if (rowNode != null) {
                                    SchemaBuilder builder = SchemaBuilder.struct();
                                    Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rowNode.fields();
                                    while (fieldsIterator.hasNext()) {
                                        Map.Entry<String, JsonNode> fieldEntry = fieldsIterator.next();
                                        String fieldName = fieldEntry.getKey();
                                        JsonNode fieldValue = fieldEntry.getValue();
                                        Schema fieldSchema = getSchemaForJsonValue(fieldValue);
                                        builder.field(fieldName, fieldSchema);
                                    }
                                    Schema structSchema = builder.build();
                                    Struct struct = new Struct(structSchema);
                                    fieldsIterator = rowNode.fields();
                                    while (fieldsIterator.hasNext()) {
                                        Map.Entry<String, JsonNode> fieldEntry = fieldsIterator.next();
                                        String fieldName = fieldEntry.getKey();
                                        JsonNode fieldValue = fieldEntry.getValue();
                                        Schema fieldSchema = getSchemaForJsonValue(fieldValue);
                                        Object fieldObject = getJavaValueForJsonValue(fieldValue, fieldSchema);
                                        struct.put(fieldName, fieldObject);
                                    }
                                    return  struct;
                                }
                            } catch (Exception e) {
                                // Handle exception
                            }
                            return null;
                        }

                        private Schema getOptionalSchemaForJsonValue(JsonNode jsonValue) {
                            Schema fieldSchema = getSchemaForJsonValue(jsonValue);

                            if (fieldSchema != null) {
                                return SchemaBuilder.struct()
                                        .field("type", fieldSchema)
                                        .field("value", fieldSchema)
                                        .optional()
                                        .build();
                            }

                            return null;
                        }
                        private Schema getSchemaForJsonValue(JsonNode jsonValue) {
                            if (jsonValue == null || jsonValue.isNull()) {
                                return SchemaBuilder.struct().optional().build();
                            } else if (jsonValue.isTextual()) {
                                return SchemaBuilder.string().optional().build();
                            } else if (jsonValue.isBoolean()) {
                                return SchemaBuilder.bool().optional().build();
                            } else if (jsonValue.isInt()) {
                                return SchemaBuilder.int32().optional().build();
                            } else if (jsonValue.isLong()) {
                                return SchemaBuilder.int64().optional().build();
                            } else if (jsonValue.isDouble()) {
                                return SchemaBuilder.float64().optional().build();
                            } else if (jsonValue.isArray()) {
                                Schema elementSchema = getSchemaForJsonValue(jsonValue.get(0));
                                return SchemaBuilder.array(elementSchema).optional().build();
                            } else if (jsonValue.isObject()) {
                                SchemaBuilder builder = SchemaBuilder.struct().optional();
                                Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonValue.fields();
                                while (fieldsIterator.hasNext()) {
                                    Map.Entry<String, JsonNode> fieldEntry = fieldsIterator.next();
                                    String fieldName = fieldEntry.getKey();
                                    JsonNode fieldValue = fieldEntry.getValue();
                                    Schema fieldSchema = getSchemaForJsonValue(fieldValue);
                                    builder.field(fieldName, fieldSchema);
                                }
                                return builder.build();
                            } else {
                                return null;
                            }
                        }

                        private Object getJavaValueForJsonValue(JsonNode jsonValue, Schema schema) {
                            if (jsonValue == null || jsonValue.isNull()) {
                                return null;
                            } else if (schema.type() == Schema.Type.STRING && schema.name() == null) {
                                return jsonValue.asText();
                            } else if (jsonValue.isTextual()) {
                                return jsonValue.asText();
                            } else if (jsonValue.isBoolean()) {
                                return jsonValue.asBoolean();
                            } else if (jsonValue.isInt()) {
                                return jsonValue.asInt();
                            } else if (jsonValue.isLong()) {
                                return jsonValue.asLong();
                            } else if (jsonValue.isDouble()) {
                                return jsonValue.asDouble();
                            } else if (jsonValue.isArray()) {
                                List<Object> array = new ArrayList<>();
                                for (JsonNode element : jsonValue) {
                                    Object elementObject = getJavaValueForJsonValue(element, schema.valueSchema());
                                    array.add(elementObject);
                                }
                                return array;
                            } else if (jsonValue.isObject()) {
                                Struct struct = new Struct(schema);
                                Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonValue.fields();
                                while (fieldsIterator.hasNext()) {
                                    Map.Entry<String, JsonNode> fieldEntry = fieldsIterator.next();
                                    String fieldName = fieldEntry.getKey();
                                    JsonNode fieldValue = fieldEntry.getValue();
                                    Schema fieldSchema = schema.field(fieldName).schema();
                                    Object fieldObject = getJavaValueForJsonValue(fieldValue, fieldSchema);
                                    struct.put(fieldName, fieldObject);
                                }
                                return struct;
                            } else {
                                return null;
                            }
                        }
                        public static void main(String[] args) {

                            String jsonInput ="{\n" +
                                    "  \"VALUE\": {\n" +
                                    "    \"string\": \"{\\\"row\\\": {\\\"c85\\\": 14028,\\\"c96\\\": 1,\\\"c11\\\": 1,\\\"c99\\\": \\\"LEGACY\\\",\\\"c76\\\": \\\"NO\\\",\\\"c78\\\": 19980723,\\\"c2\\\": 14028,\\\"c3\\\": \\\"Inw Clg Contra\\\",\\\"c5\\\": \\\"Inw Clg Contra\\\",\\\"c6\\\": \\\"INWCL14028\\\",\\\"c7\\\": \\\"TR\\\",\\\"c8\\\": \\\"USD\\\",\\\"c9\\\": 1,\\\"c217\\\": \\\"78671_INPUTTER_OFS_MB.OFS.AUTH\\\",\\\"c216\\\": 1704062201,\\\"c219\\\": 1,\\\"id\\\": \\\"USD140280001\\\",\\\"c108\\\": \\\"NO\\\",\\\"c218\\\": \\\"GB0010001\\\",\\\"c93\\\": \\\"USD\\\",\\\"c95\\\": \\\"USD\\\",\\\"c215\\\": [\\\"1_DIM\\\",{\\\"m\\\": 2,\\\"content\\\": \\\"213_CONV.ACCOUNT.201405\\\"}],\\\"c94\\\": 1,\\\"c214\\\": null}}\"\n" +
                                    "  }\n" +
                                    "}";

                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                JsonNode rootNode = objectMapper.readTree(jsonInput);
                                JsonNode valueNode = rootNode.get("VALUE");
                                String jsonString = valueNode.get("string").asText();
                                JsonFieldExtractorUDF udf = new JsonFieldExtractorUDF();
                                Struct result = udf.extractAllFieldsFromJson(jsonString);
                                SchemaBuilder structSchemaBuilder = SchemaBuilder.struct().optional();
                                for (Field field : result.schema().fields()) {
                                    structSchemaBuilder.field(field.name(), field.schema());
                                }
                                Schema optionalStructSchema = structSchemaBuilder.build();
                                Struct optionalResult = new Struct(optionalStructSchema);
                                for (Field field : result.schema().fields()) {
                                    optionalResult.put(field.name(), result.get(field));
                                }
                                System.out.println("Optional Result: " + optionalResult);

                                Schema yourStructSchema = SchemaBuilder.struct()
                                        .field("c85", Schema.INT32_SCHEMA)
                                        .field("c96", Schema.INT32_SCHEMA)
                                        .field("c11", Schema.INT32_SCHEMA)
                                        .build();
                                Struct struct = new Struct(yourStructSchema);
                                struct.put("c85", 14028);
                                struct.put("c96", 1);
                                struct.put("c11", 1);
                                Optional<Struct> optionalStruct = Optional.of(struct);
                                if (optionalStruct.isPresent()) {
                                    System.out.println("Optional Result is present.");
                                    Struct optionalResultFromStruct = optionalStruct.get();
                                    System.out.println("c85 value: " + optionalResultFromStruct.getInt32("c85"));
                                    System.out.println("c96 value: " + optionalResultFromStruct.getInt32("c96"));
                                } else {
                                    System.out.println("Optional Result is empty.");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }