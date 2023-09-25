package structoXML;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
@UdfDescription(name = "struct_to_xml", description = "Converts a STRUCT string to an XML string")
public class StructoXml {
    @Udf(description = "Converts a STRUCT string to an XML string")
    public String structToXml( final String structString) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            XmlMapper xmlMapper = new XmlMapper();
            Object json = jsonMapper.readValue(structString, Object.class);
            return xmlMapper.writeValueAsString(json);
        } catch (Exception e) {
            return null;
        }

    }
}
