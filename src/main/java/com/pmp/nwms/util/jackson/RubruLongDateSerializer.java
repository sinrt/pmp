package com.pmp.nwms.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pmp.nwms.config.Constants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RubruLongDateSerializer extends StdSerializer<Long> {

    private static SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_PATTERN);

    protected RubruLongDateSerializer() {
        super(Long.class);
    }

    @Override
    public void serialize(Long longDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Date date = new Date(longDate);
        jsonGenerator.writeString(formatter.format(date));
    }
}
