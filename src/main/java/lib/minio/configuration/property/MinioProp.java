package lib.minio.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("application.minio")
public class MinioProp {
  private String url;
  private String username;
  private String password;
  private String accessKey;
  private String secretKey;
  private String getErrorMessage;
  private String postErrorMessage;
}
