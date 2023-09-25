public class LogToXmlConverter {

    public static void main(String[] args) {
        String log = "time=\\\"2023-08-04T13:45:47.431872805+07:00\\\" level=warning msg=\\\"Health check for container 8698a9ea3b9e76060d827675c9c078b283044fa1acc4fdf6f90dbef51bf7e33f error: OCI runtime exec failed: exec failed: unable to start container process: exec: \\curl\\: executable file not found in $PATH\\";
        String xml = convertLogToXml(log);
        System.out.println(xml);
    }
    public static String convertLogToXml(String log) {
        String[] parts = log.split(" ");
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<log_entry>\n");

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].startsWith("time=")) {
                String timestamp = parts[i].substring(6, parts[i].length() - 1);
                xmlBuilder.append("  <timestamp>").append(timestamp).append("</timestamp>\n");
            } else if (parts[i].startsWith("level=")) {
                String level = parts[i].substring(6);
                xmlBuilder.append("  <level>").append(level).append("</level>\n");
            } else if (parts[i].startsWith("msg=")) {
                String message = parts[i].substring(5);
                while (!message.endsWith("\\\"")) {
                    i++;
                    if (i >= parts.length) {
                        break;
                    }
                    message += " " + parts[i];
                }
                message = message.replace("\\\"", ""); // Remove escaped quotes
                message = message.replace("\\\\curl\\\\", ""); // Remove the escaped curl tag

                int curlIndex = message.indexOf("\\curl\\");
                if (curlIndex != -1) {
                    String errorMsg = message.substring(0, curlIndex);
                    String curlMsg = message.substring(curlIndex + 7); // Skip "\\curl\\"
                    xmlBuilder.append("  <message>").append(errorMsg).append("</message>\n");
                    xmlBuilder.append("  <Curl>").append(curlMsg).append("</Curl>\n");
                } else {
                    xmlBuilder.append("  <message>").append(message).append("</message>\n");
                }
            }
        }

        xmlBuilder.append("</log_entry>");
        return xmlBuilder.toString();
    }
}
