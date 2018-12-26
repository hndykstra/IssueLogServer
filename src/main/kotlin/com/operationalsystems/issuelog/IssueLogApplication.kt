package com.operationalsystems.issuelog

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class IssueLogApplication {
  companion object {
    @JvmStatic fun main(args: Array<String>) {
      SpringApplication.run(IssueLogApplication::class.java, *args)
    }
  }
}
