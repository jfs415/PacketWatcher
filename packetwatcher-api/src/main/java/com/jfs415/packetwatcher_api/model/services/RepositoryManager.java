package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.model.analytics.StatsRecord;
import com.jfs415.packetwatcher_api.model.events.EventMappedSuperclass;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherEventRepository;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Service
public class RepositoryManager {
	
	private final WebApplicationContext webApplicationContext;

	private Repositories repositories;

	@Autowired
	public RepositoryManager(WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}

	@SuppressWarnings("unchecked")
	public <T extends EventMappedSuperclass, E extends Serializable> PacketWatcherEventRepository<T, E> getEventRepository(Class<?> entity) {
		ensureRepositoriesInstantiated();
		Object repository = repositories.getRepositoryFor(entity).orElseThrow(RuntimeException::new);

		if (!(repository instanceof PacketWatcherEventRepository)) {
			throw new RuntimeException("Found repository is not instanceof PacketWatcherEventRepository");
		}

		return (PacketWatcherEventRepository<T, E>) repository;
	}

	@SuppressWarnings("unchecked")
	public <T extends StatsRecord, E extends Serializable> PacketWatcherStatsRepository<T, E> getStatsRepository(Class<?> entity) {
		ensureRepositoriesInstantiated();
		Object repository = repositories.getRepositoryFor(entity).orElseThrow(RuntimeException::new);

		if (!(repository instanceof PacketWatcherStatsRepository)) {
			throw new RuntimeException("Found repository is not instanceof PacketWatcherStatsRepository");
		}

		return (PacketWatcherStatsRepository<T, E>) repository;
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
