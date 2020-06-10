package io.github.bhuwanupadhyay.rtms.inventory

import io.github.bhuwanupadhyay.rtms.inventory.domain.model.valueobjects.Actions
import io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto.CreateAppResource
import io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto.ReleaseVersionResource
import io.github.bhuwanupadhyay.rtms.inventory.interfaces.rest.dto.WorkflowResource
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer.Alphanumeric
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.data.repository.CrudRepository
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec
import java.util.function.Consumer

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(Alphanumeric::class)
@ActiveProfiles("test")
internal class RtmsAppIntegrationTest {
    @LocalServerPort
    private val port = 0
    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var repositories: List<CrudRepository<*, *>>

    @BeforeEach
    fun setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
    }

    @AfterEach
    fun tearDown() {
        repositories.forEach(Consumer { obj: CrudRepository<*, *> -> obj.deleteAll() })
    }

    @Test
    fun `can start application successfully`() {
    }

    @Test
    fun `return 201 if app created successfully`() {
        val body = create()
        body.jsonPath("$.appId").value { appId: String ->
            body
                    .jsonPath("$.appId").isNotEmpty
                    .jsonPath("$._links[0].rel").isEqualTo("get")
                    .jsonPath("$._links[0].method").isEqualTo("GET")
                    .jsonPath("$._links[0].path").isEqualTo("/inventories/$appId")
        }
    }

    @Test
    fun `return 200 if app get by id successfully`() {
        val body = create()
        body.jsonPath("$._links[0].path").value { link: String ->
            client
                    .get()
                    .uri(link).exchange().expectStatus().isOk.expectBody()
                    .jsonPath("$.appId").isNotEmpty
                    .jsonPath("$._links[0].rel").isEqualTo(Actions.REPAIR)
                    .jsonPath("$._links[0].method").isEqualTo("PUT")
                    .jsonPath("$._links[0].path").isEqualTo("${link}/${Actions.REPAIR}")
                    .jsonPath("$._links[1].rel").isEqualTo(Actions.SUBMIT)
                    .jsonPath("$._links[1].method").isEqualTo("PUT")
                    .jsonPath("$._links[1].path").isEqualTo("${link}/${Actions.SUBMIT}")
        }
    }

    @Test
    fun `return 200 if repair workflow completed successfully`() {
        val body = create()
        body.jsonPath("$._links[0].path").value<String> { link ->
            client
                    .get()
                    .uri(link).exchange().expectStatus().isOk.expectBody()
                    .jsonPath("$._links[0].path")
                    .value<String> {
                        client.put().uri(it)
                                .bodyValue(
                                        WorkflowResource.builder()
                                                .action(Actions.REPAIR)
                                                .comment("${Actions.REPAIR} comment")
                                                .payloadJson("{}")
                                                .build())
                                .exchange()
                                .expectStatus().isOk
                                .expectBody()
                                .jsonPath("$._links[0].path").value<String> {
                                    client.get()
                                            .uri(it).exchange().expectStatus().isOk.expectBody()
                                            .jsonPath("$.appId").isNotEmpty
                                            .jsonPath("$._links[0].rel").isEqualTo(Actions.SUBMIT)
                                            .jsonPath("$._links[0].method").isEqualTo("PUT")
                                            .jsonPath("$._links[0].path").isEqualTo("${link}/${Actions.SUBMIT}")
                                            .jsonPath("$._links[1].rel").isEqualTo(Actions.BACK_REPAIR)
                                            .jsonPath("$._links[1].method").isEqualTo("PUT")
                                            .jsonPath("$._links[1].path").isEqualTo("${link}/${Actions.BACK_REPAIR}")
                                }

                    }
        }
    }

    @Test
    fun `return 200 if submit workflow completed successfully`() {
        val body = create()
        body.jsonPath("$._links[0].path").value<String> { link ->
            client
                    .get()
                    .uri(link).exchange().expectStatus().isOk.expectBody()
                    .jsonPath("$._links[1].path")
                    .value<String> {
                        client.put().uri(it)
                                .bodyValue(
                                        WorkflowResource.builder()
                                                .action(Actions.SUBMIT)
                                                .comment("${Actions.SUBMIT} comment")
                                                .build())
                                .exchange()
                                .expectStatus().isOk
                                .expectBody()
                                .jsonPath("$._links[0].path").value<String> {
                                    client.get()
                                            .uri(it).exchange().expectStatus().isOk.expectBody()
                                            .jsonPath("$.appId").isNotEmpty
                                            .jsonPath("$._links[0].rel").isEqualTo(Actions.APPROVE)
                                            .jsonPath("$._links[0].method").isEqualTo("PUT")
                                            .jsonPath("$._links[0].path").isEqualTo("${link}/${Actions.APPROVE}")
                                            .jsonPath("$._links[1].rel").isEqualTo(Actions.BACK_REPAIR)
                                            .jsonPath("$._links[1].method").isEqualTo("PUT")
                                            .jsonPath("$._links[1].path").isEqualTo("${link}/${Actions.BACK_REPAIR}")
                                }

                    }
        }
    }

    @Test
    fun `return 200 if list by default pagination successfully`() {
        `return 201 if app created successfully`()
        `return 201 if app created successfully`()

        client
                .get()
                .uri("/inventories?number={number}&size={size}", -1, 0).exchange().expectStatus().isOk.expectBody()
                .jsonPath("$.number").isEqualTo(0)
                .jsonPath("$.size").isEqualTo(20)
                .jsonPath("$.totalElements").isEqualTo(2)
                .jsonPath("$.content.size()").isEqualTo(2)
    }

    @Test
    fun `return 200 if paginate by page number and successfully`() {
        // page - 0
        `return 201 if app created successfully`()
        `return 201 if app created successfully`()
        // page - 1
        `return 201 if app created successfully`()
        `return 201 if app created successfully`()
        // page - 2
        `return 201 if app created successfully`()
        `return 201 if app created successfully`()
        // page - 3
        `return 201 if app created successfully`()
        val pageNumber = 3
        val pageSize = 2
        val totalElements = 7
        val contentSize = 1
        client
                .get()
                .uri("/inventories?number={number}&size={size}", pageNumber, pageSize).exchange().expectStatus().isOk.expectBody()
                .jsonPath("$.number").isEqualTo(pageNumber)
                .jsonPath("$.size").isEqualTo(pageSize)
                .jsonPath("$.totalElements").isEqualTo(totalElements)
                .jsonPath("$.content.size()").isEqualTo(contentSize)
    }

    private fun create(): BodyContentSpec {
        return client
                .post()
                .uri("/inventories")
                .bodyValue(
                        CreateAppResource.builder()
                                .name("name")
                                .releaseVersion(
                                        ReleaseVersionResource.builder()
                                                .releaseId("releaseId")
                                                .date("2020-06-03T00:25:23.296286")
                                                .build())
                                .build())
                .exchange()
                .expectStatus()
                .isCreated
                .expectBody()
    }
}
