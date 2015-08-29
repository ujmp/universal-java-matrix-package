package org.ujmp.core.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.ujmp.core.Coordinates;

public class CoordinateIteratorSpliteratorTest {

	@Test
	public void getSizeTest(){
		//2D Test
		CoordinateIteratorSpliterator dut=new CoordinateIteratorSpliterator(5,4);
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
		
		//3D Test
		dut=new CoordinateIteratorSpliterator(6,5,4);
		size=dut.getSize();
		assertTrue("Wrong size calculated: 120/"+size, size==120);
		// 1st dimension
		// start cursor
		dut.setStart(0,0,1);
		size=dut.getSize();
		assertTrue("Wrong size calculated with start: 119/"+size, size==119);
		// end cursor
		dut.setEnd(5,4,2);
		size=dut.getSize();
		assertTrue("Wrong size calculated with end: 118/"+size, size==118);
		// 2nd dimension
		// start cursor
		dut.setStart(0,1,1);
		size=dut.getSize();
		assertTrue("Wrong size calculated with start: 114/"+size, size==114);
		// end cursor
		dut.setEnd(5,3,2);
		size=dut.getSize();
		assertTrue("Wrong size calculated with end: 110/"+size, size==110);
		// 3rd dimension
		// start cursor
		dut.setStart(1,1,1);
		size=dut.getSize();
		assertTrue("Wrong size calculated with start: 115/"+size, size==90);
		// end cursor
		dut.setEnd(4,3,2);
		size=dut.getSize();
		assertTrue("Wrong size calculated with end: 110/"+size, size==70);
		
	}
	

	@Test
	public void nextTest(){

		CoordinateIteratorSpliterator dut=new CoordinateIteratorSpliterator(6,5,4);
		ArrayList<long[]> coo=new ArrayList<long[]>();
		for(int z=0;z<6;z++){
			for(int y=0;y<5;y++){
				for(int x=0;x<4;x++){
					coo.add(new long[]{z,y,x});
				}
			}
		}
		for(int i=0;i<120;i++){
			long[] tmp=dut.next();
			assertTrue("Size is too small",coo.size()>0);
			assertTrue("Sequence of iterator not correct",Coordinates.equals(coo.get(0),tmp));
			coo.remove(0);
		}
		assertTrue("Elements left",coo.size()==0);
	}
	
	@Test
	public void trySplitTest(){
		//2D split test
		CoordinateIteratorSpliterator dut=new CoordinateIteratorSpliterator(50,40);
		CoordinateIteratorSpliterator dut2=null;
		ArrayList<CoordinateIteratorSpliterator> splits=new ArrayList<CoordinateIteratorSpliterator>();
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
				dut2 = (CoordinateIteratorSpliterator) dut.trySplit();
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

		//3D split test
		dut = new CoordinateIteratorSpliterator(60, 50, 40);
		dut2 = null;
		splits = new ArrayList<CoordinateIteratorSpliterator>();
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
				dut2 = (CoordinateIteratorSpliterator) dut.trySplit();
				if (dut2 == null) {
					continue;
				}
				splits.add(dut2);
				long s1 = dut.getSize();
				long s2 = dut2.getSize();
				assertTrue("Size of split smaller than 1", s1 > 0);
				assertTrue("Size of split smaller than 1", s2 > 0);
				assertTrue("Size of splits doesn't match original", size == s1 + s2);
				assertTrue("Splitted not in half", Math.abs(s1 - s2) < 50*40 + 40 + 1 + 1); // depends on number of coordinates and dimension
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
