/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.ehcache;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.ujmp.core.collections.map.AbstractMap;
import org.ujmp.core.interfaces.Erasable;

public class EhcacheMap<K, V> extends AbstractMap<K, V> implements Erasable, Flushable, Closeable {
	private static final long serialVersionUID = -2405059234958626645L;

	private int maxElementsInMemory = 1000;

	private final int maxElementsOnDisk = Integer.MAX_VALUE - 1;

	private boolean overflowToDisk = true;

	private final boolean eternal = true;

	private final boolean diskPersistent = true;

	private transient File diskStorePath = null;

	private transient String name = null;

	private transient final RegisteredEventListeners registeredEventListeners = null;

	private final int timeToLiveSeconds = Integer.MAX_VALUE - 1;

	private final int timeToIdleSeconds = 120;

	private final int diskSpoolBufferSizeMB = 16;

	private final int diskExpiryThreadIntervalSeconds = 300;

	private final MemoryStoreEvictionPolicy memoryStoreEvictionPolicy = MemoryStoreEvictionPolicy.LFU;

	private transient CacheManager manager = null;

	private transient Cache cache = null;

	private static long runningId = System.currentTimeMillis();

	private final BootstrapCacheLoader bootstrapCacheLoader = null;

	public EhcacheMap() throws IOException {
		this(null);
	}

	public EhcacheMap(String name) throws IOException {
		this(name, null, true);
	}

	public EhcacheMap(boolean overFlowToDisk) throws IOException {
		this(null, null, overFlowToDisk);
	}

	public EhcacheMap(String name, boolean overFlowToDisk) throws IOException {
		this(name, null, overFlowToDisk);
	}

	public EhcacheMap(String name, File path, boolean overFlowToDisk) throws IOException {
		runningId++;
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		this.diskStorePath = path;
		this.overflowToDisk = overFlowToDisk;
		this.name = name;
	}

	public boolean isOverflowToDisk() {
		return overflowToDisk;
	}

	public void setOverflowToDisk(boolean overflowToDisk) {
		this.overflowToDisk = overflowToDisk;
	}

	public String getName() {
		if (name == null) {
			name = "ehcache" + runningId;
		}
		return name;
	}

	public int getMaxElementsInMemory() {
		return maxElementsInMemory;
	}

	public void setMaxElementsInMemory(int maxElementsInMemory) {
		this.maxElementsInMemory = maxElementsInMemory;
	}

	public File getPath() {
		if (diskStorePath == null) {
			try {
				diskStorePath = File.createTempFile(getName(), ".tmp");
				diskStorePath.delete();
				diskStorePath.mkdir();
				new File(diskStorePath + System.getProperty("file.separator") + getName() + ".data").deleteOnExit();
				new File(diskStorePath + System.getProperty("file.separator") + getName() + ".index").deleteOnExit();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return diskStorePath;
	}

	private CacheManager getCacheManager() {
		if (manager == null) {
			Configuration config = new Configuration();
			CacheConfiguration cacheconfig = new CacheConfiguration(getName(), maxElementsInMemory);
			cacheconfig.setDiskExpiryThreadIntervalSeconds(diskExpiryThreadIntervalSeconds);
			cacheconfig.setDiskPersistent(diskPersistent);
			cacheconfig.setEternal(eternal);
			cacheconfig.setMaxElementsOnDisk(maxElementsOnDisk);
			cacheconfig.setMemoryStoreEvictionPolicyFromObject(memoryStoreEvictionPolicy);
			cacheconfig.setOverflowToDisk(overflowToDisk);
			cacheconfig.setTimeToIdleSeconds(timeToIdleSeconds);
			cacheconfig.setTimeToLiveSeconds(timeToLiveSeconds);

			DiskStoreConfiguration diskStoreConfigurationParameter = new DiskStoreConfiguration();
			diskStoreConfigurationParameter.setPath(getPath().getAbsolutePath());
			config.addDiskStore(diskStoreConfigurationParameter);
			config.setDefaultCacheConfiguration(cacheconfig);
			manager = new CacheManager(config);
		}
		return manager;
	}

	private Cache getCache() {
		if (cache == null) {
			Cache c = new Cache(getName(), maxElementsInMemory, memoryStoreEvictionPolicy, overflowToDisk, getPath()
					.getAbsolutePath(), eternal, timeToLiveSeconds, timeToIdleSeconds, diskPersistent,
					diskExpiryThreadIntervalSeconds, registeredEventListeners, bootstrapCacheLoader, maxElementsOnDisk,
					diskSpoolBufferSizeMB);
			getCacheManager().addCache(c);
			cache = getCacheManager().getCache(getName());
		}
		return cache;
	}

	public void clear() {
		getCache().removeAll();
		getCache().flush();
	}

	public boolean containsKey(Object key) {
		return getCache().isKeyInCache(key);
	}

	public boolean containsValue(Object value) {
		return getCache().isValueInCache(value);
	}

	@SuppressWarnings("unchecked")
	public synchronized V get(Object key) {
		Element e = getCache().get(key);
		if (e != null) {
			return (V) e.getValue();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Set<K> keySet() {
		return new HashSet<K>(getCache().getKeys());
	}

	public synchronized V put(K key, V value) {
		Element e = new Element(key, value);
		getCache().put(e);
		return null;
	}

	public synchronized V remove(Object key) {
		getCache().remove(key);
		return null;
	}

	public int size() {
		return getCache().getSize();
	}

	public void finalize() {
		try {
			erase();
		} catch (IOException e) {
		}
	}

	public void erase() throws IOException {
		close();
		getCacheManager().shutdown();
	}

	public void flush() throws IOException {
		getCache().flush();
	}

	public void close() throws IOException {
		getCacheManager().removeCache(getCache().getName());
	}
}
