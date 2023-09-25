import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.apache.commons.text.StringEscapeUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class StructConvertXML {
    public static String convertToXmlString(String inputStr) {
        inputStr = inputStr.trim();
        // Check if the input starts with "Struct{" and ends with "}"
        if (!inputStr.startsWith("Struct{") || !inputStr.endsWith("}")) {
            return "Invalid input format. Input must start with 'Struct{' and end with '}'.";
        }
        // Extract the key-value pairs from the input string (excluding "Struct{" and "}")
        String keyValueStr = inputStr.substring(7, inputStr.length() - 1);
        String[] keyValuePairs = keyValueStr.split(",");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("Struct");
            doc.appendChild(rootElement);

            for (String keyValuePair : keyValuePairs) {
                String[] keyValue = keyValuePair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    // Escape XML special characters in the value
                    value = StringEscapeUtils.escapeXml10(value);

                    Element element = doc.createElement(key);
                    Text textNode = doc.createTextNode(value);
                    element.appendChild(textNode);

                    rootElement.appendChild(element);
                }
            }

            return convertDocumentToString(doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String convertDocumentToString(Document doc) {
        try {
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            java.io.StringWriter writer = new java.io.StringWriter();
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(writer);
            transformer.transform(source, result);
            return writer.toString();
        } catch (javax.xml.transform.TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String inputStr = "Struct{ID=EUR1239900010001,C219=1,C76=NO,C78=20170317,C2=12399,C3=Tax Receivable,C5=Tax Recble,C6=TAXRECVBL,C7=TR,C8=EUR,C9=1,C85=12399,C93=EUR,C94=1,C95=EUR,C96=1,C99=[\"LEGACY\",{\"m\":2,\"content\":\"T24.IBAN\"},{\"m\":3,\"content\":\"PREV.IBAN\"}],C108=NO,C141=NO,C214=1,C215=28766_OFFICER__OFS_SEAT,C216=1705140624,C217=28766_OFFICER_OFS_SEAT,C218=GB0010001,C11=1,table=dwdb.GGADMIN.AVRO_RECORD2,scn=682887140,op_type=R,current_ts=1690257458318}";
        String xmlOutput = convertToXmlString(inputStr);
        System.out.println(xmlOutput);
    }
}
