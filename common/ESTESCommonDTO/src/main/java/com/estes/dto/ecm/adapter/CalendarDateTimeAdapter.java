package com.estes.dto.ecm.adapter;

import java.util.Calendar;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CalendarDateTimeAdapter extends XmlAdapter<String, Calendar> {

    @Override public Calendar unmarshal(String value) {
        return javax.xml.bind.DatatypeConverter.parseDateTime(value);
    }

    @Override public String marshal(Calendar value) {
        if(value == null) { return null; }
        return javax.xml.bind.DatatypeConverter.printDateTime(value);
    }
}
