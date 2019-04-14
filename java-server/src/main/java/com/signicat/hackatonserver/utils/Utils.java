package com.signicat.hackatonserver.utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

    private Utils() {
    }

    private static final String UTF8 = "UTF-8";

    private static final Logger LOG = LogManager.getLogger(Utils.class.getName());
    private static Pattern pattern = Pattern.compile("\\Q\"device-id\"\\E.*?\\Q<AttributeValue>\\E(.*?)\\Q</AttributeValue>\\E");

    public static String getDeviceIdFromSamlResponse(final byte[] samlResponse) {
        final String decodedInflated = getAttributesFromSamlResponse(samlResponse);
        final Matcher matcher = pattern.matcher(decodedInflated);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String getAttributesFromSamlResponse(final byte[] body) {
        try {
            final String samlResponse = Utils.getResponseSamlAttributesMap(new String(body, UTF8)).get("SAMLResponse");
            final String urlDecodedResponse = URLDecoder.decode(samlResponse, UTF8).replaceAll("[ \t\r\n]", "");
            final byte[] decodedSamlResponse = Base64.getDecoder().decode(urlDecodedResponse);
            return new String(decodedSamlResponse);
        }catch(final IOException e) {
            LOG.error(e);
        }
        return null;
    }

    private static Map<String, String> getResponseSamlAttributesMap(final String response) {
        final Map<String, String> queryParameters = new HashMap<String, String>();
        final String[] keyValuePairs = response.split("&");
        for (final String keyValuePair : keyValuePairs) {
            final int indexOfEqualSign = keyValuePair.indexOf("=");
            queryParameters.put(keyValuePair.substring(0, indexOfEqualSign), keyValuePair.substring(indexOfEqualSign + 1));
        }
        return queryParameters;
    }
}
