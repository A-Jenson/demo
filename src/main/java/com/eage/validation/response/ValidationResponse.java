package com.eage.validation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResponse {
   public String question;
   public  int answer;
   public String token;
}
