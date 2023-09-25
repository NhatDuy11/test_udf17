package PERFORXMLTOJSON;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import org.json.JSONObject;
import org.json.XML;
@UdfDescription(name = "convert_json_katalyst", description = "Converts XML to JSON")
public class performanceXMLTOJSON {

    @Udf(description = "Converts XML to JSON")
    public String xmlToJson(final String xml) {
        if (xml == null) {
            return null;
        }
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xml.replace('\u0000', ' '));
            return xmlJSONObj.toString(4);
        } catch (Exception e) {
            return null;
        }
    }
}
