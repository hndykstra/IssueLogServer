package com.operationalsystems.issuelog.service

import com.operationalsystems.issuelog.model.IssueLogEntry
import com.operationalsystems.issuelog.repository.IssueLogRepository
import com.operationalsystems.issuelog.util.DateSerializer
import kotlinx.serialization.ImplicitReflectionSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

/**
 * Service layer for issue log actions.
 */
@Service
class IssueLogService @Autowired constructor(val elasticsearchTemplate : ElasticsearchTemplate,
                                             val repository : IssueLogRepository){
  fun findAll(pageable: Pageable) : Page<IssueLogEntry> {
    return this.repository.findAll(pageable)
  }

  fun findById(id: String) : IssueLogEntry? {
    return this.repository.findById(id).orElse(null)
  }

  fun findByCreator(creator: String, pageable: Pageable) : Page<IssueLogEntry> {
    return this.repository.findByCreator(creator, pageable)
  }

  fun findByKeyword(keyword: String, pageable: Pageable) : Page<IssueLogEntry> {
    return this.repository.findByKeyword(keyword, pageable)
  }

  fun findByDateRange(startDate: Date, endDate: Date, pageable: Pageable) : Page<IssueLogEntry> {
    val startDateFormat = DateSerializer.serializeRaw(startDate)
    val endDateFormat = DateSerializer.serializeRaw(endDate)
    return this.repository.findByDateRange(startDateFormat, endDateFormat, pageable)
  }

  fun save(entry: IssueLogEntry) : IssueLogEntry {
    val idIn = entry.id
    if (idIn == null || idIn.isEmpty()) {
      entry.id = UUID.randomUUID().toString()
    }

    return this.repository.save(entry)
  }

  @PostConstruct
  private fun postConstruct() {
    this.elasticsearchTemplate.createIndex(IssueLogEntry::class.java)
    this.elasticsearchTemplate.putMapping(IssueLogEntry::class.java)
    this.elasticsearchTemplate.refresh(IssueLogEntry::class.java)
  }
}