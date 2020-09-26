package com.amuntee.auth.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class UserStoreRequest {
    private String username;
    private String password;
    private String fullname;
    private String code;
    private String email;
    private String phone;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private int partnerId;
    private int roleId;
    private int positionId;
    private int status;
}
