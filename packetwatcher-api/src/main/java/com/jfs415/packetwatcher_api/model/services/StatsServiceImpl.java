package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.annotations.PacketWatcherStats;
import com.jfs415.packetwatcher_api.exceptions.EventAnnotationNotFoundException;
import com.jfs415.packetwatcher_api.exceptions.StatsAnnotationNotFoundException;
import com.jfs415.packetwatcher_api.model.analytics.StatsRecord;
import com.jfs415.packetwatcher_api.model.repositories.PacketWatcherStatsRepository;
import com.jfs415.packetwatcher_api.util.RangedSearchAmount;
import com.jfs415.packetwatcher_api.util.RangedSearchTimeframe;
import com.jfs415.packetwatcher_api.util.SearchAmount;
import com.jfs415.packetwatcher_api.util.SearchTimeframe;
import com.jfs415.packetwatcher_api.views.collections.StatsCollectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.stream.Stream;

@Service
public class StatsServiceImpl {

	private final RepositoryManagerImpl repositoryManagerImpl;

	@Autowired
	public StatsServiceImpl(RepositoryManagerImpl repositoryManagerImpl) {
		this.repositoryManagerImpl = repositoryManagerImpl;
	}

	@Transactional(noRollbackFor = StatsAnnotationNotFoundException.class)
	public StatsCollectionView getStatsByType(Class<?> statsType) {
		try {
			validate(statsType);
		} catch (StatsAnnotationNotFoundException e) {
			return new StatsCollectionView();
		}

		PacketWatcherStatsRepository<StatsRecord, Serializable> repository = repositoryManagerImpl.getStatsRepository(statsType);

		return new StatsCollectionView(repository.findAll().stream().map(StatsRecord::toStatsView).toList());
	}

	@Transactional(noRollbackFor = StatsAnnotationNotFoundException.class)
	public StatsCollectionView getStatsByTypeAndLastCaughtWithTimeframe(Class<?> statsType, SearchTimeframe timeframe) {
		try {
			validate(statsType);
		} catch (StatsAnnotationNotFoundException e) {
			return new StatsCollectionView();
		}

		return lookupStatsByLastCaughtAndTimeframe(repositoryManagerImpl.getStatsRepository(statsType), timeframe);
	}

	@Transactional(noRollbackFor = StatsAnnotationNotFoundException.class)
	public StatsCollectionView getStatsByTypeAndFirstCaughtWithTimeframe(Class<?> statsType, SearchTimeframe timeframe) {
		try {
			validate(statsType);
		} catch (StatsAnnotationNotFoundException e) {
			return new StatsCollectionView();
		}

		return lookupStatsByFirstCaughtAndTimeframe(repositoryManagerImpl.getStatsRepository(statsType), timeframe);
	}

	@Transactional(noRollbackFor = StatsAnnotationNotFoundException.class)
	public StatsCollectionView getStatsByTypeWithRecordsCaughtAmount(Class<?> statsType, SearchAmount amount) {
		try {
			validate(statsType);
		} catch (StatsAnnotationNotFoundException e) {
			return new StatsCollectionView();
		}

		return lookupStatsWithRecordsCaughtAmount(repositoryManagerImpl.getStatsRepository(statsType), amount);
	}

	private StatsCollectionView lookupStatsByLastCaughtAndTimeframe(PacketWatcherStatsRepository<StatsRecord, Serializable> repository, SearchTimeframe timeframe) {
		Stream<StatsRecord> stats;

		switch (timeframe.getTimeframe()) {
			case BEFORE:
				stats = repository.findAllByLastCaughtBefore(timeframe.getTimestamp()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			case AFTER:
				stats = repository.findAllByLastCaughtAfter(timeframe.getTimestamp()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			case BETWEEN:
				RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) timeframe;
				stats = repository.findAllByLastCaughtBetween(dualTimeframe.getStart(), dualTimeframe.getEnd()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			default:
				return new StatsCollectionView();
		}
	}

	private StatsCollectionView lookupStatsByFirstCaughtAndTimeframe(PacketWatcherStatsRepository<StatsRecord, Serializable> repository, SearchTimeframe timeframe) {
		Stream<StatsRecord> stats;

		switch (timeframe.getTimeframe()) {
			case BEFORE:
				stats = repository.findAllByFirstCaughtBefore(timeframe.getTimestamp()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			case AFTER:
				stats = repository.findAllByFirstCaughtAfter(timeframe.getTimestamp()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			case BETWEEN:
				RangedSearchTimeframe dualTimeframe = (RangedSearchTimeframe) timeframe;
				stats = repository.findAllByFirstCaughtBetween(dualTimeframe.getStart(), dualTimeframe.getEnd()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			default:
				return new StatsCollectionView();
		}
	}

	private StatsCollectionView lookupStatsWithRecordsCaughtAmount(PacketWatcherStatsRepository<StatsRecord, Serializable> repository, SearchAmount amount) {
		Stream<StatsRecord> stats;

		switch (amount.getOperator()) {
			case LESS_THAN:
				stats = repository.findAllByRecordsCaughtLessThan(amount.getAmount()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			case LESS_THAN_OR_EQUAL_TO:
				stats = repository.findAllByRecordsCaughtLessThanEqual(amount.getAmount()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			case GREATER_THAN:
				stats = repository.findAllByRecordsCaughtGreaterThan(amount.getAmount()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			case GREATER_THAN_OR_EQUAL_TO:
				stats = repository.findAlByRecordsCaughtGreaterThanEqual(amount.getAmount()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			case BETWEEN:
				RangedSearchAmount rangedAmount = (RangedSearchAmount) amount;
				stats = repository.findAllByRecordsCaughtBetween(rangedAmount.getStart(), rangedAmount.getEnd()).stream();
				return new StatsCollectionView(stats.map(StatsRecord::toStatsView).toList());
			default:
				return new StatsCollectionView();
		}
	}

	private void validate(Class<?> statsType) throws StatsAnnotationNotFoundException {
		if (!statsType.isAnnotationPresent(PacketWatcherStats.class)) {
			throw new StatsAnnotationNotFoundException();
		}

		if (!statsType.isAnnotationPresent(Entity.class)) {
			throw new EventAnnotationNotFoundException("PacketWatcherStats must be annotated with @Entity!");
		}
	}

}
