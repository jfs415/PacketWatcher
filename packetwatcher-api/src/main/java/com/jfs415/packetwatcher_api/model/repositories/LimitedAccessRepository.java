package com.jfs415.packetwatcher_api.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * <h1>LimitedAccessRepository</h1>
 * This repository extends the base Spring Repository and only defines methods
 * that allow the searching of records. This is done to prevent unwanted updating
 * or deleting of records from the frontend. At some point in the future there may
 * be an expansion onto this interface to allow admin edits or deletions. That is 
 * not likely until thorough implementation and testing of spring security is completed.
 * 
 * @param <T>
 *          The Entity record Object type.
 *          
 * @param <ID>
 *          The primary key Object type of the Entity record.
 */

@NoRepositoryBean
interface LimitedAccessRepository<T, ID> extends Repository<T, ID> {

	Optional<T> findById(ID id);

	boolean existsById(ID id);

	Iterable<T> findAllById(Iterable<ID> key);

	long count();

}
