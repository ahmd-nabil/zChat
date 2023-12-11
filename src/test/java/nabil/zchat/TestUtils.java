package nabil.zchat;

/**
 * @author Ahmed Nabil
 */
public class TestUtils {
    public final static String API = "http://localhost:8080";

    public final static String EXPECTED_CHAT_RESPONSE_ARRAY_JSON = """
            [
                {
                    "id": 1,
                    "chatUsers": [],
                    "chatMessages": [],
                    "lastMessage": {
                        "content":"last message"
                    }
                },{
                    "id": 2,
                    "chatUsers": [],
                    "chatMessages": [],
                    "lastMessage": {
                        "content":"last message"
                    }
                }
                
            ]
            """;
}
