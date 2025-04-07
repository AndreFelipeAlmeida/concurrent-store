package ufcg.pc.concurrent_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * Define o formato padrão de definição do Erro que será
     * retornado pela API nesta Aplicação Web
     * @param message - Mensagem de erro
     * @return CustomErrorType - Tipo de erro personalizado
     */
    private CustomErrorType defaultCustomErrorTypeConstruct(String message) {
        return CustomErrorType.builder()
                .message(message)
                .build();
    }

    /**
     * Define o "manuseador" para quando, de qualquer parte da
     * Aplicação Web, uma exceção do tipo Concurrent Exception
     * for lançada
     * @param exception - Exceção Padrão para problemas de concorrência
     * @return CustomErrorType - Tipo de Erro Personalizado
     */
    @ExceptionHandler(ConcurrentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomErrorType onConcurrentException(ConcurrentException exception) {
        return defaultCustomErrorTypeConstruct(
                exception.getMessage()
        );
    }

    /**
     * Define o "manuseador" para quando, de qualquer parte da
     * Aplicação Web, uma exceção do tipo Product Not Found Exception
     * for lançada
     * @param exception - Exceção Padrão para falta de produtos
     * @return CustomErrorType - Tipo de Erro Personalizado
     */
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomErrorType onProductNotFoundException(ProductNotFoundException exception) {
        return defaultCustomErrorTypeConstruct(
                exception.getMessage()
        );
    }

    /**
     * Define o "manuseador" para quando, de qualquer parte da
     * Aplicação Web, uma exceção do tipo Product Not Found Exception
     * for lançada
     * @param exception - Exceção Padrão para produto existente
     * @return CustomErrorType - Tipo de Erro Personalizado
     */
    @ExceptionHandler(ProductConflictExpetion.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public CustomErrorType onProductConflitException(ProductConflictExpetion exception) {
        return defaultCustomErrorTypeConstruct(
                exception.getMessage()
        );
    }

    /**
     * Define o "manuseador" para quando, de qualquer parte da
     * Aplicação Web, uma exceção do tipo Insufficient Stock Exception
     * for lançada
     * @param exception - Exceção Padrão para falta de estoque
     * @return CustomErrorType - Tipo de Erro Personalizado
     */
    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onInsufficientStockException(InsufficientStockException exception) {
        return defaultCustomErrorTypeConstruct(
                exception.getMessage()
        );
    }
}