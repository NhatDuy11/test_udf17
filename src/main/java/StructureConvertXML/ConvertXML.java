package StructureConvertXML;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import org.apache.commons.text.StringEscapeUtils;
@UdfDescription(name = "convert_to_xml_string", description = "Converts the input string to XML format")
public class ConvertXML {
    @Udf(description = "Converts the input string to XML format")
    public static String convertToXmlString(String inputStr) {
        // Extract the key-value pairs from the input string
        String keyValueStr = inputStr.substring(7, inputStr.length() - 1);
        String[] keyValuePairs = keyValueStr.split(",");

        // Create a StringBuilder to store the XML output
        StringBuilder xmlOutput = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<Struct>");

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

        // Append the closing tag to the XML output
        xmlOutput.append("</Struct>");

        // Return the XML output
        return xmlOutput.toString();
    }

}
