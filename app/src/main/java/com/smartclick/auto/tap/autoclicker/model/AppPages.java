package com.smartclick.auto.tap.autoclicker.model;

import java.util.Date;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class AppPages {
    public int id;
    public String name;
    public String status;
    public Date created_at;
    public Date updated_at;
    public Date deleted_at;
    public String status_text;
}

