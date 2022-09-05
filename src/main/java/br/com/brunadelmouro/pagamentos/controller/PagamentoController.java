package br.com.brunadelmouro.pagamentos.controller;

import br.com.brunadelmouro.pagamentos.dto.PagamentoDto;
import br.com.brunadelmouro.pagamentos.service.PagamentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    public Page<PagamentoDto> findAllPayments(@PageableDefault(size = 10) Pageable pageable) {
        return pagamentoService.findAllPayments(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> findPaymentById(@PathVariable @NotNull Long id) {
        PagamentoDto dto = pagamentoService.findPaymentById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> createPayment(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder) {
        PagamentoDto pagamento = pagamentoService.createPayment(dto);
        URI address = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(address).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> updatePayment(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto dto) {
        PagamentoDto updatedPayment = pagamentoService.updatePayment(id, dto);

        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> deletePayment(@PathVariable @NotNull Long id) {
        pagamentoService.deletePayment(id);

        return ResponseEntity.noContent().build();
    }
}
