Feature: Testing NodeController endpoints

  Background:
    * url 'http://localhost:8081'

  Scenario: Test getting all nodes
    Given path '/kpi'
    When method GET
    Then status 200

  Scenario: Test getting a node by ID
    Given path '/kpi/1'
    When method GET
    Then status 200

  Scenario: Test adding or updating a node
    Given path '/kpi/add'
    And request { node_id: 1, latency: 50, error_rate: 0.2, throughput: 100 }
    When method POST
    Then status 200

  Scenario: Test deleting a node
    Given path '/kpi/remove/1'
    When method DELETE
    Then status 200

  Scenario: Test getting nodes within a time range
    Given path '/kpi/time_range'
    And param start = '2024-05-01T00:00:00'
    And param end = '2024-05-31T23:59:59'
    When method GET
    Then status 200

  Scenario: Test getting nodes within a latency range
    Given path '/kpi/latency_range'
    And param low = 0
    And param high = 100
    When method GET
    Then status 200

  Scenario: Test getting nodes within an error rate range
    Given path '/kpi/error_range'
    And param low = 0
    And param high = 0.5
    When method GET
    Then status 200

  Scenario: Test getting nodes within a throughput range
    Given path '/kpi/throughput_range'
    And param low = 0
    And param high = 200
    When method GET
    Then status 200

  Scenario: Test getting latency over time
    Given path '/kpi/latency_over_time'
    When method GET
    Then status 200

  Scenario: Test getting error rate over time
    Given path '/kpi/error_rate_over_time'
    When method GET
    Then status 200

  Scenario: Test getting throughput over time
    Given path '/kpi/throughput_over_time'
    When method GET
    Then status 200

  Scenario: Test getting latency, error rate, and throughput
    Given path '/kpi/latency_error_rate_throughput'
    When method GET
    Then status 200

  Scenario: Test getting error rate vs throughput
    Given path '/kpi/error_vs_throughput'
    When method GET
    Then status 200

  Scenario: Test getting latency vs throughput
    Given path '/kpi/latency_vs_throughput'
    When method GET
    Then status 200

  Scenario: Test getting top ten nodes by latency
    Given path '/kpi/top_ten_latency'
    When method GET
    Then status 200

  Scenario: Test getting average latency per network
    Given path '/kpi/avg_latency_per_network'
    When method GET
    Then status 200

  Scenario: Test getting average error rate per network
    Given path '/kpi/avg_error_rate_per_network'
    When method GET
    Then status 200

  Scenario: Test getting average throughput per network
    Given path '/kpi/avg_throughput_per_network'
    When method GET
    Then status 200

  Scenario: Test getting average metrics
    Given path '/kpi/average_metrics'
    When method GET
    Then status 200

  Scenario: Test getting node with highest metrics
    Given path '/kpi/node_highest_metrics'
    When method GET
    Then status 200
