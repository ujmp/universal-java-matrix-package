package org.ujmp.lucene;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.collections.MapToListWrapper;

public class LuceneList<V> extends MapToListWrapper<V> {

	public LuceneList() throws IOException {
		super(new LuceneMap<Integer, V>());
	}
	
	public LuceneList(File file) throws IOException {
		super(new LuceneMap<Integer, V>(file));
	}
	
	public LuceneList(String file) throws IOException {
		super(new LuceneMap<Integer, V>(file));
	}

}
