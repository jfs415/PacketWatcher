package com.jfs415.packetwatcher_api.model.services;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherEventRepository;

@Service
public class RepositoryManager {

	@Autowired
	WebApplicationContext webApplicationContext;

	private Repositories repositories;

	@SuppressWarnings("unchecked")
	public <T extends EventMappedSuperclass, E extends Serializable> PacketWatcherEventRepository<T, E> getRepository(Class<?> entity) {
		ensureRepositoriesInstantiated();
		Object repository = repositories.getRepositoryFor(entity).orElseThrow(RuntimeException::new);

		if (!(repository instanceof PacketWatcherEventRepository)) {
			throw new RuntimeException("Found repository is not instanceof PacketWatcherEventRepository");
		}

		return (PacketWatcherEventRepository<T, E>) repository;
	}

	private void ensureRepositoriesInstantiated() {
		/*
			The repositories' field needs to be lazily instantiated in order to prevent
			a null ListableBeanFactory (WebApplicationContext) from being passed. This
			will happen if this Service is loaded before the WebApplicationContext.
		 */
		if (repositories == null) {
			repositories = new Repositories(webApplicationContext);
		}
	}

}
