package jandy3.DesignSeller.paylaod;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    // Getters and Setters (Omitted for brevity)
}