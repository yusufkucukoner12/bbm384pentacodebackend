package pentacode.backend.code;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Marks this class as a configuration class
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for specific routes (or use "*" for all)
        registry.addMapping("/api/**")  // Allow all routes starting with "/api"
                .allowedOrigins("http://localhost:3000")  // React app origin
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .allowedHeaders("Content-Type")  // Allowed headers (e.g., for JSON)
                .allowCredentials(true);  // Allow credentials like cookies (optional)
    }
}
