package org.amit.Dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class ResponseDto {
    @NonNull public String firstName;
    @NotNull public String lastName;
    @NonNull public String career;
    @NotNull public String phone;

}
