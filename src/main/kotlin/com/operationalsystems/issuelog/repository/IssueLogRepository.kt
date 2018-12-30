package com.operationalsystems.issuelog.repository

import com.operationalsystems.issuelog.model.IssueLogEntry
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

const val FIND_BY_CREATOR = "{\"bool\" : {\"must\" : {\"match\" : {\"createdBy\" : \"?0\"}}}}"
const val FIND_BY_KEYWORD = "{ \"multi_match\" : { \"query\" : \"?0\", \"fields\" : [\"issue\", \"analysis\"]}}"
const val FIND_BY_DATE_RANGE = "{ \"range\" : { \"createdDate\" : { \"gte\": \"?0\", \"lt\": \"?1\"}}}"

/**
 * Spring data repository for IssueLogEntry.
 */
interface IssueLogRepository : ElasticsearchRepository<IssueLogEntry, String> {
  @Query(FIND_BY_CREATOR)
  fun findByCreator(creator: String, pageable: Pageable) : Page<IssueLogEntry>

  @Query(FIND_BY_KEYWORD)
  fun findByKeyword(keyword: String, pageable: Pageable) : Page<IssueLogEntry>

  @Query(FIND_BY_DATE_RANGE)
  fun findByDateRange(startDateFormat: String, endDateFormat: String, pageable: Pageable) : Page<IssueLogEntry>
}