package com.signicat.hackatonserver.utils;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class UtilsTest {

    @Test
    public void gets_device_id_from_saml_response() throws IOException {
        String deviceId = Utils.getDeviceIdFromSamlResponse(getSamlResponse());
        assert (deviceId.equals("BdK56ZdQZX7KOGtj6fR6h9MHQXDrytvB"));
    }

    private byte[] getSamlResponse() throws IOException {
        URL resourceUrl = Resources.getResource("mobile_id_register_success_response.txt");
        return Resources.toString(resourceUrl, Charsets.UTF_8)
                .getBytes();
    }
}
