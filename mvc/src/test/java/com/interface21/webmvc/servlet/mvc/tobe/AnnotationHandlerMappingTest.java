package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnnotationHandlerMappingTest {

    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setUp() {
        handlerMapping = new AnnotationHandlerMapping("samples");
        handlerMapping.initialize();
    }

    @Test
    void get() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    void post() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/post-test");
        when(request.getMethod()).thenReturn("POST");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    void duplicateHandlerKeyTest() {
        final AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping("com.interface21.webmvc.servlet.mvc.tobe");

        assertThatThrownBy(handlerMapping::initialize).isInstanceOf(IllegalStateException.class)
                .hasMessage("Duplicate handler key: HandlerKey{url='/get-test', requestMethod=GET}");
    }

    @Controller
    static class TestController {

        @RequestMapping(value = "/get-test", method = RequestMethod.GET)
        void test1() {
        }

        @RequestMapping(value = "/get-test", method = RequestMethod.GET)
        void test2() {
        }
    }
}
