package pl.kpro.pastery;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PasteryApplication
{
	//Automatically obtained from application.properties
	@Value("${server.port.http}")
	private int serverPortHttp;

	@Value("${server.port.https}")
	private int serverPortHttps;

	public static void main(String[] args)
	{
		SpringApplication.run(PasteryApplication.class, args);
	}

	//Overwrite Tomcat configuration
	@Bean
	public ServletWebServerFactory servletContainer()
	{
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory()
		{
			//Forces protocol change from http to https for everything (/*)
			@Override
			protected void postProcessContext(Context context)
			{
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirector());
		return tomcat;
	}

	//Redirection from http port to https port
	private Connector httpToHttpsRedirector()
	{
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setSecure(false);
		connector.setPort(serverPortHttp);
		connector.setRedirectPort(serverPortHttps);
		return connector;
	}

}
