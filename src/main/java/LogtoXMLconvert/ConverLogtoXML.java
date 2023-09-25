package LogtoXMLconvert;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;
@UdfDescription(name = "log_to_xml_katalyst", description = "Converts a log string to XML format")
public class ConverLogtoXML {
    private static final Pattern LOG_PATTERN = Pattern.compile("time=\"([^\"]+)\" level=([a-z]+) msg=\"(.+)\"");
    @Udf(description = "Converts a log string to XML format")
    public String logToXml(@UdfParameter(value = "log") String log) {
        log = preprocess(log);
        Matcher matcher = LOG_PATTERN.matcher(log);
        if (!matcher.find()) {
            return null;
        }
        String timestamp = matcher.group(1);
        String level = matcher.group(2);
        String message = matcher.group(3);

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<log_entry>\n");
        xmlBuilder.append("  <timestamp>").append(timestamp).append("</timestamp>\n");
        xmlBuilder.append("  <level>").append(level).append("</level>\n");

        int curlIndex = message.indexOf("curl");
        if (curlIndex != -1) {
            String errorMsg = message.substring(0, curlIndex);
            String curlMsg = message.substring(curlIndex + 7); // Skip "curl"
            curlMsg = curlMsg.replaceAll("[\\x00-\\x1F\\x7F]", "");
            StringBuilder filteredCurlMsg = new StringBuilder();
            for (char c : curlMsg.toCharArray()) {
                if (Character.isLetterOrDigit(c) || Character.isWhitespace(c) || c == '_' || c == '-' || c == ':' || c == '.') {
                    filteredCurlMsg.append(c);
                }
            }
            xmlBuilder.append("  <message>").append(escapeXml(errorMsg)).append("</message>\n");
            xmlBuilder.append("  <Curl>").append(StringEscapeUtils.escapeXml11(filteredCurlMsg.toString())).append("</Curl>\n");  // Use StringEscapeUtils for XML escaping
        } else {
            xmlBuilder.append("  <message>").append(escapeXml(message)).append("</message>\n");
        }

        xmlBuilder.append("</log_entry>");
        return xmlBuilder.toString();
    }

    private String preprocess(String log) {
        // Remove non-printable characters
        log = log.replaceAll("[^\\x00-\\x7F]+", "");

        // Replace escape sequences
        log = log.replace("\\\"", "\"");
        log = log.replace("\\\\", "\\");

        return log;
    }
    @SuppressWarnings("unchecked")
    private String escapeXml(String input) {
        return input
                .replace("&", "")
                .replace("<", "")
                .replace(">", "")
                .replace("\"", "");

    }

    public static void main(String[] args) {
        String inputLog = "\u0000\u0000\u0000\u0000\u0013\u0000\u000eRFC3164\u0002�\u0004time=\"2023-08-10T14:11:05.784139545+07:00\" level=warning msg=\"Health check for container 8698a9ea3b9e76060d827675c9c078b283044fa1acc4fdf6f90dbef51bf7e33f error: OCI runtime exec failed: exec failed: unable to start container process: exec: \\\"curl\\\": executable file not found in $PATH: unknown\"\u0002\u0018openmetadata\u0000\u0002\f\u0002\u000edockerd\u0000\u0000\u0000\u0002\u0006\u0002\u001a192.168.1.115\u0002�\u0005<30>Aug 10 14:11:05 openmetadata dockerd: time=\"2023-08-10T14:11:05.784139545+07:00\" level=warning msg=\"Health check for container 8698a9ea3b9e76060d827675c9c078b283044fa1acc4fdf6f90dbef51bf7e33f error: OCI runtime exec failed: exec failed: unable to start container process: exec: \\\"curl\\\": executable file not found in $PATH: unknown\"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002В���b\u0000";
        ConverLogtoXML logConverter = new ConverLogtoXML();
        String xmlOutput = logConverter.logToXml(inputLog);
        System.out.println(xmlOutput);
    }
}
