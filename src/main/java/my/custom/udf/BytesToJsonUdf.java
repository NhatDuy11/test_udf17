        package my.custom.udf;
        import io.confluent.ksql.function.udf.Udf;
        import io.confluent.ksql.function.udf.UdfDescription;
        import org.json.JSONObject;
        import org.json.XML;
        @UdfDescription(name = "xml_to_json15", description = "Converts XML to JSON")
        public class BytesToJsonUdf {
            @Udf(description = "Converts XML to JSON")
            public String xmlToJson(final String xml) {
                if (xml == null) {
                    return null;
                }
                try {
                    String xmlFiltered = xml.replaceAll("\\p{C}", "");
                    JSONObject xmlJSONObj = XML.toJSONObject(xmlFiltered);
                    String jsonPrettyPrintString = xmlJSONObj.toString(4);
                    return jsonPrettyPrintString;
                } catch (Exception e) {
                    return null;
                }
            }
        }
