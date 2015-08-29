package org.ujmp.core.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class CoordinateIteratorSpliterator2DTest {

	@Test
	public void getSizeTest(){
		//2D Test
		CoordinateIteratorSpliterator dut=new CoordinateIteratorSpliterator2D(5,4);
		long size=dut.getSize();
		assertTrue("Wrong size calculated: 20/"+size, size==20);
		// 1st dim
		// start cursor
		dut.setStart(0,1);
		size=dut.getSize();
		assertTrue("Wrong size calculated with start: 19/"+size, size==19);
		// end cursor
		dut.setEnd(4,2);
		size=dut.getSize();
		assertTrue("Wrong size calculated with end: 18/"+size, size==18);
		// 2nd dim
		// start cursor
		dut.setStart(1,1);
		size=dut.getSize();
		assertTrue("Wrong size calculated with start: 14/"+size, size==14);
		// end cursor
		dut.setEnd(3,2);
		size=dut.getSize();
		assertTrue("Wrong size calculated with end: 9/"+size, size==10);
		
	}
	
	@Test
	public void hasNext(){

		CoordinateIteratorSpliterator dut=new CoordinateIteratorSpliterator2D(5,4);
		for(int i=0;i<20;i++){
			assertTrue("hasNext returns false before end",dut.hasNext());
			dut.next();
		}
		assertFalse("hasNext returns true at end",dut.hasNext());
	}
	
	@Test
	public void trySplitTest(){
		//2D split test
		CoordinateIteratorSpliterator2D dut=new CoordinateIteratorSpliterator2D(50,40);
		CoordinateIteratorSpliterator2D dut2=null;
		ArrayList<CoordinateIteratorSpliterator2D> splits=new ArrayList<CoordinateIteratorSpliterator2D>();
		splits.add(dut);
		boolean cont=true;
		long size=0;
		long orgSize=dut.getSize();
		int sizeBuff=0;
		int i=0;
		while(cont){
			cont=false;
			sizeBuff=splits.size();
			i=0;
			while (i < sizeBuff) {
				dut = splits.get(i++);
				size = dut.getSize();
				dut2 = (CoordinateIteratorSpliterator2D) dut.trySplit();
				if (dut2 == null) {
					continue;
				}
				splits.add(dut2);
				long s1 = dut.getSize();
				long s2 = dut2.getSize();
				assertTrue("Size of split smaller than 1", s1 > 0);
				assertTrue("Size of split smaller than 1", s2 > 0);
				assertTrue("Size of splits doesn't match original", size == s1 + s2);
				assertTrue("Splitted not in half", Math.abs(s1 - s2) < 40 + 1 + 1); // depends on number of coordinates and dimension
				cont=true;
			}
		}
		size=0;
		for(CoordinateIteratorSpliterator2D it:splits){
			size+=it.getSize();
		}
		assertTrue("Size of all splits doesn't match original size", size == orgSize);
		

		//2D split test
		dut = new CoordinateIteratorSpliterator2D(10, 10);
		dut2 = null;
		splits = new ArrayList<CoordinateIteratorSpliterator2D>();
		splits.add(dut);
		cont = true;
		size = 0;
		orgSize = dut.getSize();
		sizeBuff = 0;
		i = 0;
		while(cont){
			cont=false;
			sizeBuff=splits.size();
			i=0;
			while (i < sizeBuff) {
				dut = splits.get(i++);
				size = dut.getSize();
				dut2 = (CoordinateIteratorSpliterator2D) dut.trySplit();
				if (dut2 == null) {
					continue;
				}
				splits.add(dut2);
				long s1 = dut.getSize();
				long s2 = dut2.getSize();
				assertTrue("Size of split smaller than 1", s1 > 0);
				assertTrue("Size of split smaller than 1", s2 > 0);
				assertTrue("Size of splits doesn't match original", size == s1 + s2);
				assertTrue("Splitted not in half", Math.abs(s1 - s2) < 40 + 1 + 1); // depends on number of coordinates and dimension
				cont=true;
			}
		}
		size=0;
		for(CoordinateIteratorSpliterator it:splits){
			size+=it.getSize();
		}
		assertTrue("Size of all splits doesn't match original size", size == orgSize);
	}
}
