package com.finance.app.util;

import com.finance.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonUtil {

    /**
     * Extracts the token from the provided input string if it adheres to the "Bearer " format.
     *
     * @param token The input token string to extract from.
     * @return The extracted token if it starts with "Bearer ", or null otherwise.
     */
    public static String extractToken(String token) {
        // Check if the token is valid
        if (token != null && token.startsWith("Bearer ")) {
            // Split the token to remove the "Bearer " part
            return token.split(" ")[1];
        }
        return null;
    }

}
