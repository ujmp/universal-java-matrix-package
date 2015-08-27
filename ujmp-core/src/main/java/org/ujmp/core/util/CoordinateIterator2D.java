/*
 * Copyright (C) 2008-2015 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.core.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.CoordinateIterator.It;

public class CoordinateIterator2D implements Iterable<long[]> {

	private static final int ROW = Matrix.ROW;

	private static final int COLUMN = Matrix.COLUMN;

	private long[] size = null;

	public CoordinateIterator2D(long... size) {
		this.size = size;
		if (size.length > 2) {
			new Exception(
					"warning: using a 2d iterator on a matrix or calculation that has more than 2 dimensions, results may be wrong")
					.printStackTrace();
		}
	}


	@Override
	public Spliterator<long[]> spliterator() {
		/*long s=size[0];
		for(int i=1;i<size.length;i++){
			s*=size[i];
		}
		return Spliterators.spliterator(new It(), s, Spliterator.IMMUTABLE | Spliterator.DISTINCT | Spliterator.NONNULL);*/
		return new It();
	}
	
	
	public Iterator<long[]> iterator() {
		return new It();
	}
	
	private class It implements Iterator<long[]>, Spliterator<long[]> {
		/** Points to the last element returned**/
		long[] cursor = new long[] { 0, -1 };
		long[] end=new long[]{0,0};

		long columnCount = size[COLUMN];

		long rowCount = size[ROW];

		long rowEnd = rowCount - 1;

		long columnEnd = columnCount - 1;

		boolean isNotEmpty = columnCount != 0 && rowCount != 0;

		public It(){
			setEnd(rowCount-1,columnCount-1);
		}
		
		public It(long[] start, long[] end){
			setStart(start);
			setEnd(end);
		}
		
		
		// Iterator implementation
		public boolean hasNext() {
			return (cursor[ROW] != rowEnd || cursor[COLUMN] != columnEnd) && isNotEmpty;
		}
		
		/**
		 * Returns the next element.
		 * Steps through coulumns first.
		 * 
		 */
		public long[] next() {
			return ++cursor[COLUMN] == columnCount && (cursor[COLUMN] = 0) == ++cursor[ROW] ? cursor
					: cursor;
		}

		public void remove() {
			throw new RuntimeException("not implemented");
		}

		@Override
		public void forEachRemaining(Consumer<? super long[]> action) {
			Iterator.super.forEachRemaining(action);
		}

		// Utility functions
		public long getSize(){
			long ret=columnCount*(rowEnd-cursor[ROW]);
			ret+=columnEnd-cursor[COLUMN];
			return ret;
		}
		
		protected void setStart(long... start){
			cursor=start.clone();
			--cursor[COLUMN];
		}
		
		/**
		 * Sets the last element.
		 * 
		 * @param end
		 */
		protected void setEnd(long... end){
			this.end=end.clone();
			rowEnd=end[ROW];
			columnEnd=end[COLUMN];
		}
		
		/**
		 * Method should be overridden by extending classes to return correct type
		 * 
		 * @param start
		 * @param end
		 * @return
		 */
		protected It getSpliterator(long[] start, long[] end){
			return new It(start,end);
		}
		
		/**
		 * Returns the current position.
		 * The current position is the position of the next element that will be returned.
		 * 
		 * @return
		 */
		public long[] getPosition(){
			return cursor;
		}
		
		// Spliterator implementation

		@Override
		public boolean tryAdvance(Consumer<? super long[]> action) {
            if (action == null) throw new NullPointerException();
            if (hasNext()) {
                action.accept(next());
                return true;
            }
            return false;
		}

		@Override
		public Spliterator<long[]> trySplit() {
			if(getSize()<10){ //no splitting below 10 elements
				//System.out.println("Size too small for splitting");
				return null;
			}
			long[] newEnd=new long[cursor.length];
			//standard: mean value
			newEnd[COLUMN]=(cursor[COLUMN]+end[COLUMN])/2;
			//if diff of next coo is 1 or less
			newEnd[ROW]=(cursor[ROW]+end[ROW])/2;
			if(newEnd[ROW]==cursor[ROW]&&end[ROW]!=cursor[ROW]){
				newEnd[COLUMN]=(cursor[COLUMN]+end[COLUMN]+size[COLUMN])/2;
				if(newEnd[COLUMN]>=size[COLUMN]){
					newEnd[COLUMN]-=size[COLUMN];
					++newEnd[ROW];
				}
			}
			//System.out.println("Splitting "+cursor[0]+"/"+cursor[1]+" "+end[0]+"/"+end[1]+" at "+newEnd[0]+"/"+newEnd[1]);
			It split=getSpliterator(newEnd, end);
			split.next();
			setEnd(newEnd);
			return split;
		}

		@Override
		public long estimateSize() {
			return getSize();
		}

		@Override
		public int characteristics() {
			return Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE | Spliterator.DISTINCT | Spliterator.NONNULL;
		}
		
	}
	

}
