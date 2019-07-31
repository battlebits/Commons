package br.com.battlebits.commons.server.loadbalancer;

import br.com.battlebits.commons.server.loadbalancer.element.LoadBalancerObject;

public interface LoadBalancer<T extends LoadBalancerObject> {

	public T next();

}
