/**
 * 
 */
package com.vivekghosh.springboottutorials.Payloads;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
