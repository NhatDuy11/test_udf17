package FormatXMLSacom;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import org.apache.commons.text.StringEscapeUtils;
@UdfDescription(name = "convert_to_xml_sacom", description = "Converts the input string to XML format")
public class SacomXML {
    @Udf(description = "Converts the input string to XML format SACOMBANK")
    public static String convertToXmlString(String inputStr) {
        // Extract the key-value pairs from the input string
        String keyValueStr = inputStr.substring(7, inputStr.length() - 1);
        String[] keyValuePairs = keyValueStr.split(",");
        // Create a StringBuilder to store the XML output
        StringBuilder xmlOutput = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<msg xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"mqcap.xsd\" version=\"1.0.0\" dbName=\"SWITCH\">\n<rowOp authID=\"SW_APP  \" cmitLSN=\"0000:0001:ea39:c7ea:0000:000e:c96c:4d3f\" cmitTime=\"2023-08-02T14:47:05.000002\">\n<updateRow subName=\"ATMCASSETTECOUNTER0001\" srcOwner=\"SW_OWN\" srcName=\"ATMCASSETTECOUNTER\" hasLOBCols=\"0\" intentSEQ=\"0000:0001:ea39:c7e9:0000:000e:c96c:4d3a\">\n");
        // Iterate over the key-value pairs
        for (String keyValuePair : keyValuePairs) {
            // Split the key-value pair into the key and value
            String[] keyValue = keyValuePair.split("=");

            // If the key and value are both present
            if (keyValue.length == 2) {
                // Get the key and value
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                // Escape XML special characters in the value
                value = StringEscapeUtils.escapeXml10(value);
                // Append the key and value to the XML output
                xmlOutput.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
            }
        }
        // Append the closing tags to the XML output
        xmlOutput.append("</updateRow>\n</rowOp>\n</msg>");
        // Return the XML output
        return xmlOutput.toString();
    }
}
