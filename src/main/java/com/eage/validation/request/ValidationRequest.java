package com.eage.validation.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationRequest {
   String question;
   String token;
}
