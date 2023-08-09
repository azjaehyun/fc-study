package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@SpringBootApplication
public class DemoApplication {




        public static void main(String args[]) throws  Exception {
            String result = makeSignature();
            System.out.println(result);
            // SpringApplication.run(DemoApplication.class, args);
        }


        public static String makeSignature() throws  Exception {
            String space = " ";					// one space
            String newLine = "\n";					// new line
            String method = "GET";					// method
            String url = "/billing/v1/cost/getContractDemandCostList?startMonth=202306&endMonth=202307&responseFormatType=json";	// url (include query string)
            String timestamp = "1691558650639";			// current timestamp (epoch)
            String accessKey = "8F1EC03454FB40716375";			// access key id (from portal or sub account)
            String secretKey = "DA2AC162A8B96B69A17985A09C2C0B1A2AB22946";

            String message = new StringBuilder()
                    .append(method)
                    .append(space)
                    .append(url)
                    .append(newLine)
                    .append(timestamp)
                    .append(newLine)
                    .append(accessKey)
                    .toString();

            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

            return encodeBase64String;
        }


}
