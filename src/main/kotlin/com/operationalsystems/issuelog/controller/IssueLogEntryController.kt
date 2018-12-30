package com.operationalsystems.issuelog.controller

import com.operationalsystems.issuelog.exception.BadRequestException
import com.operationalsystems.issuelog.exception.NotFoundException
import com.operationalsystems.issuelog.model.IssueLogEntry
import com.operationalsystems.issuelog.service.IssueLogService
import com.operationalsystems.issuelog.util.DateSerializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * REST controller class for issue log entries
 */
@RestController
@RequestMapping("/services/issueLogEntry")
class IssueLogEntryController @Autowired constructor(val issueLogService: IssueLogService) {
  private val logger = LoggerFactory.getLogger(IssueLogEntryController::class.java)

  @PostMapping
  fun createIssueLogEntry(@RequestBody entry: IssueLogEntry) : IssueLogEntry {
    return this.issueLogService.save(entry)
  }

  @PutMapping("/{adminBatchId}")
  fun updateIssueLogEntry(@PathVariable adminBatchId: String,
                                 @RequestBody entry: IssueLogEntry) : IssueLogEntry {
    return this.issueLogService.save(entry)
  }

  @GetMapping
  fun getAllIssueLogEntries(@RequestParam("page", defaultValue = "0") pageNumber: Int,
                                   @RequestParam("size", defaultValue="10") pageSize: Int) : Page<IssueLogEntry> {
    val pageable = PageRequest.of(pageNumber, pageSize)
    return this.issueLogService.findAll(pageable)
  }

  @GetMapping("/{entryId}")
  fun getIssueLogEntry(@PathVariable entryId: String) : IssueLogEntry {
    val foundEntry = this.issueLogService.findById(entryId) ?: throw NotFoundException("Not found ${entryId}")

    return foundEntry
  }

  @GetMapping("/query")
  fun queryLogEntries(@RequestParam("keyword") keyword: String?,
                      @RequestParam("createdBy") creator: String?,
                      @RequestParam("fromDate") fromDate: String?,
                      @RequestParam("toDate") toDate: String?,
                      @RequestParam("page", defaultValue = "0") pageNumber: Int,
                      @RequestParam("size", defaultValue = "10") pageSize: Int) : Page<IssueLogEntry> {
    // check consistency, only certain combinations
    if (keyword != null) {
      if (creator != null || fromDate != null || toDate != null) {
        throw BadRequestException("Invalid query parameter combination")
      }
      return this.issueLogService.findByKeyword(keyword, PageRequest.of(pageNumber, pageSize))
    } else if (creator != null) {
      if (fromDate != null || toDate != null) {
        throw BadRequestException("Invalid query parameter combination")
      }
      return this.issueLogService.findByCreator(creator, PageRequest.of(pageNumber, pageSize))
    } else if (fromDate != null) {
      val fromDateObj = DateSerializer.deserializeRaw(fromDate)
      val toDateObj = if (toDate != null) DateSerializer.deserializeRaw(toDate) else Date()
      return this.issueLogService.findByDateRange(fromDateObj, toDateObj, PageRequest.of(pageNumber, pageSize))
    } else {
      throw BadRequestException("Query without parameters")
    }
  }
}