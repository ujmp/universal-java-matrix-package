package org.ujmp.elasticsearch;

import java.net.UnknownHostException;
import java.util.TimerTask;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.UJMPTimer;

public class ElasticsearchClusterHealth extends DefaultMapMatrix<String, Object> {
	private static final long serialVersionUID = 966652253084515634L;

	private final Client client;

	private final MapMatrix<String, Object> matrix;
	private final UJMPTimer timer;

	public ElasticsearchClusterHealth(String hostname) throws UnknownHostException {
		this(ElasticsearchUtil.createTransportClient(hostname, ElasticsearchIndex.DEFAULTPORT));
	}

	public ElasticsearchClusterHealth(String hostname, int port) throws UnknownHostException {
		this(ElasticsearchUtil.createTransportClient(hostname, port));
	}

	public ElasticsearchClusterHealth(Client client) {
		this.client = client;
		matrix = this;
		timer = UJMPTimer.newInstance(this.getClass().getSimpleName());
		timer.schedule(task, 0, 10000);
	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get();
			matrix.put("ActivePrimaryShards", healths.getActivePrimaryShards());
			matrix.put("ActiveShards", healths.getActiveShards());
			matrix.put("ActiveShardsPercent", healths.getActiveShardsPercent());
			matrix.put("ClusterName", healths.getClusterName());
			matrix.put("Label", healths.getClusterName());
			matrix.put("DelayedUnassignedShards", healths.getDelayedUnassignedShards());
			matrix.put("InitializingShards", healths.getInitializingShards());
			matrix.put("NumberOfDataNodes", healths.getNumberOfDataNodes());
			matrix.put("NumberOfInFlightFetch", healths.getNumberOfInFlightFetch());
			matrix.put("NumberOfNodes", healths.getNumberOfNodes());
			matrix.put("NumberOfPendingTasks", healths.getNumberOfPendingTasks());
			matrix.put("RelocatingShards", healths.getRelocatingShards());
			matrix.put("Status", healths.getStatus());
		}
	};

}
