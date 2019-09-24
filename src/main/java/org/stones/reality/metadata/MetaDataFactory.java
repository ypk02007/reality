package org.stones.reality.metadata;

public class MetaDataFactory {

	private static MetaDataFactory instance = new MetaDataFactory();

	private MetaDataFactory() {
		
	}
	
	public static MetaDataFactory getInstance() {
		if(instance==null) {
			instance = new MetaDataFactory();
		}
		return instance;
	}
	
	public IMetaData getExecution() {
		return new OracleMetaData();
	}
}
