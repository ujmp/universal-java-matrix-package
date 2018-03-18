package org.ujmp.elasticsearch;

import java.net.UnknownHostException;
import java.util.TimerTask;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.UJMPTimer;

public class ElasticsearchIndexStatistics extends DefaultMapMatrix<String, Object> {
	private static final long serialVersionUID = -8585761939481699579L;

	private final Client client;

	private final MapMatrix<String, Object> matrix;
	private final UJMPTimer timer;
	private final String indexName;
	private int pollingInterval = 10000;

	public ElasticsearchIndexStatistics(String hostname, String indexName) throws UnknownHostException {
		this(ElasticsearchUtil.createTransportClient(hostname, ElasticsearchIndex.DEFAULTPORT), indexName);
	}

	public ElasticsearchIndexStatistics(String hostname, int port, String indexName) throws UnknownHostException {
		this(ElasticsearchUtil.createTransportClient(hostname, port), indexName);
	}

	public ElasticsearchIndexStatistics(Client client, String indexName) {
		this.client = client;
		this.indexName = indexName;
		matrix = this;
		timer = UJMPTimer.newInstance(this.getClass().getSimpleName());
		timer.schedule(task, 0, pollingInterval);
	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			ClusterHealthResponse healths = client.admin().cluster().prepareHealth(indexName).get();
			ClusterIndexHealth health = healths.getIndices().get(indexName);
			IndicesStatsResponse stats = client.admin().indices().prepareStats(indexName).get();

			matrix.put("Label", indexName);
			matrix.put("ActivePrimaryShards", health.getActivePrimaryShards());
			matrix.put("ActiveShards", health.getActiveShards());
			matrix.put("InitializingShards", health.getInitializingShards());
			matrix.put("NumberOfReplicas", health.getNumberOfReplicas());
			matrix.put("NumberOfShards", health.getNumberOfShards());
			matrix.put("RelocatingShards", health.getRelocatingShards());
			matrix.put("HealthStatus", health.getStatus());
			matrix.put("UnassignedShards", health.getUnassignedShards());
			matrix.put("FailedShards", stats.getFailedShards());
			matrix.put("Primaries", stats.getPrimaries());
			matrix.put("SuccessfulShards", stats.getSuccessfulShards());
			matrix.put("TotalShards", stats.getTotalShards());
			matrix.put("DocumentAverageSize", stats.getTotal().getDocs().getAverageSizeInBytes());
			matrix.put("DocumentCount", stats.getTotal().getDocs().getCount());
			matrix.put("DocumentDeletedCount", stats.getTotal().getDocs().getDeleted());
			matrix.put("DocumentTotalSize", stats.getTotal().getDocs().getTotalSizeInBytes());
			matrix.put("TotalSize", stats.getTotal().getStore().getSizeInBytes());
		}
	};

}
