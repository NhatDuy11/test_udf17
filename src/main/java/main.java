import AvroParse.avroParse;
import org.apache.avro.Schema;


public class main {
    public static void main(String[] args) {
        avroParse avroParse =  new avroParse();
        String avroData = "{\"VALUE\":{\"string\":\"{\\\"row\\\": {\\n    \\\"c85\\\": 12010,\\n    \\\"c96\\\": 1,\\n    \\\"c11\\\": 1,\\n    \\\"c99\\\": \\\"LEGACY\\\",\\n    \\\"c76\\\": \\\"NO\\\",\\n    \\\"c78\\\": 19980723,\\n    \\\"c2\\\": 12010,\\n    \\\"c3\\\": \\\"Commission Receivable\\\",\\n    \\\"c5\\\": \\\"Comm Recvble\\\",\\n    \\\"c6\\\": \\\"COMMI12010\\\",\\n    \\\"c7\\\": \\\"TR\\\",\\n    \\\"c8\\\": \\\"USD\\\",\\n    \\\"c9\\\": 1,\\n    \\\"c217\\\": \\\"78671_INPUTTER_OFS_MB.OFS.AUTH\\\",\\n    \\\"c216\\\": 1704062201,\\n    \\\"c219\\\": 1,\\n    \\\"id\\\": \\\"USD120100001\\\",\\n    \\\"c108\\\": \\\"NO\\\",\\n    \\\"c218\\\": \\\"GB0010001\\\",\\n    \\\"c93\\\": \\\"USD\\\",\\n    \\\"c95\\\": \\\"USD\\\",\\n    \\\"c215\\\": [\\n        \\\"1_DIM\\\",\\n        {\\n            \\\"m\\\": 2,\\n            \\\"content\\\": \\\"213_CONV.ACCOUNT.201405\\\"\\n        }\\n    ],\\n    \\\"c94\\\": 1,\\n    \\\"c214\\\": 1\\n}}\"}}}]";


        Schema schema = avroParse.createOptionalSchema(avroData);
        System.out.println("Generated Schema:");
        System.out.println(schema.toString());
    }
}
