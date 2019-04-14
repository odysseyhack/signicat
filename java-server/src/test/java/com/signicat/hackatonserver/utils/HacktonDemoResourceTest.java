package com.signicat.hackatonserver.utils;

import java.io.IOException;
import java.util.zip.DataFormatException;

import javax.ws.rs.core.Response;

import org.apache.http.ParseException;
import org.junit.Test;

import com.google.common.io.Resources;
import com.signicat.hackatonserver.HackatonDemoResource;

public class HacktonDemoResourceTest {

    @Test
    public void index_returns_200_with_html() throws ParseException, IOException {
        // Arrange
        HackatonDemoResource resource = new HackatonDemoResource();

        // Act
        Response response = resource.index();

        // Assert
        assert (response.getStatus() == 200);
        assert (((String) response.getEntity()).contains("dev01.signicat.com"));
    }

    @Test
    public void mobile_id_registered_returns_200_with_html() throws Exception {
        // Arrange
        HackatonDemoResource resource = new HackatonDemoResource();

        // Act
        Response response = resource.mobileIdRegistered(
                Resources.toByteArray(Resources.getResource("mobile_id_register_success_response.txt")));

        // Assert
        assert (response.getStatus() == 200);
        assert (((String) response.getEntity()).contains("dev01.signicat.com"));
    }

    @Test
    public void mobile_id_authenticated_returns_200_with_html_like_body() {
        // Arrange
        HackatonDemoResource resource = new HackatonDemoResource();

        // Act
        Response response = resource.mobileIdAuthenticated("my body is soft".getBytes());

        // Assert
        assert (response.getStatus() == 200);
        assert (new String(((byte[]) response.getEntity())).contains("my body is soft"));
    }
}
