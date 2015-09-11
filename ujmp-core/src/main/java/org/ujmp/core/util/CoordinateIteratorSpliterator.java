package org.ujmp.core.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.ujmp.core.Coordinates;

/**
 * This class provides an iterator and spliterator for matrix coordinates.
 * 
 * 
 * @author Jan Ortner
 *
 */
public class CoordinateIteratorSpliterator implements Iterator<long[]>, Spliterator<long[]>{


	/** Points to the last element returned (zero based)**/
	protected long[] cursor;// = new long[] { 0, -1 };
	/** Points to the last element being returned (zero based)**/
	protected long[] end;
	/** The size of the backing matrix **/
	protected long[] size;

	/**
	 * Constructor with given size
	 * 
	 * @param size - The dimensions of the matrix for this instance
	 */
	public CoordinateIteratorSpliterator(long... size){
		long[] s=size.clone();
		long[] st=new long[size.length];
		boolean zero=false;
		for(int i=0;i<s.length;i++){
			if(s[i]==0){
				Exception e=new Exception("Matrix has a zero size component, can't iterateover these coordinates");
				e.printStackTrace();
				zero=true;
			}
			s[i]--;
		}
		if(zero){ // setting pointer to end
			for(int i=0;i<s.length;i++){
				st[i]=s[i];
			}
		}
		this.size=size.clone();
		setStart(st);
		setEnd(s);
	}
	
	/**
	 * Constructor wit given size, start and end.
	 * Used for splitting...
	 * 
	 * @param size - The dimensions of the matrix for this instance
	 * @param start - The first element that should be returned
	 * @param end - The last element that should be returned
	 */
	public CoordinateIteratorSpliterator(long[] size, long[] start, long[] end){
		this.size=size.clone();
		setStart(start);
		setEnd(end);
	}
	
	
	// Iterator implementation
	@Override
	public boolean hasNext() {
		return !Coordinates.equals(cursor, end);
	}
	
	@Override
	/**
	 * Returns the next element.
	 * Coordinates are traversed last element (highest array index or last VarArg) first.
	 */
	public long[] next() {
		increment(cursor.length - 1);
		return cursor;
	}

	
	@Override
	public void remove() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void forEachRemaining(Consumer<? super long[]> action) {
		Iterator.super.forEachRemaining(action);
	}

	// Utility functions
	/**
	 * Increments the cursor at the given index.
	 * 
	 * @param dim - The index to increment
	 */
	protected void increment(int dim) {
		cursor[dim]++;
		if (cursor[dim] == size[dim]) {
			cursor[dim] = 0;
			increment(dim - 1);
		}
	}


	/**
	 * Increments the given cursor at the given index.
	 * 
	 * @param dim - The index to increment
	 * @param cursor - The cursor that will be incremented
	 */
	protected void increment(int dim, long[] cursor) {
		cursor[dim]++;
		if (cursor[dim] == size[dim]) {
			cursor[dim] = 0;
			increment(dim - 1,cursor);
		}
	}
	
	/**
	 * Decrements the current cursor at the given dimension.
	 * 
	 * @param dim - The index to decrement
	 */
	protected void decrement(int dim){
		cursor[dim]--;
		if (cursor[dim] < 0) {
			if(dim==0){ // zero position
				cursor[size.length-1]=-1;
				for(int i=0;i<size.length-1;i++){
					cursor[i]=0;
				}
				return;
			}
			cursor[dim] = size[dim]-1;
			decrement(dim - 1);
		}
	}
	
	/**
	 * Returns the number of coordinates left in this instance.
	 * 
	 * @return the number of coordinates left in this instance.
	 */
	public long getSize(){
		long s=1;
		//setting start to actual start field
		long[] start=cursor.clone();
		increment(start.length-1,start);
		// calculating full size
		for(int i=size.length-1;i>=0;i--){ 
			s*=size[i];
		}
		//subtracting fields to start
		for(int i=size.length-1;i>=0;i--){
			//start
			long miss=start[i];
			//end
			miss+=((size[i]-1)-end[i]);
			//size
			long mul=1;
			for(int j=i+1;j<size.length;j++){
				mul*=size[j];
			}
			s-=miss*mul;
		}
		return s;
	}
	
	/**
	 * Sets the position of the first element to be returned.
	 * 
	 * @param start - the coordinates to start at
	 */
	protected void setStart(long... start){
		cursor=start.clone();
		decrement(size.length-1);
	}
	
	/**
	 * Sets the last element to be returned by this iterator.
	 * 
	 * @param end - the coordinates of the last element
	 */
	protected void setEnd(long... end){
		this.end=end.clone();
	}
	
	/**
	 * This method returns a new spliterator with the given start and end.
	 * It is called by the split method to create a spliterator for the second
	 * half of the content.
	 * Method should be overridden by extending classes to return correct type
	 * 
	 * @param start the coordinates of the first element
	 * @param end the coordinates of the last element
	 * @return new Spliterator
	 */
	protected CoordinateIteratorSpliterator getSpliterator(long[] start, long[] end){
		return new CoordinateIteratorSpliterator(size,start,end);
	}
	
	/**
	 * Returns the current position.
	 * The current position is the position of the next element that will be returned.
	 * 
	 * @return
	 */
	public long[] getPosition(){
		return cursor.clone();
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
		for(int i=size.length-1;i>=0;i--){
			newEnd[i]=(cursor[i]+end[i])/2;
			if(i<size.length-1 && (cursor[i]+end[i])%2==1){ // adding half field
				newEnd[i+1]+=size[i+1]/2;
				if(newEnd[i+1]>=size[i+1]){
					newEnd[i+1]-=size[i+1];
					newEnd[i]++;
				}
			}
		}
		/*System.out.print("Splitting ");
		for(long c:cursor){
			System.out.print(c+" ");
		}
		System.out.print(" ");
		for (long c : end) {
			System.out.print(c + " ");
		}
		System.out.print(" at ");
		for (long c : newEnd) {
			System.out.print(c + " ");
		}
		System.out.println();*/
		CoordinateIteratorSpliterator split=getSpliterator(newEnd, end);
		//adjust start
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
