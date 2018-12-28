package com.operationalsystems.issuelog.configuration

import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import java.net.InetAddress

/**
 * Spring boot configuration for elasticsearch.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = ["com.operationalsystems.issuelog.repository"])
class ElasticsearchConfiguration @Autowired(required = false) constructor(@Value("\${elasticsearch.host}") val host : String = "localhost",
                                             @Value( "\${elasticsearch.port}") val port : Int = 9300,
                                             @Value("\${elasticsearch.threads}") val threads : Int = 10) {
  @Bean
  fun configureClient() : Client {
    return PreBuiltTransportClient(Settings.builder()
        .put("client.transport.ignore_cluster_name", true)
        .put("transport.netty.worker_count", this.threads)
        .build())
        .addTransportAddress(TransportAddress(InetAddress.getByName(this.host), this.port))
  }
}