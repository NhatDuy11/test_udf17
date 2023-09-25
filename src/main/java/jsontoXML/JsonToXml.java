package jsontoXML;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import org.json.JSONObject;
import org.json.XML;

@UdfDescription(name = "json_to_xml", description = "Converts JSON to XML")

public class JsonToXml {
    @Udf(description = "Converts JSON to XML")

        public String jsonToXml(final String json) {
            if (json == null) {
                return null;
            }
            try {
                JSONObject jsonObject = new JSONObject(json);
                String xml = XML.toString(jsonObject);
                return xml;
            } catch (Exception e) {
                return null;
            }
        }


}
