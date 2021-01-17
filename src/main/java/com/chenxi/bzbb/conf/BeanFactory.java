package com.chenxi.bzbb.conf;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BeanFactory {
    private static final String APP_ID = "2019092767876317";
    private static final String RSA_private = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDFdkjbcasRmLxRrLh2ZVGLYD4FzCknvVhNYxqTBFWZemakZnj6TG1AGqIBCvlDrUg+FTyrk1S/neNi7qCLourBzvEMtYCFR9DoVDvHq5up7g3SNoWmhEZpW8eYDONUf1V9esj6Dbq59FPigO9ch8N7QalU1dkQpjar+qb1bqd2LozkqUOIlfE6WYKw6/jL8rnV8g/QViHi/AAU315S6UzHy59jrWi0DRACNH3IejZmT1DgYSu4jYigijkpuC22FhbwBmfQ6qdHKR+N3HH3MgT+z6YPgyaWZmJKwjBeIujK5A//fin/OTPsRZMd8ER6bmy2+O+xJOpirLvOYLLnkxAjAgMBAAECggEAP+xq6Z+Coc+wJ0Ci9rEWFOizK21MZXEGhQbwM2fTzjuxvsbtoxnaxEXa7DuBNkeCe5akbWbkqKOUWRKnJo03MkasAlE/qeX120BpmdoUKo7GKqc73Qw1PyZXFwIVFeWi51w4c7q+GDTgcxMKagoJXn2DM9OG0bz9Ne+O3SVVUQxui5BbUjl8PfpRdGYa3ciEbPDSY4Y+TelqthQ45U4WmZvXn37A6VQWLk0U0qEil60BgDtbCz9wVCX/bm6uUGdlX42YvNqnku+5YzpJJeLp5MhFj2FAYjYZ8/0BtnbWDHkbjuQYA7VGfEJ+K1qLVhLO12CSYKDYYQnnyIRBj6pTAQKBgQD37hh/GVshb6CLH1Ylcmj54JbjN9ixPUGkll7hGvpkeLaX6f6ZwccfGRk7r1pbw4H9suIyJ32gs138WGf+SA68nLGrK5fM7+Hez9mnAEDq49cTBvDYO2EVImGyv+dXrVFgIbmDqMBhMZvCxU6U/pive2rFXXo4VBLNofp5KvE57wKBgQDL46ifuLpxrkNmNWdOee6AYA5X+20zZYnLeeGckpaI1+77LPXZqQreu+QBO5xQ4GTEidFGtjJTCFF0jx2kb7KHawqxtU6NVpxwAbs16zZp8KoRArKM3aUz8ByoPi2D8ofHUsSRAcAiDx232nUB6ecOMho15WO7r/kyS4eyWmnRDQKBgQC6BD8G5WxVat5GY42L7QpMKLCs7BK98FrW9Sdct5QUW+mLkzm9/QC8GWWwPUGLReW7BkLmJHECaHFkyqPDyiacZuh6p+tuDLEoLkR8IxmR4r5UfUmowux1IeWxaoBVbSl3+fWpjaxBgCoAeW8bJOZEC1IxoI/5wUKWXCiETq7MqwKBgHoRihJeYdnP8HBQBtK4M6/OUqggUufToeUdypKYPOIfDeh5qZOwHl7t9Ps7+kF9pzb4IECKsqR9tW7ohGG/sg5bauKsXyRFt5Fjp/cHJOPWf6uBhu7JgLAQ0Md1uoBmAx62EhOTDboWfL9nXmhP5tLb8k9UUBq9p5+SEuMYOIQ9AoGAF5Ed4/3dwhHDCijWM8UwWww7Xhw5pslNFoOTeGYugF6ZWO+/h2tr65OhReDAEnexK/xxDAebPLCcg+anO9tOcIFd3yxE0gkgTnDo+jG7nrhvsgeZRWaaT51295WXr2lpjHktDRO9JrM0o20bXr6WxDOyktLP9v+BUGzq3jGaKn8=";
    private static final String Alipay_public="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiAgLVSXEsooPg2E/jULD0tcZSxvUuF2zf5NVg4kxeazo2OAhno8Arb37HDfspF7ypqnQxmkWXp2FnW+Y56fSh9K9VZwtu/qTvIXb1UfUtEqbO/fDb3KWiBJ5CYMjY4adPOGEhcWYj50TPVC4rwAiyguUiyoRVbNKTrl4q6lNkcWQyUTSm+mErL2QtNq7r08TkOjW8LldB54mxRNFaX1nfrlnv+xI4y1f/5XGVdlsNMSYFA+q01TNM7JgryOxNRWYlV7jbcuKJwmH78jOdWGyeLC8u/cPmFMVUKKDop8lMqgou045EBs27eqzWTF77/Sc9TjmLDouHvR0zs25lcrcWwIDAQAB";
    private static final String Charset = "utf-8";

    @Autowired
    DataSource dataSource;
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public AlipayClient initAlipayClient(){
        return new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APP_ID,RSA_private,"json",Charset,Alipay_public,"RSA2");
    }
}
