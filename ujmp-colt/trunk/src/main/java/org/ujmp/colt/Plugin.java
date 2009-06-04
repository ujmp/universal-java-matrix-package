package org.ujmp.colt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ujmp.core.util.AbstractPlugin;

public class Plugin extends AbstractPlugin {

	@Override
	public Collection<String> getNeededClasses() {
		List<String> list = new ArrayList<String>();
		list.add("cern.colt.matrix.DoubleMatrix2D");
		list.add("EDU.oswjego.cs.dl.util.concurrent.Callable");
		return list;
	}

	@Override
	public String getDescription() {
		return "Interface to Colt matrices.";
	}

	@Override
	public void setDescription(String description) {
	}

	@Override
	public Collection<Object> getDependencies() {
		List<Object> list = new ArrayList<Object>();
		list.add("ujmp-core");
		list.add("colt.jar");
		list.add("concurrent.jar");
		return list;
	}

}
