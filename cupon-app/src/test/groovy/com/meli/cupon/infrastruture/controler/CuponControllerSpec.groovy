package com.meli.cupon.infrastruture.controler

import com.meli.cupon.application.api.CuponService
import com.meli.cupon.domain.excepcion.MontoDelCuponInsuficienteException
import com.meli.cupon.domain.model.entities.Cupon
import com.meli.cupon.domain.model.entities.IdItem
import com.meli.cupon.domain.model.entities.ListaDeCompraSugerida
import com.meli.cupon.testutils.ResourceFileReader
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import reactor.core.publisher.Mono
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.core.Is.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc
@ContextConfiguration(classes = [CuponController])
class CuponControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    CuponService cuponService = Stub()

    def "Respuesta 200 OK es retornada cuando el servicio del cupon retorna una lista de compras sugerida"() {
        given: "una lista de productos favoritos y un cupon"
        def listaDeItemsFavoritos = [new IdItem("MLA1"), new IdItem("MLA2"),
                                     new IdItem("MLA3"), new IdItem("MLA4"), new IdItem("MLA5")]
        def cupon = new Cupon(BigDecimal.valueOf(500))

        and: "Lista de compra sugerida"
        def itemsSugeridos = [new IdItem("MLA1"), new IdItem("MLA2"), new IdItem("MLA3")]
        def listaDeCompraSugerida = new ListaDeCompraSugerida(itemsSugeridos, BigDecimal.valueOf(480.245))

        cuponService.obtenerListaDeCompraSugerida(listaDeItemsFavoritos, cupon) >> Mono.just(listaDeCompraSugerida)

        def body = ResourceFileReader.getFileContentAsString("__files/postCouponEndpoint/postCouponRequestBody.json")

        expect:
        def resultado = mockMvc.perform(post("/coupon")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(request().asyncStarted())
                .andReturn()

        mockMvc.perform(asyncDispatch(resultado))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath('$.item_ids').isArray())
                .andExpect(MockMvcResultMatchers.jsonPath('$.item_ids', hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath('$.item_ids[0]', is("MLA1")))
                .andExpect(MockMvcResultMatchers.jsonPath('$.item_ids[1]', is("MLA2")))
                .andExpect(MockMvcResultMatchers.jsonPath('$.item_ids[2]', is("MLA3")))
//                .andExpect(MockMvcResultMatchers.jsonPath('$.total', is(480.25)))
    }

    def "Respuesta 204 NO CONTENT es retornada cuando el servicio del cupon lanza la excepcion MontoDelCuponInsuficienteException"() {
        given: "El servicio del cupon retorna MontoDelCuponInsuficienteException"
        cuponService.obtenerListaDeCompraSugerida(_ as List<IdItem>, _ as Cupon) >> Mono.error(new MontoDelCuponInsuficienteException())

        def body = ResourceFileReader.getFileContentAsString("__files/postCouponEndpoint/postCouponRequestBody.json")

        expect:
        def resultado = mockMvc.perform(post("/coupon")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(request().asyncStarted())
                .andReturn()

        mockMvc.perform(asyncDispatch(resultado))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent())
    }

    def "Respuesta 204 NO CONTENT es retornada cuando el servicio del cupon lanza una excepcion"() {
        given: "El servicio del cupon retorna MontoDelCuponInsuficienteException"
        cuponService.obtenerListaDeCompraSugerida(_ as List<IdItem>, _ as Cupon) >> Mono.error(new RuntimeException())

        def body = ResourceFileReader.getFileContentAsString("__files/postCouponEndpoint/postCouponRequestBody.json")

        expect:
        def resultado = mockMvc.perform(post("/coupon")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(request().asyncStarted())
                .andReturn()

        mockMvc.perform(asyncDispatch(resultado))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isInternalServerError())
    }
}
