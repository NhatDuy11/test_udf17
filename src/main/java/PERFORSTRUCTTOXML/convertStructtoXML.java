package PERFORSTRUCTTOXML;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import org.apache.commons.text.StringEscapeUtils;
@UdfDescription(name = "convert_xml_katalyst", description = "Converts the input STRUCT to XML format")
public class convertStructtoXML {
    @Udf(description = "Converts the input STRUCT to XML format")
    public static String convertToXmlString(String inputStr) {
        String keyValueStr = inputStr.substring(7, inputStr.length() - 1);
        StringBuilder xmlOutput = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<Struct>");
        int start = 0;
        while (start < keyValueStr.length()) {
            // Find the next key-value pair
            int end = keyValueStr.indexOf(',', start);
            if (end == -1) {
                end = keyValueStr.length();
            }
            String keyValuePair = keyValueStr.substring(start, end);
            start = end + 1;

            // Split the key-value pair into the key and value
            int separatorIndex = keyValuePair.indexOf('=');
            if (separatorIndex != -1) {
                // Get the key and value
                String key = keyValuePair.substring(0, separatorIndex).trim();
                String value = keyValuePair.substring(separatorIndex + 1).trim();
                value = StringEscapeUtils.escapeXml11(value);
                xmlOutput.append('<').append(key).append('>').append(value).append("</").append(key).append('>');
            }
        }
        xmlOutput.append("</Struct>");
        return xmlOutput.toString();
    }
}
