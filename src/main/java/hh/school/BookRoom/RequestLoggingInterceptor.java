package hh.school.BookRoom;

import jakarta.annotation.Nonnull;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.ReaderInterceptorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Provider
public class RequestLoggingInterceptor implements ReaderInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public Object aroundReadFrom(@Nonnull ReaderInterceptorContext context) throws IOException, WebApplicationException {
        byte[] bodyBytes = context.getInputStream().readAllBytes();
        String requestBody = new String(bodyBytes, StandardCharsets.UTF_8);

        log.info("Request body: {}", requestBody);

        context.setInputStream(new ByteArrayInputStream(bodyBytes));
        return context.proceed();
    }
}
