/*
 * Copyright (c) 2004-2012, The Dojo Foundation All Rights Reserved.
 * Available via Academic Free License >= 2.1 OR the modified BSD license.
 * see: http://dojotoolkit.org/license for details
 */

package com.ibm.jaggr.service.impl.cache;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.regex.Pattern;

import com.ibm.jaggr.service.cache.ICache;
import com.ibm.jaggr.service.layer.ILayerCache;
import com.ibm.jaggr.service.module.IModuleCache;

public class CacheImpl implements ICache {
	private static final long serialVersionUID = 8499083762317350377L;
	/// The caches
    private ILayerCache _layerCache;
	private IModuleCache _moduleCache;
	
	private Object _control;	// used by cache manager to control cache life span
	
	private final long _created;
	
	/**
	 * @param initialSize The initial size of the cache
	 */
	public CacheImpl(ILayerCache layerCache, IModuleCache moduleCache, Object control) {
		_layerCache = layerCache; 
		_moduleCache = moduleCache;
		_control = control;
		
		_created = new Date().getTime();
	}
	
	/**
	 * @return The ILayer cache
	 */
	@Override
	public ILayerCache getLayers() {
		return _layerCache;
	}
	
	/**
	 * @return The IModule cache
	 */
	@Override
	public IModuleCache getModules() {
		return _moduleCache;
	}
	
	@Override
	public long getCreated() {
		return _created;
	}
	
	public Object getControlObj() {
		return _control;
	}
	
	/**
	 * Returns a clone of this object.  Note that although cloning is thread-safe,
	 * it is NOT atomic.  This means that the object being cloned can be changing
	 * while we are in the process of cloning it (e.g. items being added/removed
	 * from the Maps) so there is no guaranty that the cloned object is an exact
	 * copy of the object being cloned at any one point in time.
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public synchronized Object clone() throws CloneNotSupportedException {
		
		CacheImpl clone = (CacheImpl)super.clone();
		clone._layerCache = (ILayerCache)_layerCache.clone();
		clone._moduleCache =(IModuleCache)_moduleCache.clone();
		return clone;
	}

    /**
     * Help out the GC by clearing out the cache maps.  
     */
    public synchronized void clear() {
		_layerCache.clear();
		_moduleCache.clear();
    }
    
    /* (non-Javadoc)
     * @see com.ibm.jaggr.service.cache.ICache#dump(java.io.Writer, java.util.regex.Pattern)
     */
    @Override
    public void dump(Writer writer, Pattern filter) throws IOException {
    	_layerCache.dump(writer, filter);
    	_moduleCache.dump(writer, filter);
    }
    
}
