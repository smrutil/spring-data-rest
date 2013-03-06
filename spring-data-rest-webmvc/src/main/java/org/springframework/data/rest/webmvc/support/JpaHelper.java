package org.springframework.data.rest.webmvc.support;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;

/**
 * @author Jon Brisbin
 */
public class JpaHelper implements BeanFactoryAware {

	private List interceptor = new ArrayList<Object>();

	@SuppressWarnings({"unchecked"})
	@Override public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
				(ListableBeanFactory)beanFactory,
				EntityManagerFactory.class
		);
		for(String s : beanNames) {
			EntityManagerFactory emf = (EntityManagerFactory)beanFactory.getBean(s);
			OpenEntityManagerInViewInterceptor omivi = new OpenEntityManagerInViewInterceptor();
			omivi.setEntityManagerFactory(emf);
			interceptor.add(omivi);
		}
	}

	public List getInterceptors() {
		return interceptor;
	}

}