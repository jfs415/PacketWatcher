package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.core.FlaggedPacketRecord;
import com.jfs415.packetwatcher_api.model.core.FlaggedPacketRecordProjection;
import com.jfs415.packetwatcher_api.model.core.FlaggedPacketTimelineProjection;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlaggedPacketProjectionImpl implements FlaggedPacketProjectionRepository {

    private static final String FLAGGED_COUNTRY = "flaggedCountry";
    private static final String DESTINATION_HOST = "destinationHost";
    private static final String KEY = "key";
    private static final String TIMESTAMP = "timestamp";

    private final EntityManager entityManager;

    @Autowired
    public FlaggedPacketProjectionImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * SELECT *, COUNT(*) as count from flagged_packet_records f WHERE f.source_host IS NOT NULL GROUP BY f.source_host ORDER BY count DESC
     *
     * @return - The query result list.
     */
    @Override
    public List<FlaggedPacketRecordProjection> getTopByHostname(int limit) {
        QueryParameters queryParams = getNewFlaggedPacketProjectionQueryParams();

        Expression<Integer> count =
                queryParams.builder().count(queryParams.root()).as(Integer.class);
        queryParams
                .query()
                .select(queryParams
                        .builder()
                        .construct(
                                FlaggedPacketRecordProjection.class,
                                queryParams.root().get(DESTINATION_HOST),
                                count));

        queryParams
                .query()
                .where(
                        queryParams.root().get(DESTINATION_HOST).isNotNull(),
                        queryParams.builder().notEqual(queryParams.root().get(DESTINATION_HOST), ""),
                        queryParams.builder().notEqual(queryParams.root().get(DESTINATION_HOST), "LOCALHOST"));
        queryParams.query().groupBy(queryParams.root().get(DESTINATION_HOST));
        queryParams.query().orderBy(new OrderImpl(count, false));

        return entityManager
                .createQuery(queryParams.query())
                .setMaxResults(limit)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * SELECT f.flagged_country, COUNT(*) as count from flagged_packet_records f WHERE f.flagged_country IS NOT NULL GROUP BY f.flagged_country ORDER BY count DESC
     *
     * @return - The query result list.
     */
    @Override
    public List<FlaggedPacketRecordProjection> getTopByCountry(int limit) {
        QueryParameters queryParams = getNewFlaggedPacketProjectionQueryParams();

        Expression<Integer> count =
                queryParams.builder().count(queryParams.root()).as(Integer.class);
        queryParams
                .query()
                .select(queryParams
                        .builder()
                        .construct(
                                FlaggedPacketRecordProjection.class,
                                queryParams.root().get(FLAGGED_COUNTRY),
                                count));

        queryParams.query().where(queryParams.root().get(FLAGGED_COUNTRY).isNotNull());
        queryParams.query().groupBy(queryParams.root().get(FLAGGED_COUNTRY));
        queryParams.query().orderBy(new OrderImpl(count, false));

        return entityManager
                .createQuery(queryParams.query())
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * SELECT HOUR(f.timestamp), COUNT(*) as count from flagged_packet_records f GROUP BY HOUR(f.timestamp) ORDER BY count DESC
     *
     * @return - The query result list.
     */
    @Override
    public List<FlaggedPacketRecordProjection> getTopByTimeOfDay(int limit) {
        QueryParameters queryParams = getNewFlaggedPacketProjectionQueryParams();

        Expression<Integer> count =
                queryParams.builder().count(queryParams.root()).as(Integer.class);
        Expression<?> function = createFunctionTimestampGroupBy(queryParams, "HOUR");

        queryParams
                .query()
                .select(queryParams.builder().construct(FlaggedPacketRecordProjection.class, function, count));
        queryParams.query().groupBy(function);
        queryParams.query().orderBy(new OrderImpl(count, false));

        return entityManager
                .createQuery(queryParams.query())
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * SELECT DAYOFWEEK(f.timestamp), COUNT(*) as count from flagged_packet_records f GROUP BY DAYOFWEEK(f.timestamp) ORDER BY count DESC
     *
     * @return - The query result list.
     */
    @Override
    public List<FlaggedPacketRecordProjection> getTopByDayOfWeek(int limit) {
        QueryParameters queryParams = getNewFlaggedPacketProjectionQueryParams();

        Expression<Integer> count =
                queryParams.builder().count(queryParams.root()).as(Integer.class);
        Expression<?> function = createFunctionTimestampGroupBy(queryParams, "DAYOFWEEK");

        queryParams
                .query()
                .select(queryParams.builder().construct(FlaggedPacketRecordProjection.class, function, count));
        queryParams.query().groupBy(function);
        queryParams.query().orderBy(new OrderImpl(count, false));

        return entityManager
                .createQuery(queryParams.query())
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * SELECT DAYOFMONTH(f.timestamp), COUNT(*) as count from flagged_packet_records f GROUP BY DAYOFMONTH(f.timestamp) ORDER BY count DESC
     *
     * @return - The query result list.
     */
    @Override
    public List<FlaggedPacketRecordProjection> getTopByDayOfMonth(int limit) {
        QueryParameters queryParams = getNewFlaggedPacketProjectionQueryParams();

        Expression<Integer> count =
                queryParams.builder().count(queryParams.root()).as(Integer.class);
        Expression<?> function = createFunctionTimestampGroupBy(queryParams, "DAYOFMONTH");

        queryParams
                .query()
                .select(queryParams.builder().construct(FlaggedPacketRecordProjection.class, function, count));
        queryParams.query().groupBy(function);
        queryParams.query().orderBy(new OrderImpl(count, false));

        return entityManager
                .createQuery(queryParams.query())
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<FlaggedPacketTimelineProjection> getTimelineData(long startMillis, long endMillis) {
        Timestamp start = new Timestamp(startMillis);
        Timestamp end = new Timestamp(endMillis);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlaggedPacketTimelineProjection> query =
                builder.createQuery(FlaggedPacketTimelineProjection.class);
        Root<FlaggedPacketRecord> root = query.from(FlaggedPacketRecord.class);

        Expression<Integer> count = builder.count(root).as(Integer.class);
        Expression<?> function = builder.function(
                        "DATE", Timestamp.class, root.get(KEY).get(TIMESTAMP))
                .as(String.class);

        query.select(builder.construct(FlaggedPacketTimelineProjection.class, function, count));
        query.where(builder.between(root.get(KEY).get(TIMESTAMP), start, end));
        query.groupBy(function);
        query.orderBy(new OrderImpl(function));

        return entityManager.createQuery(query).getResultList();
    }

    private synchronized QueryParameters getNewFlaggedPacketProjectionQueryParams() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlaggedPacketRecordProjection> query = builder.createQuery(FlaggedPacketRecordProjection.class);
        Root<FlaggedPacketRecord> root = query.from(FlaggedPacketRecord.class);

        return new QueryParameters(builder, query, root);
    }

    private Expression<?> createFunctionTimestampGroupBy(QueryParameters queryParams, String functionName) {
        return queryParams
                .builder()
                .function(
                        functionName,
                        Timestamp.class,
                        queryParams.root().get(KEY).get(TIMESTAMP))
                .as(String.class);
    }

    private record QueryParameters(
            CriteriaBuilder builder,
            CriteriaQuery<FlaggedPacketRecordProjection> query,
            Root<FlaggedPacketRecord> root) {}
}
