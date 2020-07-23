package com.ledger.base;

import lombok.Getter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class TestData {

	private static volatile ThreadLocal<TestData> instances = new ThreadLocal<>();
	Map<Object, Object> crossStepVariableMap = new ConcurrentHashMap<>();

	public static com.ledger.base.TestData getInstance() {
		com.ledger.base.TestData localInstance = instances.get();
		if (localInstance == null) {
			synchronized (com.ledger.base.TestData.class) {
				localInstance = instances.get();
				if (localInstance == null) {
					localInstance = new com.ledger.base.TestData();
					instances.set(localInstance);
				}
			}
		}
		return localInstance;
	}

	public static void removeInstance() {
		//System.out.format("Remove TESTDATA = %2d at Thread = %2d \n", instances.get().hashCode(), Thread.currentThread().getId());
		instances.remove();
	}

	public Object getCrossStepVariableFromMap(Object key) {
		return crossStepVariableMap.get(key);
	}
	public void setCrossStepVariableFromMap(Object key, Object value) {
		crossStepVariableMap.put(key, value);
	}
}
