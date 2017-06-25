package com.j256.simplejmx.spring;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.j256.simplejmx.common.JmxAttributeField;
import com.j256.simplejmx.common.JmxFolderName;
import com.j256.simplejmx.common.JmxResource;
import com.j256.simplejmx.common.JmxSelfNaming;
import com.j256.simplejmx.server.JmxServer;

@Ignore
public class BeanPublisherTest {

	private static final String[] SPRING_CONFIG_FILES = new String[] { "classpath:/beanPublisherTest.xml" };

	@Test
	public void testStuff() throws IOException {
		ClassPathXmlApplicationContext context = null;

		JmxServer jmxServer = null;
		try {
			// load our context
			context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
			context.registerShutdownHook();

			// get our MainJmx bean from the context
			jmxServer = (JmxServer) context.getBean("jmxServer", JmxServer.class);
			assertNotNull(jmxServer);
		} finally {
			if (jmxServer != null) {
				jmxServer.close();
			}
			if (context != null) {
				context.close();
			}
		}
		System.gc();
	}

	@JmxResource(folderNames = { "foo", "bar" })
	public static class OurJmxObject implements JmxSelfNaming {

		@JmxAttributeField
		public long foo = 10;

		@Override
		public String getJmxDomainName() {
			return "domain";
		}

		@Override
		public String getJmxBeanName() {
			return "bean";
		}

		@Override
		public JmxFolderName[] getJmxFolderNames() {
			return null;
		}

		public void voidMethod() {
			// for testing factory bean handling
		}
	}
}
