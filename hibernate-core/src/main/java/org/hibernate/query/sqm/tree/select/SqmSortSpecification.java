/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.sqm.tree.select;

import org.hibernate.NullPrecedence;
import org.hibernate.SortOrder;
import org.hibernate.query.criteria.JpaExpression;
import org.hibernate.query.criteria.JpaOrder;
import org.hibernate.query.sqm.tree.expression.SqmExpression;

/**
 * @author Steve Ebersole
 */
public class SqmSortSpecification implements JpaOrder {
	private final SqmExpression sortExpression;
	private final String collation;
	private final SortOrder sortOrder;

	private NullPrecedence nullPrecedence;

	public SqmSortSpecification(
			SqmExpression sortExpression,
			String collation,
			SortOrder sortOrder,
			NullPrecedence nullPrecedence) {
		this.sortExpression = sortExpression;
		this.collation = collation;
		this.sortOrder = sortOrder;
		this.nullPrecedence = nullPrecedence;
	}

	public SqmSortSpecification(SqmExpression sortExpression) {
		this( sortExpression, null, SortOrder.ASCENDING, null );
	}

	public SqmSortSpecification(SqmExpression sortExpression, SortOrder sortOrder) {
		this( sortExpression, null, sortOrder, null );
	}

	public SqmSortSpecification(SqmExpression sortExpression, SortOrder sortOrder, NullPrecedence nullPrecedence) {
		this( sortExpression, null, sortOrder, nullPrecedence );
	}

	public SqmExpression getSortExpression() {
		return sortExpression;
	}

	public String getCollation() {
		return collation;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// JPA

	@Override
	public JpaOrder nullPrecedence(NullPrecedence nullPrecedence) {
		this.nullPrecedence = nullPrecedence;
		return this;
	}

	@Override
	public NullPrecedence getNullPrecedence() {
		return nullPrecedence;
	}

	@Override
	public JpaOrder reverse() {
		SortOrder newSortOrder = this.sortOrder == null ? SortOrder.DESCENDING : sortOrder.reverse();
		return new SqmSortSpecification( sortExpression, collation, newSortOrder, nullPrecedence );
	}

	@Override
	public JpaExpression<?> getExpression() {
		return getSortExpression();
	}

	@Override
	public boolean isAscending() {
		return sortOrder == SortOrder.ASCENDING;
	}
}
