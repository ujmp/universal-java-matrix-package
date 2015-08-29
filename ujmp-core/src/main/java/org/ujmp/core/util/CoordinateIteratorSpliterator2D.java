package org.ujmp.core.util;

import java.util.Spliterator;

import org.ujmp.core.Matrix;

/**
 * Specialized CoordinateIteratorSpliterator for 2 dimensional Coordinates.
 * Increased performance compared to general CoordinateIteratorSpliterator.
 * 
 * 
 * @author Jan Ortner
 *
 */
public class CoordinateIteratorSpliterator2D  extends CoordinateIteratorSpliterator {

	final int COLUMN=Matrix.COLUMN;
	final int ROW=Matrix.ROW;
	
	long columnCount = size[COLUMN];
	long rowCount = size[ROW];
	long rowEnd;
	long columnEnd;
	
	
	
	public CoordinateIteratorSpliterator2D(long... size) {
		super(size);
	}

	public CoordinateIteratorSpliterator2D(long[] size, long[] start, long[] end) {
		super(size, start, end);
	}

	// Iterator implementation
	@Override
	public boolean hasNext() {
		return (cursor[ROW] != rowEnd || cursor[COLUMN] != columnEnd);
	}
	
	@Override
	/**{@inheritDoc}**/
	public long[] next() {
		return ++cursor[COLUMN] == columnCount && (cursor[COLUMN] = 0) == ++cursor[ROW] ? cursor
				: cursor;
	}

	// Utility functions
	@Override
	/**{@inheritDoc}**/
	public long getSize(){
		//long ret=columnCount*(rowEnd-cursor[ROW]);
		//ret+=columnEnd-cursor[COLUMN];
		long ret=columnCount*(rowEnd-cursor[ROW]+1)
				-(cursor[COLUMN]+columnCount-columnEnd);
		return ret;
	}
	
	@Override
	/**{@inheritDoc}**/
	protected void setStart(long... start){
		super.setStart(start);
	}
	
	@Override
	/**{@inheritDoc}**/
	protected void setEnd(long... end){
		super.setEnd(end);
		rowEnd=end[ROW];
		columnEnd=end[COLUMN];
	}
	
	@Override
	/**{@inheritDoc}**/
	protected CoordinateIteratorSpliterator2D getSpliterator(long[] start, long[] end){
		return new CoordinateIteratorSpliterator2D(size,start,end);
	}
	
	
	// Spliterator implementation

	@Override
	public Spliterator<long[]> trySplit() {
		if(getSize()<10){ //no splitting below 10 elements
			//System.out.println("Size too small for splitting");
			return null;
		}
		long[] newEnd=new long[cursor.length];
		//standard: mean value

		newEnd[COLUMN]=(cursor[COLUMN]+end[COLUMN])/2;
		newEnd[ROW]=(cursor[ROW]+end[ROW])/2;
		if((cursor[ROW]+end[ROW])%2==1){ // adding half field
			newEnd[COLUMN]+=size[COLUMN]/2;
			if(newEnd[COLUMN]>=size[COLUMN]){
				newEnd[COLUMN]-=size[COLUMN];
				newEnd[ROW]++;
			}
		}
		
		
		/*newEnd[COLUMN]=(cursor[COLUMN]+end[COLUMN])/2;
		//if diff of next coo is 1 or less
		newEnd[ROW]=(cursor[ROW]+end[ROW])/2;
		if(newEnd[ROW]==cursor[ROW]&&end[ROW]!=cursor[ROW]){
			newEnd[COLUMN]=(cursor[COLUMN]+end[COLUMN]+size[COLUMN])/2;
			if(newEnd[COLUMN]>=size[COLUMN]){
				newEnd[COLUMN]-=size[COLUMN];
				++newEnd[ROW];
			}
		}*/
		//System.out.println("Splitting "+cursor[0]+"/"+cursor[1]+" "+end[0]+"/"+end[1]+" at "+newEnd[0]+"/"+newEnd[1]);
		CoordinateIteratorSpliterator2D split=getSpliterator(newEnd, end);
		split.next();
		setEnd(newEnd);
		return split;
	}

}
