package com.signicat.hackatonserver;

import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.signicat.hackatonserver.utils.Utils;

@Path("/")
public class HackatonDemoResource {

    private static final String MOBILE_ID_AUTH_URL = "https://dev01.signicat.com/std/method/nbidmobile/?"
            + "id=mobileid-auth-web::"
            + "&trailId=james%3A3p2380l8e5y26i3zpxoo9ps5qiuclnu1mgzvqcicwvdxx0ug0l"
            + "&target=http%3A%2F%2Flocalhost%3A8080%2Fmobileidauthenticated"
            + "&prefilled.deviceId=#DEVICEID#";

    public HackatonDemoResource() {
    }

    @Path("/")
    @GET
    public Response index() {
        return Response.ok("<button"
                + " onclick=\"window.location.href='https://dev01.signicat.com/std/method/nbidmobile/"
                + "?id=mobileid-reg::&trailId=james%3A3p2380l8e5y26i3zpxoo9ps5qiuclnu1mgzvqcicwvdxx0ug0l"
                + "&target=http%3A%2F%2Flocalhost%3A8080%2Fmobileidregistered'\">Mobile ID register</button>")
                .build();
    }

    @Path("/mobileidregistered")
    @POST
    public Response mobileIdRegistered(final byte[] body) throws UnsupportedEncodingException, DataFormatException {
        final String deviceId = Utils.getDeviceIdFromSamlResponse(body);
        final String authUrl = MOBILE_ID_AUTH_URL.replace("#DEVICEID#", deviceId);
        return Response.ok("<button onclick= \"window.location.href='"+authUrl+"'\">Mobile ID authentication").build();
    }

    @Path("/mobileidauthenticated")
    @POST
    public Response mobileIdAuthenticated(final byte[] body) {
        return Response.ok(body).build();
    }
}
