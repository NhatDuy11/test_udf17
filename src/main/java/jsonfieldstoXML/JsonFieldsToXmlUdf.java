package jsonfieldstoXML;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;

import org.json.JSONObject;
import org.json.XML;
@UdfDescription(name = "json_fields_to_xml2", description = "Converts JSON fields to XML")
public class JsonFieldsToXmlUdf {
    @Udf(description = "Converts JSON Fields to XML")


    public String jsonToXml(String jsonString) {
        if (jsonString == null) {
            return "";
        }
        JSONObject json = new JSONObject(jsonString);
        JSONObject result = new JSONObject();
        for (String key : json.keySet()) {
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                Object stringValue = ((JSONObject) value).opt("string");
                if (stringValue != null) {
                    result.put(key, stringValue);
                }
            } else if (value != null) {
                result.put(key, value);
            }
        }
        return XML.toString(result);
    }


}
