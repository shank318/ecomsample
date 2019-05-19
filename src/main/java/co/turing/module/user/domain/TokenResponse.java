package co.turing.module.user.domain;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TokenResponse {
    private String token;
}
