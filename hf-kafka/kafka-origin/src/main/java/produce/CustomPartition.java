package produce;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义分区器
 *
 * @author xiehongfei
 * @description
 * @date 2023/3/2 22:06
 */
public class CustomPartition implements Partitioner {
    /**
     * keyBytes 序列化之后的key
     * valueBytes 序列化之后的value
     *
     * @param topic
     * @param key
     * @param keyBytes
     * @param value
     * @param valueBytes
     * @param cluster
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (value.toString().contains("2")) {
            return 0;
        } else if (value.toString().contains("4")) {
            return 1;
        }
        return 2;
    }

    @Override
    public void close() {

    }

    @Override
    public void onNewBatch(String topic, Cluster cluster, int prevPartition) {
        Partitioner.super.onNewBatch(topic, cluster, prevPartition);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
